package expression.exceptions;

public class NotFoundException extends ParsingException {
    public NotFoundException(String message, int position) {
        super("Expected " + message, position);
    }
}
