//this servlet gets the information from the shopping cart session array and user details and actually adds
// the selected books to the database

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;


public class placeorder extends common {

    int name = 1;
    int temp = 1;
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
	
        response.setContentType("text/html"); 
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
	
        ArrayList<String> shoppingcart;
	
        // checks the shopping cart array
        if (session.isNew()) {
            shoppingcart = new ArrayList<String>();
            session.setAttribute("shoppingCart", shoppingcart);
            response.sendRedirect("shoppingcart");
            return;
        } else {
            shoppingcart = (ArrayList<String>) session.getAttribute(
                    "shoppingCart");
        }
        
        connect(request, response); //connect to mysql method located in 'common'
        docTypeState(request, response); //html print, replica minimisation method located in 'common' 
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Order Placed</title>");
        firstBit(request, response); //html print, replica minimisation method located in 'common' 
        out.println(
                "<td height='60' colspan='4'><font size='5' face='Arial, Helvetica, sans-serif'>Order Placed</font></td>");
        navigation(request, response); //html print, replica minimisation method located in 'common' for naviation bar
        out.println(
                "<td width='29' height='153' align='center' valign='top'><p>&nbsp;</p></font></td>");
        out.println("<td width='716' align='left' valign='top'>");
        
        //variable declarations that will be changed
        boolean validated = false;
        String username = "";
        String userpassword = "";
        String nametopost = "";
        String address = "";
        String postcode = "";
		
		  //check to see if user logged in, and get their username and password
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
        
        if (validated) { //if logged in...
		
            out.println(
                    "<table align='centre' border=\"1\" cellspacing=\"2\" cellpadding=\"2\">"
                            + "<tr><th align='left'>BookID</th><th align='left'>Title</th><th align='left'>Author</th><th align='left'>Category</th><th align='left'>Price (GBP)</th></tr>");
				
				
            String selectSQL = "select * from books where "; //declare database search statement
				
				//get each book id from shopping cart array
            for (int i = 0; i < shoppingcart.size(); i++) {
                if (i != 0) {
                    selectSQL += " OR ";
                }
                selectSQL += "bookid = '" + shoppingcart.get(i) + "'";
            }
            try { //select details from database with bookid's got from shoppingcart array
	    
                System.err.println("DEBUG: Query: " + selectSQL);
                Statement stmt = conn.createStatement();
                ResultSet rs1 = stmt.executeQuery(selectSQL);
	    	
                double subtotal = 0.00; 
                DecimalFormat d = new DecimalFormat(); //declar a decimal format

					 //set the decimal format for subtotal
                d.setMinimumFractionDigits(2); 
                d.setMaximumFractionDigits(2);
                      
                while (rs1.next()) { //to view details of books that are being added to the order in database
                       	
                    out.print("<tr>");
                    int bookid = rs1.getInt("BookID");                                   

                    out.print("<td>" + rs1.getInt("BookID") + "</td>");
                    out.print("<td>" + rs1.getString("Title") + "</td>");
                    out.print("<td>" + rs1.getString("Author") + "</td>");
                    out.print("<td>" + rs1.getString("Category") + "</td>");
                    out.print("<td>" + rs1.getString("Price") + "</td>");
                    subtotal = rs1.getDouble("Price") + subtotal;
                       
                    //add the books to the database   
                    PreparedStatement pstmt;
                
                    pstmt = conn.prepareStatement(
                            "insert into orders(Username, BookID) values('"
                                    + username + "','" + bookid + "')");
	      
                    int insert;

                    insert = pstmt.executeUpdate();
                    pstmt.close(); 
                } 
                                                                           
                out.print(
                        "<tr align='center'><td colspan='5'><strong>Total: £"
                                + d.format(subtotal) + "</strong></td></tr>");
                            
            } catch (SQLException e) {
                System.err.println(e);
            }
        
            out.println("</table>");
            out.println("<font face='Arial, Helvetica, sans-serif'>");
            out.println(
                    "<p>Your books have now been ordered and will be with you soon</p>");
            out.println("</font>");
            out.println("<p></p>");
			 
            try { //select details of user to confirm order, name and address to user
                String selectUser = "select * from users where Username ='"
                        + username + "' && Password ='" + userpassword + "'";

                System.err.println("DEBUG: Query: " + selectUser);
		  
                Statement stmt2 = conn.createStatement();
                ResultSet rs2 = stmt2.executeQuery(selectUser);
	     
                ResultSetMetaData resultsMetaData = rs2.getMetaData();
                int columnCount = resultsMetaData.getColumnCount(); 
                
                while (rs2.next()) { //show results
                
                    nametopost = rs2.getString("Name");        
                    address = rs2.getString("Address");
                    postcode = rs2.getString("PostCode");
							   
                }
	          
                closeConnection(request, response); //close mysql connection method located in 'common'
	    
            } catch (SQLException se) {
                System.err.println(se);
            }   
            out.println("<br>");
			 
            out.println("<font face='Arial, Helvetica, sans-serif'>");
            out.println(
                    "<p><strong>The above books will be sent to:</strong></p>");
            out.println(
                    "<p><font face='Arial, Helvetica, sans-serif'>" + nametopost
                    + "</font></p>");
            out.println(
                    "<p><font face='Arial, Helvetica, sans-serif'>" + address
                    + "</font></p>");
            out.println(
                    "<p><font face='Arial, Helvetica, sans-serif'>" + postcode
                    + "</font></p>");
            out.println(
                    "<p><font face='Arial, Helvetica, sans-serif'><a href='clearorderredirect'>View previous orders</a></font></p>");
            out.println(
                    "<p><font face='Arial, Helvetica, sans-serif'><a href='clearcartredirect'>Go to welcome page</a></font></p>");
                     
        } else if (!(validated)) { //if not logged in...
            out.println("<p><font face='Arial, Helvetica, sans-serif'>");
            out.println("You are not signed in, please do so first</font></p>");     
            out.println(
                    "<form action=\"loginuser\" method=\"get\">"
                            + "<input type=\"submit\" value=\"log in\" &rarr;\">"
                            + "</form>");
            out.println("<p></p>");
        }
        
        lastBit(request, response); //set the decimal format
    }
}	

