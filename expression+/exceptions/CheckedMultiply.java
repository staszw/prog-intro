package expression.exceptions;

import expression.BinaryOperation;
import expression.CommonExpression;
import expression.exceptions.OverflowException;

public final class CheckedMultiply extends BinaryOperation {
    public CheckedMultiply(CommonExpression left, CommonExpression right) {
        super(left, right);
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public String getSymbol() {
        return "*";
    }

    @Override
    public int calculate(int x, int y) {
        return x * y;
    }

    @Override
    public boolean needsExtraBrackets() {
        return false;
    }

    @Override
    public void checkException(int x, int y) {
        if (x == 0 || y == 0)
            return;
        int result = x * y;
        if (result / x != y || result / y != x)
            throw new OverflowException("Multiply", x + "*" + y);
    }

    private int abs(int c) {
        return (c >= 0) ? c : -c;
    }
}
