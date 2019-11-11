package md2html;

import markup.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HighlightsParser {
    private static final Set<Character> SpecialSymbols = Set.of('*', '-', '_', '`');
    private static final Map<Character, String> SpecialInHtml = Map.of('<', "&lt;", '>', "&gt;", '&', "&amp;");
    private static final Set<String> Tags = Set.of("*", "_", "**", "__", "--", "`");

    private static boolean checkIfSingle(String text, int i, int len){
        if (i > 0 && !Character.isWhitespace(text.charAt(i - 1))) {
            return false;
        }
        if (len == 1) {
            return Character.isWhitespace(text.charAt(i + 1));
        }
        return i + 2 >= text.length() || Character.isWhitespace(text.charAt(i + 2));
    }

    public static List<PartOfHighlight> parse(String text) {
        text += "\0";
        List<PartOfHighlight> result = new ArrayList<>();
        StringBuilder currentText = new StringBuilder();
        String lastTag = null;
        for (int i = 0; i < text.length() - 1; i++) {
            char currentSymbol = text.charAt(i);
            char nextSymbol = text.charAt(i + 1);

            // check if current symbol shouldn't be parsed as markdown symbol
            if (lastTag == null) {
                if (currentSymbol == '\\' && SpecialSymbols.contains(nextSymbol)) {
                    currentText.append(nextSymbol);
                    i++;
                    continue;
                }
                if (SpecialInHtml.containsKey(currentSymbol)) {
                    currentText.append(SpecialInHtml.get(currentSymbol));
                    continue;
                }
            }
            if (!SpecialSymbols.contains(currentSymbol)) {
                currentText.append(currentSymbol);
                continue;
            }

            String currentTag = String.valueOf(currentSymbol);
            if (Tags.contains(currentTag + nextSymbol)) {
                currentTag += nextSymbol;
            }

            //check if current tag is a trash
            if (!Tags.contains(currentTag)
                    || checkIfSingle(text, i, currentTag.length())
                    || (lastTag != null && !lastTag.equals(currentTag))) {
                currentText.append(currentSymbol);
                continue;
            }

            // check if here is the start of new tag
            if (lastTag == null) {
                if (currentText.length() != 0) {
                    result.add(new Text(currentText.toString()));
                    currentText = new StringBuilder();
                }
                lastTag = currentTag;
                i += (currentTag.length() - 1);
                continue;
            }

            //now current tag is the close part of last tag
            PartOfHighlight token = null;
            List<PartOfHighlight> insideToken = parse(currentText.toString());
            currentText = new StringBuilder();
            switch (currentTag) {
                case "*":
                case "_":
                    token = new Emphasis(insideToken);
                    break;
                case "**":
                case "__":
                    token = new Strong(insideToken);
                    break;
                case "`":
                    token = new Code(insideToken);
                    break;
                case "--":
                    token = new Strikeout(insideToken);
                    break;
            }
            i += (currentTag.length() - 1);
            lastTag = null;
            result.add(token);
        }
        if (currentText.length() != 0)
            result.add(new Text(currentText.toString()));
        return result;
    }
}
