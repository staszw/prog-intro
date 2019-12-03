package expression;

public final class Divide extends BinaryOperation {
    public Divide(MyExpression left, MyExpression right) {
        super(left, right);
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public String getSymbol() {
        return "/";
    }

    @Override
    public int calculate(int x, int y) {
        return x / y;
    }

    @Override
    public boolean needsExtraBrackets() {
        return true;
    }
}
