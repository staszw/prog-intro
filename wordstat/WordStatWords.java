import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class WordStatWords {
    public static boolean isWord(char x) {
        return x == '\''
                || Character.getType(x) == Character.LOWERCASE_LETTER
                || Character.getType(x) == Character.DASH_PUNCTUATION;
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Expected 2 names of files, found - less");
            return;
        }

        Map<String, Integer> map = new HashMap<>();

        try (BufferedReader input = new BufferedReader(new InputStreamReader(
                new FileInputStream(new File(args[0])), "UTF-8"))) {
            String line;
            while ((line = input.readLine()) != null) {
                line = line.toLowerCase();
                int len = 0;
                for (int i = 0; i < line.length(); i++) {
                    if (isWord(line.charAt(i))) {
                        len++;
                    }
                    if ((i + 1 == line.length() || !isWord(line.charAt(i + 1))) && len > 0) {
                        String currentWord = line.substring(i - len + 1, i + 1);
                        map.merge(currentWord, 1, Integer::sum);
                        len = 0;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Input File doesn't exist " + e.getMessage());
            return;
        } catch (UnsupportedEncodingException e) {
            System.out.println("Unsupported Encoding for Input file " + e.getMessage());
            return;
        } catch (IOException e) {
            System.out.println("Error reading input file: " + e.getMessage());
            return;
        }

        try (BufferedWriter output = new BufferedWriter(
            new OutputStreamWriter(new FileOutputStream(args[1], "utf8")))) {
            String[] words = map.keySet().toArray(new String[0]);
            Arrays.sort(words);
            for (String currentWord : words) {
                output.write(currentWord + " " + map.get(currentWord) + '\n');
            }
        } catch (FileNotFoundException e) {
            System.out.println("Output File doesn't exist or we can't write to it " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            System.out.println("Unsupported Encoding for output file " + e.getMessage());
        }
    }
}