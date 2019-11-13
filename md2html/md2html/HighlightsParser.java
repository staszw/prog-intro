package md2html;

import markup.*;

import java.util.*;

public class HighlightsParser {
    private static final Set<Character> SPECIAL_SYMBOLS;
    private static final Map<String, String> PAIR_OF_TAG;
    private static final Map<Character, String> SPECIAL_IN_HTML;
    private static final Set<String> TAGS;

    static {
        SPECIAL_SYMBOLS = Set.of('*', '-', '_', '`', '[', ']');
        PAIR_OF_TAG = Map.of(
                "*", "*",
                "_", "_",
                "**", "**",
                "__", "__",
                "--", "--",
                "`", "`",
                "[", "]"
        );
        SPECIAL_IN_HTML = Map.of('<', "&lt;", '>', "&gt;", '&', "&amp;");
        TAGS = Set.of("*", "_", "**", "__", "--", "`", "[", "]");
    }

    private List<PartOfHighlight> result;
    private StringBuilder currentText;
    private Stack<String> stack;
    private Stack<ContainerOfHighlight> highlightStack;

    private String text;

    public HighlightsParser(String text){
        this.text = text + "\0";
        result = new ArrayList<>();
        currentText = new StringBuilder();
        stack = new Stack<>();
        highlightStack = new Stack<>();
        stack.push(null);
    }

    private boolean checkIfSingle(int i, int len) {
        if (i > 0 && !Character.isWhitespace(text.charAt(i - 1))) {
            return false;
        }
        if (len == 1) {
            return Character.isWhitespace(text.charAt(i + 1));
        }
        return i + 2 >= text.length() || Character.isWhitespace(text.charAt(i + 2));
    }

    private boolean checkIfShielded(int i) {
        return text.charAt(i) == '\\' && SPECIAL_SYMBOLS.contains(text.charAt(i + 1));
    }

    private boolean checkIfSpecialInHtml(int i) {
        return SPECIAL_IN_HTML.containsKey(text.charAt(i));
    }

    private boolean checkIfRegularSymbol(int i) {
        return !SPECIAL_SYMBOLS.contains(text.charAt(i));
    }

    private String getCurrentTag(int i) {
        String currentTag = String.valueOf(text.charAt(i));
        if (TAGS.contains(currentTag + text.charAt(i + 1))) {
            currentTag += text.charAt(i + 1);
        }

        if (!TAGS.contains(currentTag)) {
            return null;
        }
        return currentTag;
    }

    private void addToText(String newElement) {
        currentText.append(newElement);
    }

    private void addToText(char newElement) {
        currentText.append(newElement);
    }

    private Text getText(){
        Text result = new Text(currentText.toString());
        currentText = new StringBuilder();
        return result;
    }

    private void flushText(){
        if (currentText.length() != 0) {
            if (highlightStack.size() == 0) {
                result.add(getText());
            } else {
                highlightStack.lastElement().add(getText());
            }
        }
    }

    private String getAddress(int i){
        i += 2;
        StringBuilder address = new StringBuilder();
        while (text.charAt(i) != ')') {
            address.append(text.charAt(i));
            i++;
        }
        return address.toString();
    }

    private ContainerOfHighlight newContainer(String tag){
        List<PartOfHighlight> insideToken = new ArrayList<>();
        switch (tag) {
            case "*":
            case "_":
                return new Emphasis(insideToken);
            case "**":
            case "__":
                return new Strong(insideToken);
            case "`":
                return new Code(insideToken);
            case "--":
                return new Strikeout(insideToken);
            case "[":
                return new Link(insideToken);
            default:
                return null;
        }
    }

    private boolean checkIfPairOfTags(String tagA, String tagB){
        return tagA != null && tagB.equals(PAIR_OF_TAG.get(tagA));
    }

    private void addToPrevious(ContainerOfHighlight container){
        if (highlightStack.size() == 0) {
            result.add(container);
        } else {
            highlightStack.lastElement().add(container);
        }
    }

    public List<PartOfHighlight> parse() {
        for (int i = 0; i < text.length() - 1; i++) {
            char currentSymbol = text.charAt(i);
            char nextSymbol = text.charAt(i + 1);

            if (checkIfShielded(i)) {
                addToText(nextSymbol);
                i++;
                continue;
            }
            if (checkIfSpecialInHtml(i)) {
                addToText(SPECIAL_IN_HTML.get(currentSymbol));
                continue;
            }
            if (checkIfRegularSymbol(i)) {
                addToText(currentSymbol);
                continue;
            }

            String currentTag = getCurrentTag(i);
            if (currentTag == null) {
                addToText(currentSymbol);
                continue;
            }
            if (checkIfSingle(i, currentTag.length())) {
                addToText(currentTag);
                i += currentTag.length() - 1;
                continue;
            }

            flushText();

            // check if there is an end of last tag
            if (checkIfPairOfTags(stack.lastElement(), currentTag)) {
                ContainerOfHighlight last = highlightStack.pop();
                stack.pop();
                if (currentTag.equals("]")) {
                    String address = getAddress(i);
                    ((Link) last).setAddress(address);
                    i += address.length() + 2;
                }
                addToPrevious(last);
            } else {
                //now current tag is the new tag in stack
                highlightStack.push(newContainer(currentTag));
                stack.push(currentTag);
            }
            i += (currentTag.length() - 1);
        }
        flushText();
        return result;
    }
}
