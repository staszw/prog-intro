package expression;

public class Add extends Operation {
    public Add(Expression left, Expression right) {
        super(left, right);
        symbol = "+";
    }

    @Override
    public int evaluate(int x) {
        return left.evaluate(x) + right.evaluate(x);
    }
}
