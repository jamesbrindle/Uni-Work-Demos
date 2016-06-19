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
public class insertquestion extends common {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor which creates instances of other associated classes
     */
    public insertquestion() {}
    
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
                            + "<font size='5' face='Arial, Helvetica, sans-serif'>Insert Questions</font></td>");

            navigationHTML(request, response);
			
            out.println(
                    "<td width='20%' align='left' valign='top'><p>&nbsp;</p>"); 

            if (validateUser(request, response) == false) {
                loginFailureHTML(request, response); //redirect to login page
            } else {
				
                out.println(
                        "<font size='3' face='Arial, Helvetica, sans-serif'>");
                out.println("<p>Please fill in the fields below.</p>");
                out.println(
                        "<p>If you enter a question number with a corresponding week and "
                                + "subject name that already exists, it will be overwritten.</p>");
                out.println(
                        "<p>If you enter a subject name that doesn't exist, "
                                + "it will automatically be created.</p>");
                out.println(
                        "<p>You you must fill in a minimum of two answer fields.</p></font>");

                out.println(
                        "<form name='form1' method='post' action='submitinsertquestion'>");
                out.println(
                        "<font size='3' face='Arial, Helvetica, sans-serif'>");
                out.println("<p>Question No:"); 
                out.println("<input name='questionno' type='text' size='6'>");
                out.println(
                        " Week No: <input name='weekno' type='text' size='6'></p>");
                out.println(
                        "<p>Subject Name: <input name='subjectname' type='text' size='20'>");
                out.println(
                        "Correct Answer No: <input name='correctanswerno' type='text' size='6'></p>");
                out.println("<p>Question:</p>");
                out.println(
                        "<p><textarea name='thequestion'cols='100' rows='2'></textarea></p>");
                out.println("<p>Answer Option 1:</p>");
                out.println(
                        "<p><textarea name='answeroption1' cols='100' rows='2'></textarea></p>");
                out.println("<p>Answer Option 2:</p>");
                out.println(
                        "<p><textarea name='answeroption2' cols='100' rows='2'></textarea></p>");
                out.println("<p>Answer Option 3:</p>");
                out.println(
                        "<p><textarea name='answeroption3' cols='100' rows='2'></textarea></p>");
                out.println("<p>Answer Option 4:</p>");
                out.println(
                        "<p><textarea name='answeroption4' cols='100' rows='2'></textarea></p>");
                out.println("<p>Answer Option 5:</p>");
                out.println(
                        "<p><textarea name='answeroption5' cols='100' rows='2'></textarea></p>");
                out.println("<p>Answer Option 6:</p>");
                out.println(
                        "<p><textarea name='answeroption6' cols='100' rows='2'></textarea></p>");
                out.println(
                        "<p><input type='submit' name='Submit' value='Submit'></p>");
                out.println("</font></form>");

                endHTMLCode(request, response);
            }
        }
    }
}
