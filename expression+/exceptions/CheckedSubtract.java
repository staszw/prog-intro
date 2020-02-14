package expression.exceptions;

import expression.BinaryOperation;
import expression.CommonExpression;

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
        OverflowException.checkSubtract(x, y);
        return x - y;
    }

    @Override
    public boolean needsExtraBrackets() {
        return true;
    }
}
