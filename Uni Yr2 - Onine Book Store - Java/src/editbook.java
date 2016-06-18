//this servlet gets a details of a selected book and puts them into editable text fields in order to update book details

import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class editbook extends common {

    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
        doPost(request, response);
    }
  
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        connect(request, response); //connect to mysql method located in 'common'
        
        //variable declarations that will be changed
        boolean validated = false;
        String bookid = request.getParameter("bookid");
        String title = "";
        String author = "";
        String category = "";
        String price = "";
        
        // check if teacher is logged in via validation cookie
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {		
            for (Cookie cookie: cookies) {
                if ((cookie.getName().equals("validation")
                        && (cookie.getValue().equals("yes")))) {	  				
                    validated = true;
                }
              
            }
        }
        
        try { //gets the information from database with bookid selected in booksearch
            String selectSQL = "select * from books where bookid =" + bookid
                    + "";

            System.err.println("DEBUG: Query: " + selectSQL);
		  
            Statement stmt = conn.createStatement();
            ResultSet rs1 = stmt.executeQuery(selectSQL);
	     
            ResultSetMetaData resultsMetaData = rs1.getMetaData();
            
            while (rs1.next()) { //show results
                Cookie bookidcookie = new Cookie("bookid", bookid);

                bookidcookie.setMaxAge(-1);
                response.addCookie(bookidcookie);
                
                title = rs1.getString("title");
                author = rs1.getString("author");
                category = rs1.getString("category");
                price = rs1.getString("price");
            }
            
            closeConnection(request, response); //close mysql method located in 'common'
	    
        } catch (SQLException se) {
            System.err.println(se);
        }        
               
        docTypeState(request, response); //html print, replica minimisation method located in 'common'       
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Edit Book Details</title>");
        firstBit(request, response); //html print, replica minimisation method located in 'common' 
        out.println(
                "<td height='60' colspan='4'><font size='5' face='Arial, Helvetica, sans-serif'>Edit Book Details</font></td>");
        navigation(request, response); //html print, replica minimisation method located in 'common' for naviation bar
        out.println(
                "<td width='29' height='153' align='center' valign='top'><p>&nbsp;</p></font></td>");
        out.println("<td width='716' align='left' valign='top'>");
        
        if (validated) { //if teacher logged in...
		
            out.println("<form action='editbookdo' method='post'>");
            out.println(
                    "<p><font face='Arial, Helvetica, sans-serif'></font><font face='Arial, Helvetica, sans-serif'>Title:");
            out.println(
                    "<input name='title' type='text' size='40' value='" + title
                    + "'>");
            out.println("</font></p>");
            out.println(
                    "<p><font face='Arial, Helvetica, sans-serif'></font><font face='Arial, Helvetica, sans-serif'>Author:");
            out.println(
                    "<input name='author' type='text' size='55' value='"
                            + author + "'>");
            out.println("</font></p>");
        
            out.println(
                    "<p><font face='Arial, Helvetica, sans-serif'></font><font face='Arial, Helvetica, sans-serif'>Category:");
            out.println(
                    "<input name='category' type='text' size='25' value='"
                            + category
                            + "'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
            out.println("</font></p>");
		
            out.println("<p><font face='Arial, Helvetica, sans-serif'>Price:"); 
            out.println(
                    "<input name='price' type='text' size='10' value='" + price
                    + "'>");
            out.println("</font>");
            out.println(
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp<input type='submit' value='save'>");
            out.println("</p>");
      
            out.println("</form>");
            out.println(
                    "<p><font face='Arial, Helvetica, sans-serif'>Edit the fields for bookID "
                            + bookid + "&nbsp and click save</font></p>");
            out.println(
                    "<p><font face='Arial, Helvetica, sans-serif'><a href='teachsearch'>Back to book search</a></font></p>");
            out.println(
                    "<p><font face='Arial, Helvetica, sans-serif'><a href='teachersarea'>Back to teachers area</a></font></p>");
                        
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
	     
        lastBit(request, response); //html print, replica minimisation method located in 'common' 
    }
}
