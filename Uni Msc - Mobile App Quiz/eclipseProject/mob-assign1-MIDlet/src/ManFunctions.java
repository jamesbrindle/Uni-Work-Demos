import java.io.InputStream;
import java.util.Vector;

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
	 * Compares two strings to see if one stream is the same or a substring of another
	 * Created as the J2ME library doesn't contain a method for doing this
	 * @param full is the String we're comparing to
	 * @param searched is the String we're comparing with
	 * @return true if the two strings are the same or one is a subset of another
	 */
	protected boolean contains(String full, String searched) {

		if (full.indexOf(searched) != -1) {
			return true;
		} else {
			return false;
		}
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
	 * the class 'Regex' is not included in the J2ME library
	 * @param aString is the string we want to split
	 * @param splitCondition - identifies where to split the string
	 * @return result which is an array
	 */
	protected String[] splitString(String aString, String splitCondition) {
		Vector nodes = new Vector();
		String separator = splitCondition;

		int index = aString.indexOf(separator);

		while (index >= 0) {
			nodes.addElement(aString.substring(0, index));
			aString = aString.substring(index + separator.length());
			index = aString.indexOf(separator);
		}

		nodes.addElement(aString);

		String[] result = new String[ nodes.size() ];

		if (nodes.size() > 0) {
			for (int loop = 0; loop < nodes.size(); loop++) {
				result[loop] = (String) nodes.elementAt(loop);
			}

		}

		return result;
	}

	/**
	 * Takes a String and splits it where the colon (:), symbol is found
	 * the class 'Regex' is not included in the J2ME library
	 * @param aString is the string we want to split
	 * @return result which is an array
	 */
	protected String[] splitString(String aString) {
		String[] result = splitString(aString, ":");
		return result;
	}

	/**
	 * Takes a String and returns a true or false boolean value
	 * depending on whether the String contains only numeral digits
	 * or not
	 * @param aString is the string we want to test
	 * @return true if the String only contains numeral digits
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
