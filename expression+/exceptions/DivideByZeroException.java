package expression.exceptions;

public class DivideByZeroException extends ExpressionException {
    public DivideByZeroException(String arguments) {
        super("Divide by zero", arguments);
    }
}
