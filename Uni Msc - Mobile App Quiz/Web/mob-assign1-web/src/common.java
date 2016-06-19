import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * common is a simply class containing re-usable methods allowing code minimisation for other servlet classes
 * which are part of the student quiz administration website and typically involves extensive http allowing
 * the web pages to render dynamically
 * 
 * @author Jamie Brindle (06352322) - Msc - Manchester Metropolitan University
 */
public class common extends HttpServlet {

    private static final long serialVersionUID = 1L;
    protected Connection conn = null; 
    protected PrintWriter out;

    /**
     * Simply contains typical html code used by virtually all servlets for this applications
     * 
     * @param request
     * @param response
     */
    protected void startHTMLCode(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");

        out.println(
                "<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<table width='70%' border='0'><tr>");
        out.println(
                "<td width='90%'><font size='5' face='Arial, Helvetica, sans-serif'>"
                        + "Welcome to The Online Mobile Quiz Management Site</font></td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("<table width='85%' height='281' border='0'>");
        out.println("<tr><td>&nbsp;&nbsp;</td></tr>");
        out.println("<tr align='center'>");
    }

    /**
     * Simply contains typical html code used by virtually all servlets for this applications
     * 
     * @param request
     * @param response
     */
    protected void endHTMLCode(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");

        out.println("</td>");	
        out.println("</tr>");
        out.println("</table>");		
        out.println("</body>");
        out.println("</html>");
    }

    /**
     * Simply contains typical html code used by virtually all servlets for this applications
     * 
     * @param request
     * @param response
     */
    protected void docTypeState(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");

        String docType = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 "
                + "Transitional//EN\">\n";

        out.println(docType);
    }	

    /**
     * Used to clear this applications browser cookies
     * 
     * @param request
     * @param response
     */
    protected void clearCookies(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");

        String[] cookieList = { 
            "validation", "firstname" + "secondname"};

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {		
            for (Cookie cookie: cookies) {
                for (int i = 0; i < cookieList.length; i++) { 
                    if ((cookie.getName().equals(cookieList[i]))) {	  				
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }	  			
                }
            }
        }
    }

    /**
     * Authorises a user by seeing if a particular cookie and its corresponding value is present
     * 
     * @param request
     * @param response
     */
    protected boolean validateUser(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");

        boolean validated = false;
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie c: cookies) {
                if ((c.getName().equals("validation"))
                        && (c.getValue().equals("yes"))) {
                    validated = true;
                    break;
                }   
            }        
        }
        return validated;
    }
	
    /**
     * Redirects a user to the login page and prevents access to unauthorised location if a user
     * is not logged in
     * 
     * @param request
     * @param response
     */
    protected void loginFailureHTML(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        out.println("<html>");
        out.println("<META HTTP-EQUIV='refresh' CONTENT='3;URL=login'>");
        out.println("<head>");
        out.println(
                "You are not logged in or Login failed, you will be redirected to the login page<br>");
        out.println(
                "If you are not redirected in 3 seconds "
                        + "<a href='/mob-assign1-web/servlet/login'>click here</a>");
        out.println("</head>");
    }

    /**
     * Simply contains typical html code used by virtually all servlets for this applications
     * 
     * @param request
     * @param response
     */
    protected void navigationHTML(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");

        out.println("</tr>");
        out.println("<tr align='left'>");
        out.println("<td height='153' width='30%' valign='top'><blockquote>");
        out.println(
                "<p><font size='4' face='Arial, Helvetica, sans-serif'><a href='home'>"
                        + "Home</a></p>"); 
        out.println(
                "<p><font size='4' face='Arial, Helvetica, sans-serif'><a href='login'>"
                        + "Log In</font></a></p>");
        out.println(
                "<p><font size='4' face='Arial, Helvetica, sans-serif'><a href='viewquestionsps'>"
                        + "View Questions</a></font></p>");
        out.println(
                "<p><font size='4' face='Arial, Helvetica, sans-serif'><a href='insertquestion'>"
                        + "Insert Questions</a></font></p>");
        out.println(
                "<p><font size='4' face='Arial, Helvetica, sans-serif'><a href='viewstatisticsps'>"
                        + "View Statistics</a></font></p>");
        out.println(
                "<p><font size='4' face='Arial, Helvetica, sans-serif'><a href='logout'>"
                        + "Log Out</a></font></p>");
        out.println("</blockquote></td>");        
    }         
}
