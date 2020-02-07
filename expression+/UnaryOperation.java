package expression;

public abstract class UnaryOperation extends CommonExpression {
    protected final CommonExpression inner;

    public UnaryOperation(CommonExpression inner) {
        this.inner = inner;
    }

    public abstract int calculate(int x);

    public abstract void checkException(int x);

    public abstract String getSymbol();

    @Override
    public int evaluate(int x) {
        int innerValue = inner.evaluate(x);
        checkException(innerValue);
        return calculate(innerValue);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int innerValue = inner.evaluate(x, y, z);
        checkException(innerValue);
        return calculate(innerValue);
    }

    @Override
    public String toString() {
        return getSymbol() + "(" + inner.toString() + ")";
    }

    @Override
    public String toMiniString() {
        boolean checkBrackets = inner instanceof BinaryOperation;
        return getSymbol() +
                (checkBrackets ? "(" : "") +
                inner.toMiniString() +
                (checkBrackets ? ")" : "");
    }
}
