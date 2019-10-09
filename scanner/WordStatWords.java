import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class WordStatWords {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Expected 2 names of files, found - less");
            return;
        }

        Map<String, Integer> map = new TreeMap<>();

        try (Scanner input = new Scanner(new File(args[0]), "UTF-8")) {
            while (input.hasNextLine()) {
                String line = input.nextLine().toLowerCase();
                Scanner wordScanner = new Scanner(line);
                while (wordScanner.hasNextWord()) {
                    String currentWord = wordScanner.nextWord();
                    map.merge(currentWord, 1, Integer::sum);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Input File doesn't exist " + e.getMessage());
            return;
        } catch (UnsupportedEncodingException e) {
            System.out.println("Unsupported Encoding for Input file " + e.getMessage());
            return;
        } catch (IOException e) {
            System.out.println("Error reading input file " + e.getMessage());
            return;
        }

        try (BufferedWriter output = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(args[1]), "UTF-8"))) {
            for (String currentWord : map.keySet()) {
                output.write(currentWord + " " + map.get(currentWord) + '\n');
            }
        } catch (FileNotFoundException e) {
            System.out.println("Output File doesn't exist or we can't write to it " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            System.out.println("Unsupported Encoding for output file " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error writing to output file " + e.getMessage());
        }
    }
}