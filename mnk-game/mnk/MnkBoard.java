package mnk;

import java.util.*;

public class MnkBoard implements Board {
    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.'
    );
    private static final List<Move> steps = List.of(
            new Move(0, 1, Cell.E),
            new Move(1, 0, Cell.E),
            new Move(1, 1, Cell.E),
            new Move(-1, 1, Cell.E)
    );

    private final Cell[][] cells;
    private Cell turn;
    private final int m, n, k;
    private int empty;
    private final int nLength, mLength;
    private Position position;

    public MnkBoard(int m, int n, int k) {
        this.m = m;
        this.n = n;
        this.k = k;
        empty = m * n;
        nLength = Integer.toString(n - 1).length();
        mLength = Integer.toString(m - 1).length();
        cells = new Cell[m][n];
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
        position = new Position() {
            @Override
            public boolean isValid(final Move move) {
                return 0 <= move.getRow() && move.getRow() < m
                        && 0 <= move.getColumn() && move.getColumn() < n
                        && cells[move.getRow()][move.getColumn()] == Cell.E
                        && turn == move.getValue();
            }

            @Override
            public int getM() {
                return m;
            }

            @Override
            public int getN() {
                return n;
            }

            @Override
            public int getK(){
                return k;
            }

            @Override
            public Cell getCell(final int r, final int c) {
                return cells[r][c];
            }

            @Override
            public String toString() {
                final StringBuilder sb = new StringBuilder(" ".repeat(mLength));
                for (int c = 0; c < n; c++) {
                    sb.append(" ".repeat(nLength + 1 - Integer.toString(c).length())).append(c);
                }
                for (int r = 0; r < m; r++) {
                    sb.append("\n");
                    sb.append(" ".repeat(mLength - Integer.toString(r).length())).append(r);
                    for (int c = 0; c < n; c++) {
                        sb.append(" ".repeat(nLength)).append(SYMBOLS.get(cells[r][c]));
                    }
                }
                return sb.toString();
            }
        };
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    @Override
    public final Result makeMove(final Move move) {
        if (!position.isValid(move)) {
            return Result.LOSE;
        }

        cells[move.getRow()][move.getColumn()] = move.getValue();
        empty--;

        for (Move step : steps){
            if (countCells(move, step) >= k){
                return Result.WIN;
            }
        }

        if (empty == 0) {
            return Result.DRAW;
        }

        turn = turn == Cell.X ? Cell.O : Cell.X;
        return Result.UNKNOWN;
    }

    private int countCells(final Move start, final Move step){
        int result = 1;
        int row = start.getRow() + step.getRow();
        int column = start.getColumn() + step.getColumn();
        while (0 < row && row < m && 0 < column && column < n && cells[row][column] == start.getValue()) {
            result++;
            row += step.getRow();
            column += step.getColumn();
        }
        row = start.getRow() - step.getRow();
        column = start.getColumn() - step.getColumn();
        while (0 < row && row < m && 0 < column && column < n && cells[row][column] == start.getValue()) {
            result++;
            row -= step.getRow();
            column -= step.getColumn();
        }
        return result;
    }
}
