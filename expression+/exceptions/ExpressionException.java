package expression.exceptions;

public class ExpressionException extends RuntimeException {
    public ExpressionException(String type, String arguments) {
        super(type + "; arguments: " + arguments);
    }
}
