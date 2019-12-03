package expression;

import java.util.Objects;

public final class Variable extends MyExpression {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Variable && Objects.equals(name, ((Variable) obj).name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        switch (name) {
            case "x":
                return x;
            case "y":
                return y;
            case "z":
                return z;
            default:
                return 0;
        }
    }

    @Override
    public int evaluate(int x) {
        if (name.equals("x"))
            return x;
        return 0;
    }
}
