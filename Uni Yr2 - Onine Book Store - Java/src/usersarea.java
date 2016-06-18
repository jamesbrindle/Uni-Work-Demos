//this servlet shows options for the user

import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class usersarea extends common {

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
        docTypeState(request, response); //html print, replica minimisation method located in 'common' 
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Users Area</title>");
        firstBit(request, response); //html print, replica minimisation method located in 'common' 
        out.println(
                "<td height='60' colspan='4'><font size='5' face='Arial, Helvetica, sans-serif'>Users Area</font></td>");
        navigation(request, response); //html print, replica minimisation method located in 'common' for navigation bar
        out.println(
                "<td width='29' height='153' align='center' valign='top'><p>&nbsp;</p></font></td>");
        out.println("<td width='716' align='left' valign='top'>");
        
        //declare variables that will be changed
        String username = "";
        String userpassword = "";
        boolean validated = false;        
        
        //check if the user is logged in 
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
        
        if (validated == false) { //if user is not logged in...
        		//then get the username and password from the 'login' servlet if applicable
            username = request.getParameter("nameuser");
            userpassword = request.getParameter("passworduser");  
        
        }          
      		
        int rowCount = 1; 		

        try { //get users details from database
            String selectSQL = "select * from users where Username ='"
                    + username + "' && Password ='" + userpassword + "'";

            System.err.println("DEBUG: Query: " + selectSQL);
		  
            Statement stmt = conn.createStatement();
            ResultSet rs1 = stmt.executeQuery(selectSQL);
	     
            ResultSetMetaData resultsMetaData = rs1.getMetaData();
            
            while (rs1.next()) { //if user exists on database, set validation to true and set username and password cookie
                rowCount++;
                Cookie validationCookie = new Cookie("validationuser", "yes");

                validationCookie.setMaxAge(-1);
                response.addCookie(validationCookie);
                    
                Cookie detailsCookie1 = new Cookie("detailnameuser", username);

                detailsCookie1.setMaxAge(-1);
                response.addCookie(detailsCookie1);
	  		
                Cookie detailsCookie2 = new Cookie("detailpassworduser",
                        userpassword);

                detailsCookie2.setMaxAge(-1);
                response.addCookie(detailsCookie2);
                out.println("<p></p>");
                out.println("<p></p>");
                String greeting = " " + rs1.getString("Name") + "";

                out.println(
                        "<p><font face='Arial, Helvetica, sans-serif'><strong>Welcome"
                                + greeting + "</strong></font></p>");
	     
                rowCount++; //increment rowcount if rows returned (user exists on databsae)
                out.println("<p></p>");
                out.println("<font face='Arial, Helvetica, sans-serif'>");
                out.println(
                        "<p>You are logged in, please chose a task</font></p>");
                out.println("<p></p>");
                out.println(
                        "<form action=\"vieworders\" method=\"get\">"
                                + "<input type=\"submit\" value=\"view previous orders\" &rarr;\">"
                                + "</form>");
                out.println("<p></p>");    			
                out.println(
                        "<form action=\"changeuserdetails\" method=\"get\">"
                                + "<input type=\"submit\" value=\"change your details\" &rarr;\">"
                                + "</form>");
                out.println("<p></p>");    			
                out.println(
                        "<form action=\"shoppingcart\" method=\"get\">"
                                + "<input type=\"submit\" value=\"go to shopping cart\" &rarr;\">"
                                + "</form>");
                out.println(
                        "<form action=\"favourites\" method=\"get\">"
                                + "<input type=\"submit\" value=\"go to favourites\" &rarr;\">"
                                + "</form>");
            }
	          
            closeConnection(request, response); //close mysql connection method located in 'common'
	    
        } catch (SQLException se) {
            System.err.println(se);
        }   
	 
        if (rowCount == 1) { //if rowcount is equivalant to 1, then no rows returned (not users found in database) and...
            out.println("<font face='Arial, Helvetica, sans-serif'>");
            out.println(
                    "<p>Sorry, either the details you provided were invalid or your session expired</p>");
            out.println(
                    "<p><a href='loginuser'>Please click here to log in again</a></p>");
            out.println("</font>");
        } else {}
	    	
        lastBit(request, response); //html print, replica minimisation method located in 'common' 
  
    } 
}
