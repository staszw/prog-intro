package expression.exceptions;

import expression.CommonExpression;
import expression.Const;
import expression.UnaryOperation;

public class CheckedLog2 extends UnaryOperation {
    public CheckedLog2(CommonExpression inner) {
        super(new CheckedLog(inner, new Const(2)));
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
        return "log2";
    }
}
