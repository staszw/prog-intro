package expression.exceptions;

import expression.CommonExpression;
import expression.Const;
import expression.UnaryOperation;

public class CheckedPow2 extends UnaryOperation {
    public CheckedPow2(CommonExpression inner) {
        super(new CheckedPow(new Const(2), inner));
    }

    @Override
    public int calculate(int x) {
        return x;
    }

    @Override
    public void checkException(int x) {
        return;
    }

    @Override
    public String getSymbol() {
        return "pow2";
    }
}
