import java.io.IOException;

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
public class viewquestions extends common {

    private static final long serialVersionUID = 1L;

    private DatabaseConnector connector;
    private ManFunctions manFun;

    /**
     * Constructor which creates instances of other associated classes
     */
    public viewquestions() {
        connector = new DatabaseConnector();
        manFun = new ManFunctions();
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
            loginFailureHTML(request, response); //redirect to login page
        } else {
            String questionNo = request.getParameter("questionno");
            String weekNo = request.getParameter("weekno");
            String subjectName = request.getParameter("subjectname");
			
            int questionNoInt = Integer.parseInt(questionNo);
			
            String inputString = "";

            inputString = connector.retrieveQuestion(weekNo.substring(5), //get rid of the letters from the week number
                    Integer.toString(questionNoInt), subjectName);
			
            docTypeState(request, response);        
            out.println("<html>");
            out.println("<head>");
            out.println("<title>View Questions</title>");
            startHTMLCode(request, response); 
            out.println(
                    "<td height='60' align='center' colspan='5'>"
                            + "<font size='5' face='Arial, Helvetica, sans-serif'>View Questions</font></td>");

            navigationHTML(request, response);

            out.println(
                    "<td width='40%' align='center' valign='top'><p>&nbsp;</p>"); 

            if (inputString.length() > 3) { //if question exists
                String inputArray[] = manFun.splitString(inputString);
				
                out.println(
                        "<font size = '5' face='Arial, Helvetica, sans-serif'>"
                                + "" + questionNoInt + ": " + inputArray[0]
                                + "<br><br></font>");
				
                out.println(
                        "<font size = '4' face='Arial, Helvetica, sans-serif'>");
                for (int i = 1; i < inputArray.length; i++) {
					
                    out.println(i + ": " + inputArray[i] + "<br>");
                }
				
                out.println("</font>");
                if (questionNoInt != 1) { //if not at the beginning of the quesions, we need a 'back' button
                    out.println(
                            "<form name='form1' method='post' action='viewquestions'>");
                    out.println(
                            "<input type='hidden' name='subjectname' value='"
                                    + subjectName + "'>");
                    out.println(
                            "<input type='hidden' name='weekno' value='"
                                    + weekNo + "'>");
                    out.println(
                            "<input type='hidden' name='questionno' value='"
                                    + (questionNoInt - 1) + "'>");
                    out.println(
                            "<br><input type='submit' name='Back' value='Back'></form>");
                }
				
                out.println(
                        "<form name='form2' method='post' action='viewquestions'>");
                out.println(
                        "<input type='hidden' name='subjectname' value='" //to carry forward variables
                                + subjectName + "'>");
                out.println(
                        "<input type='hidden' name='weekno' value='" + weekNo //to carry forward variables
                        + "'>");
                out.println(
                        "<input type='hidden' name='questionno' value='" //to carry forward variables
                                + (questionNoInt + 1) + "'>");
                out.println(
                        "<p><input type='submit' name='Back' value='Next'></p></form>");
            } else { 
                if (questionNoInt > 1) { 
                    out.println(
                            "<font size = '4' face='Arial, Helvetica, sans-serif'>"
                                    + "There are no more questions for this week<br><br>");
                    out.println(
                            "<form name='form2' method='post' action='viewquestions'>");
                    out.println(
                            "<input type='hidden' name='subjectname' value='" //to carry forward variables
                                    + subjectName + "'>");
                    out.println(
                            "<input type='hidden' name='weekno' value='" //to carry forward variables
                                    + weekNo + "'>");
                    out.println(
                            "<input type='hidden' name='questionno' value='" //to carry forward variables
                                    + (questionNoInt - 1) + "'>");
                    out.println(
                            "<p><input type='submit' name='Back' value='Back'></p></form>");
                }
            }

            out.println(
                    "<form name='form3' method='post' action='viewquestionspw'>");	
            out.println(
                    "<input type='hidden' name='subjectname' value='"
                            + subjectName + "'>");
            out.println(
                    "<p><input type='submit' name='Back' value='Back To Week Selection'></p></form>");

            out.println(
                    "<td width='30%' align='center' valign='top'>&nbsp;</td>");

            endHTMLCode(request, response);
        }
    }
}
