package expression.exceptions;

import expression.BinaryOperation;
import expression.CommonExpression;

public class CheckedLog extends BinaryOperation {
    public CheckedLog(CommonExpression left, CommonExpression right) {
        super(left, right);
    }

    @Override
    public int getPriority() {
        return -1;
    }

    @Override
    public String getSymbol() {
        return "//";
    }

    @Override
    public int calculate(int x, int y) {
        int result = 0;
        while (x >= y) {
            result++;
            x = x / y;
        }
        return result;
    }

    @Override
    public boolean needsExtraBrackets() {
        return false;
    }

    @Override
    public void checkException(int x, int y) {
        if (y <= 0 || y == 1 || x <= 0)
            throw new InvalidArgumentException("Invalid arguments of log", x + "//" + y);
    }
}
