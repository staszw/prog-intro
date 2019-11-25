package mnk;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;

    public HumanPlayer(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    public HumanPlayer() {
        this(System.out, new Scanner(System.in));
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        out.println("Position");
        out.println(position);
        out.println(cell + "'s move");
        int row, column;
        while (true) {
            while (true) {
                out.println("Enter row and column");
                try {
                    row = in.nextInt();
                    column = in.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    out.println("Input cannot be translated into a valid move");
                }
            }
            final Move move = new Move(row, column, cell);
            if (position.isValid(move)) {
                return move;
            }
            out.println("Move " + move + " is invalid");
        }
    }
}
