package expression.exceptions;

import expression.BinaryOperation;
import expression.CommonExpression;

public final class CheckedAdd extends BinaryOperation {
    public CheckedAdd(CommonExpression left, CommonExpression right) {
        super(left, right);
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public String getSymbol() {
        return "+";
    }

    @Override
    public int calculate(int x, int y) {
        OverflowException.checkAdd(x, y);
        return x + y;
    }

    @Override
    public boolean needsExtraBrackets() {
        return false;
    }
}
