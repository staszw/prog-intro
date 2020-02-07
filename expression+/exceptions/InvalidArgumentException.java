package expression.exceptions;

public class InvalidArgumentException extends ExpressionException {
    public InvalidArgumentException(String type, String arguments) {
        super(type, arguments);
    }
}
