import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class Reverse {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        //Scanner linesScanner = new Scanner(new File("output.txt"));
        Scanner linesScanner = new Scanner(System.in);
        int[][] ints = new int[1][];
        int index = 0;
        while (linesScanner.hasNextLine()){

            int[] row = new int[1];
            String line = linesScanner.nextLine();
            Scanner intsScanner = new Scanner(line);
            int currentIndex = 0;
            while (intsScanner.hasNextInt()) {
                int current = intsScanner.nextInt();
                if (currentIndex >= row.length){
                    row = Arrays.copyOf(row, row.length * 2);
                }
                row[currentIndex++] = current;
            }
            if (index >= ints.length) {
                ints = Arrays.copyOf(ints, ints.length * 2);
            }
            ints[index++] = Arrays.copyOf(row, currentIndex);
        }
        PrintWriter printWriter = new PrintWriter(System.out);
        for (int i = index - 1; i >= 0; i--){
            /*
            if (ints[i].length != 100)
            System.out.println(i);
            */
            for (int j = ints[i].length - 1; j >=0; j--){
                printWriter.print(ints[i][j] + " ");
            }
            printWriter.println();
            
        }
        printWriter.close();
    }
}
