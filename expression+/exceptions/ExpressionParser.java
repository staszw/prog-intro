package expression.exceptions;

import expression.*;
import expression.parser.*;

import java.util.List;
import java.util.Map;

public class ExpressionParser extends BaseParser implements Parser {
    private static final int MAX_LEVEL = 1;
    private static final int MIN_LEVEL = -2;
    private static final Map<Integer, List<String>> PRIORITIES = Map.of(
            1, List.of("+", "-"),
            0, List.of("*", "/"),
            -1, List.of("**", "//")
    );

    private static final List<Character> SPECIAL_SYMBOLS = List.of('+', '-', '/', '*', '(', ')', '\0');

    @Override
    public TripleExpression parse(String expression) {
        setSource(new StringSource(expression));
        skipWhitespace();
        TripleExpression result = parse(MAX_LEVEL);
        if (ch != '\0') {
            throw new ExtraSymbolsException("Unexpected symbols at the end");
        }
        return result;
    }

    private CommonExpression parse(int level) {
        if (level == MIN_LEVEL) {
            return getMinLevelExpression();
        }
        skipWhitespace();
        CommonExpression current = parse(level - 1);
        while (true) {
            boolean flag = false;
            skipWhitespace();
            for (String operation : PRIORITIES.get(level)) {
                if (test(operation)) {
                    expect(operation);
                    current = getExpression(operation, current, parse(level - 1));
                    flag = true;
                }
            }
            if (!flag) {
                break;
            }
        }
        return current;
    }

    private CommonExpression getMinLevelExpression() {
        skipWhitespace();
        if (test('-')) {
            if (isDigit()) {
                return getConst(true);
            } else {
                return new CheckedNegate(getMinLevelExpression());
            }
        } else if (isDigit()) {
            return getConst(false);
        } else if (test('(')) {
            CommonExpression result = parse(MAX_LEVEL);
            expect(')');
            return result;
        } else if (test('l')) {
            expect("og2");
            expectSpace();
            return new CheckedLog2(getMinLevelExpression());
        } else if (test('p')) {
            expect("ow2");
            expectSpace();
            return new CheckedPow2(getMinLevelExpression());
        } else {
            return getVariable();
        }
    }

    private Const getConst(boolean isNegative) {
        StringBuilder builder = new StringBuilder();
        if (isNegative)
            builder.append("-");
        while (isDigit()) {
            builder.append(ch);
            nextChar();
        }
        skipWhitespace();
        try {
            return new Const(Integer.parseInt(builder.toString()));
        } catch (NumberFormatException e) {
            throw new IllegalConstException("Illegal const value, possible overflow");
        }
    }

    private Variable getVariable() {
        StringBuilder builder = new StringBuilder();
        skipWhitespace();
        while (!SPECIAL_SYMBOLS.contains(ch)) {
            builder.append(ch);
            nextChar();
            skipWhitespace();
        }
        try {
            return new Variable(builder.toString());
        } catch (IllegalStateException e) {
            throw new InvalidVariableException("Invalid name of variable");
        }
    }

    private BinaryOperation getExpression(String lastOperation, CommonExpression left, CommonExpression right) {
        switch (lastOperation) {
            case "+":
                return new CheckedAdd(left, right);
            case "-":
                return new CheckedSubtract(left, right);
            case "*":
                return new CheckedMultiply(left, right);
            case "/":
                return new CheckedDivide(left, right);
            case "**":
                return new CheckedPow(left, right);
            case "//":
                return new CheckedLog(left, right);
            default:
                throw new InvalidOperationException("Unsupported operation");
        }
    }
}
