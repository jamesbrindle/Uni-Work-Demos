import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A servlet which acts as a dynamically created html web page for the quiz administration panel
 * and includes user authentication via created browser cookie values. It supposed both Get and Post
 * requests
 * 
 * @author Jamie Brindle (06352322) - Msc - Manchester Metropolitan University
 */
public class submitinsertquestion extends common {

    private static final long serialVersionUID = 1L;
    protected DatabaseConnector connector;
    protected ManFunctions manFun;
    private Vector<String> answerOptions;

    /**
     * Constructor which creates instances of other associated classes
     */
    public submitinsertquestion() {
        connector = new DatabaseConnector();
        manFun = new ManFunctions();
        answerOptions = new Vector<String>();

    }
    
    /**
     * Method to execute upon a Get request/response, this simply calls the doPost method
     * This is common work-around no not have to uniquely specify with the request/request is
     * a Get or a Post
     * @param request
     * @param response
     * @throws IOException
     */
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Method to execute upon a Post request/response
     * @param request
     * @param response
     * @throws IOException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        response.setContentType("text/html");
        out = response.getWriter();

        //authenticate user
        if (validateUser(request, response) == false) {
            loginFailureHTML(request, response);
        } else {		

            docTypeState(request, response);        
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Insert Questions</title>");
            startHTMLCode(request, response); 
            out.println(
                    "<td height='60' align='center' colspan='5'>"
                            + "<font size='5' face='Arial, Helvetica, sans-serif'>Insert Questions</td>");

            navigationHTML(request, response);
			
            out.println(
                    "<td width='40%' align='center' valign='top'><p>&nbsp;</p>"); 

            if (validateUser(request, response) == false) {
                loginFailureHTML(request, response); //redirect to login page
            } else {

                String questionNo = request.getParameter("questionno");
                String weekNo = request.getParameter("weekno");
                String subjectName = request.getParameter("subjectname");
                String correctAnswerNo = request.getParameter("correctanswerno");
                String theQuestion = request.getParameter("thequestion");

                answerOptions.add(request.getParameter("answeroption1"));
                answerOptions.add(request.getParameter("answeroption2"));
                answerOptions.add(request.getParameter("answeroption3"));
                answerOptions.add(request.getParameter("answeroption4"));
                answerOptions.add(request.getParameter("answeroption5"));
                answerOptions.add(request.getParameter("answeroption6"));

                int answerOptionCounter = 0;

                //make sure required fields aren't empty or have incorrect datatypes in them
                if (questionNo.equalsIgnoreCase("")
                        || weekNo.equalsIgnoreCase("")
                        || subjectName.equalsIgnoreCase("")
                        || subjectName.equalsIgnoreCase("")
                        || theQuestion.equalsIgnoreCase("")
                        || answerOptions.get(0).equalsIgnoreCase("")
                        || answerOptions.get(1).equalsIgnoreCase("")
                        || manFun.containsOnlyNumbers(questionNo) == false
                        || manFun.containsOnlyNumbers(weekNo) == false
                        || manFun.containsOnlyNumbers(correctAnswerNo) == false) {
					
                    out.println(
                            "<font size='3' face='Arial, Helvetica, sans-serif'>");
                    out.println(
                            "You have not entered all the required fields"
                                    + "or have entered incorrect data into them such as letters"
                                    + "or symbols in the fields that require a number, please go back and try again");

                    out.println("</font>");
                    out.println(
                            "<form name='form1' method='post' action='insertquestion'>");
                    out.println(
                            "<p><input type='submit' name='Back' value='Back'></p></form>");
				
                } else {
                    for (int i = 0; i < answerOptions.size(); i++) {
                        if (!(answerOptions.get(i).equalsIgnoreCase(""))) {
                            answerOptionCounter++;
                        } //check which answer option text fields are empty and count the answer options
                    }

                    for (int i = 1; i <= answerOptionCounter; i++) {
                        int isCorrectAnswer = 0;
                        
                        //if the answer is correct or not, it's stored as a boolean value on the database
                        if (i == Integer.parseInt(correctAnswerNo)) {
                            isCorrectAnswer = 1;
                        } else {
                            isCorrectAnswer = 0;

                        }
                        
                        //attempt to insert or update the new / existing question
                        connector.insertQuestion(questionNo, weekNo, subjectName,
                                theQuestion, Integer.toString(i),
                                answerOptions.get(i - 1),
                                Integer.toString(isCorrectAnswer));
                    }
					
                    out.println(
                            "<font size='3' face='Arial, Helvetica, sans-serif'>");
                    out.println(
                            "Your question has been inserted into the database succesfully");
                    out.println(
                            "<form name='form1' method='post' action='insertquestion'>");
                    out.println(
                            "<p><input type='submit' name='Back' value='Add Another Question'></p></form>");
										
                }
                out.println(
                        "<td width='20%' align='left' valign='top'><p>&nbsp;</p>"); 
		
                endHTMLCode(request, response);
            }
        }
    }
}
