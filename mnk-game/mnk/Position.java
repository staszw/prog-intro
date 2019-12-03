package mnk;

public interface Position {
    boolean isValid(Move move);
    int getM();
    int getN();
    int getK();

    Cell getCell(int r, int c);
}
