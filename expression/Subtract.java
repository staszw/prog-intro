package expression;

public class Subtract extends Operation {
    public Subtract(Expression left, Expression right) {
        super(left, right);
        symbol = "-";
    }

    @Override
    public int evaluate(int x) {
        return left.evaluate(x) - right.evaluate(x);
    }
}
