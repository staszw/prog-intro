package expression;

public class Const implements Expression {
    private int number;
    public Const(int number){
        this.number = number;
    }

    @Override
    public int evaluate(int x) {
        return number;
    }

    @Override
    public String toString() {
        return Integer.toString(number);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Const && number == ((Const) obj).number;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(number);
    }
}
