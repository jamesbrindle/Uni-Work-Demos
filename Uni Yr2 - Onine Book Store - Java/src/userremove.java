//this servlet deletes a user from the database with the details got from 'removeuser' servlet

import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class userremove extends common {

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
        docTypeState(request, response);
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Remove User</title>");
        firstBit(request, response); //html print, replica minimisation method located in 'common' 
        out.println(
                "<td height='60' colspan='4'><font size='5' face='Arial, Helvetica, sans-serif'>Remove User</font></td>");
        navigation(request, response); //html print, replica minimisation method located in 'common' for navigation bar
        out.println(
                "<td width='29' height='153' align='center' valign='top'><p>&nbsp;</p></font></td>");
        out.println("<td width='716' align='left' valign='top'>");
		
		  //get details from form in 'removeuser'
        String usernametoremove = request.getParameter("nameuser");
        String passwordtoremove = request.getParameter("passworduser");
 		
        int rowCount = 1;

        try { //get the details of the user from database
            String selectSQL = "select * from users where Username ='"
                    + usernametoremove + "' && Password ='" + passwordtoremove
                    + "'";

            System.err.println("DEBUG: Query: " + selectSQL);
		  
            Statement stmt = conn.createStatement();
            ResultSet rs1 = stmt.executeQuery(selectSQL);
	     
            ResultSetMetaData resultsMetaData = rs1.getMetaData();
	                  
            PreparedStatement pstmt;

				//prepare delition of user from database
            pstmt = conn.prepareStatement(
                    "delete from users where username ='" + usernametoremove
                    + "' && password = '" + passwordtoremove + "'"); 
            int delete;
	      	                                    
            while (rs1.next()) { //show users details for confirmation purposes
                rowCount++;
                out.println("<font face='Arial, Helvetica, sans-serif'>");
                out.println(
                        "<p>Your details have now been removed from our database:</p>");
                out.print("Name: " + rs1.getString("Name") + "<br>");
                out.print("Address: " + rs1.getString("Address") + "<br>");
                out.print("Post Code:" + rs1.getString("Postcode") + "<br>");
                out.print("User Name: " + rs1.getString("Username") + "<br>");
                out.print("Password: " + rs1.getString("Password") + "<br>");
                out.println("<br>");
                out.println("<p><p><a href=\"welcome\">Ok</a></p></p>");
                out.println("</font>");
                rowCount++; //increment rowcount if rows returned (i.e. user exists)
            }	     	          
            delete = pstmt.executeUpdate(); //execute user delition
            pstmt.close();             
            closeConnection(request, response); //close mysql connection method located in 'common'
        } catch (SQLException se) {
	     
            System.err.println(se);
        }   
	 
        if (rowCount == 1) { //if no rows returned (i.e no user found on database)...
            out.println("<font face='Arial, Helvetica, sans-serif'>");
            out.println("<p>Sorry, the details you provided were invalid</p>");
            out.println(
                    "<p><a href='loginuser'>Please click here to try again</a></p>");
            out.println("</font>");
        } else {}
	 		 	
        lastBit(request, response); //html print, replica minimisation method located in 'common' 
        ;
    }
}
