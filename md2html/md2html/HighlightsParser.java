package md2html;

import markup.*;

import java.util.*;

public class HighlightsParser {
    private static final Set<Character> SPECIAL_SYMBOLS = Set.of('*', '-', '_', '`', '[', ']');
    private static final Map<String, String> PAIR_OF_TAG = Map.of(
            "*", "*",
            "_", "_",
            "**", "**",
            "__", "__",
            "--", "--",
            "`", "`",
            "[", "]"
    );
    private static final Map<Character, String> SPECIAL_IN_HTML = Map.of(
            '<', "&lt;",
            '>', "&gt;",
            '&', "&amp;"
    );
    private static final Set<String> TAGS = Set.of("*", "_", "**", "__", "--", "`", "[", "]");


    private final List<PartOfHighlight> result;
    private StringBuilder currentText;
    private final Deque<String> stack;
    private final Stack<ContainerOfHighlight> highlightStack;

    private final String text;

    public HighlightsParser(final String text) {
        this.text = text + "\n";
        result = new ArrayList<>();
        currentText = new StringBuilder();
        stack = new ArrayDeque<>();
        highlightStack = new Stack<>();
        stack.push(null);
    }

    private boolean checkIfSingle(int position, int len) {
        if (position > 0 && !Character.isWhitespace(text.charAt(position - 1))) {
            return false;
        }
        if (len == 1) {
            return Character.isWhitespace(text.charAt(position + 1));
        }
        return position + 2 >= text.length() || Character.isWhitespace(text.charAt(position + 2));
    }

    private String getCurrentTag(int position) {
        String currentTag = String.valueOf(text.charAt(position));
        if (TAGS.contains(currentTag + text.charAt(position + 1))) {
            return currentTag + text.charAt(position + 1);
        }

        return TAGS.contains(currentTag) ? currentTag : null;
    }

    private void flushText() {
        if (currentText.length() != 0) {
            if (highlightStack.size() == 0) {
                result.add(new Text(currentText.toString()));
            } else {
                highlightStack.lastElement().add(new Text(currentText.toString()));
            }
        }
        currentText = new StringBuilder();
    }

    private ContainerOfHighlight newContainer(String tag) {
        switch (tag) {
            case "*":
            case "_":
                return new Emphasis(new ArrayList<>());
            case "**":
            case "__":
                return new Strong(new ArrayList<>());
            case "`":
                return new Code(new ArrayList<>());
            case "--":
                return new Strikeout(new ArrayList<>());
            case "[":
                return new Link(new ArrayList<>());
            default:
                return null;
        }
    }

    public List<PartOfHighlight> parse() {
        for (int position = 0; position < text.length() - 1; position++) {
            char currentSymbol = text.charAt(position);
            char nextSymbol = text.charAt(position + 1);

            if (currentSymbol == '\\' && SPECIAL_SYMBOLS.contains(nextSymbol)) {
                currentText.append(nextSymbol);
                position++;
            } else if (SPECIAL_IN_HTML.containsKey(currentSymbol)) {
                currentText.append(SPECIAL_IN_HTML.get(currentSymbol));
            } else if (!SPECIAL_SYMBOLS.contains(currentSymbol)) {
                currentText.append(currentSymbol);
            } else {

                String currentTag = getCurrentTag(position);
                if (currentTag == null) {
                    currentText.append(currentSymbol);
                } else if (checkIfSingle(position, currentTag.length())) {
                    currentText.append(currentTag);
                    position += currentTag.length() - 1;
                } else {
                    flushText();
                    // check if there is an end of last tag
                    if (stack.getLast() != null && currentTag.equals(PAIR_OF_TAG.get(stack.getLast()))) {
                        ContainerOfHighlight last = highlightStack.pop();
                        stack.pop();
                        if (currentTag.equals("]")) {
                            position += 2;
                            StringBuilder address = new StringBuilder();
                            while (text.charAt(position) != ')') {
                                address.append(text.charAt(position));
                                position++;
                            }
                            ((Link) last).setAddress(address.toString());
                        }
                        if (highlightStack.size() == 0) {
                            result.add(last);
                        } else {
                            highlightStack.lastElement().add(last);
                        }
                    } else {
                        //now current tag is the new tag in stack
                        highlightStack.push(newContainer(currentTag));
                        stack.push(currentTag);
                    }
                    position += (currentTag.length() - 1);
                }
            }
        }
        flushText();
        return result;
    }
}
