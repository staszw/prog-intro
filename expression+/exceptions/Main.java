package expression.exceptions;

import expression.TripleExpression;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExpressionParser parser = new ExpressionParser();
        TripleExpression expression = parser.parse("1000000*x*x*x*x*x/(x-1)");
        System.out.println(expression.evaluate(scanner.nextInt(), 0,0));
    }
}
