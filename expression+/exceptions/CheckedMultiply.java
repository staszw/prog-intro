package expression.exceptions;

import expression.BinaryOperation;
import expression.CommonExpression;

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
        if (x == 0 || y == 0)
            return 0;
        OverflowException.checkMultiply(x, y);
        return x * y;
    }

    @Override
    public boolean needsExtraBrackets() {
        return false;
    }
}
