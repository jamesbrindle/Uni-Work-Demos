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
public class viewquestionspw extends common {

    private static final long serialVersionUID = 1L;

    private DatabaseConnector connector;
    private ManFunctions manFun;

    /**
     * Constructor which creates instances of other associated classes
     */
    public viewquestionspw() {
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
            String inputString = "";
            String subjectName = request.getParameter("subjectname");

            inputString = connector.queryWeeks(subjectName, 0);

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

            if (inputString.length() > 2) {
                String[] inputArray = manFun.splitString(inputString);

                out.println(
                        "<p><font face='Arial, Helvetica, sans-serif'>"
                                + "Please Select an Available Week for subject: "
                                + subjectName + "</font></p>");
                out.println(
                        "<form name='form1' method='post' action='viewquestions'>");
                out.println("<select name='weekno'>");

                for (int i = 0; i < inputArray.length; i++) {
                    out.println("<option>" + inputArray[i] + "</option>");
                }

                out.println("</select><br>");
                out.println(
                        "<input type='hidden' name='subjectname' value='"
                                + subjectName + "'>");
                out.println("<input type='hidden' name='questionno' value='1'>"); //to carry forward variables
				
                out.println(
                        "<p><input type='submit' name='Next' value='Next'></p></form>");

                out.println(
                        "<form name='form1' method='post' action='viewquestionsps'>");
                out.println(
                        "<p><input type='submit' name='Back' value='Back'></p></form>");

            } else {
                out.println(
                        "<font face='Arial, Helvetica, sans-serif'>"
                                + "There are no weeks for this subject</font>");
                out.println(
                        "<form name='form1' method='post' action='viewquestionsps'>");
                out.println(
                        "<p><input type='submit' name='Back' value='Back'></p></form>");
	
            }

            out.println(
                    "<td width='30%' align='center' valign='top'>&nbsp;</td>");

            endHTMLCode(request, response);
        }
    }
}
