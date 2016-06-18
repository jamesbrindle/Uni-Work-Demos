package assignment2;

import java.io.*;

/**
 * This class constructs a Store object which allows Person objects to be added to it, it also implements
 * Serializable which can save a store object to a file
 * <p>
 * Functions include: receiving a count of the number of elements in a store (number of Person objects), 
 * adding a Person object to a Store, retrieving date such as is the Store empty, full, get the first
 * element in the store, the last element in the store, decrementing to the previous element in the store, incrementing to thethe next element in the store,
 * is a particular person object in the store, the current element of the store, incrementing to the last element of the sore,
 * decrementing to the first element of the store, displaying a particular element of the store (as string) displaying all elements in the store (as string),
 * saving and retrieving a store to and from a file.
 * 
 * @author Jamie Brindle ID: 06352322 *
 */

public class Store
    implements Serializable {

    private static final long serialVersionUID = 1L;
    private Person list [];
    private int count;
    private int maxSize;
    public int current = 0;
    // constructor

    /**
     * Construct the store
     */
    public Store() {
    }

    /** 
     * Construct a store to a maximum value
     * @param max Maximum store capacity (int)
     */
    public Store(int max) {
        // creates array of length max;
        // sets count to 0; maxSize to max
        list = new Person[max];
        maxSize = max;
        count = 0;
    }
    
    // transformer
    /**
     * Adds a Person object to a store
     */
    public void add(Person p) {
        if(isFull()) {		//if current == max (if store full)
            System.out.println("Store is full");
        }

        else {
            list[count] = p;
            count++;
        }
    }

    // accessor
    /**
     * Checks to see if the store is full
     * @return FALSE is count (int) is not equal to the maxSize (int) TRUE otherwise
     */
    public boolean isFull() {
        // post returns true if no more room in store
        return count == maxSize;
    }

    /**
     * Checks to see if the focus is on the first element of the store (current == 0)
     * @return TRUE if current ==0, FALSE otherwise
     */
    public boolean isFirst() {
        // post returns true if current record is first record
        return (current == 0);
    }

    /**
     * Checks to see if the focus is on the last element of the store (current+1 == count)
     * @return TRUE if current +1 == count, FALSE otherwise
     */
    public boolean isLast() {
        // post returns true if current record is last record
        return (current + 1 == count);
    }
    
    /**
     * Checks to see if the store is empty (count == 0)
     * @return TRUE if count == 0, FALSE otherwise
     */
    public boolean isEmpty() {
        // returns true if store is empty
        return count == 0;
    }
    /**
     * Gets the count (how many elements) are held in the store
     * @return count (int)
     */
    public int getCount() {
        // returns the number of elements currently in store
        return count;
    }

    /**
     * Checks to see if a particular Person object is in the store
     * @param p The given Person object
     * @return TRUE if the given Person object is in the store, FALSE otherwise
     */
    public boolean isIn(Person p) {
        if(isEmpty()) {
            return false;
        }

        else {
            for(int i = 0; i < count; i++) {  //go through each element of store
                if(list[i].equals(p)) {    //if person in store equals given person
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Returns the current Person the store (current person defined by current (int) )
     * @return list[current] - current person
     */
    public Person currentRecord() {
        return list[current];
    }

    /**
     * Increments the element the store is focusing on (defined by current (int) )
     * @param q An int from another class or method so that the other class can keep its own currrent record (int)
     * @return this current record
     */
    public int incrementCurrentPointer(int q) {

        current = q;		//sets the current to given int
        return current;		//returns it back
    }

    /**
     * Decrements the element the store is focusing on (defined by current (int) )
     * @param q An int from another class or method so that the other class can keep its own current record (int) )
     * @return this current record
     */
    public int decrementCurrentPointer(int q) {

        current = q;		//sets this current to given int
        return current;		//returns it back
    }

    /**
     * Displays a particular element a store corrisponding to the given int i
     * @param i Given int to display a record of a particular place in the list
     */
    public void displayOne(int i) {

        System.out.println(list[i - 1]);
    }

    /**
     * Set the current to the first record pointer
     */
    public void firstRecordPointer() {
        current = 0;
    }

    /**
     * Set the current to the last record pointer
     */
    public void lastRecordPointer() {
        current = count;
        current--;
    }

    /**
     * Display all the elements in a store (as String)
     */
    public void displayAll() {
        // displays the contents of store on screen
        for(int index = 0; index < count; index++) {
            System.out.println(list[index]);
        }
    }

    /**
     * Save a Store object to a file
     * @param filename The given file name (String) another class or method wishes to use
     * @throws e If any problems occur while trying to save (e.g. directory read only)
     * @throws ieo if there were any problems trying to close the output stream or flush the output stream
     */
    public void fileOut(String filename) {
        ObjectOutputStream oos = null;	//initialise objectOutputStream

        try {
            oos = new ObjectOutputStream(new FileOutputStream(filename));  //set output stream
            oos.writeObject(Store.this);	//write the store to a file
        }

        catch (Exception e) {  //if any errors occur catch them
            String errMessage = e.getMessage();
            System.out.println("Error: " + errMessage);  //print out the error message to console
        }

        finally {
            if(oos != null) {
                try {
                    oos.flush();	//flush object output stream (clear memory)
                }

                catch (IOException ioe) {
                }

                try {
                    oos.close();	//close object output stream
                }

                catch (IOException ioe) {
                }
            }
        }
    }

    /**
     * Retrieve a Store object from a file
     * @param fileName The file name string given that another method or class wishes to use
     * @return temp (Store)
     * @throws e If there was a problem reading the file (e.g. file corrupt or altered file name)
     * @throws ioe If there were any problems trying to close the object input stream
     */
    public Store fileIn(String fileName) {
        ObjectInputStream oi = null;	//initialise object input stream
        Store temp = null;		//initialise a new store called temp

        try {		//try to retrieve the object from the file
            FileInputStream fi = new FileInputStream(fileName);
            oi = new ObjectInputStream(fi);		
            temp = (Store)oi.readObject();		//cast the Store retrieved from the file to the temp Store
        }

        catch (Exception e) {		//if any errors occur, catch them
            String error = e.getMessage();
            System.out.println("Error: " + error);	//print out an error message to the console
        }

        finally {
            if(oi != null) {
                try {
                    oi.close();		//close object input stream
                }

                catch (IOException ioe) {
                }
            }
        }
        return temp;	//return the temp store
    }
}