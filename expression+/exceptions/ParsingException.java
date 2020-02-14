package expression.exceptions;

public class ParsingException extends RuntimeException {
    public ParsingException(final String message, final int position) {
        super(message + "; position: " + position);
    }
}
