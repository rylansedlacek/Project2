import java.awt.event.WindowAdapter;
import java.util.NoSuchElementException;


// Linked list implementation
class LList implements List {
    private Link head = null;         // Pointer to list header
    private Link tail = null;         // Pointer to last element
    private Link curr = null;         // Access to current element
    private int listSize = 0;      // Size of list

    // Constructors
    LList(int size) {
        this();
    }     // Constructor -- Ignore size

    LList() {
        clear();
    }

    // Remove all elements
    public void clear() {
        curr = tail = new Link(null); // Create trailer
        head = new Link(tail);        // Create header
        listSize = 0;
    }

    // Insert "it" at current position
    public boolean insert(Object it) {
        //Create a new link, following current, and copy current's values into it
        curr.setNext(new Link(curr.element(), curr.next()));

        //Change the old curr value to the new value (it), the pointer doesn't change
        curr.setElement(it);
        if (tail == curr) {tail = curr.next();}  // New tail
        listSize++;
        return true;
    }

    // Append "it" to list
    public boolean append(Object it) {
        // Create a new link with a null pointer, and point tail to it
        tail.setNext(new Link(null));
        //input a new value for tail (an original value, since tail has null value)
        tail.setElement(it);
        //Assign tail to the new link created, which means that original tail is no longer tail
        tail = tail.next();
        listSize++;
        return true;
    }

    // Remove and return current element
    public Object remove() {
        if (curr == tail) {// Nothing to remove
            return null;
        }
        Object it = curr.element();             // Remember value
        curr.setElement(curr.next().element()); // Pull forward the next element
        if (curr.next() == tail) {tail = curr;}   // Removed last, move tail
        curr.setNext(curr.next().next());       // Point around unneeded link
        listSize--;                             // Decrement element count
        return it;                              // Return value
    }

    public void moveToStart() {
        curr = head.next();
    } // Set curr at list start

    public void moveToEnd() {
        curr = tail;
    }          // Set curr at list end

    // Move curr one step left; no change if now at front
    public void prev() {
        if (head.next() == curr) {return;} // No previous element
        Link temp = head;
        // March down list until we find the previous element
        while (temp.next() != curr) {temp = temp.next();}
        curr = temp;
    }

    // Move curr one step right; no change if now at end
    public void next() {
        if (curr != tail) {curr = curr.next();}
    }

    public int length() {
        return listSize;
    } // Return list length


    // Return the position of the current element
    public int currPos() {
        Link temp = head.next();
        int i;
        for (i = 0; curr != temp; i++){
            temp = temp.next();}
        return i;
    }

    // Move down list to "pos" position
    public boolean moveToPos(int pos) {
        if ((pos < 0) || (pos > listSize)){ return false;}
        curr = head.next();
        for (int i = 0; i < pos; i++) {curr = curr.next();}
        return true;
    }

    // Return true if current position is at end of the list
    public boolean isAtEnd() {
        return curr == tail;
    }

    // Return current element value.
    public Object getValue() throws NoSuchElementException {
        if (curr == tail){ // No current element
            throw new NoSuchElementException("getvalue() in LList has current of " + curr + " and size of "
                    + listSize + " that is not a a valid element");}
        return curr.element();
    }

    // Check if the list is empty
    public boolean isEmpty() {
        return listSize == 0;
    }

    /**
     * This method checks if the LList contains a specific value
     *
     * @param item the value to be searched for
     * @return true if the list contains the value, false otherwise
     */
    public boolean contains(Object item) {
        // Create a new link to store the values contained in each link being evaluated
        Link temp = head.next();
        // Loop through the entire list. When temp = tail, the list has been exhausted
        while (temp != tail) {
            // extract each actual link's value
            Object foundVal = temp.element();
            if (foundVal == item) {
                return true;
            } // end if foundVal == item
            // if item was not found, move to the next link in the list
            temp = temp.next();
        } // end while (temp != tail)
        return false;
    } // end contains()

    /**
     * This method attempts to collect the value stored at a specified index
     *
     * @param index the index who's we want to retrieve
     * @return null if an invalid index was passed (indices < 0 || > listSize are invalid). The index
     * is also invalid if it is the tail index. If a valid index was found, return the value found
     * at that index
     */
    public Object get(int index) {
        // attempt to move to position index. If index > listSize, moveToPos() will catch it and return false
        boolean checkIndex = this.moveToPos(index);
        if (!checkIndex) {
            return null;
        } // end if (!checkIndex)

        // moveToPos allows index to point to tail, which has no value, so if curr = tail
        // the index passed was also invalid and this function should return null
        if (this.curr == tail) {
            return null;
        } // end if (this.curr == tail)

        // Collect the value stored at index when index is valid
        Object foundVal = this.getValue();
        // reset curr pointer to point to the link at index[0]
        this.curr = head.next();
        return foundVal;
    } // end get(int index)

/**
     * This method reverses a Linked List in place
     * If the list is originally head-->1-->2-->tail
     * after reverseLink is called, the list will be head-->2-->1-->tail
     */
    public void reverseLink() {
        // If the list is empty, do nothing
        if (head.next() == tail) {
            return;
        }
        // prev is used to store the previous link
        Link prev = null;
        // temporary head link is created to traverse the list without changing the actual head
        Link temp = head;
        // next is used to store the next link in the progression
        Link next;
        // When temp == null, the whole list has been traversed
        while (temp != null) {
            // point next to the next link in the list
            next = temp.next();
            //point temp's pointer to the previous node
            temp.setNext(prev);
            // assign prev to the same link as temp
            prev = temp;
            // move temp to next, which is the next node
            temp = next;
        }
        // prev is now where tail originally was, so make that the new head
        head = prev;
        // Now traverse the list again to add a new tail, starting at new head, but using temp in head's place
        temp = head;
        // Move temp to the very end of the list
        while (temp.next() != null) {
            temp = temp.next();
        }
        // Now that temp is at the end of the list, make temp the new tail
        tail = temp;
    }

    /**
     * THROW BELOW PRINT METHOD AWAY
     */
    public void printList() {
        Link temp = head.next();
        while (temp != tail) {
            if (temp != null) {
                System.out.print(temp.element());
                temp = temp.next();
            }
        }
        System.out.println();
    }
}
