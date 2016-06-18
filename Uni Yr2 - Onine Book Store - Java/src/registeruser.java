//this servlet asks a user to enter their details in order to add a new user to the databse

import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class registeruser extends common {

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
        out.println("<title>Register User</title>");
        firstBit(request, response); //html print, replica minimisation method located in 'common' 
        out.println(
                "<td height='60' align='center' colspan='4'><font size='5' face='Arial, Helvetica, sans-serif'>Register User</font></td>");
        navigation(request, response); //html print, replica minimisation method located in 'common' for navigation bar
        out.println(
                "<td width='29' height='153' align='center' valign='top'><p>&nbsp;</p></font></td>");
        out.println("<td width='716' align='left' valign='top'>");
		
        out.println("<form method='post' action='adduser'>");
        out.println(
                "<p><strong><font face='Arial, Helvetica, sans-serif'>Please Enter Details</font></strong></p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'> Name: &nbsp &nbsp &nbsp &nbsp"); 
        out.println("<input type='text' name='name' />");
        out.println("</font></p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'>Address:&nbsp &nbsp &nbsp");
        out.println("<input name='address' type='text' size='50' />");
        out.println("</font></p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'>Post Code:&nbsp");
        out.println("<input type='text' name='postcode' />");
        out.println("</font></p>");
        out.println("<p><font face='Arial, Helvetica, sans-serif'>User Name:"); 
        out.println("<input type='text' name='username' />");
        out.println("</font></p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'>Password:&nbsp &nbsp"); 
        out.println("<input type='password' name='password' />");
        out.println("</font></p>");
        out.println(
                "<font face='Arial, Helvetica, sans-serif'></p></font> <font face='Arial, Helvetica, sans-serif'>"); 
        out.println(
                "<input type='submit' name='Submitdetails' value='Submit' />");
        out.println("</font>"); 
        out.println("</form>");  
        out.println(
                "<p align='left'><font face='Arial, Helvetica, sans-serif'><a href='login'>Back</a></font></p>");
        out.println("<p align='center'>&nbsp;</p>");
        out.println("<p align='center'>&nbsp;</p>");
        out.println("<p align='center'>&nbsp;</p>");
        out.println(
                "<p align='center'><font face='Arial, Helvetica, sans-serif'></font></p>");
        out.println("<td width='171' align='left' valign='top'>&nbsp;");
        out.println("<td width='121' align='center' valign='top'><p>&nbsp;</p>");
        out.println("</td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }
}
