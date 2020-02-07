package expression.parser;

import expression.exceptions.ParsingException;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface ExpressionSource {
    boolean hasNext();
    boolean hasNext(int delta);
    char next();
    char next(int delta);
}
