package expression.exceptions;

public class ExpressionException extends RuntimeException {
    public ExpressionException(String type) {
        super(type);
    }
}
