//this servlet asks a user for their details in order to remove them from the database

import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class removeuser extends common {

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
        out.println("<title>Remove User</title>");
        firstBit(request, response); //html print, replica minimisation method located in 'common' 
        out.println(
                "<td height='60' align='center' colspan='5'><font size='5' face='Arial, Helvetica, sans-serif'>Remove User</font></td>");
        navigation(request, response); //html print, replica minimisation method located in 'common' for navigation bar
        out.println(
                "<td width='67' height='153' align='center' valign='top'><p>&nbsp;</p></td>");
        out.println("<td width='484' align='center' valign='top'> <p>&nbsp;</p>");
		
        out.println("<form method='post' action='userremove'>");
        out.println(
                "<p><strong><font face='Arial, Helvetica, sans-serif'>Type Details to Remove</font></strong></p>");
        out.println("<p><font face='Arial, Helvetica, sans-serif'> User Name:"); 
        out.println("<input type='text' name='nameuser' />");
        out.println("</font></p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'>Password:&nbsp &nbsp ");
        out.println("<input type='password' name='passworduser' />");
        out.println("</font></p>");
        out.println(
                "<font face='Arial, Helvetica, sans-serif'></p></font> <font face='Arial, Helvetica, sans-serif'> ");
        out.println("<input type='submit' name='Submituser2' value='Remove' />");
        out.println("</font></form>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'><a href='loginuser'>Back</a></font></p><td width='222' align='left' valign='top'>&nbsp;");
        out.println("</td>");
        lastBitTwo(request, response); //html print, replica minimisation method located in 'common' 
    }
}
