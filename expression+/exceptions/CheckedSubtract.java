package expression.exceptions;

import expression.BinaryOperation;
import expression.CommonExpression;
import expression.exceptions.OverflowException;

public final class CheckedSubtract extends BinaryOperation {
    public CheckedSubtract(CommonExpression left, CommonExpression right) {
        super(left, right);
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public String getSymbol() {
        return "-";
    }

    @Override
    public int calculate(int x, int y) {
        checkException(x, y);
        return x - y;
    }

    @Override
    public boolean needsExtraBrackets() {
        return true;
    }

    @Override
    public void checkException(int x, int y) {
        if (y > 0 && Integer.MIN_VALUE + y > x || y < 0 && Integer.MAX_VALUE + y < x) {
            throw new OverflowException("Subtract", x + "-" + y);
        }
    }
}
