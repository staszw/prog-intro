import java.util.Arrays;

public class IntList {
    private int[] list;
    private int size;

    IntList() {
        list = new int[1];
        size = 0;
    }

    private void resize() {
        if (size >= list.length) {
            list = Arrays.copyOf(list, list.length * 2);
        }
    }

    IntList add(int x) {
        resize();
        list[size] = x;
        size++;
        return this;
    }

    IntList merge(IntList other) {
        for (int i = 0; i < other.size; i++)
            add(other.list[i]);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(size).append(" ");
        for (int i = 0; i < size; i++) {
            result.append(list[i]);
            if (i + 1 != size) result.append(" ");
        }
        return result.toString();
    }
}
