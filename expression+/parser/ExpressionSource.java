package expression.parser;

import expression.exceptions.ParsingException;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface ExpressionSource {
    boolean hasNext();
    char next();
}
