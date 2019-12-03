package mnk;

import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

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
            try {
                out.println("Enter row and column");
                row = nextInt();
                column = nextInt();
            } catch (NoSuchElementException e){
                throw new IllegalStateException("Input has been closed");
            }
            final Move move = new Move(row, column, cell);
            if (position.isValid(move)) {
                return move;
            }
            out.println("Move " + move + " is invalid. Try again");
        }
    }

    private int nextInt(){
        while (!in.hasNextInt()) {
            in.next();
        }
        return in.nextInt();
    }
}
