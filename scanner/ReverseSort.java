import java.io.IOException;
import java.util.Arrays;
import java.io.PrintWriter;
import java.util.Comparator;

public class ReverseSort {
    public static void main(String[] args) {

        int[][] ints = new int[1][];
        long[] sums = new long[1];
        int index = 0;
        try (Scanner linesScanner = new Scanner(System.in)) {
            while (linesScanner.hasNextLine()) {
                int[] row = new int[1];
                String line = linesScanner.nextLine();
                Scanner intsScanner = new Scanner(line);
                int currentIndex = 0;
                long currentSum = 0;
                while (intsScanner.hasNextInt()) {
                    int current = intsScanner.nextInt();
                    if (currentIndex >= row.length) {
                        row = Arrays.copyOf(row, row.length * 2);
                    }
                    currentSum += current;
                    row[currentIndex++] = current;
                }
                if (index >= ints.length) {
                    ints = Arrays.copyOf(ints, ints.length * 2);
                    sums = Arrays.copyOf(sums, ints.length);
                }
                ints[index] = Arrays.copyOf(row, currentIndex);
                sums[index] = currentSum;
                Arrays.sort(ints[index]);
                index++;
            }
            ints = Arrays.copyOf(ints, index);
        } catch (IOException e) {
            System.out.println("Error reading input" + e.getMessage());
            return;
        }

        final long[] fsums = sums;
        Integer[] indexs = new Integer[index];
        for (int i = 0; i < index; i++) {
            indexs[i] = i;
        }
        Arrays.sort(indexs, new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return Long.compare(fsums[a], fsums[b]);
            }
        });

        PrintWriter output = new PrintWriter(System.out);
        for (int sortedIndex = index - 1; sortedIndex >= 0; sortedIndex--) {
            int i = indexs[sortedIndex];
            for (int j = ints[i].length - 1; j >= 0; j--) {
                output.print(ints[i][j] + " ");
            }
            output.println();
        }
        output.close();
    }
}
