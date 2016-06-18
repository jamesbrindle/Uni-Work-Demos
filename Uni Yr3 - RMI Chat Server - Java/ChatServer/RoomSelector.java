import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

/**
 * This is the first class which is run even though the main runnable class is the chatter
 * class. The chatter class first creates an instance of the RoomSelector which retrieves
 * a list of rooms available on the server from a mysql database that clients can connect to.
 * This class also allows a user to give a desired screen name and also allows the
 * user to connect to a pre-defined server address (as found and is also editable in the
 * serverOptions.txt file, or the user can enter a server address manually.
 * 
 * @author Jamie Brindle (06352322), Enterprise Programming, Level 3
 *
 */
public class RoomSelector extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private Panel serverNamesPanel, TopPanel, BottomPanel;
	
    private FlowLayout flow;
    private GridLayout grid;	
	
    private JLabel Message, screenNameLabel;
	
    private JButton dynButton;
	
    private JTextField screenNameField;
	
    private String[] serverList;	//array list of room names
    private String room, screenName;	
    private Connector connector;	//deals with connecting to the mysql database

    /**
     * Constructs RoomSelector GUI
     */
    public RoomSelector() {

        setTitle("Room Selector");
		
        Container container = this.getContentPane();

        container.setLayout(new BorderLayout());
		
        serverNamesPanel = new Panel();
        TopPanel = new Panel();
        BottomPanel = new Panel();
		
        flow = new FlowLayout();
        grid = new GridLayout(0, 3);
		
        serverNamesPanel.setLayout(grid);
        BottomPanel.setLayout(flow);
	
        connector = new Connector();
        serverList = connector.receiveServers();
				
        //dynamically creates a list of buttons which correspond to rooms
        //selectable by the user, dynamically created by retieving the list
        //of rooms from the mysql database
        for (int i = 0; i < serverList.length; i++) {
            if (serverList[i] != null) {
				
                dynButton = new JButton(serverList[i]);
                serverNamesPanel.add(dynButton);
                dynButton.addActionListener(this);
															
            }			
        }
		
        //the 'manual server address entry button'
        dynButton = new JButton("Manual Server Entry");
        serverNamesPanel.add(dynButton);
        dynButton.addActionListener(this);
		
        String message = null;
		
        //if connection problems... show this message. the RMIClient and
        //server may still run without a connection to the mysql database
        //so the 'manual entry' button is still visible. other buttons aren't
        if (serverList[0] == null) {
            message = "Sorry there either seems to be a problem with the "
                    + "database or the server connection";
			
        //if connection ok, show this message    
        } else {
            message = "Please chose a desired room";
        }
        Message = new JLabel(message);
		
        screenNameLabel = new JLabel("Desired screen name:");
        screenNameField = new JTextField(15);
			
        TopPanel.add(Message);
        BottomPanel.add(screenNameLabel);
        BottomPanel.add(screenNameField);
				
        container.add(TopPanel, BorderLayout.NORTH);
        container.add(serverNamesPanel, BorderLayout.CENTER);
        container.add(BottomPanel, BorderLayout.SOUTH);
	
        pack();

        // window listener set to listen for window closing and then
        //execute the close operation
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {

                setVisible(false);
                dispose();
                System.exit(0);
            }
        });
				
    }
    
   /**
    * Re-defines the action performed method
    * if the text field where the user enters their desired screen name
    * is blank, a pop up will appear asking for users to enter a screen
    * name as one must be given
    * pop up will not disappear until screen name is given
    */
    public void actionPerformed(ActionEvent e) {
        room = e.getActionCommand();
        if (screenNameField.getText().equalsIgnoreCase("")) {
            while (screenName == null || screenName.equalsIgnoreCase("")) {
                screenName = JOptionPane.showInputDialog(this,
                        "Please give screen name", "Chatter",
                        JOptionPane.INFORMATION_MESSAGE);
            }
		
         //if screen name has been entered
        } else {
            screenName = screenNameField.getText();
        }
		
        //activate the chatter GUI and hide the room selector GUI
        setVisible(false);
        Chatter chat = new Chatter();

        chat.setLocationRelativeTo(null);
        chat.setVisible(true);
        chat.connectToServer();
						
    }
	
    //for the chatter class to retrieve the room name selected by the user
    public String getRoomName() {
        return room;
    }
	
    //for the chatter class to retrieve the screen name input by the user
    public String getScreenName() {
        return screenName;
    }
	
}
