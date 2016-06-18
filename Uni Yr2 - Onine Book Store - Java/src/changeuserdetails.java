//this servlet get the use information from the database and display it in editable text fields in order for a user
// to update their details

import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class changeuserdetails extends common {

    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
        doPost(request, response);
    }
  
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        //variable declarations that will be changed
        connect(request, response);
        boolean validated = false;
        String name = "";
        String username = "";
        String userpassword = "";
        String address = "";
        String postcode = "";
        		
        // cookie to check a user is logged in and get their username and password		
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
        
        try { //get the users details in order to display them in editable text fields
            String selectSQL = "select * from users where Username ='"
                    + username + "' && Password ='" + userpassword + "'";

            System.err.println("DEBUG: Query: " + selectSQL);
		  
            Statement stmt = conn.createStatement();
            ResultSet rs1 = stmt.executeQuery(selectSQL);
	     
            ResultSetMetaData resultsMetaData = rs1.getMetaData();
            
            while (rs1.next()) {
                name = rs1.getString("name");
                address = rs1.getString("address");
                postcode = rs1.getString("postcode");
            }
            
            closeConnection(request, response); //close mysql method located in 'common'
	    
        } catch (SQLException se) {
            System.err.println(se);
        }        
               
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
        
        if (validated) { // if logged in..
		
            out.println("<form action='changeuserdetailsdo' method='post'>");
            out.println(
                    "<p><font face='Arial, Helvetica, sans-serif'></font><font face='Arial, Helvetica, sans-serif'>Name:");
            out.println(
                    "<input name='getname' type='text' size='40' value='" + name
                    + "'>");
            out.println("</font></p>");
            out.println(
                    "<p><font face='Arial, Helvetica, sans-serif'></font><font face='Arial, Helvetica, sans-serif'>Address:");
            out.println(
                    "<input name='getaddress' type='text' size='55' value='"
                            + address + "'>");
            out.println("</font></p>");
        
            out.println(
                    "<p><font face='Arial, Helvetica, sans-serif'></font><font face='Arial, Helvetica, sans-serif'>Post Code:");
            out.println(
                    "<input name='getpostcode' type='text' size='15' value='"
                            + postcode
                            + "'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
            out.println("</font></p>");
		
            out.println("<p><font face='Arial, Helvetica, sans-serif'>Password:"); 
            out.println(
                    "<input name='getpassword' type='password' size='20' value='"
                            + userpassword + "'>");
            out.println("</font>");
            out.println(
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp<input type='submit' value='save'>");
            out.println("</p>");
      
            out.println("</form>");
            out.println(
                    "<p><font face='Arial, Helvetica, sans-serif'>Edit the fields and click save</font></p>");
            out.println(
                    "<p><font face='Arial, Helvetica, sans-serif'><a href='usersarea'>Back</a></font></p>");
                        
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
