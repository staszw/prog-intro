package expression.exceptions;

import expression.CommonExpression;
import expression.UnaryOperation;

public final class CheckedNegate extends UnaryOperation {

    public CheckedNegate(CommonExpression inner) {
        super(inner);
    }

    @Override
    public int calculate(int x) {
        return -x;
    }

    @Override
    public void checkException(int x) {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException("Negate", "-" + x);
        }
    }

    @Override
    public String getSymbol() {
        return "-";
    }
}
