package expression.exceptions;

import expression.CommonExpression;
import expression.UnaryOperation;

public final class CheckedNegate extends UnaryOperation {

    public CheckedNegate(CommonExpression inner) {
        super(inner);
    }

    @Override
    public int calculate(int x) {
        OverflowException.checkSubtract(0, x);
        return -x;
    }

    @Override
    public String getSymbol() {
        return "-";
    }
}
