package assignment2;

import java.io.*;

/**
 * This class constructs a specific date consisting of 3 integers (day, month, year).
 *It allows a date to be compared against another, returns a string of the dates including
 *converting the month number string to a worded month string (e.g. 01 to 'January') and can
 *also compare if a date to be early than a given date
 *
 *@author Jamie Brindle ID: 06352322
 */

public class Date
    implements Serializable {

    private static final long serialVersionUID = 1L;
    public int day, month, year;

    //constructors
    /**
     * Construct Date object
     */
    public Date() {
        this(0, 0, 0);
    //day = month = year = 0
    }

    /**
     * Construct date object from given parameters
     * @param dd Day (int)
     * @param mm Month (int)
     * @param yy Year (int)
     */
    public Date(int dd, int mm, int yy) {
        //day = dd, month = mm; year = yy
        day = dd;
        month = mm;
        year = yy;
    }

    /**
     * Transforms date
     * @param other Date object
     */
    public Date(Date other) {
        this(other.day, other.month, other.year);
    }
    // transformers

    /**
     * Transformers of Date obejct
     */
    public void copy(Date other) {
        this.day = other.day;
        this.month = other.month;
        this.year = other.year;
    }

    //accessors
    /**
     * Converts an integer month to its corrisponding worded month string for output purposes
     * i.e '1' becomes 'January' or invalid if an invalid month is set
     */
    public String monthAsString() {
        //Partial implementation to be completed
        switch(month) {
            case 1:
                return "January";

            case 2:
                return "February";

            case 3:
                return "March";

            case 4:
                return "April";

            case 5:
                return "May";

            case 6:
                return "June";

            case 7:
                return "July";

            case 8:
                return "August";

            case 9:
                return "September";

            case 10:
                return "October";

            case 11:
                return "November";

            case 12:
                return "December";

            case 13:
                return "Invalid Month";

            default:
                return "Invalid Month";
        }
    }

    /**
     * Boolean - compare a given date
     * @param other
     * @return true if the the given date is equal to one in the store
     */
    public boolean equals(Date other) {
        //return true if day, month and year are all equal
        //to correspond fields in other
        return day == other.day && month == other.month && year == other.year;
    }
    
/**
 * Get the day integer
 * @return day (int)
 */
    public int getDay() {
        return day;
    }

 /**
  * get the month integer
  * @return month(int)
  */
    public int getMonth() {
        return month;
    }
    
 /**
  * get the year int
  * @return year (int)
  */
    public int getYear() {
        return year;
    }

   /**
    * Method used to output the day as a string, with corrisponding month int converted to worded string int
    *@return Date as a string representative
    */
    public String toString() {
        return day + " " + monthAsString() + " " + year;
    }

    /**
     * Compares a date to another, returning true if the given date is earlier
     * @param other The given Date object
     * @return true if given date other is earlier
     */
    public boolean earlierThan(Date other) {
        if(this.year < other.year) {
            return false;
        }

        if(this.year > other.year) {
            return true;
        }

        if(this.month < other.year) {
            return false;
        }

        if(this.month > other.year) {
            return true;
        }

        if(this.day < other.day) {
            return false;
        }

        if(this.day > other.day) {
            return true;
        }

        else {
            return false;
        }
    }
}