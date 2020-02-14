package expression.exceptions;

import expression.CommonExpression;
import expression.UnaryOperation;

public final class CheckedPow2 extends UnaryOperation {
    public CheckedPow2(CommonExpression inner) {
        super(inner);
    }

    @Override
    public int calculate(int x) {
        if (x < 0) {
            throw new OutsideTheDefinitionException("pow2");
        }
        if (x >= 31) {
            throw new OverflowException();
        }

        return (1 << x);
    }

    @Override
    public String getSymbol() {
        return "pow2";
    }
}
