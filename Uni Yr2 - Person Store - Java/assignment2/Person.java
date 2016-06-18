package assignment2;

import java.io.*;

/**
 * This class constructs a person object, which can include variables such as name, gender, date of birth,
 * address, national insurance number and a photo string (simple a string referring to a file location)
 * 
 * @author Jamie Brindle ID: 06352322
 */

public class Person
    implements Serializable {

    private static final long serialVersionUID = 1L;
    protected String name;       // String
    protected char gender;       // char
    protected Date dateOfBirth;  // class Date
    protected String address;    // String
    protected String natInsceNo; // String
    protected String phoneNo;    // String
    private static int counter;  // integer
    private String photo;

    // constructors
    /**
     * Construct Person object
     */
    public Person() {
        // post dateOfBirth, name, address, natInscNo  != null;
        // counter incremented
        dateOfBirth = new Date();
        name = " ";
        address = " ";
        natInsceNo = " ";
        phoneNo = " ";
        counter++;
    }
    
    /**
     * Constructs a person with a specific name and national insurance number
     * @param name name of the person (String)
     * @param ni national insurance number of the person (String)
     */
    public Person(String name, char gender, Date dob, String ni) {
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dob;
        this.natInsceNo = ni;
    }
    /**
     * Transforms person
     * @param other The given person object
     */
    public Person(Person other) {
        this(other.name, other.gender, other.dateOfBirth, other.natInsceNo);
    }
    
   /**
    * Transformers, set the person object with given variables
    * @param other The given person object
    */
    public void copy(Person other) {
        this.name = other.name;
        this.gender = other.gender;
        this.dateOfBirth = other.dateOfBirth;
        this.natInsceNo = other.natInsceNo;
        this.address = other.address;
        this.phoneNo = other.phoneNo;
        this.photo = other.photo;
    }

    /**
     * Set the address of a Person object
     * @param addr Given address string
     */
    public void setAddress(String addr) {
        this.address = addr;
    }
    
    /**
     * Set the phone number the Person object
     * @param phone Given phone number string
     */
    public void setPhone(String phone) {
        this.phoneNo = phone;
    }
    
    /**
     * Set the name of the Person object
     * @param name Given name string
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Set the gender of the Person object
     * @param gender Given gender (char)
     */
    public void setGender(char gender) {
        this.gender = gender;
    }

    /**
     * Set the national insurance number of the Person object
     * @param ni Given national insurance number (string)
     */
    public void setNatInsceNo(String ni) {
        this.natInsceNo = ni;
    }
    
    /**
     * Set the string corrispoding to the location of a persons photo to Person object
     * @param photoString Given photo location (String)
     */
    public void setPhoto(String photoString) {
        this.photo = photoString;
    }
    
    /**
     * Set the date of birth of the person to the Person object
     * @param dob Given date of birth (Date object)
     */
    public void setDOB(Date dob) {
        this.dateOfBirth = dob;
    }
    
    /**
     * Get the date of birth of the Person object
     * @return dateOfBirth
     */
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Gets the gender of the Person object
     * @return gender (String)
     */
    public String gender() {
        if(gender == 'm' || gender == 'M')return "Male";

        if(gender == 'f' || gender == 'F')return "Female";

        else return "Invalid Gender Input";
    }

    /**
     * Get the gender of the Person object
     * @return gender (char)
     */
    public char getGender() {
        return gender;
    }

    /**
     * Get the name of the Person object
     * @return name (String)
     */
    public String getName() {
        return name;
    }

    /**
     * get the photo string of the Person object
     * @return photo (String)
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * Get the address of the Person object
     * @return address (String)
     */
    public String getAddress() {
        return address;
    }

    /**
     * Get the phone number of the Person object
     * @return phoneNo (String)
     */
    public String getPhone() {
        return phoneNo;
    }

    /**
     * Get the national insurance number of the Person object
     * @return natInsceNo (String)
     */
    public String getNatInsceNo() {
        return natInsceNo;
    }

    /**
     * Compares the person object to another
     * @param other (given person)
     * @return true of the name and national insurance number matches, false otherwise
     */
    public boolean equals(Person other) {
        //true if name and national insurance number are same
        return name.equals(other.name) && natInsceNo.equals(other.natInsceNo);
    }

    /**
     * Gives a count of the number of instances of the class (or person records)
     * @return counter (int)
     */
    public static int count() {
        // post returns count of number of instances of class
        // person that have been created
        return counter;
    }

    /**
     * return the Person object represented as a string consisting of all variables of a Person object
     *@return String
     */    
    public String toString() {
        return "\n Name: " + name + "\n Address: " + address + "\n Gender: " + gender() + "\n Nat Insurance No: "
            + natInsceNo + "\n Telephone No: " + phoneNo + ' ' + "\n Date of Birth: " + dateOfBirth
            + "\n Photo Location: " + photo;
    }
}