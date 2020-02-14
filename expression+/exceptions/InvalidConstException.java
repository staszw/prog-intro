package expression.exceptions;

public class InvalidConstException extends ParsingException {

    public InvalidConstException(String value, int position) {
        super("Invalid const value " + value, position);
    }
}
