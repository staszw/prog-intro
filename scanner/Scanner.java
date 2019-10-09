import java.io.*;
import java.util.Arrays;

public class Scanner implements AutoCloseable {
    private char[] buffer;
    private Reader reader;
    private final int sz = 88888;
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

    private boolean readBuffer() {
        if (isEndOfInputReached) {
            return false;
        }
        shiftBuffer();
        int flag;
        try {
            flag = reader.read(buffer, sz, sz);
        } catch (IOException e) {
            System.out.println("IOException while trying to read buffer" + e.getMessage());
            System.exit(0);
            return false;
        }
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

    public boolean hasNext() {
        return (head < sz && buffer[head] != 0)
                || readBuffer();
    }

    public boolean hasNextLine() {
        return hasNext();
    }

    public boolean hasNextToken(IsToken isToken) {
        while (hasNext() && !isToken.isToken(charFromFirst(0))) {
            nextChar();
        }
        if (!hasNext()) {
            return false;
        }
        return isToken.isToken(charFromFirst(0));
    }

    public boolean hasNextInt() {
        return hasNextToken(new IsInt());
    }

    public boolean hasNextWord() {
        return hasNextToken(new IsWord());
    }

    public char charFromFirst(int delta) {
        if (!hasNext()) {
            return (char) -1;
        }
        return buffer[head + delta];
    }

    public char nextChar() {
        char result = charFromFirst(0);
        head++;
        return result;
    }

    public String nextToken(IsToken comp) {
        StringBuilder result = new StringBuilder();
        char curChar = charFromFirst(0);
        while (hasNext() && !comp.isToken(curChar)) {
            nextChar();
            curChar = charFromFirst(0);
        }
        while (comp.isToken(curChar)) {
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

    public int nextInt() {
        return Integer.parseInt(nextToken(new IsInt()));
    }

    public String nextWord() {
        return nextToken(new IsWord());
    }

    private boolean isLineSeparator() {
        int len = System.lineSeparator().length();
        for (int i = 0; i < len; i++) {
            if (charFromFirst(i) != System.lineSeparator().charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public String nextLine() {
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
