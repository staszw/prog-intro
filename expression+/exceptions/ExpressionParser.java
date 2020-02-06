package expression.exceptions;

import expression.*;
import expression.parser.*;

import java.util.Map;

public class ExpressionParser extends BaseParser implements Parser {
    private static final int MAX_LEVEL = 1;
    private static final int MIN_LEVEL = -1;
    private String lastOperation = "";
    private static final Map<String, Integer> PRIORITIES = Map.of(
            "+", 1,
            "-", 1,
            "*", 0,
            "/", 0,
            ")", MAX_LEVEL + 1
    );

    private static final Map<Character, String> GET_BY_FIRST_CHAR = Map.of(
            '+', "+",
            '-', "-",
            '*', "*",
            '/', "/",
            ')', ")"
    );

    @Override
    public TripleExpression parse(String expression) {
        setSource(new StringSource(expression));
        skipWhitespace();
        TripleExpression result = parse(MAX_LEVEL, false);
        if (ch != '\0' || !lastOperation.equals(""))
            throw new ExtraSymbolsException("Unexpected symbols at the end");
        return result;
    }

    private CommonExpression parse(int level, boolean brackets) {
        skipWhitespace();
        if (level == MIN_LEVEL) {
            return getMinLevelExpression();
        }
        CommonExpression current = parse(level - 1, false);
        while (PRIORITIES.getOrDefault(lastOperation, MIN_LEVEL) == level) {
            current = getExpression(lastOperation, current, parse(level - 1, false));
        }
        skipWhitespace();
        if (brackets) {
            if (!lastOperation.equals(")")){
                throw new AssertException("Expected closing brackets");
            }
            getOperation();
        }
        return current;
    }

    private CommonExpression getMinLevelExpression() {
        if (test('-')) {
            skipWhitespace();
            if (isDigit()) {
                return getConst(true);
            } else {
                return new CheckedNegate(getMinLevelExpression());
            }
        } else if (isDigit()) {
            return getConst(false);
        } else if (test('(')) {
            return parse(MAX_LEVEL, true);
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
        getOperation();
        try {
            return new Const(Integer.parseInt(builder.toString()));
        } catch (NumberFormatException e) {
            throw new IllegalConstException("Illegal const value, possible overflow");
        }
    }

    private Variable getVariable() {
        StringBuilder builder = new StringBuilder();
        skipWhitespace();
        while (!getOperation() && ch != '\0') {
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
            default:
                throw new InvalidOperationException("Unsupported operation");
        }
    }

    private boolean getOperation() {
        if (ch == '\0'){
            lastOperation = "";
        }
        if (!GET_BY_FIRST_CHAR.containsKey(ch)) {
            return false;
        }
        String operation = GET_BY_FIRST_CHAR.get(ch);
        expect(operation);
        lastOperation = operation;
        return true;
    }
}
