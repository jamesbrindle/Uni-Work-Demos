//this servlet searches the database for a specific users favourites

import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class favourites extends common {

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
        docTypeState(request, response); //html print, replica minimisation method located in 'common' 
	     
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Favourites</title>");
        firstBit(request, response); //html print, replica minimisation method located in 'common' 
        out.println(
                "<td height='60' colspan='4'><font size='5' face='Arial, Helvetica, sans-serif'>Favourites</font></td>");
        navigation(request, response);   //html print, replica minimisation method located in 'common' for navigation bar
        out.println( 
                "<td width='29' height='153' align='center' valign='top'><p>&nbsp;</p></font></td>");
        out.println("<td width='716' align='left' valign='top'>");
       
        //declare variables that will be changed
        boolean validated = false;
        String username = "";
        String userpassword = "";
        String greeting = "";
		
		  //check to see if a user is logged in and if so get their username and password
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
    	  	    
        if (validated) { //if user is logged in...
            int rowCount = 1; 		
 		 
            try { //get users details
                String selectUser = "select * from users where Username ='"
                        + username + "' && Password ='" + userpassword + "'";

                System.err.println("DEBUG: Query: " + selectUser);
		  
                Statement stmt2 = conn.createStatement();
                ResultSet rs2 = stmt2.executeQuery(selectUser);
	     
                ResultSetMetaData resultsMetaData = rs2.getMetaData();
                int columnCount = resultsMetaData.getColumnCount(); 
                
                while (rs2.next()) { //set greeting with user's name
        
                    greeting = " " + rs2.getString("Name") + "";
        
                }
	          
            } catch (SQLException se) {
                System.err.println(se);
            }   
 		  
            try { //select specific user's favourite books
                String selectSQL = "select * from books,favourites,users where favourites.username=users.username && favourites.bookid=books.bookid && favourites.username='"
                        + username + "'";
				
                System.err.println("DEBUG: Query: " + selectSQL);
		  
                Statement stmt = conn.createStatement();
                ResultSet rs1 = stmt.executeQuery(selectSQL);
                // Retrieve the results
              
                ResultSetMetaData resultsMetaData = rs1.getMetaData();
                  
                out.println();
            
                out.println(
                        "<table align='centre' border=\"1\" cellspacing=\"1\" cellpadding=\"1\">"
                                + "<tr><th align='left'>BookID</th><th align='left'>Title</th><th align='left'>Author</th><th align='left'>Category</th><th align='left'>Price (GBP)</th><th align='left'>&nbsp</th></tr>");
            
                while (rs1.next()) { //show results 
                    rowCount++;     	
                    out.println("<tr>");
                    out.println("<td>" + rs1.getInt("BookID") + "</td>");
                    out.println("<td>" + rs1.getString("Title") + "</td>");
                    out.println("<td>" + rs1.getString("Author") + "</td>");
                    out.println("<td>" + rs1.getString("Category") + "</td>");
                    out.println(
                            "<td align='center'>" + rs1.getString("Price")
                            + "</td>");
                    out.println(
                                "<td align='center' valign='middle'><form action=\"removefavourite\" method=\"get\">"
                                        + "<input type=\"hidden\" name=\"bookid\" value=\""
                                        + rs1.getString("bookid") + "\">"
                                        + "<input type=\"submit\" value=\"remove\" &rarr;\">"
                                        + "</form>");
                    out.println(
                                "<form action=\"addtocart\" method=\"get\">"
                                        + "<input type=\"hidden\" name=\"bookid\" value=\""
                                        + rs1.getString("bookid") + "\">"
                                        + "<input type=\"submit\" value=\"add to cart\" &rarr;\">"
                                        + "</form></td>");
                
                    rowCount++; //increment rowcount if rows returned (i.e. items exist)
                }
            
                out.println("</table>");
                            		    
                closeConnection(request, response); //close mysql connection located in 'common'
            
            } catch (SQLException se) {
                System.err.println(se);
            } 
        
            if (rowCount == 1) { //if rowcount is equivalent to 1, then no rows have returned (i.e. no items found)
                out.println("<font face='Arial, Helvetica, sans-serif'>"); 
                out.println("<p>You have no current favourites</p>");
                out.println("</font>");
            } else {}
            
            out.println("<font face='Arial, Helvetica, sans-serif'>");
            out.println("<p><a href='usersarea'>Go to user area</a></p>"); 
            out.println("<p><a href='welcome'>Go to welcome page</a></font></p>");
            
        } else if (!(validated)) { //if not logged in...
            out.println("<font face='Arial, Helvetica, sans-serif'>");
            out.println(
                    "<p>Either your session expired or you are somewhere you shouldn't be</p>");
            out.println(
                    "<p><a href='loginuser'>Please click here to log in as user</a></p>");
            out.println(
                    "<p><a href='welcome'>or cick here to go to welcome page</a></p>");
            out.println("</font>");
        }
        
        lastBit(request, response); //html print, replica minimisation method located in 'common' 
	 
    }
}

