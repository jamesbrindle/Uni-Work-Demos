//this servlet adds a book to the shopping cart selected from 'booksearch' as a session array

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;


public class addtocart extends common {
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
	
        response.setContentType("text/html"); 
        PrintWriter out = response.getWriter();

        // get the book id from 'booksearch'
        String bookid = request.getParameter("bookid");

        HttpSession session = request.getSession();
	
        ArrayList<String> shoppingcart; // an array of the bookids
	
        docTypeState(request, response); //html print, replica minimisation method located in 'common'    
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Adding to Cart</title>");
        firstBit(request, response); //html print, replica minimisation method located in 'common'
        out.println(
                "<td height='60' colspan='4'><font size='5' face='Arial, Helvetica, sans-serif'>Adding to Cart</font></td>");
        navigation(request, response); //html print, replica minimisation method located in 'common' for navigation bar
        out.println(
                "<td width='29' height='153' align='center' valign='top'><p>&nbsp;</p></font></td>");
        out.println("<td width='716' align='left' valign='top'>");
        if (session.isNew()) {
            // new session so create a new ArrayList
            shoppingcart = new ArrayList<String>(); 
            session.setAttribute("shoppingCart", shoppingcart); // add array to session 
        } else { //get array information
            shoppingcart = (ArrayList<String>) session.getAttribute(
                    "shoppingCart");
        }
	
        shoppingcart.add(bookid); //add bookid to array
		
        connect(request, response); //connect to mysql method located in 'common'
          
        try { //get book information from selected book id for confirmation purposes
            String selectSQL = "select * from books WHERE bookid ='" + bookid
                    + "'";

            System.err.println("DEBUG: Query: " + selectSQL);
            Statement stmt = conn.createStatement();
            ResultSet rs1 = stmt.executeQuery(selectSQL);

            rs1.next(); //show results
            
            out.println("<font face='Arial, Helvetica, sans-serif'>");    
            out.println("<p>The following book has been added to your cart:</p>");
            out.print("BookID: " + rs1.getInt("BookID") + "<br>");
            out.print("Title: " + rs1.getString("Title") + "<br>");
            out.print("Author: " + rs1.getString("Author") + "<br>");
            out.print("Category:" + rs1.getString("Category") + "<br>");
            out.print("Price: £" + rs1.getString("Price") + "<br>");
            
            closeConnection(request, response);	//close mysql connected method located in common    
        } catch (SQLException e) {
            System.err.println(e);
        }
        out.println(
                "<p><p><a href=\"booksearchmain\">search for more books</a></p></p>");
        out.println(
                "<p><p><a href=\"shoppingcart\">go to shopping cart</a></p></p>");
        out.println("</font>");
        lastBit(request, response); //html print, replica minimisation method located in 'common'
    }
}	

