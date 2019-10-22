import java.io.*;
import java.util.*;
import scanner.Scanner;

public class WordStatFirstIndex {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Expected 2 names of files, found - less");
            return;
        }

        Map<String, FirstIntList> map = new LinkedHashMap<>();
        int lineCounter = 0;
        try (Scanner input = new Scanner(new File(args[0]), "UTF-8")) {
            while (input.hasNextLine()) {
                lineCounter++;
                int numCounter = 0;
                String line = input.nextLine().toLowerCase();
                Scanner wordScanner = new Scanner(line);
                while (wordScanner.hasNextWord()) {
                    numCounter++;
                    String currentWord = wordScanner.nextWord();
                    map.computeIfAbsent(currentWord, k -> new FirstIntList()).add(lineCounter, numCounter);
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
            for (Map.Entry<String, FirstIntList> current : map.entrySet()) {
                StringBuilder result = new StringBuilder()
                    .append(current.getKey()).append(" ")
                    .append(current.getValue().getCount()).append(" ");
                IntList nums = current.getValue().getNums();
                int length = nums.getSize();
                for (int i = 0; i < length; i++) {
                    result.append(nums.intAt(i));
                    if (i + 1 < length) {
                        result.append(" ");
                    }
                }
                output.write(result.toString());
                output.newLine();
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