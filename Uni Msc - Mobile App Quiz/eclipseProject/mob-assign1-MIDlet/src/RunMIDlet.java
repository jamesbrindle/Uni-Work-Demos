import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.StringItem;
import javax.microedition.midlet.MIDlet;

/**
 * RunMIDlet is the main class that is run when a mobile device runs the MIDlet application
 * This application is a multiple choice answer quiz designed for university students, in which this
 * application is intended to reside on a mobile device, which connects to a servlet, typically on the Internet
 * which connects to a database and fetches the questions and stores a students selected answer
 * @author Jamie Brindle (06352322) - Msc - Manchester Metropolitan University
 */
public class RunMIDlet extends MIDlet implements Runnable, CommandListener {
	//Declare global variables
    private Display d; //The main display
    private String message, subjectSelectMessage, subjectChoice, userID;
    private MIDletForms forms; //Declare instance of our MIDletForms class
    private ServletConnector connector; //Declare an instance of our ServletConnector class
    private ManFunctions manFun; //Declare an instance of our ManFunctions class
    private String[] subjectList, weeksList;
    private int weekNo, questionNo, maxQuestions, selector, isFirstSubjectListArrival;;
    protected int[] session; //stores a users selected ChoiceGroup

    /**
     * RunMIDlet Constructor
     */
    public RunMIDlet() {
    //initialise global variables
        forms = new MIDletForms(this); //our MIDletForms class instance
        connector = new ServletConnector(); //our ServletConenctor class instance
        manFun = new ManFunctions(); //our ManFunctions class instance
        questionNo = 0;
        maxQuestions = 0;
        session = new int[100];
    }
    
    /**
     * Similar to the 'main' method - this starts the applications execution
     */
    public void startApp() {
        if (d == null) {
            d = Display.getDisplay(this);
        }
        d.setCurrent(forms.welcomeForm); //set first form visible

        Thread t = new Thread(this);

        t.start();

    }

    /**
     * Used to pause an application - not used in this application
     */
    public void pauseApp() {}

    /**
     * Used to force quit an application - not used in this application
     */
    public void destroyApp(boolean unconditional) {}

    /**
     * Used to listen for I/O input and execute the corresponding code
     * @param c is any I/O command picked up
     * @param s is the current display we are listening to
     */
    public void commandAction(Command c, Displayable s) {
    	
    	/**
         * Whenever the 'Exit' command type is pressed, do the following...
         * This allows for easier navigation due to not having to remember an extensive
         * list of created commands, only a form name.
         */
        if (c.getCommandType() == Command.EXIT) {
            if (d.getCurrent().equals(forms.subjectSelectionForm)) {
                d.setCurrent(forms.mainMenuForm);
            } else if (d.getCurrent().equals(forms.weekSelectionForm)) {
                d.setCurrent(forms.subjectSelectionForm);
            } else if (d.getCurrent().equals(forms.answerQuestionsForm)) {
                questionNo = 0;
                saveQuestions();
                d.setCurrent(forms.mainMenuForm);		
            } else if (d.getCurrent().equals(forms.answeredQuestionsForm)) {
                questionNo = 0;
                d.setCurrent(forms.mainMenuForm);
            } else {
                System.out.println("Exiting");
                notifyDestroyed();
            }
        }

        /**
         * Whenever the 'OK' command type is pressed, do the following...
         * This allows for easier navigation due to not having to remember an extensive
         * list of created commands, only a form name.
         */
        if (c.getCommandType() == Command.OK) {
            if (d.getCurrent().equals(forms.welcomeForm)) {
                d.setCurrent(forms.loginForm);

            } else if (d.getCurrent().equals(forms.loginForm)) {

                login();

            } else if (d.getCurrent().equals(forms.loginNotSuccessForm)) {
                d.setCurrent(forms.loginForm);

            } else if (d.getCurrent().equals(forms.mainMenuForm)) {
                if (forms.mainMenuChoice.getSelectedIndex() == 0) {
                    subjectSelectMessage = "Please Chose The Subject to Answer Questions For";
                    selector = 0; //to allow a different action to execute using the same command
                } else if (forms.mainMenuChoice.getSelectedIndex() == 1) {
                    subjectSelectMessage = "Please Chose Which Subject You Previously Answered Questions For";
                    selector = 1;
                } else if (forms.mainMenuChoice.getSelectedIndex() == 2) {
                    subjectSelectMessage = "Please Chose The Subject You'd Like To View Statistics For";
                    selector = 2;
                }
                querySubject();
                d.setCurrent(forms.subjectSelectionForm);

            } else if (d.getCurrent().equals(forms.subjectSelectionForm)) {
                subjectChoice = subjectList[forms.subjectGroup.getSelectedIndex() + 1];
                if (selector == 2) {
                    d.setCurrent(forms.statisticsForm);
                    retrieveStatistics();	
                } else {
                    queryWeeks(subjectChoice, selector); 
                    d.setCurrent(forms.weekSelectionForm);
                }

            } else if (d.getCurrent().equals(forms.statisticsForm)) { 
                d.setCurrent(forms.subjectSelectionForm);
                 	            	
            } else if (d.getCurrent().equals(forms.weekSelectionForm)) {
            
                questionNo = 0; //reset the global questionNo
                weekNo = forms.weekGroup.getSelectedIndex() + 1;
                if (selector == 0) {
                    d.setCurrent(forms.answerQuestionsForm);
                    retrieveQuestion();

                } else if (selector == 1) {

                    d.setCurrent(forms.answeredQuestionsForm);
                    retrieveAnsweredQuestion();
                    queryWeekStatistics();

                }
            } else if (c.getLabel().equalsIgnoreCase("next")
                    && d.getCurrent().equals(forms.answerQuestionsForm)) { 
                if (questionNo != 0) { //using for navigation, we don't want to go into negative integers
                    session[questionNo] = forms.answerOptions.getSelectedIndex();
                }
                questionNo++;
                maxQuestions = questionNo; //set maxQuestions as we iteratively save answered questions to a database
                retrieveQuestion();	
            } else if (c.getLabel().equalsIgnoreCase("next")
                    && d.getCurrent().equals(forms.answeredQuestionsForm)) {
                questionNo++;
                retrieveAnsweredQuestion();
            } else if (c.getLabel().equalsIgnoreCase("back")
                    && d.getCurrent().equals(forms.answerQuestionsForm)) {				
                if (questionNo != 0) {
                    session[questionNo] = forms.answerOptions.getSelectedIndex();
                    questionNo--;					
                    retrieveQuestion();
                } else {
                    d.setCurrent(forms.weekSelectionForm);
                }
            } else if (c.getLabel().equalsIgnoreCase("back")
                    && d.getCurrent().equals(forms.answeredQuestionsForm)) {		
                if (questionNo != 0) {
                    questionNo--;
                    retrieveAnsweredQuestion();
                } else {
                    d.setCurrent(forms.weekSelectionForm);
                }

            } else if (c.getLabel().equalsIgnoreCase("save")) { 
                saveQuestions();

            }

        }

    }

