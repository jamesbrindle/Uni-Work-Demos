//this servlet asks the teacher to enter details in order to make a search for specific users

import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class usersearch extends common {

    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
      
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        docTypeState(request, response); //html print, replica minimisation method located in 'common' 
        out.println("<html>");
        out.println("<head>");
        out.println("<title>User Search - Teachers Area</title>");
        firstBit(request, response); //html print, replica minimisation method located in 'common' 
        out.println(
                "<td height='60' colspan='4'><font size='5' face='Arial, Helvetica, sans-serif'>User Search - Teachers Area</font></td>");
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
            out.println("<p>Please search for a user to manage</font></p>");
		  		  
            out.println("<form action='usersearchresult' method='post' >");
            out.println(
                    "<p><font face='Arial, Helvetica, sans-serif'></font><font face='Arial, Helvetica, sans-serif'>Name:&nbsp &nbsp&nbsp&nbsp&nbsp &nbsp");
            out.println("<input name='getname' type='text' size='40'>");
            out.println("</font></p>");        
            out.println(
                    "<p><font face='Arial, Helvetica, sans-serif'></font><font face='Arial, Helvetica, sans-serif'>Address: &nbsp&nbsp&nbsp");
            out.println(
                    "<input name='getaddress' type='text' size='40'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
            out.println("<input type='submit' />");
            out.println("</font></p>");		
            out.println("<p><font face='Arial, Helvetica, sans-serif'>Post Code:"); 
            out.println("<input name='getpostcode' type='text' size='40'>");
            out.println("</font></p>");      
            out.println("</form>");
            out.println(
                    "<p><font face='Arial, Helvetica, sans-serif'>Leave fields blank and click 'submit' to show all users</p>"); 
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
