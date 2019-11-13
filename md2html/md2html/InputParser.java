package md2html;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringJoiner;

public class InputParser {
    private BufferedReader input;
    private StringJoiner inputJoiner;
    private StringBuilder outputBuilder;

    public InputParser(BufferedReader input){
        this.input = input;
        inputJoiner = new StringJoiner("\n");
        outputBuilder = new StringBuilder();
    }

    private void processParagraph(){
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
            }
            else {
                inputJoiner.add(current);
            }
        }
        if (inputJoiner.length() != 0){
            processParagraph();
        }
        return outputBuilder.toString();
    }
}
