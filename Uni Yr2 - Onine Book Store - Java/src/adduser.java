//this servlet is to actually add the information entered in 'registeruser' servlet to the actual database

import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class adduser extends common {

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
        out.println("<title>Register Details</title>");
        firstBit(request, response); //html print, replica minimisation method located in 'common'
        out.println(
                "<td height='60' colspan='4'><font size='5' face='Arial, Helvetica, sans-serif'>Register Details</font></td>");
        navigation(request, response); //html print, replica minimisation method located in 'common' for navigation bar
        out.println(
                "<td width='29' height='153' align='center' valign='top'><p>&nbsp;</p></font></td>");
        out.println("<td width='716' align='left' valign='top'>");
		
		  //get the information of a user to be added from a form in 'registeruser'
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String postcode = request.getParameter("postcode");
        String username = request.getParameter("username");
        String userpassword = request.getParameter("password");
        
        //declaration of a way in which to see if the entered username already exists on the database
        boolean Available = true;
        String notAvailable = "";
        int rowCount = 1;
        
        try { //search for user information on database to see if the username already exists on database
            String isAvailable = "select * from users where username ='"
                    + username + "'";

            System.err.println("DEBUG: Query: " + isAvailable);
		  
            Statement stmt2 = conn.createStatement();
            ResultSet rs2 = stmt2.executeQuery(isAvailable);
	     
            ResultSetMetaData resultsMetaData2 = rs2.getMetaData();
                                            
            while (rs2.next()) { //show results
                rowCount++;
                notAvailable = rs2.getString("username");
                rowCount++; //increment rowcount twice if result returns a row (i.e. username already exists)
            }
            if ((rowCount > 1)) { //if username exists (i.e. rowcount is more than one) then availability is false
                Available = false;
            }
 				
        } catch (SQLException se) {
            System.err.println(se);
        }
        
        if (Available) { //if entered username does not already exist on database, then add the entered user details to database
            try {
                PreparedStatement pstmt;

                pstmt = conn.prepareStatement(
                        "insert into users(Name, Address, Postcode, Username, Password) values('"
                                + name + "','" + address + "','" + postcode
                                + "','" + username + "','" + userpassword + "')");
	      
                int insert;

                insert = pstmt.executeUpdate();
                pstmt.close();
               
                //and then check to see if the user has been successfully entered to database and confirm  
	     
                String selectSQL = "select * from users where Username ='"
                        + username + "' && Password ='" + userpassword + "'";

                System.err.println("DEBUG: Query: " + selectSQL);
		  
                Statement stmt = conn.createStatement();
                ResultSet rs1 = stmt.executeQuery(selectSQL);
	     
                ResultSetMetaData resultsMetaData = rs1.getMetaData();
                                            
                while (rs1.next()) { //show results
            
                    out.println("<font face='Arial, Helvetica, sans-serif'>"); 
                    out.println(
                            "<p>Your details have now been added to our database:</p>");
                    out.print("Name: " + rs1.getString("Name") + "<br>");
                    out.print("Address: " + rs1.getString("Address") + "<br>");
                    out.print("Post Code:" + rs1.getString("Postcode") + "<br>");
                    out.print("User Name: " + rs1.getString("Username") + "<br>");
                    out.print("Password: " + rs1.getString("Password") + "<br>");
                    out.println(
                            "<p><a href=\"loginuser\">Click here to log in</a></p>");
                    out.println("</font>");
             
                }
	          
                closeConnection(request, response); //close mysql database method located in 'common'
            } catch (SQLException se) { //if problem exists while trying to add user (i.e. invalid value in fields) then...
                System.err.println(se);
            
                out.println(
                        "<p><font face='Arial, Helvetica, sans-serif'>There seemed to be a problem while adding your account, "
                                + "make sure all fields are filled in</font></p>");
                out.println(
                        "<p><font face='Arial, Helvetica, sans-serif'><a href=\"registeruser\">Click here to go back</a></font></p>");
            } 
        
        } else if (!(Available)) { //if username not available (i.e. already exists)...
            out.println(
                    "<p><font face='Arial, Helvetica, sans-serif'>Sorry, that username is already taken, please try again:</font></p>");
            out.println(
                    "<p><a href=\"registeruser\">Click here to go back</a></p>");
        }
        lastBit(request, response); //html print, replica minimisation method located in 'common'
    }
}