    /**
     * Contains the code that will be executed when the thread is stared, necessary method
     * due to implementing 'Runnable', here we have the starting point to the application, contained
     * within a thread as network failures can cause deadlocks which block anything calling the application
     */
    public void run() {
        forms.welcomeForm.setTitle("Welcome Form");
        StringItem stringItem;

        if (manFun.contains(connector.request("connect"), "connected") == true) {

            message = "\n \n \nYou have connected successfully, please log in to continue";
            stringItem = new StringItem(null, message);
            forms.welcomeForm.addCommand(new Command("Login", Command.OK, 1));
            stringItem.setLayout(3);
            forms.welcomeForm.append(stringItem);
            System.out.println("Connected to MIDlet");
            isFirstSubjectListArrival = 1;
        } else {
            message = "\n \n \nYou have not been able to connect successfully, please exit and try again";
            stringItem = new StringItem(null, message);
            stringItem.setLayout(3);
            forms.welcomeForm.append(stringItem);
            System.out.println("Cannot Connect To MIDlet");
        } 
    }

    /**
     * This method contacts the ServletConnector class to athorise a login request, which
     * in turn connects to a servlet on a network which connects to a database
     */
    protected void login() {

        String loginString = "login:" + forms.userIDField.getString() + ":"
                + forms.passwordField.getString();
        String userDetails = connector.request(loginString);

        if (manFun.contains(userDetails, "userdetails") == true) {

            String[] userDetailsArray = manFun.splitString(userDetails);
            String firstName = userDetailsArray[1];
            String secondName = userDetailsArray[2];

            userID = forms.userIDField.getString();

            forms.mainMenuMessageStringItem.setText(
                    "Welcome " + firstName + " " + secondName + "\n"
                    + "Your login was successful \nPlease choose a task from the menu");

            d.setCurrent(forms.mainMenuForm);
            System.out.println("Login Successful");
        } else {
            d.setCurrent(forms.loginNotSuccessForm);
            System.out.println("Login Unsuccessful");
        }
    }

    /**
     * This method contacts the ServletConnector class to query for a list of subject names, which
     * in turn connects to a servlet on a network which connects to a database
     */
    protected void querySubject() {
        String inputString = connector.request("querySubjects");		

        if (manFun.contains(inputString, "nosubjects") == true) {
            forms.subjectSelectMessage.setText("There Are No Subjects Available");
        } else {
            questionNo = 0;
            subjectList = manFun.splitString(inputString);
            forms.subjectSelectMessage.setText(subjectSelectMessage);

            if (isFirstSubjectListArrival == 1) {
                isFirstSubjectListArrival = 0;
                for (int i = 1; i < subjectList.length - 1; i++) {
                    forms.subjectGroup.append(subjectList[i], null);
                }
            }
        }
    }

