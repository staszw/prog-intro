package expression.exceptions;

import expression.BinaryOperation;
import expression.CommonExpression;

public final class CheckedPow extends BinaryOperation {
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
        if (x == 0 && y == 0 || y < 0) {
            throw new OutsideTheDefinitionException("pow");
        }
        if (x == 0) {
            return 0;
        }

        int result = 1;
        int currentX = x, currentY = y;
        while (currentY != 0) {
            if (currentY % 2 == 1) {
                OverflowException.checkMultiply(result, currentX);
                result = result * currentX;
                currentY--;
            } else {
                OverflowException.checkMultiply(currentX, currentX);
                currentX *= currentX;
                currentY /= 2;
            }
        }
        return result;
    }

    @Override
    public boolean needsExtraBrackets() {
        return false;
    }
}
