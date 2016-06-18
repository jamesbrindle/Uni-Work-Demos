//this servlet is to actually add the details got from addbook serlvet to database and confirm to user

import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class addbookdo extends common {

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
        
        connect(request, response); //connect to mysql method in 'common' - see 'common' for more details
        docTypeState(request, response);
        
        boolean validated = false;
        Cookie[] cookies = request.getCookies();
        
        // Check to see if teacher is logged in, in order to view this page
        if (cookies != null) {		
            for (Cookie cookie: cookies) {
                if ((cookie.getName().equals("validation")
                        && (cookie.getValue().equals("yes")))) {
                    validated = true;
                    break;
                }
            }
        }
      
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Add Book</title>");
        firstBit(request, response); //html print, replica minimisation method located in 'common'
        out.println(
                "<td height='60' colspan='4'><font size='5' face='Arial, Helvetica, sans-serif'>Add Book</font></td>");
        navigation(request, response); //html print, replica minimisation method located in 'common' for navigation bar
        out.println(
                "<td width='29' height='153' align='center' valign='top'><p>&nbsp;</p></font></td>");
        out.println("<td width='716' align='left' valign='top'>");
		
		  // get details from form entered in 'addbook'
        String bookid = request.getParameter("bookid");
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String category = request.getParameter("category");
        String price = request.getParameter("price");
        
        //declaration to check if the book already exists on the system
        boolean Available = true;
        String notAvailable = "";
        int rowCount = 1;
        
        if (validated) { //if teacher logged in...
        
            try { //search for book in database corrisponding to entered book id in 'addbook'
                String isAvailable = "select * from books where bookid ="
                        + bookid + "";                 
                    
                System.err.println("DEBUG: Query: " + isAvailable);
		  
                Statement stmt2 = conn.createStatement();
                ResultSet rs2 = stmt2.executeQuery(isAvailable);
	     
                ResultSetMetaData resultsMetaData2 = rs2.getMetaData();
                                            
                while (rs2.next()) {
                    rowCount++; 
                    notAvailable = rs2.getString("title");
                    rowCount++; //increment rowcount twice for each rowcount (i.e. if book already exists)
                }
                if ((rowCount > 1)) { //if row count is more than one, then the book doesn't already exist in database
                    Available = false;
                }
 				
            } catch (SQLException se) {
                System.err.println(se);
            }
        
            if (Available) { //if the book entered in 'addbook' doesn't exist in the database...
            
            	 //add book to database then see if the book successfully got added to database (confirmation)
                try {
                    PreparedStatement pstmt;

							//prepare insertion into database
                    pstmt = conn.prepareStatement(
                            "insert into books(bookid, title, author, category, price) values('"
                                    + bookid + "','" + title + "','" + author
                                    + "','" + category + "','" + price + "')");
	      
                    int insert;

                    insert = pstmt.executeUpdate(); //execute insertion
                    pstmt.close();  
                                        
	     				  // check to see if book has been successfully addded to the databse
                    String selectSQL = "select * from books where bookid ="
                            + bookid + "";  

                    System.err.println("DEBUG: Query: " + selectSQL);
		  
                    Statement stmt = conn.createStatement();
                    ResultSet rs1 = stmt.executeQuery(selectSQL);
	     
                    ResultSetMetaData resultsMetaData = rs1.getMetaData();
                                            
                    while (rs1.next()) { //show results
               
                        out.println("<font face='Arial, Helvetica, sans-serif'>"); 
                        out.println(
                                "<p>Your book has now been added to our database:</p>");
                        out.print("BookID: " + rs1.getString("bookid") + "<br>");
                        out.print("Title: " + rs1.getString("title") + "<br>");
                        out.print("Author:" + rs1.getString("author") + "<br>");
                        out.print(
                                "Category: " + rs1.getString("category")
                                + "<br>");
                        out.print(
                                "Price (GBP): " + rs1.getString("price")
                                + "<br>");
                        out.println(
                                "<p><font face='Arial, Helvetica, sans-serif'><a href=\"addbook\">Click here to add another</a></font></p>");
                        out.println("</font>");
              
                    }
                    out.println(
                            "<p><font face='Arial, Helvetica, sans-serif'><a href=\"teachersarea\">Click here to go back to teachers area</a></font></p>");
               	          
                    closeConnection(request, response); //close mysql method located in 'common'
                } catch (SQLException se) { //if problem exists while adding back, for example text entered into price field then...
                    System.err.println(se);
            
                    out.println(
                            "<p><font face='Arial, Helvetica, sans-serif'>There seemed to be a problem while adding your book, "
                                    + "make sure all fields are filled in and with the correct type, ie numbers can only go in the bookid field</font></p>");
                    out.println(
                            "<p><font face='Arial, Helvetica, sans-serif'><a href=\"addbook\">Click here to go back</a></font></p>");
                } 
       
            } else if (!(Available)) { //if the book entered in 'addbook' already exists in database...
                out.println(
                        "<p><font face='Arial, Helvetica, sans-serif'>Sorry, that bookID or serial number is already taken, please try again:</font></p>");
                out.println(
                        "<p><font face='Arial, Helvetica, sans-serif'><a href=\"addbook\">Click here to go back</a></font></p>");
            }
        } else if (!(validated)) { //if not logged in as a teacher...
            out.println(
                    "<p><font face='Arial, Helvetica, sans-serif'>Either your session expired or you are somewhere you shouldn't be</font></p>");
            out.println(
                    "<p><font face='Arial, Helvetica, sans-serif'><a href='loginteacher'>Please click here to log in as teacher</a></font></p>");
            out.println(
                    "<p><font face='Arial, Helvetica, sans-serif'><a href='welcome'>or cick here to go to welcome page</a></font></p>");
        }
       
        lastBit(request, response); //html print, replica minimisation method located in 'common'
    }
}
