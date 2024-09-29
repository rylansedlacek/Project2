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
        
        BNA.read();

    }
    
    void read() {
        try {
            FileReader filereader = new FileReader(filename);
            BufferedReader bufferedreader = new BufferedReader(filereader);

            String line = "";

             while ((line = bufferedreader.readLine()) != null) {
             //   System.out.println(line);
             //  System.out.println("==================="); 
                String cleaned = cleanExpression(line);
                System.out.println(cleaned);
              //  System.out.println("------------------");
            }

            bufferedreader.close();

        } catch (IOException e) {
            return;
        }        
    } // end read

    public String cleanExpression(String expression) {
        String cleaned = "";
        String[] numbers = expression.split("\\s+"); // split based on whitespace

        for (int i=0; i<numbers.length; ++i) {
            String number = numbers[i]; // get our number
            String cleanedNumber = cleanNumber(number); // pass it to be cleaned

            if (!cleanedNumber.equals("0") || cleaned.length() > 0) { // first make sure its not 0
                if (!cleaned.isEmpty()) { // if its not empty give it a space
                    cleaned += " ";
                }
                cleaned += cleanedNumber; // add our cleaned number to the cleaned string do the loop again
            }
        }
        return cleaned; // at the end of the loop return the cleaned line
    }

    public String cleanNumber(String number) {
        int index = 0;

         for (index = 0; index < number.length(); index++) {
            if (number.charAt(index) != '0') { // if the number isnt 0 break
                break;
            }
        }

        if (index == number.length()) { 
            return ""; // if the index hasnt moved then return nothing
        } else {
            return number.substring(index); // retrun the substring of the number using index
        }
    }





    // Store the values in the List in the same order they appear in the string
    //"123" = head-->1-->2-->3-->tail
    public LList stringToList(String str) {
        // create a new Linked List to store all the strings
        LList stringList = new LList();
        
        // Loop through the string, adding each char into LList
        for (int i = 0; i < str.length(); i++) {
            // collect the character at i 
            char stringChar = str.charAt(i);

            // convert each character to integer
            int charVal = Integer.parseInt(String.valueOf(stringChar));

            // add the character (now an integer) to the LList
            stringList.append(charVal);
        } // end for (char strincChar : givenString)
        return stringList;
    }

    //TODO change the return value on if(listSize < 1)
    // convert a LinkedList to a string
    public String listToString(LList list) {
        // initialize an empty string to populate with values in list
        String str = "";

        // collect the length of list
        int listSize = list.length();
        
        // if the list is empty, there is nothing to do, so return
        if (listSize < 1) {
            return "Empty list provided";
        }

        // ensure list.curr is at the start of the list
        list.moveToStart();
        
        int count = 0;
        while (count < listSize) {
            // collect the value stored in each link
            Object linkVal = list.getValue();
            // add the collected value to str
            str += linkVal;
            // move list.curr to the next Link in the list
            list.next();
            // increment count
            count++;
        }// end while
        return str;
    }// end listToString

}
