import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;

/**
 * MIDletForms is used in conjunctions with the RunMIDlet class. It's simply a class containing
 * the forms and the form properties of the MIDlet created in the RunMidlet class
 * 
 * @author Jamie Brindle (06352322) - Msc - Manchester Metropolitan University
 */
public class MIDletForms {

    protected Form welcomeForm, loginForm, mainMenuForm, loginNotSuccessForm, subjectSelectionForm, weekSelectionForm, 
            answerQuestionsForm, answeredQuestionsForm, statisticsForm;
    protected TextField userIDField, passwordField;
    protected ChoiceGroup mainMenuChoice, subjectGroup, weekGroup, answerOptions, answeredOptions;
    protected StringItem mainMenuMessageStringItem, subjectSelectMessage, weekSelectMessage, AQFormMessage, 
            AAQFormMessage, correctAnswer, answerChosen, isCorrect, statisticsHeader, statisticsBody;
    protected Command nextCommand, next, back, save, next2, back2;

    /**
     * Constructor for MIDletForms, which needs to pass in the RunMIDlet class
     * 
     * @param r takes the RunMIDlet class
     */
    protected MIDletForms(RunMIDlet r) {
        StringItem space = new StringItem(null, "\n \n \n");
        StringItem space2 = new StringItem(null, "\n");
        StringItem space3 = new StringItem(null, "\n \n");
        StringItem space4 = new StringItem(null, "\n \n");
        StringItem space5 = new StringItem(null, "\n \n");
				
        // Welcome Form
        welcomeForm = new Form("Welcome");
        welcomeForm.setCommandListener(r);
        welcomeForm.addCommand(new Command("Exit", Command.EXIT, 0));	    
        welcomeForm.setTitle("Welcome Page");

        // Login Form
        loginForm = new Form("login");
        loginForm.setCommandListener(r);
        loginForm.addCommand(new Command("Exit", Command.EXIT, 0));
        loginForm.addCommand(new Command("OK", Command.OK, 0));
        loginForm.setTitle("Login Page");
	    	    
        userIDField = new TextField("User ID:  ", null, 12, TextField.ANY);
        userIDField.setLayout(3);	    
        passwordField = new TextField("Password:", null, 12, TextField.PASSWORD);
        passwordField.setLayout(3);	   

        StringItem footnote = new StringItem(null,
                "\n \n \nFor Testing Purposes:\nUsername: 123456\nPassword: password");
	    
        loginForm.append(space);
        loginForm.append(userIDField);
        loginForm.append(passwordField);
        loginForm.append(space2);
        loginForm.append(footnote);
	    
        // Login Success Form
        mainMenuForm = new Form("Login Successful");
        mainMenuForm.setCommandListener(r);
        mainMenuForm.addCommand(new Command("Exit", Command.EXIT, 0));
        mainMenuForm.addCommand(new Command("Next", Command.OK, 0));
        mainMenuForm.setTitle("Main Menu");
        mainMenuChoice = new ChoiceGroup(null, Choice.EXCLUSIVE);
        mainMenuChoice.setLayout(1);
	    
        mainMenuChoice.append("Take a Quiz", null);
        mainMenuChoice.append("View Results of an Already Answered Quiz", null);
        mainMenuChoice.append("View Current Statistics", null);
        mainMenuMessageStringItem = new StringItem(null, "");
	    
        mainMenuForm.append(mainMenuMessageStringItem);
        mainMenuForm.append(space3);
        mainMenuForm.append(mainMenuChoice);
	    
        // Login Unsuccessful Success Form	    
        loginNotSuccessForm = new Form("Login UnSuccess");
        loginNotSuccessForm.setCommandListener(r);
        loginNotSuccessForm.addCommand(new Command("Exit", Command.EXIT, 0));
        loginNotSuccessForm.addCommand(new Command("Back", Command.OK, 0));
        loginNotSuccessForm.setTitle("Login Unsuccessful");	    
        StringItem successMess = new StringItem(null,
                "Your login was unsuccessful, please go back and try again");
	    
        loginNotSuccessForm.append(successMess);

        // Subject Selection Form
        subjectSelectionForm = new Form("Subject Selection");
        subjectSelectionForm.setCommandListener(r);
        subjectSelectionForm.addCommand(new Command("Back", Command.EXIT, 0));
        subjectSelectionForm.addCommand(new Command("Next", Command.OK, 0));
        subjectSelectionForm.setTitle("Subject Selection");
        subjectSelectMessage = new StringItem(null, "");		
        subjectGroup = new ChoiceGroup(null, Choice.EXCLUSIVE);
        subjectSelectionForm.append(subjectSelectMessage);
        subjectSelectionForm.append(space4);
        subjectSelectionForm.append(subjectGroup);
	
        // Week Selection Form
        weekSelectionForm = new Form("Week Selection");
        weekSelectionForm.setCommandListener(r);
        weekSelectionForm.addCommand(new Command("Back", Command.EXIT, 0));
        weekSelectionForm.setTitle("Week Selection");
        weekSelectMessage = new StringItem(null, "");
        nextCommand = new Command("Next", Command.OK, 0);
		
        // Answer Questions Form
        answerQuestionsForm = new Form("Answer Questions");
        answerQuestionsForm.setCommandListener(r);
        answerQuestionsForm.addCommand(
                new Command("Save & Quit", Command.EXIT, 0));
        next = new Command("Next", Command.OK, 1);
        back = new Command("Back", Command.OK, 2);
        save = new Command("Save", Command.OK, 3);		
        answerQuestionsForm.addCommand(next);
        answerQuestionsForm.addCommand(back);
        answerQuestionsForm.addCommand(save);
        answerQuestionsForm.setTitle("Answer Questions");
        AQFormMessage = new StringItem(null, "");
		
        answerQuestionsForm.append(AQFormMessage);
		
        // Answered Questions Form
        answeredQuestionsForm = new Form("Answer Questions - Results");
        answeredQuestionsForm.setCommandListener(r);
        answeredQuestionsForm.addCommand(new Command("Quit", Command.EXIT, 0));
        next2 = new Command("Next", Command.OK, 1);
        back2 = new Command("Back", Command.OK, 2);
        answeredQuestionsForm.addCommand(next2);
        answeredQuestionsForm.addCommand(back2);
        answeredQuestionsForm.setTitle("Answered Questions - Results");
        AAQFormMessage = new StringItem(null, "");
        correctAnswer = new StringItem(null, "");
        answerChosen = new StringItem(null, "");
        isCorrect = new StringItem(null, "");
		
        answeredQuestionsForm.append(AAQFormMessage);
		
        // Statistics Form
        statisticsForm = new Form("Statistics");
        statisticsForm.setCommandListener(r);
        statisticsForm.addCommand(new Command("Back", Command.OK, 0));
        statisticsForm.setTitle("Current Statistics");
        statisticsHeader = new StringItem(null,
                "Here are the statistics for the results so far");
        statisticsBody = new StringItem(null, "");
	    
        statisticsForm.append(statisticsHeader);
        statisticsForm.append(space5);
        statisticsForm.append(statisticsBody);
    }
}
	
