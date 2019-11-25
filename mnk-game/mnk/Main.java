package mnk;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final Game game = new Game(false, new HumanPlayer(), new HumanPlayer());
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter n, m and k: ");
        int n = sc.nextInt();
        int m = sc.nextInt();
        int k = sc.nextInt();
        int result = game.play(new MnkBoard(n, m, k));
        System.out.println("Game result: " + result);
    }
}
