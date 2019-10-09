import java.util.Arrays;
import java.util.Scanner;

public class ReverseEven {
    public static void main(String[] args) {
        Scanner linesScanner = new Scanner(System.in);
        int[][] ints = new int[1][];
        int[] row = new int[1];
        int index = 0;
        while (linesScanner.hasNextLine()){
    
            String line = linesScanner.nextLine();
            Scanner intsScanner = new Scanner(line);
            int currentIndex = 0;
            while (intsScanner.hasNextInt()) {
                int current = intsScanner.nextInt();
                if (current % 2 != 0){
                    continue;
                }
                if (currentIndex >= row.length){
                    row = Arrays.copyOf(row, row.length * 2);
                }
                row[currentIndex++] = current;
            }

            if (index >= ints.length) {
                ints = Arrays.copyOf(ints, ints.length * 2);
            }
            ints[row++] = Arrays.copyOf(row, currentIndex);
        }
        for (int i = index - 1; i >= 0; i--){
            for (int j = ints[i].length - 1; j >=0; j--){
                System.out.print(ints[i][j] + " ");
            }
            System.out.println();
        }
    }
}
