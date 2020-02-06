package expression.exceptions;

import expression.BinaryOperation;
import expression.CommonExpression;
import expression.exceptions.OverflowException;

public final class CheckedNegate extends CommonExpression {
    private final CommonExpression inner;

    public CheckedNegate(CommonExpression inner) {
        this.inner = inner;
    }

    public int calculate(int x){
        checkException(x);
        return -x;
    }

    @Override
    public int evaluate(int x) {
        return calculate(inner.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return calculate(inner.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return "-(" + inner.toString() + ")";
    }

    @Override
    public String toMiniString() {
        boolean checkBrackets = inner instanceof BinaryOperation;
        return "-" +
                (checkBrackets ? "(" : "") +
                inner.toMiniString() +
                (checkBrackets ? ")" : "");
    }

    private void checkException(int x) {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException("Negate", "-" + x);
        }
    }
}
