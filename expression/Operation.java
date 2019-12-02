package expression;

import java.util.Map;

public abstract class Operation implements Expression {
    public static final Map<String, Integer> PRIORITIES = Map.of(
            "+", 0,
            "-", 0,
            "*", 1,
            "/", 1
    );
    protected final Expression left;
    protected final Expression right;
    protected String symbol;

    public int getPriority(){
        return PRIORITIES.get(symbol);
    }

    public Operation(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " " + symbol + " " + right.toString() + ")";
    }

    private boolean checkBrackets(Expression expression){
        return expression instanceof Operation
                && ((Operation) expression).getPriority() < getPriority();
    }

    private boolean checkExtraBrackets(Expression expression) {
        return expression instanceof Operation
                && ((Operation) expression).getPriority() == getPriority()
                && (this instanceof Divide || this instanceof Subtract || expression instanceof Divide);
    }

    @Override
    public String toMiniString(){
        StringBuilder result = new StringBuilder();
        if (checkBrackets(left)) {
            result.append("(").append(left.toMiniString()).append(")");
        } else {
            result.append(left.toMiniString());
        }
        result.append(" ").append(symbol).append(" ");
        if (checkBrackets(right) || checkExtraBrackets(right)) {
            result.append("(").append(right.toMiniString()).append(")");
        } else {
            result.append(right.toMiniString());
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Operation
                && ((Operation) obj).symbol.equals(symbol)
                && ((Operation) obj).left.equals(left)
                && ((Operation) obj).right.equals(right);
    }

    @Override
    public int hashCode() {
        return 194752 * left.hashCode() + 1237 * right.hashCode() + symbol.hashCode();
    }
}
