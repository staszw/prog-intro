import java.util.Arrays;

public class FirstIntList {
    public IntList lines;
    public IntList nums;

    IntList() {
        lines = new IntList();
        nums = new IntList();
        size = 0;
    }

    FirstIntList add(int x, int y) {
        lines.add(x);
        lines.add(y);
        return this;
    }

    FirstIntList merge(FirstIntList other) {
        for (int i = 0; i < other.size; i++)
            add(other.lines.get(i), other.nums.get(i));
        return this;
    }
}
