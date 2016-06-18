//this servlet adds a selected book to the shopping cart array from the reading list search serlvet

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;


public class addtocart2 extends common {
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
	
        response.setContentType("text/html"); 
        PrintWriter out = response.getWriter();

        // get information on bookid to enter to cart array and the courseid to enable user to go back to previous
        // servlet without loosing their search result information they've already selected
        String bookid = request.getParameter("bookid");
        String selectcourse = request.getParameter("selectcourse");

        HttpSession session = request.getSession();
	
        ArrayList<String> shoppingcart; // is an array of bookid's
	
        docTypeState(request, response);  //html print, replica minimisation method located in 'common'      
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
        } else { //get shoppingcart array information
            shoppingcart = (ArrayList<String>) session.getAttribute(
                    "shoppingCart");
        }
	
        shoppingcart.add(bookid); //add book to shopping cart
		
        connect(request, response); //connect to mysql method located in 'common'
          
        try { //get book information for confirmation purposes
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
            
            closeConnection(request, response); //close mysql connection method located in 'common'  
        } catch (SQLException e) {
            System.err.println(e);
        }
        out.println("<br><br><form action='readinglistresult' action='post'>");
        out.println(
                "<input type=\"hidden\" name=\"selectcourse\" value=\""
                        + selectcourse + "\">"
                        + "<input type=\"submit\" value=\"back\" &rarr;\">"
                        + "</form>");
        out.println(
                "<p><p><a href=\"shoppingcart\">go to shopping cart</a></p></p>");
        out.println("</font>");
        lastBit(request, response); //html print, replica minimisation method located in 'common'
    }
}	

