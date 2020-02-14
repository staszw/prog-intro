package expression.exceptions;

import expression.BinaryOperation;
import expression.CommonExpression;
import expression.Const;
import expression.Variable;
import expression.parser.BaseParser;
import expression.parser.StringSource;

import java.util.List;

public class ExpressionParser extends BaseParser implements Parser {
    private static final List< List<String> > PRIORITIES = List.of(
            List.of("**", "//"),
            List.of("*", "/"),
            List.of("+", "-")
    );
    private static final int MAX_LEVEL = PRIORITIES.size() - 1;
    private static final int MIN_LEVEL = -1;

    private static final List<Character> SPECIAL_SYMBOLS = List.of('+', '-', '/', '*', '(', ')', '\0');

    @Override
    public CommonExpression parse(String expression) {
        setSource(new StringSource(expression));
        skipWhitespace();
        CommonExpression result = parse(MAX_LEVEL);
        if (ch != '\0') {
            throw new ExtraSymbolsException(getPosition());
        }
        return result;
    }

    private CommonExpression parse(int level) {
        if (level == MIN_LEVEL) {
            return getMinLevelExpression();
        }
        skipWhitespace();
        CommonExpression current = parse(level - 1);
        boolean flag = true;
        while (flag) {
            flag = false;
            skipWhitespace();
            for (String operation : PRIORITIES.get(level)) {
                if (test(operation)) {
                    current = getExpression(operation, current, parse(level - 1));
                    flag = true;
                }
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
        } else {
            StringBuilder builder = new StringBuilder();
            skipWhitespace();
            while (!SPECIAL_SYMBOLS.contains(ch) && !Character.isWhitespace(ch)) {
                builder.append(ch);
                nextChar();
            }
            skipWhitespace();

            String value = builder.toString();
            switch (value) {
                case "log2":
                    return new CheckedLog2(getMinLevelExpression());
                case "pow2":
                    return new CheckedPow2(getMinLevelExpression());
                case "x":
                case "y":
                case "z":
                    return new Variable(value);
                case "":
                    throw new NotFoundException("variable, found nothing", getPosition());
                default:
                    throw new InvalidVariableException(value, getPosition());
            }
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
        String value = builder.toString();
        try {
            return new Const(Integer.parseInt(value));
        } catch (NumberFormatException e) {
            throw new InvalidConstException(value, getPosition());
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
                throw new AssertionError("Unsupported operation " + lastOperation);
        }
    }
}
