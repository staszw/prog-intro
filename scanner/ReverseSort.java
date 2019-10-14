import java.util.Arrays;
import java.io.IOException;
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
        } catch (UnsupportedEncodingException e) {
            System.err.println("Incorrect encoding: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
        }

        ints = Arrays.copyOf(ints, index);
        final long[] fsums = sums;

        Integer[] indexs = new Integer[index];
        for (int i = 0; i < index; i++) {
            indexs[i] = i;
        }
        Arrays.sort(indexs, new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                long s = fsums[a] - fsums[b];
                if (s < 0) {
                    return -1;
                }
                if (s == 0) {
                    return 0;
                }
                return 1;
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
try(
