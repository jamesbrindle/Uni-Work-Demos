package assignment2;

import java.io.*;

/**
 * The class is a simple Test file, for solving problems whilst in the process of creating the GUI class
 * This class is also used for re-entering information into a file containing a Store object, again for test
 * purposes
 * 
 * @author Jamie Brindle ID: 06352322
 */

public class Test
    implements Serializable {

    private static final long serialVersionUID = 1L;

    public static void main(String [] args) {

    // set up Person object P1    
    	Date d1 = new Date(07, 07, 85);
        Person p1 = new Person("Jamie", 'M', d1, "JS 02 22 75D");
        p1.setAddress("Flat 3, 55 Richmond Grove");
        p1.setPhone("07725891514");
        p1.setPhoto("images/jamie.jpg");
        
    // set up Person object P2    
        Date d2 = new Date(03, 07, 88);
        Person p2 = new Person("Danielle", 'F', d2, "PP 3322 65F");
        p2.setAddress("Flat 3, 55 Richmond Grove");
        p2.setPhone("07323232323");
        p2.setPhoto("images/danielle.jpg");
        p2.setGender('f');
        
     // set up Person object P3  
        Date d3 = new Date(03, 04, 88);
        Person p3 = new Person("Benjamin", 'M', d3, "GG 7654 98D");
        p3.setAddress("66 Marsden Hall Road, North, Nelson");
        p3.setPhone("07435345353");
        p3.setGender('M');
        
     // set up Person object P4    
        Date d4 = new Date(03, 04, 88);
        Person p4 = new Person("Henry", 'M', d4, "HH 45 6567 D");
        p4.setAddress("66 Marsden Hall Road, North, Nelson");
        p4.setPhone("07435345353");
        p4.setGender('M');

        Store s1 = new Store(4);	// Create a new store of a maximum value of 4

     // add to Person object to the store   
        s1.add(p1);
        s1.add(p2);
        s1.add(p3);
        s1.add(p4);
        s1.add(s1.currentRecord());
        s1.fileOut("Person.dat");   //save the store to the file "Person.dat"

        Store temp = new Store();	// create a new store called temp
        temp = temp.fileIn("Person.dat");	// catch the store retrieved to a new store called temp

        temp.displayAll();		//display all the elements (Persons) in the store
        System.out.println(temp.getCount());	//display a count of all the elements in the store

        String s;	
        s = temp.currentRecord().getName();		//display the name of the person in the current element of the store
        System.out.println(s);

        Person persontemp = new Person("Jamie",'M', d1, "JS 02 22 75D");
        System.out.println(temp.isIn(persontemp));  //display a boolean of whether or not the Person tempPerson is contained anywhere in the store

        System.out.println(temp.currentRecord().getPhoto());  //display the string of the location of the person photo
    }
}