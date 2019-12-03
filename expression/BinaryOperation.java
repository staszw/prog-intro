package expression;

import java.util.Objects;

public abstract class BinaryOperation extends MyExpression {
    private final MyExpression left;
    private final MyExpression right;

    public BinaryOperation(MyExpression left, MyExpression right) {
        this.left = left;
        this.right = right;
    }

    public abstract int getPriority();

    public abstract String getSymbol();

    public abstract int calculate(int x, int y);

    public abstract boolean needsExtraBrackets();

    @Override
    public int evaluate(int x, int y, int z) {
        return calculate(left.evaluate(x, y, z), right.evaluate(x, y, z));
    }

    @Override
    public int evaluate(int x) {
        return calculate(left.evaluate(x), right.evaluate(x));
    }

    @Override
    public String toString() {
        return addBrackets(left.toString() + " " + getSymbol() + " " + right.toString(), true);
    }

    private boolean checkBrackets(Expression expression) {
        return expression instanceof BinaryOperation
                && ((BinaryOperation) expression).getPriority() < getPriority();
    }

    private boolean checkExtraBrackets(Expression expression) {
        if (expression instanceof BinaryOperation) {
            BinaryOperation binaryOperation = (BinaryOperation) expression;
            return binaryOperation.getPriority() == getPriority() &&
                    (this.needsExtraBrackets() || binaryOperation.needsExtraBrackets());
        }
        return false;
    }

    @Override
    public String toMiniString() {
        return addBrackets(left.toMiniString(), checkBrackets(left)) +
                " " + getSymbol() + " " +
                addBrackets(right.toMiniString(), checkBrackets(right) || checkExtraBrackets(right));
    }

    private String addBrackets(String expression, boolean condition) {
        if (condition) {
            return "(" + expression + ")";
        }
        return expression;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BinaryOperation) {
            BinaryOperation binaryOperation = (BinaryOperation) obj;
            return this.getClass() == binaryOperation.getClass()
                    && Objects.equals(left, binaryOperation.left)
                    && Objects.equals(right, binaryOperation.right);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right, getSymbol());
    }
}
