import java.util.Scanner;
import java.io.File;
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
        BNA.read();
    } // end main
    
   public void read() {
        try {
            File file = new File(filename); // make a new file object
            Scanner stdin = new Scanner(file); 

            while (stdin.hasNextLine()) { 
                String line = stdin.nextLine(); 
                String cleaned = cleanExpression(line); 
              //  System.out.println(cleaned); // TODO remove this after testing
              //  evaluate(cleaned); // Each time we read in a line we will evaluate it
            }
            stdin.close(); 
        } catch (IOException e) {
            return; 
      }   
   } // end read 
   
    public String cleanExpression(String expression) {
        String cleaned = "";
        String[] numbers = expression.split("\\s+"); // split based on whitespace

        for (int i=0; i<numbers.length; ++i) {
            String number = numbers[i]; 
            String cleanedNumber = cleanNumber(number); // pass it to be cleaned

            if (!cleanedNumber.equals("0") || cleaned.length() > 0) { // first make sure its not 0
                if (!cleaned.isEmpty()) { // if its not empty give it a space
                    cleaned += " ";
                }
                cleaned += cleanedNumber; // add cleaned number to cleaned string do loop again
            }
        }
        return cleaned; 
    } // end clean

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
    } // end clean number
    

    public void evaluate(String expression) {
        //TODO discuss these notes during MONDAY MEETING

        //here will be the stack logic
        // First we will create a stack like the instructions tell us to do
        //
        // we will then split up the CLEANED string we get passed based on (" ")
        //
        // then using a for loop probably we will loop through the array looking for operatos
        // if we find an operator we pop twice, and call the appropriate method, like add, mult
        //
        // if we dont we first create a LList of the number we have using stringToList
        // then we push that LList onto the stack
        //
        // once the loop concludes, the only thing left in the stack SHOULD be the result so
        // we pop it out of the stack
        //
        // then we print out the result, in the format provided 2 3 += 5 cool
        //
        // NOTE:
        // we can use the other methods add, multiply, exponent to do the actual math, so this
        // doesnt get too gunked up like it is right now with comments, might be more
        // efficient to not use a stack of linked lists, but this is my idea, 

    }

    public boolean operatorCheck(String op) { // this might be uneeded just wrote it down on paper
       // this is just a helper
        if (op.equals("+")) {
            return true;
        } else if (op.equals("*")) {
            return true;
        } else if (op.equals("^")) {
            return true;
        }
        return false; //default
    } //end operatorCheck

    public LList doOperation(LList top, LList bottom, String op) {
        // this is just a helper
        LList result = new LList();
        if (op.equals("+")) {
            result = add(top,bottom);
        } else if (op.equals("*")) {
            result = multiply(top,bottom);
        } else if (op.equals("^")) {
            result = exponent(top,bottom);
        } else {
            result = null; //TODO make sure we put logic in for null operation
        }
        return result;
    } //end doOperation

    public LList add(LList top, LList bottom) {
        return null; //placeholder will explain my reasoning at meeting
        
        // we will expect to get a top and bottom LList, to do the math how explained by PC
        // so we might have 3 - 2 and 4 - 6
        // we can add them, we will need logic for when we have lists of differing length
        // so if we had like 0 - 1 and 9 - 9 - 9. Addtionally we need to watch for this case
        // where out list size will go up by one like above!!!
        //
        // all we plan to return is the result, so LList result will be returned after we do
        // the math likely with a for loop
    } // end add

    //THESE ARE FOR LATER
    public LList multiply(LList top, LList bottom) {
        return null; //TODO SAME AS ABOVE
    } // end multiply

    public LList exponent(LList top, LList bottom) {
        return null; //TODO SAME AS ABOVE
    } // end exponent

    //your method is close for the stringToList,
    // we just need to use a reverse for loop instead and we are golden,
    // I learned how to do this on leetcode this summer here is what I'm thinking
    
    /*
    public LList stringToList(String str) {
        LList stringList = new LList();

        for (int i = str.length() - 1; i>=0; --i) {
            char stringChar = str.charAt(i);
            int charVal = Integer.parseInt(String.valueOf(stringChar));
            stringList.append(charVal);
        }
        return stringList;
    } 
    */

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