    /**
     * This method contacts the ServletConnector class to query for a list of week number, which
     * in turn connects to a servlet on a network which connects to a database.
     * @param subjectName - The subject name of the week list to fetch
     * @param selector - defines the difference in which subject weeks to collect - the results or the questions
     */
    protected void queryWeeks(String subjectName, int selector) {
        String weeks = "";

        if (selector == 0) {
            weeks = connector.request("queryWeeks:" + subjectName);
        } else {
            weeks = connector.request("queryResultWeeks:" + subjectName);
        }

        StringItem space = new StringItem(null, "\n \n");
        StringItem weekSelectMessage = new StringItem(null, "");

        forms.weekGroup = new ChoiceGroup(null, Choice.EXCLUSIVE);		
        forms.weekSelectionForm.deleteAll();

        weeksList = manFun.splitString(weeks);		

        if (manFun.contains(weeks, "noweeks") == true) {
            if (selector == 0) {
                weekSelectMessage.setText(
                        "Sorry, There Are weeks Available for This Subject");			
            } else {
                weekSelectMessage.setText(
                        "Sorry, You Have Not Previously Answered Any Questions For This Subject");	
            }
            forms.weekSelectionForm.append(weekSelectMessage);
            forms.weekSelectionForm.append(space);
            forms.weekSelectionForm.append(forms.weekGroup);
            forms.weekSelectionForm.removeCommand(forms.nextCommand);
        } else {
            forms.weekSelectionForm.removeCommand(forms.nextCommand);
            if (selector == 0) {
                weekSelectMessage.setText("Please Select An Available Week");
            } else {
                weekSelectMessage.setText(
                        "Please Select A Week to View Results, If You Cannot See Your Week, You Either Haven't Taken "
                                + "That Weeks Quiz or You Forgot to Save Your Results");
            }
            forms.weekSelectionForm.append(weekSelectMessage);
            forms.weekSelectionForm.append(space);
            forms.weekSelectionForm.append(forms.weekGroup);
            forms.weekSelectionForm.addCommand(forms.nextCommand);

            for (int i = 1; i < weeksList.length - 1; i++) {
                forms.weekGroup.append(weeksList[i], null);
            }
        }
    }

    /**
     * This method contacts the ServletConnector class to query for a single question
     * and available answer options, which the user may answer, which
     * in turn connects to a servlet on a network which connects to a database
     */
    protected void retrieveQuestion() {
        String inputString = connector.request(
                "retrieveQuestion:" + weekNo + ":" + questionNo + ":"
                + subjectChoice);
        String[] inputArray = manFun.splitString(inputString);
        StringItem space = new StringItem(null, "\n \n \n");

        forms.answerOptions = new ChoiceGroup(null, Choice.EXCLUSIVE);
        forms.answerQuestionsForm.deleteAll();

        if (manFun.contains(inputString, "retrievedQuestion")) {
            forms.answerQuestionsForm.removeCommand(forms.next);
            forms.AQFormMessage.setText(questionNo + ") " + inputArray[1]);
            forms.answerQuestionsForm.append(forms.AQFormMessage);
            forms.answerQuestionsForm.append(space);
            forms.answerQuestionsForm.append(forms.answerOptions);
            forms.answerQuestionsForm.addCommand(forms.next);

            for (int i = 2; i < inputArray.length - 1; i++) {
                forms.answerOptions.append(i - 1 + ") " + inputArray[i], null);
            }
            if (session[questionNo] > 0) {
                forms.answerOptions.setSelectedIndex(session[questionNo], true);
            }

        } else if (manFun.contains(inputString, "noquestion") && questionNo > 1) {
            forms.AQFormMessage.setText(
                    "There are no more questions for this week");
            forms.answerQuestionsForm.removeCommand(forms.next);
            forms.answerQuestionsForm.append(forms.AQFormMessage);
            maxQuestions = questionNo - 1;

        } else {
            forms.answerQuestionsForm.removeCommand(forms.next);
            forms.AQFormMessage.setText(
                    "\n \n \n \n \nTo answer a question please chose from one of the four multiple choices. "
                            + "Your selections can be saved at any time by selection save from the right hand menu. "
                            + "You may re-attempt a question at any time during this session.");
            forms.answerQuestionsForm.append(forms.AQFormMessage);	
            forms.answerQuestionsForm.addCommand(forms.next);
        }
    }

