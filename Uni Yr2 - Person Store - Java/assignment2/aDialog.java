package assignment2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This class constructs a dialogue with a specified title and message. These are set when calling the method
 * The buttons are predefined to 'Yes' and 'No' which set a global integer to either 0
 * or 1 depending on the choice made which can then be retrieved by the calling class
 * <p>
 * Examples (and which are used) of calling this class include a warning function to the user when exit is selected
 * Prompting a user to to overwrite a file or not
 * Prompting a user if the wish to save or not 
 * 
 *  @author Jamie Brindle ID: 06352322
 */
   
public class aDialog extends JDialog
    implements ActionListener {
	

    private static final long serialVersionUID = 1L;

    JButton yesButton, noButton;
    public int choice;

    /** This method constructs the frame (or dialogue)
     */
    public aDialog(JFrame parent, String title, String message) {

        super(parent, title, true);

        setResizable(false);	//lock the size of the frame
    //set the location of the frame...        
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        setLocation(screenWidth / 4, screenHeight / 4);
    //...        
        JPanel messagePane = new JPanel();
        messagePane.add(new JLabel(message));
        getContentPane().add(messagePane);
        JPanel buttonPane = new JPanel();
        yesButton = new JButton("Yes");
        noButton = new JButton("No");
        buttonPane.add(yesButton);		//add button to panel
        buttonPane.add(noButton);		
        yesButton.addActionListener(this);
        noButton.addActionListener(this);
        getContentPane().add(buttonPane, BorderLayout.SOUTH);	//add panel to frame
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);	//show the frame
    }
/**
 * Get the choice integer that has been set (on selection of a button)
 * @return choice integer
 */
    public int getChoice() {
        return choice;
    }
    
/**
 * set the choice integer to a particular value
 * @param c value of selection
 */
    public void setChoice(int c) {
        this.choice = c;
    }

/**
 * effects of particular action listeners
 */
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == yesButton) {
            this.setChoice(1);
            setVisible(false);	//after selection, close the frame
            dispose();
        }

        if(e.getSource() == noButton) {
            this.setChoice(0);
            setVisible(false);	//after selection, close the frame
            dispose();
        }
    }
}