package mnk;

public class Main {
    public static void main(String[] args) {
        final Game game = new Game(false, new HumanPlayer(), new RandomPlayer(), new SequentialPlayer());
        int result = game.play(new MnkBoard(3, 3, 3, game.getNumPlayers()));
        System.out.println("Game result: " + result);
    }
}
