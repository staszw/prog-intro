package expression.parser;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class StringSource implements ExpressionSource {
    private final String data;
    private int pos = 0;

    public StringSource(final String data) {
        this.data = data;
    }

    @Override
    public boolean hasNext() {
        return pos < data.length();
    }

    @Override
    public boolean hasNext(int delta) {
        return pos + delta - 1 < data.length();
    }

    @Override
    public char next() {
        return data.charAt(pos++);
    }

    @Override
    public char next(int delta) {
        return data.charAt(pos + delta - 1);
    }

    @Override
    public int getPosition() {
        return pos;
    }
}
