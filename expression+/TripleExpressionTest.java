package expression;

import expression.exceptions.CheckedAdd;
import expression.exceptions.CheckedDivide;
import expression.exceptions.CheckedMultiply;
import expression.exceptions.CheckedSubtract;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class TripleExpressionTest extends ExpressionTest {
    public TripleExpressionTest(final boolean checkMini) {
        super(checkMini);
    }

    @Override
    protected void test() {
        super.test();
        handmade();
        generated();
    }

    private void handmade() {
        testExpression("10", "10", new Const(10), (x, y, z) -> 10);
        testExpression("x", "x", new Variable("x"), (x, y, z) -> x);
        testExpression("y", "y", new Variable("y"), (x, y, z) -> y);
        testExpression("z", "z", new Variable("z"), (x, y, z) -> z);
        testExpression("(x + 2)", "x + 2", new CheckedAdd(new Variable("x"), new Const(2)), (x, y, z) -> x + 2);
        testExpression("(2 - y)", "2 - y", new CheckedSubtract(new Const(2), new Variable("y")), (x, y, z) -> 2 - y);
        testExpression("(3 * z)", "3 * z", new CheckedMultiply(new Const(3), new Variable("z")), (x, y, z) -> 3 * z);
        testExpression("(x / -2)", "x / -2", new CheckedDivide(new Variable("x"), new Const(-2)), (x, y, z) -> -x / 2);
        testExpression("((1 + 2) + 3)", "1 + 2 + 3", new CheckedAdd(new CheckedAdd(new Const(1), new Const(2)), new Const(3)), (x, y, z) -> 6);
        testExpression("(1 + (2 + 3))", "1 + 2 + 3", new CheckedAdd(new Const(1), new CheckedAdd(new Const(2), new Const(3))), (x, y, z) -> 6);
        testExpression("((1 - 2) - 3)", "1 - 2 - 3", new CheckedSubtract(new CheckedSubtract(new Const(1), new Const(2)), new Const(3)), (x, y, z) -> -4);
        testExpression("(1 - (2 - 3))", "1 - (2 - 3)", new CheckedSubtract(new Const(1), new CheckedSubtract(new Const(2), new Const(3))), (x, y, z) -> 2);
        testExpression("((1 * 2) * 3)", "1 * 2 * 3", new CheckedMultiply(new CheckedMultiply(new Const(1), new Const(2)), new Const(3)), (x, y, z) -> 6);
        testExpression("(1 * (2 * 3))", "1 * 2 * 3", new CheckedMultiply(new Const(1), new CheckedMultiply(new Const(2), new Const(3))), (x, y, z) -> 6);
        testExpression("((10 / 2) / 3)", "10 / 2 / 3", new CheckedDivide(new CheckedDivide(new Const(10), new Const(2)), new Const(3)), (x, y, z) -> 10 / 2 / 3);
        testExpression("(10 / (3 / 2))", "10 / (3 / 2)", new CheckedDivide(new Const(10), new CheckedDivide(new Const(3), new Const(2))), (x, y, z) -> 10);
        testExpression(
                "((x * y) + ((z - 1) / 10))",
                "x * y + (z - 1) / 10", new CheckedAdd(
                        new CheckedMultiply(new Variable("x"), new Variable("y")),
                        new CheckedDivide(new CheckedSubtract(new Variable("z"), new Const(1)), new Const(10))
                ),
                (x, y, z) -> x * y + (z - 1) / 10
        );
        testExpression("(x + y)", "x + y", new CheckedAdd(new Variable("x"), new Variable("y")), (x, y, z) -> x + y);
        testExpression("(x + y)", "x + y", new CheckedAdd(new Variable("x"), new Variable("y")), (x, y, z) -> x + y);
        testExpression("(y + x)", "y + x", new CheckedAdd(new Variable("y"), new Variable("x")), (x, y, z) -> y + x);
    }

    private void generated() {
        final Variable vx = new Variable("x");
        final Variable vy = new Variable("y");
        final Variable vz = new Variable("z");
        final Const c1 = new Const(1);
        final Const c2 = new Const(2);

        testExpression("(1 + 1)", "1 + 1", new CheckedAdd(c1, c1), (x, y, z) -> 1 + 1);
        testExpression("(y - x)", "y - x", new CheckedSubtract(vy, vx), (x, y, z) -> y - x);
        testExpression("(2 * x)", "2 * x", new CheckedMultiply(c2, vx), (x, y, z) -> 2 * x);
        testExpression("(2 / x)", "2 / x", new CheckedDivide(c2, vx), (x, y, z) -> 2 / x);
        testExpression("(z + (1 + 1))", "z + 1 + 1", new CheckedAdd(vz, new CheckedAdd(c1, c1)), (x, y, z) -> z + 1 + 1);
        testExpression("(2 - (y - x))", "2 - (y - x)", new CheckedSubtract(c2, new CheckedSubtract(vy, vx)), (x, y, z) -> 2 - (y - x));
        testExpression("(z * (2 / x))", "z * (2 / x)", new CheckedMultiply(vz, new CheckedDivide(c2, vx)), (x, y, z) -> z * (2 / x));
        testExpression("(z / (y - x))", "z / (y - x)", new CheckedDivide(vz, new CheckedSubtract(vy, vx)), (x, y, z) -> z / (y - x));
        testExpression("((2 * x) + y)", "2 * x + y", new CheckedAdd(new CheckedMultiply(c2, vx), vy), (x, y, z) -> 2 * x + y);
        testExpression("((y - x) - 2)", "y - x - 2", new CheckedSubtract(new CheckedSubtract(vy, vx), c2), (x, y, z) -> y - x - 2);
        testExpression("((2 / x) * y)", "2 / x * y", new CheckedMultiply(new CheckedDivide(c2, vx), vy), (x, y, z) -> 2 / x * y);
        testExpression("((1 + 1) / x)", "(1 + 1) / x", new CheckedDivide(new CheckedAdd(c1, c1), vx), (x, y, z) -> (1 + 1) / x);
        testExpression("(2 + (z + (1 + 1)))", "2 + z + 1 + 1", new CheckedAdd(c2, new CheckedAdd(vz, new CheckedAdd(c1, c1))), (x, y, z) -> 2 + z + 1 + 1);
        testExpression("(1 - ((2 * x) + y))", "1 - (2 * x + y)", new CheckedSubtract(c1, new CheckedAdd(new CheckedMultiply(c2, vx), vy)), (x, y, z) -> 1 - (2 * x + y));
        testExpression("(1 * (z / (y - x)))", "1 * (z / (y - x))", new CheckedMultiply(c1, new CheckedDivide(vz, new CheckedSubtract(vy, vx))), (x, y, z) -> 1 * (z / (y - x)));
        testExpression("(z / (z + (1 + 1)))", "z / (z + 1 + 1)", new CheckedDivide(vz, new CheckedAdd(vz, new CheckedAdd(c1, c1))), (x, y, z) -> z / (z + 1 + 1));
        testExpression("((2 * x) + (1 + 1))", "2 * x + 1 + 1", new CheckedAdd(new CheckedMultiply(c2, vx), new CheckedAdd(c1, c1)), (x, y, z) -> 2 * x + 1 + 1);
        testExpression("((1 + 1) - (1 + 1))", "1 + 1 - (1 + 1)", new CheckedSubtract(new CheckedAdd(c1, c1), new CheckedAdd(c1, c1)), (x, y, z) -> 1 + 1 - (1 + 1));
        testExpression("((y - x) * (2 / x))", "(y - x) * (2 / x)", new CheckedMultiply(new CheckedSubtract(vy, vx), new CheckedDivide(c2, vx)), (x, y, z) -> (y - x) * (2 / x));
        testExpression("((y - x) / (2 * x))", "(y - x) / (2 * x)", new CheckedDivide(new CheckedSubtract(vy, vx), new CheckedMultiply(c2, vx)), (x, y, z) -> (y - x) / (2 * x));
        testExpression("(((y - x) - 2) + 1)", "y - x - 2 + 1", new CheckedAdd(new CheckedSubtract(new CheckedSubtract(vy, vx), c2), c1), (x, y, z) -> y - x - 2 + 1);
        testExpression("(((2 * x) + y) - z)", "2 * x + y - z", new CheckedSubtract(new CheckedAdd(new CheckedMultiply(c2, vx), vy), vz), (x, y, z) -> 2 * x + y - z);
        testExpression("(((1 + 1) / x) * 2)", "(1 + 1) / x * 2", new CheckedMultiply(new CheckedDivide(new CheckedAdd(c1, c1), vx), c2), (x, y, z) -> (1 + 1) / x * 2);
        testExpression("((z / (y - x)) / x)", "z / (y - x) / x", new CheckedDivide(new CheckedDivide(vz, new CheckedSubtract(vy, vx)), vx), (x, y, z) -> z / (y - x) / x);
    }

    private void testExpression(final String full, final String mini, final TripleExpression actual, final TripleExpression expected) {
        System.out.println("Testing " + mini);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < 10; k++) {
                    assertEquals(String.format("f(%d, %d, %d)", i, j, k), evaluate(expected, i, j, k), evaluate(actual, i, j, k));
                }
            }
        }
        checkEqualsAndToString(full, mini, actual);
    }

    private Object evaluate(final TripleExpression expected, final int i, final int j, final int k) {
        try {
            return expected.evaluate(i, j, k);
        } catch (Exception e) {
            return e.getClass().getName();
        }
    }

    public static void main(final String[] args) {
        new TripleExpressionTest(mode(args)).run();
    }
}
