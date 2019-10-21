public class FirstIntList {
    private int last_line;
    private IntList nums;
    private int count;

    FirstIntList() {
        last_line = -1;
        nums = new IntList();
        count = 0;
    }

    FirstIntList add(int new_line, int new_num) {
        if (new_line != last_line){
            nums.add(new_num);
            last_line = new_line;
        }
        count++;
        return this;
    }

    int getCount(){
        return count;
    }

    IntList getNums(){
        return nums;
    }
}
