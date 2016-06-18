import java.rmi.*;
import java.rmi.server.*;
import javax.swing.*;


/**
 *  This class implements an RMI Chat Client
 *  It is intended to run under RMI technology and receives
 * 	messages from connected servers
 *  Methods are available to send messages  *
 *
 *   @author Jamie Brindle (06352322), Enterprise Programming, Level 3
 */
public class RMIChatClientImpl extends UnicastRemoteObject implements RMIChatClient {

    private static final long serialVersionUID = 1L;

    //declare the sink in which is actually the reference to the receivedMessages
    //text area in the chatter class
    JTextArea messageSink, onlineUserSink;
    private String newString;	//used in removing users
    private String[] tempStringArray;	//used in removing users

    /**
	 * Constructs RMIchatClientImpl. References the clients sink
	 * in order to allow the transfer of messages
	 */
    public RMIChatClientImpl(JTextArea theSuppliedSink, JTextArea theSuppliedSink2) throws RemoteException {

        messageSink = theSuppliedSink;
        onlineUserSink = theSuppliedSink2;
		
    }

    /**
	 * Used to retrieve the message from the server dispatcher and show
	 * on clients received messages text area
	 */
    public void dispatchMessage(String theMessage) throws RemoteException {
	
        messageSink.append(theMessage + "\n");
		
    }
    
    /**
	 * Used to remove users from the clients user list sink. The client
	 * Sends a message to the server calling this method which then dispatches
	 * the message all other RMI clients corresponding to the room to remove
	 * the screen name from their user list
	 */
    public void ShowOffline(String theUser) throws RemoteException {
		
        if (onlineUserSink.getText().contains(theUser)) {
            newString = "";
            tempStringArray = new String[onlineUserSink.getLineCount()];
            
            //split string, put into array
            tempStringArray = onlineUserSink.getText().split("\r\n|\r|\n");
			
            //iterate through array
            for (int i = 0; i < tempStringArray.length; i++) {
					
            	//construct new string without given screen name
                if (!(tempStringArray[i].contains(theUser))) {
                    newString = newString + tempStringArray[i] + "\n";
                }				
					
            }
            //reset the sink
            onlineUserSink.setText("");
				
            //supply the new list of online users
            onlineUserSink.setText(newString);
        }
    }
	
    /**
     * When the RMIChatServer calls this method, it sends a message
     * containing the screen name in which to add to the sink, the method
     * then appends the clients online users sink containing the new user
     * As the chatter class continually updates the sink for new coming
     * of users, the method needs to make sure duplicate online users
     * are not appended to the sink
     */
    public void ShowOnline(String theUser) throws RemoteException {	
				
    	//make sure online user name doesn't already exist
        if (!(onlineUserSink.getText().contains(theUser + "\n"))) {
        	
        	//if not then add the online user to the sink
            onlineUserSink.append(theUser + "\n");
        }
			
    }
	
}
