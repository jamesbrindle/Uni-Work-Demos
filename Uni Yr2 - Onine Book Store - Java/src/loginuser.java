//this servlet asks a user to enter their username and password to log in, and also gives the option to remove their details

import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class loginuser extends common {

    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
        doPost(request, response);
    }
  
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        logOut(request, response); //clears all session cookies as a new user is logging in
        
        docTypeState(request, response); //html print, replica minimisation method located in 'common'        
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Login</title>");
        firstBit(request, response); //html print, replica minimisation method located in 'common' 
        out.println(
                "<td height='60' align='center' colspan='5'><font size='5' face='Arial, Helvetica, sans-serif'>Log In</font></td>");
        navigation(request, response); //html print, replica minimisation method located in 'common' for navigation bar
        out.println(
                "<td width='67' height='153' align='center' valign='top'><p>&nbsp;</p></td>");
        out.println("<td width='484' align='center' valign='top'> <p>&nbsp;</p>");
		
        out.println("<form method='post' action='usersarea'>");
        out.println(
                "<p><strong><font face='Arial, Helvetica, sans-serif'>Users Login</font></strong></p>");
        out.println("<p><font face='Arial, Helvetica, sans-serif'> User Name:");
        out.println("<input type='text' name='nameuser' />");
        out.println("</font></p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'>Password:&nbsp &nbsp"); 
        out.println("<input type='password' name='passworduser' />");
        out.println("</font></p>");
        out.println("<font face='Arial, Helvetica, sans-serif'></p>"); 
        out.println(
                "<input type='submit' name='Submitteacher' value='Log In' />");
        out.println("</font></form>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'><a href='login'>Back</a></font></p>");
        out.println("<p align='center'>&nbsp;</p>");
        out.println("<p align='center'>&nbsp;</p>");
        out.println(
                "<p align='center'><font face='Arial, Helvetica, sans-serif'>To remove your details from our system please click <a href='removeuser'>here</a></font></p>");
        out.println("</td>");
        out.println("<td width='222' align='left' valign='top'>&nbsp;</td>");
      
        lastBitTwo(request, response); //html print, replica minimisation method located in 'common' 
    }
}
