import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;


class BigNumArithmetic {

    private String filename;

    public BigNumArithmetic(String filename) {
        this.filename = filename;
    }

    public static void main(String args[]) {
        
        if (args.length != 1) {
            System.out.println("Invalid Usage");
            return;
        }

        BigNumArithmetic BNA = new BigNumArithmetic(args[0]);
    
        // method calls will go here like BNA.read(filename) etc...

    }
    
    void read(String filename) {
        



    }







}
