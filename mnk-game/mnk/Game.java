package mnk;

public final class Game {
    private final boolean log;
    private final Player[] players;
    private final int numPlayers;

    public Game(final boolean log, final Player... players) {
        this.log = log;
        this.players = players;
        this.numPlayers = players.length;
    }

    public final int getNumPlayers() {
        return numPlayers;
    }

    public final int play(Board board) {
        while (true) {
            for (int index = 0; index < players.length; index++) {
                final int result = move(board, players[index], index + 1);
                if (result != -1) {
                    return result;
                }
            }
        }
    }

    private int move(final Board board, final Player player, final int no) {
        final Move move = player.move(board.getPosition(), board.getCell());
        final Result result = board.makeMove(move);
        log("Player " + no + " move: " + move);
        log("Position:\n" + board);
        if (result == Result.WIN) {
            log("Player " + no + " won");
            return no;
        } else if (result == Result.LOSE) {
            log("Player " + no + " lose");
            return -100500;
        } else if (result == Result.DRAW) {
            log("Draw");
            return 0;
        } else {
            return -1;
        }
    }

    private void log(final String message) {
        if (log) {
            System.out.println(message);
        }
    }
}
