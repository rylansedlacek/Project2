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

              //  ADD EVALUATE LOGIC HERE
                evaluate(cleaned); //TODO this wont work until later

            }
            stdin.close(); 
        } catch (IOException e) {
            return; 
      }   
   } // end read 
   
    public void evaluate(String expression) {
        LStack stack = new LStack(); // create the stacl
        String[] split = expression.split("\\s+"); // split based on white space
        
        int countNum = 0; // counting for valid expression
        int countOps = 0;

        for (int i=0; i<split.length; ++i) {
            boolean op = operatorCheck(split[i]);
            if (op) { 
                countOps++;
            } else {
                countNum++;
            }
        }

        if (countNum  - 1 != countOps) { // if we have 1 2 +, have 2 and 1, logic gives us 1 and 1
            System.out.println(expression + " = ");
            return; // if its bad we print blank and return 
        }

        for (int i=0; i<split.length; ++i) {
            String check = split[i]; // this is our checker

            if (operatorCheck(check)) { // if its an operator
                String num1 = stack.pop().toString(); // pop first two
                String num2 = stack.pop().toString(); //~~~~~~~
                String result = doOperation(num1,num2,check); // do the operation
                stack.push(result);// push the result
            } else {
                stack.push(check); // otherwise we throw the number on waiting for an operator
            }
        }

            if (!stack.isEmpty()) { // if the stack is NOT EMPTY
                String finalRes = stack.pop().toString(); // get the final result
                System.out.println(expression + " = " + finalRes); // and print it hooray
            }
    } // end evaluate

    public boolean operatorCheck(String op) {
        if (op.equals("+")) {
            return true;
        } else if (op.equals("*")) {
            return true;
        } else if (op.equals("^")) {
            return true;
        } else {
            return false;
        }
    } // end op check

    public String doOperation (String num1, String num2, String op) {
        boolean test = operatorCheck(op);
        String result = "";
        if (test == false) { return ""; }
        
        if (op.equals("+")) {
            result = add(num1,num2);
        } else if (op.equals("*")) {
            result = multiply(num1,num2);
        } else if (op.equals("^")) {
            result = exponent(num1,num2);
        } else {
            result = "";
        }
        return result;
    } // end do op

    public String add(String num1, String num2) { // TODO
        return "";
    } // end add

    public String multiply(String num1, String num2) { // TODO
        return "";
    } // end multiply

    public String exponent(String num1, String num2) { //TODO
        return "";
    } // end exponent



   
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
