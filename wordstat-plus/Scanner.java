import java.io.*;
import java.util.Arrays;

public class Scanner implements AutoCloseable {
    private char[] buffer;
    private Reader reader;
    private final int sz = 128;
    private int head;
    private boolean isEndOfInputReached;

    public Scanner(Reader r) {
        buffer = new char[sz * 2];
        head = 0;
        reader = r;
        isEndOfInputReached = false;
    }

    public Scanner(File f, String cs) throws FileNotFoundException, UnsupportedEncodingException {
        this(new InputStreamReader(new FileInputStream(f), cs));
    }

    public Scanner(InputStream f) {
        this(new InputStreamReader(f));
    }

    public Scanner(String s) {
        this(new StringReader(s));
    }

    private void shiftBuffer() {
        System.arraycopy(buffer, sz, buffer, 0, sz);
        Arrays.fill(buffer, sz, sz * 2, (char) 0);
    }

    private boolean readBuffer() throws IOException {
        if (isEndOfInputReached) {
            return false;
        }
        shiftBuffer();
        int flag = reader.read(buffer, sz, sz);
        if (flag != -1) {
            head = 0;
            if (buffer[head] == 0) {
                readBuffer();
            }
            return true;
        }
        isEndOfInputReached = true;
        if (buffer[0] != 0) {
            head = 0;
            return true;
        }
        return false;
    }

    public boolean hasNext() throws IOException {
        return (head < sz && buffer[head] != 0)
                || readBuffer();
    }

    public boolean hasNextLine() throws IOException {
        return hasNext();
    }

    public boolean hasNextToken(IsToken isToken) throws IOException {
        while (hasNext() && !isToken.isToken(charFromFirst(0))) {
            nextChar();
        }
        if (!hasNext()) {
            return false;
        }
        return isToken.isToken(charFromFirst(0));
    }

    public boolean hasNextInt() throws IOException {
        return hasNextToken(new IsInt());
    }

    public boolean hasNextWord() throws IOException {
        return hasNextToken(new IsWord());
    }

    private char charFromFirst(int delta) {
        return buffer[head + delta];
    }

    public char nextChar() throws IOException {
        if (!hasNext()) {
            throw new IOException("Tried to read character, not found any");
        }
        char result = charFromFirst(0);
        head++;
        return result;
    }

    public String nextToken(IsToken isToken) throws IOException {
        if (!hasNextToken(isToken)){
            throw new IOException("Tried to get next token, but not found any tokens of this type");
        }
        StringBuilder result = new StringBuilder();
        char curChar = charFromFirst(0);
        while (hasNext() && !isToken.isToken(curChar)) {
            nextChar();
            curChar = charFromFirst(0);
        }
        while (isToken.isToken(curChar)) {
            result.append(curChar);
            nextChar();
            if (hasNext()) {
                curChar = charFromFirst(0);
            } else {
                break;
            }
        }
        return result.toString();
    }

    public int nextInt() throws IOException {
        return Integer.parseInt(nextToken(new IsInt()));
    }

    public String nextWord() throws IOException {
        return nextToken(new IsWord());
    }

    private boolean isLineSeparator() throws IOException {
        int len = System.lineSeparator().length();
        for (int i = 0; i < len; i++) {
            if (charFromFirst(i) != System.lineSeparator().charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public String nextLine() throws IOException {
        StringBuilder result = new StringBuilder();
        while (hasNext()) {
            if (isLineSeparator()) {
                for (int i = 0; i < System.lineSeparator().length(); i++) {
                    nextChar();
                }
                return result.toString();
            }
            result.append(nextChar());
        }
        return result.toString();
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
