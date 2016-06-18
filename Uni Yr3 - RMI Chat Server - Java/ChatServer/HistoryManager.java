import java.util.*;
import javax.swing.*;

/**
 * This class is used to save and retrieve user chat history. It's designed so
 * that a user when connecting to a room can view the last xx messages sent between
 * users in order for them to start chatting right away and can identify the subject
 * at hand. The history size (in lines) can be altered in the serverOptions.txt
 * file
 * 
 * @author Jamie Brindle (06352322), Enterprise Programming, Level 3
 */
public class HistoryManager extends JFrame {
	
    private static final long serialVersionUID = 1L;

    private String history;
    private ArrayList<String> historyArray;
	
    //declare max history size. serverOptions.txt looked up
    private ServerOptions serverOptions = new ServerOptions();
    private int maxHistorySize = serverOptions.getMaxHistorySize();  
    private Connector connector = new Connector();
       
    /**
	 * Constructs the history manager
	 */
    public HistoryManager() {}	
    	
	/**
	 * Simply invokes the connector to retrieve the history from the mysql
	 * database and pass it to the chatter class
	 */
    public String getHistory(int roomID) {
		
        history = connector.retrieveHistory(roomID);
        return history;		
    }
    
    /**
	 * Saves the history in the mysql database. If the current history size
	 * (in lines) is less than 10, then the strings can be saved to the mysql
	 * database immediately. However, as there is a maximum save history, the method
	 * takes the last xx messages sent and saves them by deriving them from a larger
	 * string, in which an array is used to store each line, remove the first line
	 * beyond is maximum, transfer back to a string then saved in the mysql database
	 */
    public void setHistory(int roomID, String text, int lineCount) {
		
        historyArray = new ArrayList<String>();
		
        //less than max save size
        if (lineCount <= maxHistorySize) {
            connector.storeHistory(roomID, text);
		
         //large than max save size
        } else {
        	
        	//split the string, save into an array
            String[] tempArray = text.split("\r\n|\r|\n");
            
            //convert to array list
            for (int i = 0; i < tempArray.length; i++) {
				
                historyArray.add(tempArray[i]);
			
            }
            //reduce to max save size
            while (historyArray.size() >= maxHistorySize) {
				
                historyArray.remove(0);
			    
            }
			
            //convert back to string
            String[] tempString = new String[maxHistorySize - 1];
            String newString = "";
				
            historyArray.toArray(tempString);
				
            for (int i = 0; i < tempString.length; i++) {
				
                newString = newString + tempString[i] + "\n";
				
            }
			
            //save into mysql database
            connector.storeHistory(roomID, newString);
			
        }
    }
	
}