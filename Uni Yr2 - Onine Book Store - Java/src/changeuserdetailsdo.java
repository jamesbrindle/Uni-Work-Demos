//this servlet actually updates the users information in the database from the details entered in 'changeuserdetails'

import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class changeuserdetailsdo extends common {

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
        out.println("<title>Edit User Details</title>");
        firstBit(request, response); //html print, replica minimisation method located in 'common'
        out.println(
                "<td height='60' colspan='4'><font size='5' face='Arial, Helvetica, sans-serif'>Edit User Details</font></td>");
        navigation(request, response); //html print, replica minimisation method located in 'common' for navigation bar
        out.println(
                "<td width='29' height='153' align='center' valign='top'><p>&nbsp;</p></font></td>");
        out.println("<td width='716' align='left' valign='top'>");
		
		  //get the users details entered in a form from 'changeuserdetails'
        String name = request.getParameter("getname");
        String address = request.getParameter("getaddress");
        String postcode = request.getParameter("getpostcode");
        String userpasswordchange = request.getParameter("getpassword");
        
        //checks to see if the user is logged in and get their username          
        boolean validated = false;
        String username = "";
        String newpassword = "";
                      		
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
                
            }
        }
        if (validated) { //if user logged in...		
            try {
                PreparedStatement pstmt;
						
					 //update their details in database
                pstmt = conn.prepareStatement(
                        "update users SET name ='" + name + "', address='"
                        + address + "', postcode='" + postcode + "', password='"
                        + userpasswordchange + "' where username='" + username
                        + "'");
                int insert;

                insert = pstmt.executeUpdate();
                pstmt.close(); 
                
                //check to see if the users datails have been updated in the database successfully and confirms 	     
                String selectSQL = "select * from users where Username ='"
                        + username + "' && Password ='" + userpasswordchange
                        + "'";

                System.err.println("DEBUG: Query: " + selectSQL);
		  
                Statement stmt = conn.createStatement();
                ResultSet rs1 = stmt.executeQuery(selectSQL);
	     
                ResultSetMetaData resultsMetaData = rs1.getMetaData();
                                            
                while (rs1.next()) { //show results
                    out.println("<font face='Arial, Helvetica, sans-serif'>"); 
                    out.println(
                            "<p>Your details have now been changed and are as follows:</p>");
                    out.print("Name: " + rs1.getString("Name") + "<br>");
                    out.print("Address: " + rs1.getString("Address") + "<br>");
                    out.print("Post Code:" + rs1.getString("Postcode") + "<br>");
                    out.print("User Name: " + rs1.getString("Username") + "<br>");
                    out.print("Password: " + rs1.getString("Password") + "<br>");
                    newpassword = rs1.getString("password");
                    out.println("<p><a href=\"usersarea\">Ok</a></p>");
                    out.println("</font>");
	         
                }
             
                //resets the users password cookie to keep the user logged in
                Cookie detailsCookie2 = new Cookie("detailpassworduser",
                        newpassword);

                detailsCookie2.setMaxAge(-1);
                response.addCookie(detailsCookie2);
	          
                closeConnection(request, response); //close mysql method located in 'common'
            } catch (SQLException se) {
                System.err.println(se);
            } 
        
        } else if (!(validated)) { //if not validated...
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
