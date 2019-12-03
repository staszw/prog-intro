package mnk;

public final class Move {
    private final int row;
    private final int column;
    private final Cell value;

    public Move(final int row, final int column, final Cell value) {
        this.row = row;
        this.column = column;
        this.value = value;
    }

    public final int getRow() {
        return row;
    }

    public final int getColumn() {
        return column;
    }

    public final Cell getValue() {
        return value;
    }

    @Override
    public final String toString() {
        return "row = " + row + ", column = " + column + ", value = " + value;
    }
}
