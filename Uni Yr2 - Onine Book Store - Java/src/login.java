//this servlet brings up the log in page that asks a user which 'level' to log in as

import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class login extends common {

    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
        doPost(request, response);
    }
  
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        docTypeState(request, response); //html print, replica minimisation method located in 'common'         
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Login</title>");
        firstBit(request, response); //html print, replica minimisation method located in 'common' 
        out.println(
                "<td height='60' align='center' colspan='5'><font size='5' face='Arial, Helvetica, sans-serif'>Log In</font></td>");
        navigation(request, response); //html print, replica minimisation method located in 'common' for navigation bar
        out.println(
                "<td width='13' height='153' align='center' valign='top'><p>&nbsp;</p></td>");
        out.println("<td width='575' align='center' valign='top'> <p>&nbsp;</p>");
        
        out.println("<form name='formuser' id='form1' action='loginuser'>");
        out.println("<input type='submit' name='user' value='Log in as User' />");
        out.println("</form>");
        out.println("<p>"); 
        out.println("<form name='formteacher' id='form1' action='loginteacher'>");
        out.println(
                "<input type='submit' name='teacher' value='Log in as Teacher' />");
        out.println("</form>");
        out.println("<p>"); 
        out.println("</form></p>");
        out.println("</td>");
        out.println("<td width='183' align='center' valign='top'>&nbsp;");
        lastBitTwo(request, response); //html print, replica minimisation method located in 'common' 
    }
}
