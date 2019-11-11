package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import markup.*;

public class Md2Html {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Expected 2 names of files, found less\n");
            return;
        }
        String parsed;
        try (BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(new File(args[0])), StandardCharsets.UTF_8))) {
            parsed = parse(input);
        } catch (FileNotFoundException e) {
            System.err.println("Not found or can't use input file\n" + e.getMessage());
            return;
        } catch (IOException e) {
            System.err.println("IOException while read input file\n" + e.getMessage());
            return;
        }

        try (BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(args[1])), StandardCharsets.UTF_8))) {
            output.write(parsed);
        } catch (FileNotFoundException e) {
            System.err.println("Not found or can't use output file\n" + e.getMessage());
        } catch (IOException e) {
            System.err.println("IOException while write to output file\n" + e.getMessage());
        }
    }

    private static String parse(BufferedReader input) throws IOException {
        List<ExternalClass> list = new ArrayList<>();
        StringJoiner inputJoiner = new StringJoiner("\n");
        while (true) {
            String current = input.readLine();
            if (current == null || (current.length() == 0 && inputJoiner.length() != 0)) {
                list.add(ParagraphsParser.parse(inputJoiner.toString()));
                inputJoiner = new StringJoiner("\n");
            }
            if (current == null){
                break;
            }
            if (current.length() != 0) {
                inputJoiner.add(current);
            }
        }
        StringBuilder outputBuilder = new StringBuilder();
        for (ExternalClass external : list) {
            external.toHtml(outputBuilder);
            outputBuilder.append("\n");
        }
        return outputBuilder.toString();
    }
}
