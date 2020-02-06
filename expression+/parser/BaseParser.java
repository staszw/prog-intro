package expression.parser;

import expression.exceptions.AssertException;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class BaseParser {
    private ExpressionSource source;
    protected char ch;

    protected void setSource(final ExpressionSource source) {
        this.source = source;
        nextChar();
    }

    protected void nextChar() {
        ch = source.hasNext() ? source.next() : '\0';
    }

    protected boolean test(char expected) {
        if (ch == expected) {
            nextChar();
            return true;
        }
        return false;
    }

    protected void expect(final char c) {
        if (ch != c) {
            throw new AssertException("Expected '" + c + "', found '" + ch + "'");
        }
        nextChar();
    }

    protected void expect(final String value) {
        for (char c : value.toCharArray()) {
            expect(c);
        }
    }

    protected boolean between(final char from, final char to) {
        return from <= ch && ch <= to;
    }

    protected boolean isDigit() {
        return between('0', '9');
    }

    protected void skipWhitespace() {
        while (Character.isWhitespace(ch)) {
            nextChar();
        }
    }
}
