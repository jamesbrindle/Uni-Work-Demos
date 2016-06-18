//this servlet is to enter details of a book to be searched for

import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class booksearchmain extends common {

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
        out.println("<title>Search for Books</title>");
        firstBit(request, response); //html print, replica minimisation method located in 'common' 
        out.println(
                "<td height='60' colspan='4'><font size='5' face='Arial, Helvetica, sans-serif'>Search for Books</font></td>");
        navigation(request, response); //html print, replica minimisation method located in 'common' for navigation bar
        out.println(
                "<td width='29' height='153' align='center' valign='top'><p>&nbsp;</p></font></td>");
        out.println("<td width='716' align='left' valign='top'>");
		
        out.println("<form action='booksearch' method='post'>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'></font><font face='Arial, Helvetica, sans-serif'>Title:&nbsp &nbsp&nbsp&nbsp&nbsp &nbsp");
        out.println("<input name='gettitle' type='text' size='40'>");
        out.println("</font></p>");
        
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'></font><font face='Arial, Helvetica, sans-serif'>Author: &nbsp&nbsp&nbsp");
        out.println(
                "<input name='getauthor' type='text' size='40'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
        out.println("<input type='submit'/>");
        out.println("</font></p>");
		
        out.println("<p><font face='Arial, Helvetica, sans-serif'>Category:"); 
        out.println("<input name='getcat' type='text' size='40'>");
        out.println("</font></p>");
      
        out.println("</form>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'>Leave fields blank and click 'submit' to show all books</font></p>");
            	
        lastBit(request, response); //html print, replica minimisation method located in 'common'
    }
}
