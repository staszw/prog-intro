import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Reverse {
    public static void main(String[] args) {
        Scanner linesScanner = new Scanner(System.in);
        ArrayList lines = new ArrayList<String>();
        while (linesScanner.hasNextLine())
            lines.add(linesScanner.nextLine());
        for (int i = lines.size() - 1; i >= 0; i--){
            ArrayList ints = new ArrayList<Integer>();
            Scanner intsScanner = new Scanner((String) lines.get(i));
            while (intsScanner.hasNextInt())
                ints.add(intsScanner.nextInt());
            for (int j = ints.size() - 1; j >= 0; j--)
                System.out.print(ints.get(j) + " ");
            System.out.println();
        }
    }
}
