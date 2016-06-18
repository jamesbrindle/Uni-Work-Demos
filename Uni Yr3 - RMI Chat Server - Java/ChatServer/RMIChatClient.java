import java.rmi.*;

/**
 * RMIChatClient Method declaration class
 * 
 * @author Jamie Brindle (06352322), Enterprise Programming, Level 3
 */
public interface RMIChatClient extends Remote {

    /**
     *  allows the sending of a message to the client
     */
    public void dispatchMessage(String theMessage) throws RemoteException;
    
    /**
	 *  Allows a user to appear online in an online user list
	 */
    public void ShowOnline(String theUser) throws RemoteException;
    
    /**
	 *  Allows a user to appear offline in an online user list
	 */
    public void ShowOffline(String theUser) throws RemoteException;

}
