package md2html;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringJoiner;

public class InputParser {
    private final BufferedReader input;
    private StringJoiner inputJoiner;
    private final StringBuilder outputBuilder;

    public InputParser(final BufferedReader input) {
        this.input = input;
        inputJoiner = new StringJoiner("\n");
        outputBuilder = new StringBuilder();
    }

    private void processParagraph() {
        if (inputJoiner.length() != 0) {
            ParagraphsParser parser = new ParagraphsParser(inputJoiner.toString());
            parser.parse().toHtml(outputBuilder).append("\n");
            inputJoiner = new StringJoiner("\n");
        }
    }

    public String parse() throws IOException {
        String current;
        while ((current = input.readLine()) != null) {
            if (current.length() == 0) {
                processParagraph();
            } else {
                inputJoiner.add(current);
            }
        }
        processParagraph();
        return outputBuilder.toString();
    }
}
