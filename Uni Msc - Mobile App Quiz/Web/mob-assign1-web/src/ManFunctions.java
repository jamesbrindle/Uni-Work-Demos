import java.io.InputStream;
import java.util.Vector;
import java.util.regex.Pattern;

/**
 * ManFunctions (Manual/Manipulation Function) class contains re-usable methods which generally
 * manipulate Strings and also check their content. 
 * 
 * @author Jamie Brindle (06352322) - Msc - Manchester Metropolitan University
 */
public class ManFunctions {

	/**
     * The Manfunctions constructor - takes no parameters
     */
    protected ManFunctions() {}
    
    /**
     * The database doesn't contain a list of weeks for a particular subject so 
     * a query has to be done on the database to see which week is associated with each question
     * which comes back as a large list with lots of duplicate week numbers. This method
     * removes the duplicate and as a header 'week' to each week item and returns it as a single
     * string with each week seperated via the colon (:) symbol
     * @param weeksVector is the vector containing the full list of raw weeks
     * @return weeksList - The string containing the sorted weeks
     */
    protected String sortWeeks(Vector<Integer> weeksVector) {

        String weeksList = "";

        if (weeksVector.size() > 0) {

            for (int i = 0; i < weeksVector.size(); i++) {
                if (!(weeksList.contains(weeksVector.get(i).toString()))) {
                    weeksList = weeksList + "Week "
                            + weeksVector.get(i).toString() + ":";
                }								
            }
        }

        return weeksList;
    }
    
    /**
     * Reads the contents of an entire text file into a single String and returns an array
     * @param fileName is the filename and location of the text file we want to read
     * @return inputArray each index of the array contains a line of the text file
     */			 
    protected String[] readFile(String fileName) {

        String inputString = "";
        String[] inputArray;

        InputStream is = getClass().getResourceAsStream(fileName);
        StringBuffer sb = new StringBuffer();

        try {
            int chars;

            while ((chars = is.read()) != -1) {
                sb.append((char) chars);
            }
            inputString = sb.toString();
            inputArray = splitString(inputString, "\n");

            return inputArray;
        } catch (Exception e) {}
        return null;
    }
    
    /**
     * Takes a String and splits it where the particular character is found and is
     * stored in an array. 
     * @param aString is the string we want to split
     * @param splitCondition - identifies where to split the string
     * @return items which is an array
     */
    protected String[] splitString(String aString, String splitCondition) {
        String REGEX = splitCondition;

        Pattern p = Pattern.compile(REGEX);
        String[] items = p.split(aString);

        return items;
    }
    
    /**
     * Takes a String and splits it where the colon (:), symbol is found
     * @param aString is the string we want to split
     * @return items which is an array
     */
    protected String[] splitString(String aString) {
    	String [] items = splitString(aString, ":");
    	return items;
    }
      
    /**
     * Takes a String and and tests whether it contains only numeral digits or not
     * returning a boolean value
     * @param aString is the string we want to split
     * @return true if the string only contains numbers
     */
    public boolean containsOnlyNumbers(String aString) {

		if (aString == null || aString.length() == 0) {
			return false;
		}

		for (int i = 0; i < aString.length(); i++) {

			if (!Character.isDigit(aString.charAt(i))
				 && aString.charAt(i) != '.') {
				return false;
			}
		}
		return true;
	}   
}
