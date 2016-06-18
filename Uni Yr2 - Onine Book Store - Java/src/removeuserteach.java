//this servlet removes a selected user that has been found via 'userserach' from the database

import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class removeuserteach extends common {

    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
        doPost(request, response);
    }
  
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
                
        //checks if the teacher is logged in
        boolean validated = false;
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {		
            for (Cookie cookie: cookies) {
                if ((cookie.getName().equals("validation")
                        && (cookie.getValue().equals("yes")))) {
                    validated = true;
                    break;
                }
            }
        }
        
        connect(request, response); //connect to mysql method located in 'common'
        docTypeState(request, response);
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Remove User - Teachers Area</title>");
        firstBit(request, response); //html print, replica minimisation method located in 'common' 
        out.println(
                "<td height='60' colspan='4'><font size='5' face='Arial, Helvetica, sans-serif'>Remove User - Teachers Area</font></td>");
        navigation(request, response); //html print, replica minimisation method located in 'common' for navigation bar
        out.println(
                "<td width='29' height='153' align='center' valign='top'><p>&nbsp;</p></font></td>");
        out.println("<td width='716' align='left' valign='top'>");
			
        if (validated) { //if teacher logged in...	
		
				//get username from the search
            String username = request.getParameter("username");
         		
            int rowCount = 1;
			
            try { //get user details
                String selectSQL = "select * from users where username ='" + username
                        + "'";

                System.err.println("DEBUG: Query: " + selectSQL);
		  
                Statement stmt = conn.createStatement();
                ResultSet rs1 = stmt.executeQuery(selectSQL);
	     
                ResultSetMetaData resultsMetaData = rs1.getMetaData();
	                  
	             //prepare the deletion of the selected user from the datbase     
                PreparedStatement pstmt;

                pstmt = conn.prepareStatement(
                        "delete from users where username ='" + username + "'"); 
                int delete;
	      	                                    
                while (rs1.next()) { //confirm which user has been removed from the database
                    out.println("<font face='Arial, Helvetica, sans-serif'>");
                    out.println(
                            "<p>The Following user has been removed from the database:</p>");
                    out.print("Name: " + rs1.getString("name") + "<br>");
                    out.print("Address: " + rs1.getString("address") + "<br>");
                    out.print("Post Code:" + rs1.getString("postcode") + "<br>");
                    out.print("username: " + rs1.getString("username") + "<br>");
                    out.println("<br>");
                    out.println(
                            "<br><a href=\"usersearch\">Click here to search more users to remove</a><br>");
                    out.println(
                            "<br><a href=\"teachersarea\">Click here to go to teachers area</a>");
                    out.println("</font>");
                }	     	          
                delete = pstmt.executeUpdate(); // execute deletion
                pstmt.close();             
                closeConnection(request, response);
            } catch (SQLException se) {
	     
                System.err.println(se);
            }   
        } else if (!(validated)) { //if teacher not logged in...
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