    /**
     * This method contacts the ServletConnector class to query for the results 
     * of an answered question, which in turn connects to a servlet on a
     *  network which connects to a database
     */
    protected void retrieveAnsweredQuestion() {
        String inputString = connector.request(
                "retrieveAnsweredQuestion:" + weekNo + ":" + questionNo + ":"
                + subjectChoice + ":" + userID);
        String[] inputArray = manFun.splitString(inputString);
        StringItem space = new StringItem(null, "\n \n \n");

        int answerChosen = 0; 
        int correctAnswer = 0; 

        if (inputArray.length > 2) {
            answerChosen = Integer.parseInt(inputArray[1]);
            correctAnswer = Integer.parseInt(inputArray[2]);
        }

        forms.answeredQuestionsForm.deleteAll();
        forms.answeredOptions = new ChoiceGroup(null, Choice.EXCLUSIVE);

        if (manFun.contains(inputString, "retrievedQuestion")) {
            forms.answeredQuestionsForm.removeCommand(forms.next2);
            forms.AAQFormMessage.setText(questionNo + ") " + inputArray[3]);
            forms.answeredQuestionsForm.append(forms.AAQFormMessage);
            forms.answeredQuestionsForm.append(space);
            forms.answeredQuestionsForm.append(forms.answeredOptions);
            forms.answeredQuestionsForm.addCommand(forms.next2);

            for (int i = 4; i < inputArray.length - 1; i++) {
                forms.answeredOptions.append(i - 3 + ") " + inputArray[i], null);
            }

            forms.answerChosen.setText(
                    "\n \nThe Answer You Chose is: " + answerChosen);
            forms.correctAnswer.setText(
                    "The Correct Answer is: " + correctAnswer);
            if (answerChosen == correctAnswer) {

                forms.isCorrect.setText("You Got This Question Correct");
            } else {
                forms.isCorrect.setText("You Got This Question Incorrect");
            }
            forms.answeredQuestionsForm.append(forms.answerChosen);
            forms.answeredQuestionsForm.append(forms.correctAnswer);
            forms.answeredQuestionsForm.append(forms.isCorrect);

            if (inputArray.length > 1 && correctAnswer > 0) {
                forms.answeredOptions.setSelectedIndex(correctAnswer - 1, true);
            }

        } else if (manFun.contains(inputString, "noquestion") && questionNo > 1) {
            forms.AAQFormMessage.setText("This is the end of the results");
            forms.answeredQuestionsForm.removeCommand(forms.next2);
            forms.answeredQuestionsForm.append(forms.AAQFormMessage);

        } else {
            forms.answeredQuestionsForm.removeCommand(forms.next2);
            forms.AAQFormMessage.setText(queryWeekStatistics());

            forms.answeredQuestionsForm.append(forms.AAQFormMessage);	
            forms.answeredQuestionsForm.addCommand(forms.next2);
        }
    }

    /**
     * This method contacts the ServletConnector class to query for the statistical results
     * for a particular week that questions have been answered for, which
     * in turn connects to a servlet on a network which connects to a database
     */
    protected String queryWeekStatistics() {
        String responseString = "";
        String inputString = connector.request(
                "retrieveWeekStatistics:" + weekNo + ":" + subjectChoice + ":"
                + userID);
        String[] inputArray = manFun.splitString(inputString);

        responseString = "\n \n \n \n \n \nSelect 'Next' from the menu to view your results for each question\n \n"
                + "You scored " + inputArray[0] + " out of " + inputArray[1]
                + " this week\nGiving you a total of " + inputArray[2]; 

        return responseString;
    }

    /**
     * This method contacts the ServletConnector class to query for the statistical results
     * for all weeks that questions have been answered for, which
     * in turn connects to a servlet on a network which connects to a database
     */
    protected void retrieveStatistics() {
        String inputString = connector.request(
                "retrieveStatistics:" + subjectChoice + ":" + userID);
        String[] inputArray = manFun.splitString(inputString);

        if (manFun.containsOnlyNumbers(inputArray[1])) {
            forms.statisticsBody.setText(
                    "\nYour Current Average = " + inputArray[0] + "%"
                    + "\nEveryone's Total Average = " + inputArray[1] + "%"
                    + "\nThe Highest Average = " + inputArray[2] + "%"
                    + "\nThe Lowest Averaage of = " + inputArray[3]);
        } else {
            forms.statisticsBody.setText(
                    "There are no results available for this subject");
        }
    }

    /**
     * Iteratively saves the answer choices of all answered questions, which has been being
     * stored in an array. This is done via passing in a string to a servlet on some network
     * by the ServletConnector
     */
    protected void saveQuestions() {
        for (int i = 1; i <= maxQuestions; i++) {
            connector.request(
                    "saveQuestion:" + subjectChoice + ":" + userID + ":"
                    + weekNo + ":" + i + ":" + (session[i] + 1));
        }

    }
}
