//this servlet confirms the logging out (clearing of session cookies) 

import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class logout extends common {

    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
        doPost(request, response);	
    }      

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
      
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
      
        logOut(request, response); //method to clear all session cookies located in 'common'
     
        docTypeState(request, response); //html print, replica minimisation method located in 'common' 
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Logging Out</title>");
        firstBit(request, response); //html print, replica minimisation method located in 'common' 
        out.println(
                "<td height='60' colspan='4'><font size='5' face='Arial, Helvetica, sans-serif'>Logging Out</font></td>");
        navigation(request, response);	 //html print, replica minimisation method located in 'common'  for navigation bar
        out.println(
                "<td width='29' height='153' align='center' valign='top'><p>&nbsp;</p></font></td>");
        out.println("<td width='716' align='left' valign='top'>");	
        out.println("<br>"); 
        out.println("<font face='Arial, Helvetica, sans-serif'>");  	   	   
        out.println("<p> You are now logged out </p>"); 
        out.println("<p><a href='welcome'>Ok</a></p>"); 
        out.println("</font>");	
        lastBit(request, response);  //html print, replica minimisation method located in 'common' 
    }
}
        
