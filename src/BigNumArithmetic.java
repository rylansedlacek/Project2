import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
class BigNumArithmetic {
    public static void Main(String[] args) {
        try {
            if (args.length == 0) {
                System.out.println("No file given");
            }// end if no args
            else {
                String file = args[0];
            }// end else

        } catch (FileNotFoundException e) {
            System.out.println(e);
            return;
        } // end catch(FileNotFoundException
    } //end Main

}
