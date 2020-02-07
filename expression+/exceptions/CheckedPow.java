package expression.exceptions;

import expression.BinaryOperation;
import expression.CommonExpression;

public class CheckedPow extends BinaryOperation {
    public CheckedPow(CommonExpression left, CommonExpression right) {
        super(left, right);
    }

    @Override
    public int getPriority() {
        return -1;
    }

    @Override
    public String getSymbol() {
        return "**";
    }

    @Override
    public int calculate(int x, int y) {
        if (y < 0)
            return 0;
        if (y == 0)
            return 1;
        int result = 1;
        for (int i = 0; i < y; i++)
            result *= x;
        return result;
    }

    @Override
    public boolean needsExtraBrackets() {
        return false;
    }

    @Override
    public void checkException(int x, int y) {
        if (x == 0 && y == 0 || y < 0) {
            throw new InvalidArgumentException("Not defined pow", Integer.toString(x));
        }
        if (x >= -1 && x <= 1)
            return;
        int result = 1;
        for (int i = 0; i < y; i++) {
            int nw = result * x;
            if (nw / result != x || nw / x != result)
                throw new OverflowException("Pow", x + "**" + y);
            result = nw;
        }
    }
}
