package expression;

import expression.exceptions.CheckedAdd;
import expression.exceptions.CheckedMultiply;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int x = scanner.nextInt();
        System.out.println(
                new CheckedAdd(
                        new CheckedMultiply(
                                new Variable("x"),
                                new Variable("x")
                        ),
                        new CheckedAdd(
                                new CheckedMultiply(
                                        new Const(-2),
                                        new Variable("x")
                                ),
                                new Const(1)
                        )
                ).evaluate(x)
        );
    }
}
