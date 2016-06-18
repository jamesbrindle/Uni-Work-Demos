//this servlet get the details of a teacher that is logged in and displays them in editable text fields in order
// for a teacher to change their details

import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class changeteacherdetails extends common {

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
        
        //variable declarations that will be changed
        boolean validated = false;
        String name = "";
        String teachername = "";
        String teacherpassword = "";
        String department = "";
        
        //get the teachername and password of the teachers details to be changed, and see if their validated (logged in)
               		
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {		
            for (Cookie cookie: cookies) {
                if ((cookie.getName().equals("validation")
                        && (cookie.getValue().equals("yes")))) {	  				
                    validated = true;
                }
                if ((cookie.getName().equals("detailname"))) {
                    teachername = cookie.getValue();
                }
                if ((cookie.getName().equals("detailpassword"))) {
                    teacherpassword = cookie.getValue();
	  				
                }
            }
        }
        
        try { //get details of teacher from their username and password to display in editable text fields
            String selectSQL = "select * from teachers where Username ='"
                    + teachername + "' && Password ='" + teacherpassword + "'";

            System.err.println("DEBUG: Query: " + selectSQL);
		  
            Statement stmt = conn.createStatement();
            ResultSet rs1 = stmt.executeQuery(selectSQL);
	     
            ResultSetMetaData resultsMetaData = rs1.getMetaData();
            
            while (rs1.next()) { //declar strings from information returned from database search
                name = rs1.getString("name");
                department = rs1.getString("department");
            }
            
            closeConnection(request, response); //close mysql connection method located in 'common'
	    
        } catch (SQLException se) {
            System.err.println(se);
        }        
               
        docTypeState(request, response); //html print, replica minimisation method located in 'common'         
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Edit Teacher Details</title>");
        firstBit(request, response); //html print, replica minimisation method located in 'common'
        out.println(
                "<td height='60' colspan='4'><font size='5' face='Arial, Helvetica, sans-serif'>Edit Teacher Details</font></td>");
        navigation(request, response); //html print, replica minimisation method located in 'common' for navigation bar
        out.println(
                "<td width='29' height='153' align='center' valign='top'><p>&nbsp;</p></font></td>");
        out.println("<td width='716' align='left' valign='top'>");
        
        if (validated) { //if teacher logged in...
		
            out.println("<form action='changeteacherdetailsdo' method='post'>");
            out.println(
                    "<p><font face='Arial, Helvetica, sans-serif'></font><font face='Arial, Helvetica, sans-serif'>Name:");
            out.println(
                    "<input name='getname' type='text' size='40' value='" + name
                    + "'>");
            out.println("</font></p>");
            out.println(
                    "<p><font face='Arial, Helvetica, sans-serif'></font><font face='Arial, Helvetica, sans-serif'>Department:");
            out.println(
                    "<input name='getdepartment' type='text' size='55' value='"
                            + department + "'>");
            out.println("</font></p>");
        
            out.println("<p><font face='Arial, Helvetica, sans-serif'>Password:"); 
            out.println(
                    "<input name='getpassword' type='password' size='20' value='"
                            + teacherpassword + "'>");
            out.println("</font>");
            out.println(
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp<input type='submit' value='save'>");
            out.println("</p>");
      
            out.println("</form>");
            out.println(
                    "<p><font face='Arial, Helvetica, sans-serif'>Edit the fields and click save</font></p>");
            out.println(
                    "<p><font face='Arial, Helvetica, sans-serif'><a href='teachersarea'>Back</a></font></p>");
                        
        } else if (!(validated)) { //if teacher not logged in...
            out.println("<font face='Arial, Helvetica, sans-serif'>");
            out.println(
                    "<p>Either your session expired or you are somewhere you shouldn't be</p>");
            out.println(
                    "<p><a href='loginteacher'>Please click here to log in as user</a></p>");
            out.println(
                    "<p><a href='welcome'>or cick here to go to welcome page</a></p>");
            out.println("</font>");
            
        }
	     
        lastBit(request, response); //html print, replica minimisation method located in 'common'
    }
}
