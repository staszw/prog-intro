package scanner;

import java.io.*;

public class Scanner implements AutoCloseable {
    private Reader reader;
    private int last;

    public Scanner(Reader r) throws IOException {
        reader = r;
        last = reader.read();
    }

    public Scanner(File f, String cs) throws IOException {
        this(new InputStreamReader(new FileInputStream(f), cs));
    }

    public Scanner(InputStream f, String cs) throws IOException {
        this(new InputStreamReader(f, cs));
    }

    public Scanner(String s) throws IOException {
        this(new StringReader(s));
    }

    public boolean hasNext() {
        return last != -1;
    }

    private char lastChar() {
        return (char) last;
    }

    public boolean hasNextLine() {
        return hasNext();
    }

    public boolean hasNextToken(IsToken isToken) throws IOException {
        while (hasNext() && !isToken.isToken(lastChar())) {
            nextChar();
        }
        if (!hasNext()) {
            return false;
        }
        return isToken.isToken(lastChar());
    }

    public boolean hasNextInt() throws IOException {
        return hasNextToken(IsInt.INSTANCE);
    }

    public boolean hasNextWord() throws IOException {
        return hasNextToken(IsWord.INSTANCE);
    }

    public void nextChar() throws IOException {
        last = reader.read();
    }

    public String nextToken(IsToken isToken) throws IOException {
        if (!hasNextToken(isToken)) {
            throw new EOFException("Tried to get next token, but not found any tokens of this type");
        }
        StringBuilder result = new StringBuilder();
        while (hasNext() && !isToken.isToken(lastChar())) {
            nextChar();
        }
        while (hasNext() && isToken.isToken(lastChar())) {
            result.append(lastChar());
            nextChar();
        }
        return result.toString();
    }

    public int nextInt() throws IOException {
        return Integer.parseInt(nextToken(new IsInt()));
    }

    public String nextWord() throws IOException {
        return nextToken(new IsWord());
    }

    private boolean isLineSeparator() {
        return last == '\n' || last == '\r';
    }

    private void passLineSeparator() throws IOException {
        for (int i = 0; i < System.lineSeparator().length(); i++) {
            nextChar();
        }
    }

    public String nextLine() throws IOException {
        StringBuilder result = new StringBuilder();
        while (hasNext()) {
            if (isLineSeparator()) {
                passLineSeparator();
                return result.toString();
            }
            result.append(lastChar());
            nextChar();
        }
        return result.toString();
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
