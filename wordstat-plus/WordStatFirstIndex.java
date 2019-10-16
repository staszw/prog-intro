import java.io.*;
import java.util.*;

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
                try (Scanner wordScanner = new Scanner(line)) {
                    while (wordScanner.hasNextWord()) {
                        numCounter++;
                        String currentWord = wordScanner.nextWord();
                        map.merge(currentWord, new FirstIntList().add(lineCounter, numCounter), FirstIntList::merge);
                    }
                } catch (IOException e) {
                    System.out.println("Error parsing line" + e.getMessage());
                    return;
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
            for (Map.Entry current : map.entrySet()) {
                output.write(current.getKey() + " ");
                FirstIntList intList = current.getValue();
                
                output.write('\n');
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