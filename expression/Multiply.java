package expression;

public class Multiply extends Operation {
    public Multiply(Expression left, Expression right) {
        super(left, right);
        symbol = "*";
    }

    @Override
    public int evaluate(int x) {
        return left.evaluate(x) * right.evaluate(x);
    }
}
