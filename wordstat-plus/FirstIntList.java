public class FirstIntList {
    private int last_line;
    private IntList nums;
    private int count;

    public FirstIntList() {
        last_line = -1;
        nums = new IntList();
        count = 0;
    }

    public FirstIntList add(int new_line, int new_num) {
        if (new_line != last_line) {
            nums.add(new_num);
            last_line = new_line;
        }
        count++;
        return this;
    }

    public int getCount() {
        return count;
    }

    public IntList getNums() {
        return nums;
    }
}
