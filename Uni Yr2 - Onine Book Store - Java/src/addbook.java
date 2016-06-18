//this servlet is to enter to details of a book to be added

import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class addbook extends common {

    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
        doPost(request, response);
    }
  
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        boolean validated = false;

        Cookie[] cookies = request.getCookies();
		  
		  // Check to see if teacher is logged in, in order to view this page
        if (cookies != null) {		
            for (Cookie cookie: cookies) {
                if ((cookie.getName().equals("validation")
                        && (cookie.getValue().equals("yes")))) {	  				
                    validated = true;
                }
                
            }
        }        
               
        docTypeState(request, response);        
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Add Book</title>");
        firstBit(request, response);
        out.println(
                "<td height='60' align='center' colspan='4'><font size='5' face='Arial, Helvetica, sans-serif'>Add Book</font></td>");
        navigation(request, response);
        out.println(
                "<td width='29' height='153' align='center' valign='top'><p>&nbsp;</p></font></td>");
        out.println("<td width='716' align='left' valign='top'>");
			
        if (validated) { //if teacher logged in...
            out.println("<form method='post' action='addbookdo'>");
            out.println(
                    "<p><strong><font face='Arial, Helvetica, sans-serif'>Please Enter Book Details</font></strong></p>");
            out.println("<p><font face='Arial, Helvetica, sans-serif'> BookID:"); 
            out.println(
                    "<input type='text' size='12' name='bookid'>&nbsp This could be a serial number");
            out.println("</font></p>");
            out.println("<p><font face='Arial, Helvetica, sans-serif'>Title:");
            out.println("<input name='title' type='text' size='50' />");
            out.println("</font></p>");
            out.println("<p><font face='Arial, Helvetica, sans-serif'>Author:");
            out.println("<input type='text' name='author' size='50'>");
            out.println("</font></p>");
            out.println("<p><font face='Arial, Helvetica, sans-serif'>Category:"); 
            out.println("<input type='text' name='category' size='40'>");
            out.println("</font></p>");
            out.println("<p><font face='Arial, Helvetica, sans-serif'>Price:"); 
            out.println(
                    "<input type=text' name='price' size='10'>&nbsp Enter in GBP as number only (without £)");
            out.println("</font></p>");
            out.println(
                    "<font face='Arial, Helvetica, sans-serif'></p></font> <font face='Arial, Helvetica, sans-serif'>"); 
            out.println(
                    "<input type='submit' name='Submitdetails' value='Submit' />");
            out.println("</font>"); 
            out.println("</form>");  
            out.println(
                    "<p align='left'><font face='Arial, Helvetica, sans-serif'><a href='teachersarea'>Back</a></font></p>");
            out.println("<p align='center'>&nbsp;</p>");
            out.println("<p align='center'>&nbsp;</p>");
            out.println("<p align='center'>&nbsp;</p>");
            out.println(
                    "<p align='center'><font face='Arial, Helvetica, sans-serif'></font></p>");
        
        } else if (!(validated)) { //if teacher not logged in...
            out.println("<font face='Arial, Helvetica, sans-serif'>");
            out.println(
                    "<p>Either your session expired or you are somewhere you shouldn't be</p>");
            out.println(
                    "<p><a href='loginteacher'>Please click here to log in as teacher</a></p>");
            out.println(
                    "<p><a href='welcome'>or cick here to go to welcome page</a></p>");
            out.println("</font>");
        }
        
        out.println("<td width='171' align='left' valign='top'>&nbsp;");
        out.println("<td width='121' align='center' valign='top'><p>&nbsp;</p>");
        out.println("</td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }
}
