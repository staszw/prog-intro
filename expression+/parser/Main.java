package expression.parser;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(new ExpressionParser().parse(scanner.nextLine()));
    }
}
