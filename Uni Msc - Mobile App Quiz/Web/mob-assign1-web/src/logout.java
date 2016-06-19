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
public class logout extends common {

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
        clearCookies(request, response);
        
        //redirect to login page
        out.println("<html>");
        out.println("<META HTTP-EQUIV='refresh' CONTENT='3;URL=login'>");
        out.println("<head>");
        out.println("<title>Log Out</title>");
        out.println("You have been logged out<br>");
        out.println(
                "If you are not redirected in 3 seconds "
                        + "<a href='/mob-assign1-web/servlet/login'>click here</a>");
        out.println("</head>");
        out.println("</html>");
    }
}
