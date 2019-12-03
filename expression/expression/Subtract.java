package expression;

public final class Subtract extends BinaryOperation {
    public Subtract(MyExpression left, MyExpression right) {
        super(left, right);
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public String getSymbol() {
        return "-";
    }

    @Override
    public int calculate(int x, int y) {
        return x - y;
    }

    @Override
    public boolean needsExtraBrackets() {
        return true;
    }
}
