//this servlet adds a book to the favourite's table in the database

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;


public class addtofavourites extends common {
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
	
        response.setContentType("text/html"); 
        PrintWriter out = response.getWriter();

        // get the book id from 'booksearch'
        String bookid = request.getParameter("bookid");

        docTypeState(request, response); //html print, replica minimisation method located in 'common'    
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Adding to Favourites</title>");
        firstBit(request, response); //html print, replica minimisation method located in 'common'
        out.println(
                "<td height='60' colspan='4'><font size='5' face='Arial, Helvetica, sans-serif'>Adding to Favourites</font></td>");
        navigation(request, response); //html print, replica minimisation method located in 'common' for navigation bar
        out.println(
                "<td width='29' height='153' align='center' valign='top'><p>&nbsp;</p></font></td>");
        out.println("<td width='716' align='left' valign='top'>");
        
        //declare variables that will be changed
        boolean validated = false;
        String username = "";
        String userpassword = "";
       		
		  //check to see if a user if logged in and if so get their username and password
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {		
            for (Cookie cookie: cookies) {
                if ((cookie.getName().equals("validationuser")
                        && (cookie.getValue().equals("yes")))) {	  				
                    validated = true;
                }
                if ((cookie.getName().equals("detailnameuser"))) {
                    username = cookie.getValue();
                }
                if ((cookie.getName().equals("detailpassworduser"))) {
                    userpassword = cookie.getValue();
	  				
                }
            }
        }
        
        if(validated) { //if user logged in...
        connect(request, response); //connect to mysql method located in 'common'
          
        try { //get book information from selected book id for confirmation purposes
            String selectSQL = "select * from books WHERE bookid ='" + bookid
                    + "'";

            System.err.println("DEBUG: Query: " + selectSQL);
            Statement stmt = conn.createStatement();
            ResultSet rs1 = stmt.executeQuery(selectSQL);

            rs1.next(); //show results
            
            out.println("<font face='Arial, Helvetica, sans-serif'>");    
            out.println("<p>The following book has been added to your favourites:</p>");
            out.print("BookID: " + rs1.getInt("BookID") + "<br>");
            out.print("Title: " + rs1.getString("Title") + "<br>");
            out.print("Author: " + rs1.getString("Author") + "<br>");
            out.print("Category:" + rs1.getString("Category") + "<br>");
            out.print("Price: £" + rs1.getString("Price") + "<br>");
                    
        } catch (SQLException e) {
            System.err.println(e);
        }
        
        try {//add favourite to database
        		PreparedStatement pstmt;

            pstmt = conn.prepareStatement(
                        "insert into favourites (bookid, username) values("
                                + bookid + ",'" + username + "')");
	      
            int insert;

            insert = pstmt.executeUpdate();
            pstmt.close();
                
            closeConnection(request, response); //close mysql connection method located in 'common'
                   
        } catch (SQLException e) {
            System.err.println(e);
        }
        
        out.println("<font face='Arial, Helvetica, sans-serif'>"); 
        out.println(
                "<br><a href=\"booksearchmain\">search for more books</a>");  
        out.println(
                "<br><a href=\"favourites\">go to favourites</a>");
        out.println("</font>");        
        
        } else if(!(validated)) { //if not logged in...
        		out.println("<font face='Arial, Helvetica, sans-serif'>"); 
        		out.println("You are not logged in, please log in to add to favourites");
        		out.println(
                "<br><a href=\"login\">click here to log in</a>");
				out.println(
                "<br><a href=\"booksearchmain\">search for more books</a>");
          }
        
        lastBit(request, response); //html print, replica minimisation method located in 'common'
    }
}	

