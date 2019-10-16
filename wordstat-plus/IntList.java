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

    int get(int x){
        if (x >= size){
            return -1;
        }
        return list[x];
    }

    int length(){
        return size;
    }

    IntList merge(IntList other) {
        for (int i = 0; i < other.size; i++)
            add(other.list[i]);
        return this;
    }
}
