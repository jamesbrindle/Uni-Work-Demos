//this servlet shows the user the books they've added to the shopping cart and shows additional options
// such as remove item(s), place order and checks if a user is logged in

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;


public class shoppingcart extends common {

    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
	
        response.setContentType("text/html"); 
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
	
        ArrayList<String> shoppingcart; 
	
        // Check to see if this is a new session
        if (session.isNew()) {
            shoppingcart = new ArrayList<String>();
            session.setAttribute("shoppingCart", shoppingcart);
            response.sendRedirect("shoppingcart");
            return;
        } else { //gets session information
            shoppingcart = (ArrayList<String>) session.getAttribute(
                    "shoppingCart");
        }
			
        connect(request, response); //connect to mysql method located in 'common'
        docTypeState(request, response); //html print, replica minimisation method located in 'common' 
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Shopping Cart</title>");
        firstBit(request, response); //html print, replica minimisation method located in 'common' 
        out.println(
                "<td height='60' colspan='4'><font size='5' face='Arial, Helvetica, sans-serif'>Shopping Cart</font></td>");
        navigation(request, response); //html print, replica minimisation method located in 'common' for navigation bar
        out.println(
                "<td width='29' height='153' align='center' valign='top'><p>&nbsp;</p></font></td>");
        out.println("<td width='716' align='left' valign='top'>");
		
        out.println(
                "<table align='centre' border=\"1\" cellspacing=\"2\" cellpadding=\"2\">"
                        + "<tr><th align='left'>BookID</th><th align='left'>Title</th><th align='left'>Author</th><th align='left'>Category</th><th align='left'>Price (GBP)</th><th>&nbsp;</th></tr>");

		  //prepare book details search statement
        String selectSQL = "select * from books where ";

		  //get all book id's from shoppingcart array
        for (int i = 0; i < shoppingcart.size(); i++) {
            if (i != 0) {
                selectSQL += " OR ";
            }
            selectSQL += "bookid = '" + shoppingcart.get(i) + "'";
        }
        int rowCount = 1;

        try { //show all book details found in shoppingcart
	    
            System.err.println("DEBUG: Query: " + selectSQL);
            Statement stmt = conn.createStatement();
            ResultSet rs1 = stmt.executeQuery(selectSQL);
	    	
            double subtotal = 0.00;
            DecimalFormat d = new DecimalFormat(); //declare decimal format

				//set decimal format
            d.setMinimumFractionDigits(2); 
            d.setMaximumFractionDigits(2);
               
            while (rs1.next()) { //show results
                rowCount++;
                      	
                out.print("<tr>");
                out.print("<td>" + rs1.getInt("BookID") + "</td>");
                out.print("<td>" + rs1.getString("Title") + "</td>");
                out.print("<td>" + rs1.getString("Author") + "</td>");
                out.print("<td>" + rs1.getString("Category") + "</td>");
                out.print("<td>" + rs1.getString("Price") + "</td>");
                subtotal = rs1.getDouble("Price") + subtotal;
                
                out.print(
                        "<td valign='middle'><form action=\"removeitem\" method=\"get\">"
                                + "<input type=\"hidden\" name=\"bookid\" value=\""
                                + rs1.getString("bookid") + "\">"
                                + "<input type=\"submit\" value=\"remove\" &rarr;\">"
                                + "</form></td>");
                rowCount++; //increment row counter
                                
            }	
			
            out.print(
                    "<tr align='center'><td colspan='6'><strong>Subtotal: £"
                            + d.format(subtotal) + "</strong></td></tr>");
                            
        } catch (SQLException e) {
            System.err.println(e);
        }
        
        out.println("</table>");
        out.println("<font face='Arial, Helvetica, sans-serif'>");
        out.println(
                "<p><p><a href=\"booksearchmain\">click here to search for books</a></p></p>");
        out.println("</font>");
			 
		  //checks to see if a use is logged in and gets their username and password	 
        boolean validated = false;
        String username = "";
        String userpassword = "";
        String greeting = "";
		
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
    
        if (validated) { //if user is logged in ...
            out.println("<p></p>");
			 
            try { //select their name from database with username and password to give user greeting
                String selectUser = "select * from users where Username ='"
                        + username + "' && Password ='" + userpassword + "'";

                System.err.println("DEBUG: Query: " + selectUser);
		  
                Statement stmt2 = conn.createStatement();
                ResultSet rs2 = stmt2.executeQuery(selectUser);
	     
                ResultSetMetaData resultsMetaData = rs2.getMetaData();
                int columnCount = resultsMetaData.getColumnCount(); 
                
                while (rs2.next()) { //gets name
        
                    greeting = " " + rs2.getString("Name") + "";
        
                }
	          
                closeConnection(request, response); //close mysql connection located in 'common'
	    
            } catch (SQLException se) {
                System.err.println(se);
            }   
            out.println("<br>");
			 
            out.println("<font face='Arial, Helvetica, sans-serif'>");
            out.println(
                    "<p>What would you like to do" + greeting + "?</font></p>");
            out.println("<p></p>");
            
            out.println("<p></p>");
            out.println(
                    "<form action=\"clearcart\" method=\"get\">"
                            + "<input type=\"submit\" value=\"clear cart\" &rarr;\">"
                            + "</form>");
            out.println("<p></p>");
            
            out.println(
                    "<form action=\"favourites\" method=\"get\">"
                            + "<input type=\"submit\" value=\"go to favourites\" &rarr;\">"
                            + "</form>");
            out.println("<p></p>");
            
            if (!(rowCount == 1)) { //if rowcount is not equivalent to 1, then items do exist in shopping cart (rows were returned) and...
                out.println(
                        "<form action=\"placeorder\" method=\"get\">"
                                + "<input type=\"submit\" value=\"order books\" &rarr;\">"
                                + "</form>");
                out.println("<p></p>");
            }
        }
		
        if (!(validated)) { //if user not logged in... (then they can't place an order)
            out.println("<p></p>");
            out.println(
                    "<form action=\"clearcart\" method=\"get\">"
                            + "<input type=\"submit\" value=\"clear cart\" &rarr;\">"
                            + "</form>");
            out.println("<p></p>");     
            out.println(
                    "<form action=\"loginuser\" method=\"get\">"
                            + "<input type=\"submit\" value=\"log in\" &rarr;\">"
                            + "</form>");
            out.println("<p></p>");
        }
		
        lastBit(request, response); //html print, replica minimisation method located in 'common' 
    }
}	

