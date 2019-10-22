import java.util.Arrays;

public class IntList {
    private int[] list;
    private int size;

    public IntList() {
        list = new int[1];
        size = 0;
    }

    private void resize() {
        if (size >= list.length) {
            list = Arrays.copyOf(list, list.length * 2);
        }
    }

    public IntList add(int x) {
        resize();
        list[size] = x;
        size++;
        return this;
    }

    public int getSize(){
        return size;
    }

    public int intAt(int index){
        return list[index];
    }

}
