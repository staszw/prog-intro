package expression;

import java.util.Objects;

public final class Const extends CommonExpression {
    private final int number;

    public Const(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return Integer.toString(number);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Const &&
                number == ((Const) obj).number;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(number);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return number;
    }

    @Override
    public int evaluate(int x) {
        return number;
    }
}
