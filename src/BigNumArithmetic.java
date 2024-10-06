import java.util.Scanner;
import java.io.File;
import java.io.IOException;

class BigNumArithmetic {

    private String filename;

    public BigNumArithmetic(String filename) {
        this.filename = filename;
    }

    // TODO write a readme here just to explain what we are doing

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
              
                evaluate(cleaned); 
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

        if (expression.isEmpty()) {return;}

        if (countNum  - 1 != countOps) { // if we have 1 2 +, have 2 and 1, logic gives us 1 and 1
            System.out.println(expression + " = ");
            return; // if its bad we print blank and return 
        }

        for (int i=0; i<split.length; ++i) {
            String check = split[i]; // this is our checker

            if (operatorCheck(check)) { // if its an operator
                String top = stack.pop().toString();
                LList topList = stringToList(top);
                String bottom = stack.pop().toString();
                LList bottomList = stringToList(bottom);
                LList result = doOperation(topList,bottomList,check);
                stack.push(listToString(result));
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

    public LList doOperation (LList top,LList bottom, String op) {
        boolean test = operatorCheck(op);
        LList result = new LList();
        if (test == false) { return null; }
        
        if (op.equals("+")) {
            result = add(top,bottom);
        } else if (op.equals("*")) {
            result = multiply(top,bottom);
        } else if (op.equals("^")) {
            result = exponent(top,bottom);
        } else {
            result = null;
        }
        return result;
    } // end do op

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
            } else if (cleanedNumber.equals("0")) {
                cleaned += "0";
            }
        }
        return cleaned; 
    } // end clean

    public String cleanNumber(String number) {
        int index = 0;
        int zeroCounter = 0;

         for (index = 0; index < number.length(); index++) {
            if (number.charAt(index) != '0') { // if the number isnt 0 break
                break;
            }
        }
        
        if (index == number.length() && number.length() != 0) {
            return "0"; // if the index hasnt moved then return nothing
        } else {
            return number.substring(index); // retrun the substring of the number using index
        }
    } // end clean number
   
    /*
     Function to convert a String to a Linked List
     Values are stored in the list in the same order they appear in the string
     EX: "123" = head-->1-->2-->3-->tail
    */
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

    /*
      Function to convert a Linked List to a String
    */
    public String listToString(LList list) {
        // initialize an empty string to populate with values in list
        String str = "";

        // collect the length of list
        int listSize = list.length();
        
        // if the list is empty, there is nothing to do, so return
        if (listSize < 1) {
            return str;
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
   
    /*
     Function to add values stored in two linked lists together
    */ 
    public LList add(LList top, LList bottom) {
   
        // Collect the sizes of top and bottom
        int topSize = top.length();
        int bottomSize = bottom.length();

        // variable to store the number of leading zeros that must be
        // added to the smaller LList when top and bottom  are not the same size initially
        int zerosNeeded;

        if (topSize > bottomSize) {
            // set zerosNeeded to the result of topSize - bottomSize
            // this gives us the number of leading zeros that must be added to the front of bottom
            zerosNeeded = topSize - bottomSize;

            // count variable to control following while loop
            int count = 0;
            // ensure bottom's current pointer is at head.next()
            bottom.moveToStart();
            while (count < zerosNeeded) {
                // insert a number (zerosNeeded) of zeros to the front of bottom 
                //until topSize = bottomSize;
                bottom.insert(0);
                count++;
            }// end while (count < zerosNeeded)
        } // end if topSize > bottomSize

        if (bottomSize > topSize) {
            // set zerosNeeded to the result of bottomSize - topSize
            // this gives us the number of leading zeros that must be 
            //added to the front of top
            zerosNeeded = bottomSize - topSize;

            // cout variable to control following while loop
            int count = 0;
            // ensure top's current pointer is at head.next()
            top.moveToStart();
            while (count < zerosNeeded) {
                // insert a number (zerosNeeded) of zeros into the front of 
                //top until topSize = bottomSize
                top.insert(0);
                count++; 
            }// end while (count < zerosNeeded)
        } // end if (bottomSize > topSize)

        // reverse the order of both top and bottom
        top.reverseLink();
        bottom.reverseLink();

        // apply the actual addition logic

        // Create a new, empty LList to store the result of adding top to bottom
        LList result = new LList();

        // Variable to store the remainder value when adding 2 numbers together
        // default value is 0. If the result of adding 2 numbers is >= 10, 
        //remainder will be changed to 1
        int remainder = 0;

        // Ensure top and bottom's current pointer is at the start of the lists
        top.moveToStart();
        bottom.moveToStart();

        // Update the values of topSize and bottomSize, since list sizes may have changed
        topSize = top.length();
        bottomSize = bottom.length();

        // Execute the following loop the number of times equal to topSize
        // At this point, topSize = bottomSize. topSize chosen in this loop 
        // arbitrarily, bottomSize would also work
        for (int i = 0; i < topSize; i++) {
            // collect the values at each Link in both top and bottom
            int topVal = (Integer) top.getValue();
            int bottomVal = (Integer) bottom.getValue();
            
            // Calculate the sum 
            int sum = topVal + bottomVal + remainder;

            // If the two numbers added together are 10 or more, there will be a 
            // remainder value of 1
            // the sum must be changed so that we only get the second number
            // Ex: if sum = 19. We want to keep 9 and change remainder to 1, 
            // which will be added to the next sum in the next iteration
            if (sum >= 10) {
                remainder = 1;
                sum = sum % 10;
            }
            else {
                remainder = 0;
            }
            // insert sum into the first Link of the result LList
            result.insert(sum);
            // increment top and bottom so that we process the following links in both next
            top.next();
            bottom.next();
        }

        // if the last summation in the for loop left a remainder of 1, insert that into result's first Link
        if (remainder == 1) {
            result.insert(remainder);
        }
        // convert result LList to a String
        String convertedList = listToString(result);

        // Remove leading zeros if any exist in convertedList
        String cleanedFinalSum = cleanNumber(convertedList);

        return stringToList(cleanedFinalSum);

    } // end add
   
    /*
     This function is used to supplement the multiply function. For regular addition
     add() function is used. When calculating addition from within multiply() function
     muldAdd() is used
    */
    public LList multAdd(LList num1, LList num2) { 
                                                   
        num1.moveToStart();
        num2.moveToStart();

        LList result = new LList();
        int carry = 0;

        // loop through both of the lists
        while (!num1.isAtEnd() || !num2.isAtEnd() || carry != 0) {
            int sum = carry;

            if (!num1.isAtEnd()) { // add from num 1
                sum += (Integer) num1.getValue();
                num1.next();
            }

            if (!num2.isAtEnd()) { // add from num 2
                sum += (Integer) num2.getValue();
                num2.next();
            }

            result.append(sum % 10); // append our sum

            carry = sum / 10; // find the new carry vakue and store it
        }

        return result;
    }

    /*
    Function to multiply the numeric values stored in two Linked Lists
    */
    public LList multiply(LList top, LList bottom) {

        top.reverseLink(); // reverse our lists here
        bottom.reverseLink();

         LList result = new LList();
        result.append(0); 

        int bottomPosition = 0; // keep track of bottom position
        bottom.moveToStart(); // confirm we are at start of list

        for (int i = 0; i < bottom.length(); ++i) { // iterate through bottom first
            int bottomDigit = (Integer) bottom.getValue();
            LList middle = new LList(); // result for intermidiate result
            int carry = 0;

            for (int j = 0; j < bottomPosition; ++j) {
                middle.append(0); // append 0's as needed
            }

            top.moveToStart(); // confirm at start
            for (int j = 0; j < top.length(); ++j) {
                int topDigit = (Integer) top.getValue(); // now iterate through top
                int product = topDigit * bottomDigit + carry; // create product
                carry = product / 10; // calc the carry for next steps
                middle.append(product % 10); // append product
                top.next(); // move to next digit in top number
            }

            if (carry > 0) {
                middle.append(carry);
            }


        result = multAdd(result, middle); // add partial result to our full result count

        bottom.next(); // mmove to next bottom digit
        bottomPosition++; // 
    }

        result.reverseLink(); // reverse the result back to proper order
        String cleanedFinal = cleanNumber(listToString(result));
        return stringToList(cleanedFinal);
    } // end multiply   

    /*
    Function to calulate the exponentiation of two linked lists
    LList n is the exponent value (if calculating 2 ^ 4, n represents 4)
    LList x is the value to be exponentiated. It represents 2 in the above example
    */
    public LList exponent(LList n, LList x) {
        LList y = new LList();
        y.append(1); // firstly init our result to 1

         String exponentString = listToString(n); // then convert our exponent to an int 
         int exponent = Integer.parseInt(exponentString);


        if (exponent == 0) { // if we try to square by zero we can just return y which is 1
            return y; 
        }   

        while (exponent > 0) {

            LList newX = new LList(); // do this to avoid mangling pointers!
            newX = stringToList(listToString(x));

            if (exponent % 2 == 1) { // if we have an odd exponent do this
                y = multiply(y, x); // multiply resuly by x
            }
            x = multiply(x, newX); // here we square x

            exponent /= 2; // then finally divide the exponent by 2
        }
        return y; 
    } // end exponent()

} // end class


