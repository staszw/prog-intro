package expression.exceptions;

import expression.BinaryOperation;
import expression.CommonExpression;

public final class CheckedDivide extends BinaryOperation {
    public CheckedDivide(CommonExpression left, CommonExpression right) {
        super(left, right);
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public String getSymbol() {
        return "/";
    }

    @Override
    public int calculate(int x, int y) {
        return x / y;
    }

    @Override
    public boolean needsExtraBrackets() {
        return true;
    }

    @Override
    public void checkException(int x, int y) {
        if (x == Integer.MIN_VALUE && y == -1) {
            throw new OverflowException("Divide", x + "/" + y);
        }
        if (y == 0) {
            throw new DivideByZeroException(x + "/" + y);
        }
    }
}
