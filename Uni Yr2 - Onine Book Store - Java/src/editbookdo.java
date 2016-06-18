//This servet actually updates the book details on the database with details got from 'editbook'

import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class editbookdo extends common {

    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
        doPost(request, response);
    }
  
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        connect(request, response); //connect to mysql method located in 'common'
        docTypeState(request, response);
      
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
		
		  //gets the details from a form from 'editbook'
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String category = request.getParameter("category");
        String price = request.getParameter("price");
        String bookid = "";
        
        
        //checks to see if teacher is logged in          
        boolean validated = false;
                             		
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {		
            for (Cookie cookie: cookies) {
                if ((cookie.getName().equals("validation")
                        && (cookie.getValue().equals("yes")))) {	  				
                    validated = true;
                }
                if ((cookie.getName().equals("bookid"))) {	  				
                    bookid = cookie.getValue();
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
               
            }
        }
        if (validated) { //if teacher is logged it...		
            try { //update the book with the details from 'editbook'
                PreparedStatement pstmt;

                pstmt = conn.prepareStatement(
                        "update books SET title ='" + title + "', author='"
                        + author + "', category='" + category + "', price='"
                        + price + "' where bookid='" + bookid + "'");
                int insert;

                insert = pstmt.executeUpdate();
                pstmt.close();  
	     
	     			 //then check if the book has been successfully updated and confirms
                String selectSQL = "select * from books where bookid =" + bookid
                        + "";

                System.err.println("DEBUG: Query: " + selectSQL);
		  
                Statement stmt = conn.createStatement();
                ResultSet rs1 = stmt.executeQuery(selectSQL);
	     
                ResultSetMetaData resultsMetaData = rs1.getMetaData();
                                            
                while (rs1.next()) { //show results
                    out.println("<font face='Arial, Helvetica, sans-serif'>"); 
                    out.println(
                            "<p>The details have now been changed and are as follows:</p>");
                    out.print("BookID: " + rs1.getString("bookid") + "<br>");
                    out.print("Title: " + rs1.getString("title") + "<br>");
                    out.print("Author:" + rs1.getString("author") + "<br>");
                    out.print("Category: " + rs1.getString("category") + "<br>");
                    out.print("Price: " + rs1.getString("price") + "<br>");
                    out.println(
                            "<br><a href=\"teachsearch\">Search more books</a>");
                    out.println(
                            "<br><a href=\"teachersarea\">Back to teachers area</a>");
                    out.println("</font>");
	         
                }
            
                closeConnection(request, response); //close mysql connection located in 'common'
            } catch (SQLException se) {
                System.err.println(se);
            } 
        
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
