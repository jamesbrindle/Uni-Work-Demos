import java.rmi.*;

/**
 * RMIChatServer Method declaration class
 * 
 * @author Jamie Brindle (06352322), Enterprise Programming, Level 3
 */
public interface RMIChatServer extends Remote {
	
	 /**
	 * allows clients to connect to the server and be registered
	 */
    public void connect(RMIChatClient theClient, int roomID) throws RemoteException;

    /**
	 * allows clients to send messages to the server, where they are
	 * filtered and dispatched to the appropriate clients/rooms
	 */
    public void sendMessageToServer(String theMessage, int roomID) throws RemoteException;
	
      /**
	 * allows clients screen names to be updated on each other clients online user list
	 */
    public void updateUsers(int roomID, String screenName) throws RemoteException;
	
    /**
	 * allows clients to remove their screen names from the online user list
	 */
    public void removeUsers(int roomID, String screenName) throws RemoteException;

}
