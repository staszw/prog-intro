package expression.exceptions;

public class DivideByZeroException extends ExpressionException {
    public DivideByZeroException() {
        super("Division by zero");
    }
}
