//this servlet actually 'updates' the teachers details in the database changed in 'changeteacherdetails' servlet

import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class changeteacherdetailsdo extends common {

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
        out.println("<title>Edit Teacher Details</title>");
        firstBit(request, response); //html print, replica minimisation method located in 'common'
        out.println(
                "<td height='60' colspan='4'><font size='5' face='Arial, Helvetica, sans-serif'>Edit Teacher Details</font></td>");
        navigation(request, response); //html print, replica minimisation method located in 'common' for naviation bar
        out.println(
                "<td width='29' height='153' align='center' valign='top'><p>&nbsp;</p></font></td>");
        out.println("<td width='716' align='left' valign='top'>");
		
		  //get details from a form that has been changed in 'changeteacherdetails'
        String name = request.getParameter("getname");
        String department = request.getParameter("getdepartment");
        String teacherpasswordchange = request.getParameter("getpassword");
        
        //check to see if a teacher is logged in and get the teachername from cookie
        boolean validated = false;
        String teachername = "";
        String newpassword = "";
                      		
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
                
            }
        }
        if (validated) { // if teacher logged in, then updata their details			
            try {
                PreparedStatement pstmt;

                pstmt = conn.prepareStatement(
                        "update teachers SET name ='" + name + "', department='"
                        + department + "', password='" + teacherpasswordchange
                        + "' where username='" + teachername + "'");
                int insert;

                insert = pstmt.executeUpdate();
                pstmt.close();  
                
                //check if teacher details have been updated successfully and confirm	     
                String selectSQL = "select * from teachers where Username ='"
                        + teachername + "' && Password ='"
                        + teacherpasswordchange + "'";

                System.err.println("DEBUG: Query: " + selectSQL);
		  
                Statement stmt = conn.createStatement();
                ResultSet rs1 = stmt.executeQuery(selectSQL);
	     
                ResultSetMetaData resultsMetaData = rs1.getMetaData();
                                            
                while (rs1.next()) { //show results
                    out.println("<font face='Arial, Helvetica, sans-serif'>"); 
                    out.println(
                            "<p>Your details have now been changed and are as follows:</p>");
                    out.print("Name: " + rs1.getString("Name") + "<br>");
                    out.print(
                            "Department: " + rs1.getString("department")
                            + "<br>");
                    out.print("User Name: " + rs1.getString("Username") + "<br>");
                    out.print("Password: " + rs1.getString("Password") + "<br>");
                    newpassword = rs1.getString("password");
                    out.println("<p><a href=\"teachersarea\">Ok</a></p>");
                    out.println("</font>");
	         
                }
                
                //update teachers password cookie to keep teacher logged in            
                Cookie detailsCookie2 = new Cookie("detailpassword", newpassword); 

                detailsCookie2.setMaxAge(-1);
                response.addCookie(detailsCookie2);
	          
                closeConnection(request, response);
            } catch (SQLException se) {
                System.err.println(se);
            } 
        
        } else if (!(validated)) { //if not logged in...
            out.println("<font face='Arial, Helvetica, sans-serif'>");
            out.println(
                    "<p>Either your session expired or you are somewhere you shouldn't be</p>");
            out.println(
                    "<p><a href='loginteacher'>Please click here to log in as user</a></p>");
            out.println(
                    "<p><a href='welcome'>or cick here to go to welcome page</a></p>");
            out.println("</font>");
            
        }
                
        lastBit(request, response); //update cookie to keep teacher logged in
    }
}
