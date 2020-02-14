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
        OverflowException.checkDivide(x, y);
        if (y == 0) {
            throw new DivideByZeroException();
        }
        return x / y;
    }

    @Override
    public boolean needsExtraBrackets() {
        return true;
    }
}
