package expression.exceptions;

import expression.CommonExpression;
import expression.UnaryOperation;

public final class CheckedLog2 extends UnaryOperation {
    public CheckedLog2(CommonExpression inner) {
        super(inner);
    }

    @Override
    public int calculate(int x) {
        if (x <= 0)
            throw new OutsideTheDefinitionException("log2");
        int result = 0;
        while (x >= 2) {
            result++;
            x /= 2;
        }
        return result;
    }

    @Override
    public String getSymbol() {
        return "log2";
    }
}
