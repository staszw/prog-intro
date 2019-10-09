import java.io.*;
import java.nio.file.FileSystemException;
import java.util.LinkedHashMap;
import java.util.Map;

public class WordStatInput {
    public static boolean isWord(char x) {
        if (Character.getType(x) == Character.LOWERCASE_LETTER)
            return true;
        if (Character.getType(x) == Character.DASH_PUNCTUATION)
            return true;
        return x == '\'';
    }

    public static void main(String[] args) {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();

        BufferedReader input = null;
        try {
            input = new BufferedReader(new InputStreamReader(new FileInputStream(new File(args[0])), "UTF-8"));
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound");
        } catch (UnsupportedEncodingException e) {
            System.out.println("UnsupportedEncoding");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("WrongNameOfFile");
        }
        if (input != null) {
            try {
                String line = null;
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
                input.close();
            } catch (FileSystemException e) {
                System.out.println("FileSystemException");
            } catch (IOException e) {
                System.out.println("IOException");
            }
        }
        try {
            PrintWriter output = new PrintWriter(new File(args[1]), "UTF-8");
            for (Map.Entry<String, Integer> currentWord : map.entrySet()) {
                output.println(currentWord.getKey() + " " + currentWord.getValue());
            }
            output.close();
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFound");
        } catch (UnsupportedEncodingException e) {
            System.out.println("UnsupportedEncoding");
        }
    }
}
