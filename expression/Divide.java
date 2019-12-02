package expression;

public class Divide extends Operation {
    public Divide(Expression left, Expression right) {
        super(left, right);
        symbol = "/";
    }

    @Override
    public int evaluate(int x) {
        return left.evaluate(x) / right.evaluate(x);
    }
}
