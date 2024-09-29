import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
class BigNumArithmetic {



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
