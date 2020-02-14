package expression.exceptions;

public class OverflowException extends ExpressionException {
    public OverflowException() {
        super("Overflow");
    }

    public static void checkMultiply(int x, int y) {
        int result = x * y;
        if (result / x != y || result / y != x)
            throw new OverflowException();
    }

    public static void checkAdd(int x, int y) {
        if (y > 0 && Integer.MAX_VALUE - y < x || y < 0 && Integer.MIN_VALUE - y > x) {
            throw new OverflowException();
        }
    }

    public static void checkSubtract(int x, int y) {
        if (y > 0 && Integer.MIN_VALUE + y > x || y < 0 && Integer.MAX_VALUE + y < x) {
            throw new OverflowException();
        }
    }

    public static void checkDivide(int x, int y) {
        if (x == Integer.MIN_VALUE && y == -1) {
            throw new OverflowException();
        }
    }
}
