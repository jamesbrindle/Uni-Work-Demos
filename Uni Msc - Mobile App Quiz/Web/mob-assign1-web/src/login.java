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
public class login extends common {

    private static final long serialVersionUID = 1L;

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
        
        docTypeState(request, response);        
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Login</title>");
        startHTMLCode(request, response); 
        out.println(
                "<td height='60' align='center' colspan='5'>"
                        + "<font size='5' face='Arial, Helvetica, sans-serif'>Log In</font></td>");
        
        navigationHTML(request, response);
 
        out.println("<td width='40%' align='center' valign='top'><p>&nbsp;</p>");        
        out.println("<form method='post' action='home'>");
        out.println(
                "<p><strong><font face='Arial, Helvetica, sans-serif'>Login</font></strong></p>");
        out.println("<p><font face='Arial, Helvetica, sans-serif'> User ID:");
        out.println("<input type='text' name='userid' />");
        out.println("</font></p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'>Password:&nbsp &nbsp"); 
        out.println("<input type='password' name='password'/>");
        out.println("</font></p>");
        out.println("<font face='Arial, Helvetica, sans-serif'>"); 
        out.println("<input type='submit' name='submit' value='login'/>");
        out.println("</font></form>");        
        out.println(
                "<br><br><font face='Arial, Helvetica, sans-serif'>For Testing Purposes<br>"
                        + "User ID: 54321<br>Password: password</font></td>");
        out.println("<td width='30%' align='center' valign='top'>&nbsp;</td>");
        
        endHTMLCode(request, response);
    }
}
