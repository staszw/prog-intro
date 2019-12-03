package mnk;

import java.util.*;

public final class MnkBoard implements Board {
    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.M, '-',
            Cell.V, '|',
            Cell.E, '.'
    );

    private final Cell[][] cells;
    private Cell turn;
    private final int m, n, k;
    private final int numPlayers;
    private int empty;
    private final int nLength, mLength;
    private final String nSpaces, mSpaces;
    private final Position position = new Position() {
        @Override
        public boolean isValid(final Move move) {
            return 0 <= move.getRow() && move.getRow() < m
                    && 0 <= move.getColumn() && move.getColumn() < n
                    && cells[move.getRow()][move.getColumn()] == Cell.E
                    && turn == move.getValue();
        }

        public int getM() {
            return m;
        }

        public int getN() {
            return n;
        }

        public int getK() {
            return k;
        }

        @Override
        public Cell getCell(final int r, final int c) {
            return cells[r][c];
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder(mSpaces);
            for (int c = 0; c < n; c++) {
                sb.append(" ".repeat(nLength + 1 - Integer.toString(c).length())).append(c);
            }
            for (int r = 0; r < m; r++) {
                sb.append("\n");
                sb.append(" ".repeat(mLength - Integer.toString(r).length())).append(r);
                for (int c = 0; c < n; c++) {
                    sb.append(nSpaces).append(SYMBOLS.get(cells[r][c]));
                }
            }
            return sb.toString();
        }
    };

    public MnkBoard(final int m, final int n, final int k, int numPlayers) {
        this.m = m;
        this.n = n;
        this.k = k;
        this.numPlayers = numPlayers;
        empty = m * n;
        nLength = Integer.toString(n - 1).length();
        mLength = Integer.toString(m - 1).length();
        nSpaces = " ".repeat(nLength);
        mSpaces = " ".repeat(mLength);
        cells = new Cell[m][n];
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.CELLS.get(0);
    }

    @Override
    public final Position getPosition() {
        return position;
    }

    @Override
    public final Cell getCell() {
        return turn;
    }

    @Override
    public final Result makeMove(final Move move) {
        if (!position.isValid(move)) {
            return Result.LOSE;
        }

        cells[move.getRow()][move.getColumn()] = move.getValue();
        empty--;

        if (hasWon(move, 0, 1) || hasWon(move, 1, 0) ||
                hasWon(move, 1, 1) || hasWon(move, 1, -1)) {
                return Result.WIN;
        }

        if (empty == 0) {
            return Result.DRAW;
        }

        turn = nextCell(turn);
        return Result.UNKNOWN;
    }

    private boolean hasWon(Move move, int dr, int dc) {
        return countOneSideCells(move, dr, dc) + countOneSideCells(move, -dr, -dc) - 1 >= k;
    }

    private Cell nextCell(Cell current) {
        int index = Cell.CELLS.indexOf(current);
        return Cell.CELLS.get((index + 1) % numPlayers);
    }

    private int countOneSideCells(final Move start, final int dr, int dc) {
        int result = 0;
        int row = start.getRow();
        int column = start.getColumn();
        while (0 <= row && row < m && 0 <= column && column < n && cells[row][column] == start.getValue()) {
            result++;
            row += dr;
            column += dc;
        }
        return result;
    }
}
