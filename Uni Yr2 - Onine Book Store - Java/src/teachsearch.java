//this servlet asks the teacher to enter details in order to make a search on a particular book

import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class teachsearch extends common {

    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
      
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        docTypeState(request, response); //html print, replica minimisation method located in 'common' 
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Book Search - Teachers Area</title>");
        firstBit(request, response); //html print, replica minimisation method located in 'common' 
        out.println(
                "<td height='60' colspan='4'><font size='5' face='Arial, Helvetica, sans-serif'>Book Seach - Teachers Area</font></td>");
        navigation(request, response); //html print, replica minimisation method located in 'common' for navigation bar
        out.println(
                "<td width='29' height='153' align='center' valign='top'><p>&nbsp;</p></font></td>");
        out.println("<td width='716' align='left' valign='top'>");
		
		  // check if the teacher is logged in
        boolean validated = false;
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {		
            for (Cookie cookie: cookies) {
                if ((cookie.getName().equals("validation")
                        && (cookie.getValue().equals("yes")))) {
                    validated = true;
                    break;
                }
            }
        }
	  	
        if (validated) { //if teacher logged in...
            out.println("<font face='Arial, Helvetica, sans-serif'>");
            out.println("<p>Please search for a book to manage</font></p>");
		  		  
            out.println("<form action='teachbooksearch' method='post' >");
            out.println(
                    "<p><font face='Arial, Helvetica, sans-serif'></font><font face='Arial, Helvetica, sans-serif'>Title:&nbsp &nbsp&nbsp&nbsp&nbsp &nbsp");
            out.println("<input name='gettitle' type='text' size='40'>");
            out.println("</font></p>");        
            out.println(
                    "<p><font face='Arial, Helvetica, sans-serif'></font><font face='Arial, Helvetica, sans-serif'>Author: &nbsp&nbsp&nbsp");
            out.println(
                    "<input name='getauthor' type='text' size='40'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
            out.println("<input type='submit' />");
            out.println("</font></p>");		
            out.println("<p><font face='Arial, Helvetica, sans-serif'>Category:"); 
            out.println("<input name='getcat' type='text' size='40'>");
            out.println("</font></p>");      
            out.println("</form>");
            out.println(
                    "<p><font face='Arial, Helvetica, sans-serif'>Leave fields blank and click 'submit' to show all books</p>"); 
            out.println("<p></p>");
            out.println("<p><a href='teachersarea'>Back</a></font></p>");
		 
            lastBit(request, response); //html print, replica minimisation method located in 'common' 
        } else if (!(validated)) { //if teacher not logged in...
            out.println("<font face='Arial, Helvetica, sans-serif'>");
            out.println(
                    "<p>Either your session expired or you are somewhere you shouldn't be</p>");
            out.println(
                    "<p><a href='loginteacher'>Please click here to log in as teacher</a></p>");
            out.println(
                    "<p><a href='welcome'>or cick here to go to welcome page</a></p>");
            out.println("</font>");
            lastBit(request, response); //html print, replica minimisation method located in 'common' 
        }
		 
    }
  
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {}
}
