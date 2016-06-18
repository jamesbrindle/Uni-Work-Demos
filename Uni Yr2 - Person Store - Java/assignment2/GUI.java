package assignment2;

import java.io.File;
import java.io.Serializable;
import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.util.regex.Pattern;

 /**
  * This class provides a GUI (graphical user interface) linking the Person, Date, aDialog, Store and TextFilter classes
  * It allows a record of people to be created, edited and loaded to and from a file along with a image of that person.
  * 
  * @author Jamie Brindle ID: 06352322
 */

public class GUI extends JFrame
    implements ActionListener, Serializable {

 
    private static final long serialVersionUID = 1L;
    
//Declarations-----------------------
    Container contentPane;
    GridBagConstraints c, d;
    ImageIcon photo;

    private String photoString = "images/default.jpg";
    private String currentFileName = "";
    private String day, month, year;

    private int monthInt, yearInt, dayInt, mode;
    public int current = 0;
    int counter, error;

    private char sex;

    private JFrame frame;
    private JPanel g, b, i, t;
    private JMenuBar menuBar;
    public JMenu menu;
    private JMenuItem menuItem1, menuItem2, menuItem3, menuItem4, menuItem5;
    private JFileChooser fc, fc2, fc3;
    private JButton start, previous, next, last, newRecord, updatePic;
    JButton save;
    private JLabel title, nameL, addressL, genderL, dobL, niL, phoneL, message, image;
    private JTextField name, gender, dob, ni, phone;
    JTextArea address;
    private JScrollPane jScrollPane1;

    private Store temp = new Store(); //stores from imported files are cast to this store
    private Store backup = new Store(); //used to revert back to a safe store if any problems
    private Store temp2; //used for convert temp to a larger store and add new records

    private File file, filePic, saveAs; //used for importing/exporting .dat and .jpg files
    private Color red = new Color(255, 0, 0);

    static GUI frame2;

    public aDialog fileExistsDialog, exitDialog;
    
/**
      * Constructor
      */
    public GUI() {
   
        runGUI();
    }
/**
         * Loads the main graphics into the frame, sets locations and sizes of all objects
         * methods are called for buttons, labels, menus and images
         */
    public void runGUI() {

//Container, Frame, Panels and Layout methods------------------------------        

        frame = new JFrame("Person Records GUI"); //main frame

        contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());

        g = new JPanel();
        b = new JPanel();
        i = new JPanel();
        t = new JPanel();
        t.setLayout(new GridBagLayout());  //title
        b.setLayout(new FlowLayout());  //buttons
        i.setLayout(new GridBagLayout());  //photo
        g.setLayout(new GridBagLayout());  //main labels and text fields
        c = new GridBagConstraints();   //labels and text fields
        d = new GridBagConstraints();   //title

        c.fill = GridBagConstraints.HORIZONTAL; //constraints to horizontal mode setting

        labelsAndTextFields();   //method call to load labels and text fields
        buttons();   //method call to load buttons
        menus();   //method call to load menus
        photoSelector();   //method call to load the photo
        frame.setJMenuBar(menuBar);   //add menu to frame
        frame.setResizable(false);   //don't allow to resize the frame
//to display the frame in the centre of the screen------
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        frame.setLocation(screenWidth / 4, screenHeight / 4);  
 //---       
        frame.pack();   //render layout
        frame.setVisible(true);   //show frame
    }
    
  /**
      * The main run method activated the frame along with its contents
      */
    public static void main(String [] args) {
   
        frame2 = new GUI();
    }
    
    /**
      * Event actions for buttons and menu selection items
      * If the next button is clicked and there's no name field
      * it would indicate that someone has clicked the add new record button and not gone
      * on to fill in the fields, this if statement ensures cohesion of the temp store
      */
    public void actionPerformed(ActionEvent e) {
     
        if(e.getSource() == updatePic) {  //if Add/Update Photo button is clicked
            updatePhoto();
        }

        if(e.getSource() == newRecord) {  //if New Record button is clicked
            newRecordCreate(); 
        }
        
        
        if(e.getSource() == next) {  //if > buttons is clicked
         
            if(name.getText().equalsIgnoreCase("")) {
                temp = backup;
                current = temp.getCount() - 1;
                temp.lastRecordPointer(); //go to end of record, 1 before where the new record would be created
                setRecord();  //method call to reset the text fields of the frame
                updateMessage(); //method call showing the current record numeral
            }

            else if(current==temp.getCount()-1) {  //iff current record is equal to the last record, go to first record (loop effect)
                saveCurrentRecord();                
                current = 0;
                temp.firstRecordPointer();
                setRecord();
                updateMessage();
            }
            
            else {
                saveCurrentRecord(); //save record to ensure no loss of data if a person navigated through the store
                incrementCurrent();  //navigate the the next record
            }
        }
        
        
          //button event to go to previous record in store
          //see 'next' action event          
        if(e.getSource() == previous) {
         
            if(name.getText().equalsIgnoreCase("")) {   //if there could be problems...(similar to next object event)
                temp = backup;
                current = temp.getCount() - 1;
                temp.lastRecordPointer();
                setRecord();
                updateMessage();
            }
            
            else if(current==0) {	//if the current record is equal to the first record, then go to the last record (loop effect)
                saveCurrentRecord();
                current = temp.getCount() - 1;
                temp.lastRecordPointer();
                setRecord();
                updateMessage();            	
            }
            
            else {
                saveCurrentRecord();
                decrementCurrent();
            }
        }
        
        
          //button event to go to the very last record in the store
          //@see 'next' action event          
        if(e.getSource() == last) {
         
            if(name.getText().equalsIgnoreCase("")) {
                temp = backup;
            }

            else {
                saveCurrentRecord();
            }
            current = temp.getCount() - 1;
            temp.lastRecordPointer();
            setRecord();
            updateMessage();
        }
        
          //button event to go to the very first record in the store
          //see 'next' action event         
        if(e.getSource() == start) {
         
          if(name.getText().equalsIgnoreCase("")) {
                temp = backup;
            }

            else {
                saveCurrentRecord();
            }
            current = 0;
            temp.firstRecordPointer();
            setRecord();
            updateMessage();
        }

        if(e.getSource() == menuItem1) {
            newStoreCreate();  //method call to create a new no store (thus new file)
        }

        if(e.getSource() == menuItem2) {
                openFile();  //method call to open an existing store (or file)
            }
        
       
         //if the user has created a new store and has not yet chosen a file name
         //for the store, this ensures a file name is chosen, otherwise simply saves the store      
        if(e.getSource() == menuItem3) {
         
        if(currentFileName.equalsIgnoreCase("Unsaved.dat")) {
                saveFile();
                }
                    else {
                        saveCurrentRecord();
                    }
                }
        
       
           //this is the 'Save As' menu item, this can be used to overwrite existing stores
           //copying stores to different file names or saving the store for the first time        
        if(e.getSource() == menuItem4) {
             
                saveFile();  
        }
        
        
        // exit the frame while insuring the user is sure of their actions            
        if(e.getSource() == menuItem5) {
         
            exitGUI();
        }
    }
    
      /**
      * In order to create a brand new store, the current record is to be saved
      * a new person object to be created to give no null pointers
      * the temp store to revert back to only a single entry
      * clear all text fields ready for input
      * reset the navigation pointer to 0
      * temp = newStore;
         */
    public void newStoreCreate() {
   
     //if a current store is not open, don't try and save, but if it is, do
     if(currentFileName.equalsIgnoreCase("Unsaved.dat")) { 
      saveFile();
     }
     //ensure a persons record is saved if in the process of making a new one or editing a record
     else if(!(currentFileName.equalsIgnoreCase("") && temp.currentRecord().getName().equalsIgnoreCase(""))) {
      saveCurrentRecord();
     }
  //create fresh store and person object-----
        Person p = new Person();
        Store newStore = new Store(1);
        temp = newStore; //cast new store onto temp
        current = 0;
        temp.add(p);
        temp.firstRecordPointer();
        currentFileName = "Unsaved.dat";
 //clear all text fields and images
        name.setText("");
        address.setText("");
        dob.setText("");
        gender.setText("");
        ni.setText("");
        phone.setText("");
        photoString = "images/default.jpg";
        photo = new ImageIcon(photoString);
        image.setIcon(photo);
        setButtonsEnabled(); //enable buttons if user hasn't yet opened a store (there set to disabled as default
        updateMessage(); //update the the 'red message' indicated the current record shown
    }
    
    /**
      * Allows user to open a file with the use of a GUI dialog
      * @throws k if someone loads a corrupt or invalid file for example
      */
    public void openFile() {
        int returnVal = 0;
        fc = new JFileChooser();
        TextFilter tf = new TextFilter(".dat");

        fc.setCurrentDirectory(new File("."));  //set current directory to current working directory (where class file located)
        fc.setFileFilter(tf);
        returnVal = fc.showOpenDialog(menuItem2);

        if(returnVal == JFileChooser.APPROVE_OPTION) { //if a file is selected
            try {
                file = fc.getSelectedFile(); //load the file
                temp = temp.fileIn(file.getName());  //cast the object found inside the file to the temp store
                currentFileName = file.getName(); //update a string to use for saving and opening in the future
                temp.firstRecordPointer(); //go the first record on the store

                setRecord(); //display the record on the frame
                updateMessage(); //update the record point message

                setButtonsEnabled(); //enable buttons for operation

                error = 0; //disable the error int if everything loads ok
            }

            catch (Exception k) {
            }
        }
    }

     /**
      * allows user to save the current file giving them a choice of the file name
      * if the file they are trying to use already exists, then ask the user if they want to overwrite or not
      * @throws k if can't save for reasons such as user rights, read-only directory / file trying to overwrite
      */
    public void saveFile() {
    
        int returnVal3 = 0;
        fc3 = new JFileChooser();
        TextFilter tf3 = new TextFilter(".dat");

        fc3.setCurrentDirectory(new File("."));
        fc3.setFileFilter(tf3);
        returnVal3 = fc3.showSaveDialog(menuItem4);

        if(returnVal3 == JFileChooser.APPROVE_OPTION) {
            try {
                saveAs = fc3.getSelectedFile();

                if(saveAs.exists()) { //if file exists, ask to overwrite or not
                    fileExistsDialog = new aDialog(new JFrame(), "File Name Already Exists",
                        "Would you like to overwrite the existing file?");

                    if(fileExistsDialog.getChoice() == 1) {
                        temp.fileOut(saveAs.getName());
                        currentFileName = saveAs.getName();
                        saveCurrentRecord();
                    }

                    else if(fileExistsDialog.getChoice() == 0) { //if user doesn't want to overwrite...
                        fileExistsDialog.setChoice(1);
                        saveFile();  //then recall this method again to open file chooser to enter a different file name
                    }
                }

                else {  //if file doesn't exist
                    temp.fileOut(saveAs.getName());  
                    currentFileName = saveAs.getName();
                    setRecord();
                }
            }

            catch (Exception k) {
             error=3; //set error int to 3 (cannot write file error)
            }
        }
    }
    
     /**
      * For the action event for menu item 'save record'
      * Saves an individual record in a store after creation or editing
      * but allows the date to be stored and recalled a persistent format along each record of the store
      * @throws k if the user enters a very unusual date format
      */
    public void saveCurrentRecord() {
    
        if(dob.getText().contains("/")) {  //for example entering date(dd/mm/yyyy)
            mode = 1;
        }

        else if(dob.getText().contains("-")) {  //for example entering date (dd-mm-yy)
            mode = 2;
        }

        else {
            mode = 3;  //if someone enters (1 July 99) it needs to be saved as an int...
        }

        try {
        if(mode == 1) {  //will split the date into separate day, month, year integers
            String REGEX = "/";  //define the string split character
            String INPUT = dob.getText(); //define the text to split
            Pattern pat = Pattern.compile(REGEX); //proceed with split
            String [] item = pat.split(INPUT);  //cast split into an array

            for(int i = 0; i < item.length; i++) {
                day = item[0];
                month = item[1];
                year = item[2];
              //converts the string numbers into integer numbers
                dayInt = Integer.parseInt(day);  
                yearInt = Integer.parseInt(year);
                monthInt = Integer.parseInt(month);
            }
            Date dob = new Date(dayInt, monthInt, yearInt);
            temp.currentRecord().setDOB(dob);  //saves the date for working record
        }

        else if(mode == 2) {  //see mode == 1 (above)
            String REGEX = "-";
            String INPUT = dob.getText();
            Pattern pat = Pattern.compile(REGEX);
            String [] item = pat.split(INPUT);

            for(int i = 0; i < item.length; i++) {
                day = item[0];
                month = item[1];
                year = item[2];
                dayInt = Integer.parseInt(day);
                yearInt = Integer.parseInt(year);
                monthInt = Integer.parseInt(month);
            }
            Date dob = new Date(dayInt, monthInt, yearInt);
            temp.currentRecord().setDOB(dob);
        }

        else if(mode == 3) {  //if the 'dob' string contains a non numerical string ie 'July'

            String REGEX = " ";  //see mode == 1 (above)
            String INPUT = dob.getText();
            Pattern pat = Pattern.compile(REGEX);
            String [] item = pat.split(INPUT);

            for(int i = 0; i < item.length; i++) {
                day = item[0];
                month = item[1];
                year = item[2];
                dayInt = removeCharsFromDay(day); //call a method to remove any characters such as 'th' and 'nd' at the end of the day string
                yearInt = Integer.parseInt(year);
                monthInt = stringMonthToInt(month);  //call a method to convert non numeric month strings to integer numbers
            }
            Date dob = new Date(dayInt, monthInt, yearInt);
            temp.currentRecord().setDOB(dob);
        }
        }
        catch (Exception k) {
         
        }
        
     //gender stores as a character to converts a string to character of given sex
        if(gender.getText().equalsIgnoreCase("Male") || gender.getText().equalsIgnoreCase("M")) {
            sex = 'M';
        }

        else if(gender.getText().equalsIgnoreCase("Female") || gender.getText().equalsIgnoreCase("F")) {
            sex = 'F';
        }

        else {
            sex = 'O';  //would display in a field as 'invalid gender entry' if user makes a mistake
        }
      //the rest of the fields are stored as strings anyway so simply import them...
        temp.currentRecord().setName(name.getText());
        temp.currentRecord().setGender(sex);
        temp.currentRecord().setAddress(address.getText());
        temp.currentRecord().setNatInsceNo(ni.getText());
        temp.currentRecord().setPhone(phone.getText());
        temp.currentRecord().setPhoto(photoString);

        temp.fileOut(currentFileName);  //save the current store to current file
        updateMessage();
    }

    /**
     * Method used to exit the GUI, warning messages will appear to make sure they wish to exit
     * or if they haven't saved a file
     */
    public void exitGUI() {  //exit method for exit menu event
        if(currentFileName.equalsIgnoreCase("Unsaved.dat")) {  
         //if store working on not yet saved to a specific file, warn user
            exitDialog = new aDialog(new JFrame(), "Exit", "Are you sure you want to exit without saving your store?");
           
            if(exitDialog.getChoice() == 1) {    //if user aware, close frame
                System.exit(0);
            }
        }

        else if(currentFileName.equalsIgnoreCase(null)) {    //if user not opened a file, warn of frame closing
            exitDialog = new aDialog(new JFrame(), "Exit", "Are you sure you wish to exit?");

            if(exitDialog.getChoice() == 1) {
                System.exit(0);
            }
        }

        else {  //all else, close the frame
            exitDialog = new aDialog(new JFrame(), "Exit", "Are you sure you wish to exit?");

            if(exitDialog.getChoice() == 1) {
                System.exit(0);
            }
        }
    }
    
    /**
      * Creates a new record entry for a person
      * Clears current frame
      * makes the store larger to enable user to add new person
      */
    public void newRecordCreate() {
     //if not already on new record
     if(!(name.getText().equalsIgnoreCase("") && ni.getText().equalsIgnoreCase(null) || dob.getText().equalsIgnoreCase(""))) {
      saveCurrentRecord();
        Person p = new Person();
        backup = temp;
        temp2 = new Store(temp.getCount() + 1);
        temp.firstRecordPointer();
        current = 0;

        for(int i = 0; i < temp.getCount(); i++) {
            temp2.add(temp.currentRecord());
            forceIncrementCurrent(); //increment current counter dispite no record being there
        }
        temp = temp2;
        temp.add(p);
        current = temp.getCount() - 1;  //reset this classes current record location
        temp.lastRecordPointer();
      //clears frame's fields...
        name.setText("");
        address.setText("");
        dob.setText("");
        gender.setText("");
        ni.setText("");
        phone.setText("");
        photoString = "images/default.jpg";
        photo = new ImageIcon(photoString);
        image.setIcon(photo);
        updateMessage();  ///updates record count message
     }
     else {
      
     }
    }
    
    /**
      * enables a user to choice whether to add a photo or not to a persons record
      * can also update the photo to another photo if they wish
      * updatePic action event button
      * @throws k if file trying to import is currupt or the wrong file type
      */
    public void updatePhoto() {
     
        int returnVal2 = 0;
        fc2 = new JFileChooser();
        TextFilter tf2 = new TextFilter(".jpg");

        fc2.setCurrentDirectory(new File("images"));
        fc2.setFileFilter(tf2);
        returnVal2 = fc2.showOpenDialog(updatePic);

        if(returnVal2 == JFileChooser.APPROVE_OPTION) {
            try {
                filePic = fc2.getSelectedFile();
                temp.currentRecord().setPhoto("images/" + filePic.getName()); //sets the file name to the persons record on file
                photoString = temp.currentRecord().getPhoto();   //updates the users photo name on the frame
                photo = new ImageIcon(photoString);  //updates the actual photo
                image.setIcon(photo);  //sets the photo active on the frame
                error = 0; //clears any errors if successful
                updateMessage();
            }

            catch (Exception k) {
                error = 2;  //if unsuccessful displays an error message
            }

            finally {
            }
        }
    }
    
    /**
      * adds the labels and text fields to the frame using the gridlayout
      * constraints (locations, sizes of labels and text fields are set here
      */
    public void labelsAndTextFields() {
        c.insets = new Insets(10, 0, 5, 0);  //sets an invisible border to a field (aesthetic)

        title = new JLabel("Welcome to the Person Records GUI");
        title.setHorizontalAlignment(JLabel.CENTER); //centre alignment
        c.gridx = 0; //grid x location relative to other objects on panel
        c.gridy = 0; //gid y location relative to other objects on panel
        t.add(title, c); //adds label/text field to JPanel

        c.insets = new Insets(10, 0, 10, 0);

        message = new JLabel("No file loaded");
        message.setHorizontalAlignment(JLabel.CENTER);
        message.setForeground(red);
        c.gridx = 0;
        c.gridy = 1;
        t.add(message, c);

        c.insets = new Insets(0, 10, 0, 0);

        nameL = new JLabel("Name:");
        nameL.setHorizontalAlignment(JLabel.RIGHT);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        g.add(nameL, c);

        name = new JTextField(15);
        c.gridx = 1;
        c.gridy = 0;
        g.add(name, c);

        dobL = new JLabel("Date of Birth:");
        dobL.setHorizontalAlignment(JLabel.RIGHT);
        c.gridx = 2;
        c.gridy = 0;
        g.add(dobL, c);
        c.insets = new Insets(0, 10, 0, 10);

        dob = new JTextField(10);
        c.gridwidth = 2;
        c.gridx = 3;
        c.gridy = 0;
        g.add(dob, c);

        c.insets = new Insets(0, 10, 0, 0);

        addressL = new JLabel("Address / Post Code:");
        addressL.setHorizontalAlignment(JLabel.RIGHT);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        g.add(addressL, c);

        c.insets = new Insets(0, 10, 0, 10);

        address = new JTextArea();
        address.setRows(4);
        jScrollPane1 = new JScrollPane(address);
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 4;
        g.add(jScrollPane1, c);

        c.insets = new Insets(0, 10, 0, 0);

        genderL = new JLabel("Gender:");
        genderL.setHorizontalAlignment(JLabel.RIGHT);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 2;
        g.add(genderL, c);

        gender = new JTextField(5);
        c.gridx = 1;
        c.gridy = 2;
        g.add(gender, c);

        niL = new JLabel("National I/N:");
        niL.setHorizontalAlignment(JLabel.RIGHT);
        c.gridx = 2;
        c.gridy = 2;
        g.add(niL, c);

        c.insets = new Insets(0, 10, 0, 10);

        ni = new JTextField(10);
        c.gridwidth = 2;
        c.gridx = 3;
        c.gridy = 2;
        g.add(ni, c);

        c.insets = new Insets(0, 10, 10, 0);

        phoneL = new JLabel("Telephone No:");
        phoneL.setHorizontalAlignment(JLabel.RIGHT);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 3;
        g.add(phoneL, c);

        phone = new JTextField(12);
        c.gridx = 1;
        c.gridy = 3;
        g.add(phone, c);

        contentPane.add(t, BorderLayout.NORTH);  //adds the t panel to the frame
        contentPane.add(g, BorderLayout.CENTER); //add the g panel to the frame
       
    }

    /**
      * adds all the buttons to the panel 'b'
      * sets the buttons dimensions and what text they display
      * sets all the buttons to disabled (unclickable) until a store has been loaded
      * adds action listeners to each button
      */
    public void buttons() {
     
        start = new JButton("<<");
        start.setPreferredSize(new Dimension(start.getMinimumSize()));
        start.setToolTipText("Go to first record");
        start.setEnabled(false);
        b.add(start);  //adds a button to the panel
        start.addActionListener(this);

        previous = new JButton("<");
        previous.setPreferredSize(new Dimension(previous.getMinimumSize()));
        previous.setToolTipText("Previous record");
        previous.setEnabled(false);
        b.add(previous);
        previous.addActionListener(this);

        next = new JButton(">");
        next.setPreferredSize(new Dimension(next.getMinimumSize()));
        next.setToolTipText("Next record");
        next.setEnabled(false);
        b.add(next);
        next.addActionListener(this);

        last = new JButton(">>");
        last.setPreferredSize(new Dimension(last.getMinimumSize()));
        last.setToolTipText("Go to last record");
        last.setEnabled(false);
        b.add(last);
        last.addActionListener(this);

        newRecord = new JButton("New Record");
        newRecord.setPreferredSize(new Dimension(newRecord.getMinimumSize()));
        newRecord.setToolTipText("Add a new record to current store");
        newRecord.setEnabled(false);
        b.add(newRecord);
        newRecord.addActionListener(this);

        updatePic = new JButton("Add / Update Photo");
        updatePic.setPreferredSize(new Dimension(updatePic.getMinimumSize()));
        updatePic.setToolTipText("This button allow you to add a picture to the current record");
        updatePic.setEnabled(false);
        b.add(updatePic);
        updatePic.addActionListener(this);

        contentPane.add(b, BorderLayout.SOUTH);  //add the panel to the frame
    }

    /**
      * adds the drop down menus to the gui and activated action listeners for each menu item
      * sets the text each button displays
      */
    public void menus() {
     
        menuBar = new JMenuBar();
        menu = new JMenu("File");
        menuBar.add(menu);

        menuItem1 = new JMenuItem("New Store");		//create a new store (thus new file)
        menuItem1.addActionListener(this);

        menuItem2 = new JMenuItem("Open Store");	//open a store (thus open a file)
        menuItem2.addActionListener(this);

        menuItem3 = new JMenuItem("Save Store");	//save store (thus save current file) if store unsaved, prompt to save
        menuItem3.addActionListener(this);

        menuItem4 = new JMenuItem("Save Store As");		//save store to a specific file (if file exists, prompt to overwrite or rename)
        menuItem4.addActionListener(this);

        menuItem5 = new JMenuItem("Exit GUI");		//exit GUI, double check this is what the user wants, warn if store not saved
        menuItem5.addActionListener(this);

        //adds each button to the frame
        menu.add(menuItem1);
        menu.add(menuItem2);
        menu.add(menuItem3);
        menu.add(menuItem4);
        menu.add(menuItem5);
    }
    /**
      * this method is called when a file/store is loaded and enables a user to use them
      * before this method call the buttons are disabled (unclickable)
      */
    public void setButtonsEnabled() {
     
        newRecord.setEnabled(true);
        start.setEnabled(true);
        next.setEnabled(true);
        previous.setEnabled(true);
        last.setEnabled(true);
        updatePic.setEnabled(true);
    }

    /**
      * sets the photo image to the east (left) side of the frame
      */
    public void photoSelector() {
        photo = new ImageIcon(photoString);
        image = new JLabel(photo);

        d.insets = new Insets(0, 0, 0, 10);
        i.add(image, d); //adds the image to the panel
        contentPane.add(i, BorderLayout.EAST);  //adds the panel to the frame
    }
    /**
      * If a user enters a month as a 'word' rather than a number, this method converts
      * the month to its corisponding integer
      * @param s the given month string
      * @return s integer 1 - 13 - 13 used if theres an error
      */
    public int stringMonthToInt(String s) {
        if(s.equalsIgnoreCase("January"))return 1;

        else if(s.equalsIgnoreCase("February"))return 2;

        else if(s.equalsIgnoreCase("March"))return 3;

        else if(s.equalsIgnoreCase("April"))return 4;

        else if(s.equalsIgnoreCase("May"))return 5;

        else if(s.equalsIgnoreCase("June"))return 6;

        else if(s.equalsIgnoreCase("July"))return 7;

        else if(s.equalsIgnoreCase("August"))return 8;

        else if(s.equalsIgnoreCase("September"))return 9;

        else if(s.equalsIgnoreCase("October"))return 10;

        else if(s.equalsIgnoreCase("November"))return 11;

        else if(s.equalsIgnoreCase("December"))return 12;

        else return 13;  //return an error
    }
    
    /**
      * used to set each text field of the gui when using the navigation buttons
      * uses a default images if a person hasn't get a image location on file
      * @throws k if for some reason a person record can't be loaded (generally file open issues)
      */
    public void setRecord() {
        try {
            name.setText(temp.currentRecord().getName());
            address.setText(temp.currentRecord().getAddress());
            gender.setText(temp.currentRecord().gender());
            ni.setText(temp.currentRecord().getNatInsceNo());
            dob.setText(temp.currentRecord().getDateOfBirth().toString());
            phone.setText(temp.currentRecord().getPhone());
            photoString = temp.currentRecord().getPhoto();

            if(temp.currentRecord().getPhoto() == null) { //if no photo location found...
                photoString = "images/default.jpg";
                photo = new ImageIcon(photoString);
                image.setIcon(photo);
            }

            else {
                photo = new ImageIcon(photoString);  //... use default
                image.setIcon(photo);
            }
            error = 0;
          }

        catch (Exception k) {
            error = 1;
        }
    }

    /**
      * removes the safety to allow current to increment even though a record doesn't exist
        */
    public void forceIncrementCurrent() {
        current++;
        temp.incrementCurrentPointer(current);
        
    }

     /**
      * increments this classes current and the store classes current
      * if the this classes current is less than the store classes current (or count-1)
      * then it's safe to navigate, otherwise null pointers would throw exceptions
      * or index out of bounds as records may not exist
      */
    public void incrementCurrent() {
    
        if(current < temp.getCount() - 1) {
            current++;
            temp.incrementCurrentPointer(current);
            updateMessage();
            setRecord();
        }
    }
    
    /**
      * decrements this classes current and the store classes current
      * if the this classes current is less than the store classes current (or count-1)
      * then it's safe to navigate, otherwise null pointers would throw exceptions
      * or index out of bounds as records may not exist
      * @throws k is user enters unusual day string
      */
    public void decrementCurrent() {
     
        if(current >= 1) {
            current--;
            temp.decrementCurrentPointer(current);
            updateMessage();
            setRecord();
        }
    }

    /**
      * A method to remove the 'st', 'nd', 'th' character on the end of the day string
      * for example the 2(nd) of July, to be able to convert the string number into an integer number
      * and save into the store (coherent records) - thus allows other functions such as comparison
      * @param s the given string in which needs to be modified and converted to an integer
      * @return actualNumber the number corrisponding to the month, converted in this method
      */
    public int removeCharsFromDay(String s) {
     
        String numberString;
        int actualNumber = 0;
        try {
         
        //if the string contains an 'st' then get the string before the s and convert to an int
        if(s.contains("st")) {

            String REGEX = "s";
            Pattern pat = Pattern.compile(REGEX);
            String [] item = pat.split(s);

            for(int i = 0; i < item.length; i++) {
                numberString = item[0];
                actualNumber = Integer.parseInt(numberString); //convert to int
            }
        }

        //if the string contains a 'th' then get the string before the t and convert to an int
        else if(s.contains("th")) {

            String REGEX = "t";
            Pattern pat = Pattern.compile(REGEX);
            String [] item = pat.split(s);

            for(int i = 0; i < item.length; i++) {
                numberString = item[0];
                actualNumber = Integer.parseInt(numberString);
            }
        }

        //if the string contains an 'nd' then get the string before the n and convert to an int
        else if(s.contains("nd")) {

            String REGEX = "n";
            Pattern pat = Pattern.compile(REGEX);
            String [] item = pat.split(s);

            for(int i = 0; i < item.length; i++) {
                numberString = item[0];
                actualNumber = Integer.parseInt(numberString);
            }
        }

        //if the user didn't put st, th or nd on the end of the day number, then proceed as normal and convert
        //straight to int
        else {
            actualNumber = Integer.parseInt(s);
        }
        }
        catch (Exception k) {
         
        }
        return actualNumber;
    }
    
     /**
      * simply a red message indicating any warnings a user has or just the current
      * record out of how many records there are to the user
      */
    public void updateMessage() {
    
        if(error == 1) { //couldn't load file - possibly wrong file type
            message.setText("No file loaded, file currupt of wrong file type");
        }

        else if(error == 2) { //couldn't load picture, possibly wrong type or corrupt
            message.setText("Error: Could not open image file");
        }
        
        else if(error == 3) { //couldn't write file, possibly file cannot overwrite because is read-only
            message.setText("Error: Could not write file, possibly ready-only file");
        }
        else if(error == 4) { //tried to save an empty store so show warning
            message.setText("No store to save");
        }
        else {  //no error warning so display current record number
            counter = current + 1;
            message.setText("Record: " + (counter)+"/" + temp.getCount());
        }
    }
}