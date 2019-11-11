package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import markup.*;

public class Md2Html {
    private static BufferedReader input;
    private static BufferedWriter output;

    private static final Set<Character> SpecialSymbols = Set.of('*', '-', '_', '`');
    public static final Map<Character, String> SpecialInHtml = Map.of
            ('<', "&lt;", '>', "&gt;", '&', "&amp;");
    private static final Set<String> Tags = Set.of("*", "_", "**", "__", "--", "`");

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Expected 2 names of files, found less\n");
            return;
        }
        try {
            input = new BufferedReader(new InputStreamReader(new FileInputStream(new File(args[0])), StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            System.err.println("Not found or can't use input file\n" + e.getMessage());
            return;
        }

        try {
            output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(args[1])), StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            System.err.println("Not found or can't use output file\n" + e.getMessage());
            return;
        }

        String parsed;

        try {
            parsed = parse();
        } catch (IOException e) {
            System.err.println("IOException while read input file\n" + e.getMessage());
            return;
        }

        try {
            output.write(parsed);
        } catch (IOException e) {
            System.err.println("IOException while write to output file\n" + e.getMessage());
            return;
        }

        try {
            input.close();
        } catch (IOException e) {
            System.err.println("IOException while close input file\n" + e.getMessage());
            return;
        }
        try {
            output.close();
        } catch (IOException e) {
            System.err.println("IOException while close output file\n" + e.getMessage());
        }
    }

    private static String parse() throws IOException {
        List<ExternalClass> list = new ArrayList<>();
        StringJoiner inputJoiner = new StringJoiner("\n");
        String current;
        while ((current = input.readLine()) != null) {
            if (current.length() == 0 && inputJoiner.length() != 0) {
                list.add(parseParagraph(inputJoiner.toString()));
                inputJoiner = new StringJoiner("\n");
            }
            if (current.length() != 0) {
                inputJoiner.add(current);
            }
        }
        if (inputJoiner.length() != 0) {
            list.add(parseParagraph(inputJoiner.toString()));
        }
        StringBuilder outputBuilder = new StringBuilder();
        for (ExternalClass outerClass : list) {
            outerClass.toHtml(outputBuilder);
            outputBuilder.append("\n");
        }
        return outputBuilder.toString();
    }

    private static ExternalClass parseParagraph(String text) {
        int level = 0;
        while (text.length() > level && text.charAt(level) == '#') {
            level++;
        }
        if (text.length() == level || text.charAt(level) != ' ') {
            level = 0;
        }
        if (level != 0)
            text = text.substring(level + 1);

        List<PartOfHighlight> list = parseHighlights(text);

        if (level == 0) {
            return new Paragraph(list);
        } else {
            return new Header(list, level);
        }
    }

    private static List<PartOfHighlight> parseHighlights(String text) {
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
            if (!Tags.contains(currentTag)) {
                currentText.append(currentSymbol);
                continue;
            }

            //check if current tag is a single
            if ((i == 0 || Character.isWhitespace(text.charAt(i - 1)))
                    && (currentTag.length() == 1 || i + 2 >= text.length() || Character.isWhitespace(text.charAt(i + 2)))
                    && (currentTag.length() == 2 || Character.isWhitespace(nextSymbol))) {
                currentText.append(currentSymbol);
                continue;
            }

            // check if current tag should be inside of last tag
            if (lastTag != null && !lastTag.equals(currentTag)) {
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

            //now current tag is the closing of last
            PartOfHighlight token = null;
            List<PartOfHighlight> insideToken = parseHighlights(currentText.toString());
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
