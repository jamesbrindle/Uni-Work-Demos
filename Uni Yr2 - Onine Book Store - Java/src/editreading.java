//this servlet is to select information in order to edit the reading list

import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class editreading extends common {

    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
        doPost(request, response);
    }
  
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        docTypeState(request, response); //html print, replica minimisation method located in 'common' 
        connect(request, response); //connect to mysql method located in 'common'
                
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Edit Reading List</title>");
        firstBit(request, response); //html print, replica minimisation method located in 'common' 
        out.println(
                "<td height='60' colspan='4'><font size='5' face='Arial, Helvetica, sans-serif'>Edit Reading List</font></td>");
        navigation(request, response); //html print, replica minimisation method located in 'common' for navigation bar
        out.println(
                "<td width='29' height='153' align='center' valign='top'><p>&nbsp;</p></font></td>");
        out.println("<td width='716' align='left' valign='top'>");
        
        //check to see if the teacher is logged in
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
	  	
        if (validated) { //if teacher logged in...
        
            out.println("<font face='Arial, Helvetica, sans-serif'>");
            out.println(
                    "<br><a href='teachsearch'> Click here to add books to a reading list</a>");
		  
            out.println(
                    "<br><br>Please select a course to view it's book reading list<br>");
            out.println(
                    "You can also remove books from a reading list from this option<br><br></font>");
            out.println(
                    "<font face='Arial, Helvetica, sans-serif'></font><font face='Arial, Helvetica, sans-serif'>Course:&nbsp</font>"
                            + "<form name='readinglistsearch' method='post' action='editreadingresult'>"
                            + "<select name='selectcourse'>");
  				  
            try { //get list of courses for the courses dropdown selection menu
                String selectSQL = "select CourseName from courses order by coursename";

                System.err.println("DEBUG: Query: " + selectSQL);
		  
                Statement stmt = conn.createStatement();
                ResultSet rs1 = stmt.executeQuery(selectSQL);
                // Retrieve the results
              
                ResultSetMetaData resultsMetaData = rs1.getMetaData();
            
                while (rs1.next()) {	 //show results
            	   	  
                    out.println(
                            "<option>" + rs1.getString("CourseName")
                            + "</option>");
                }
    	  			 
            } catch (SQLException se) {
                System.err.println(se);
            }  
    	  			 
            out.println("</select>");
                
            out.println("&nbsp&nbsp<input type='submit'/>");
            out.println("</form>");
        
            out.println("<font face='Arial, Helvetica, sans-serif'>");
        
            out.println(
                    "<br><br>If you wish to add another course, please fill in the details and click add<br><br>");
            out.println("<form action='addcourse' method='post'>");
            out.println("Course Name: ");
            out.println(
                    "<input type='text' name='coursename' size='35'>&nbsp&nbsp");
            out.println(
                    "<input type='submit' name='add' value='add'><br></form><br><br>");
            out.println(
                    "To remove a course, select a course and click remove<br><br>");
            out.println("Course:<br>");
				  
            out.println("<form method='post' action='removecourse'>");
            out.println("<select name='coursename'>");
				  
            try { //get list of courses for the courses drop down select menu
                String selectSQLremove = "select CourseName from courses order by coursename";

                System.err.println("DEBUG: Query: " + selectSQLremove);
		  
                Statement stmt2 = conn.createStatement();
                ResultSet rs2 = stmt2.executeQuery(selectSQLremove);
                // Retrieve the results
              
                ResultSetMetaData resultsMetaData2 = rs2.getMetaData();
            
                while (rs2.next()) {	//show results
            	   	  
                    out.println(
                            "<option>" + rs2.getString("CourseName")
                            + "</option>");
                }
    	  			 
                closeConnection(request, response); //close mysql connection method located in 'common'
    	  	 
            } catch (SQLException se) {
                System.err.println(se);
            }  
        
            out.println("</select>");
                
            out.println("&nbsp&nbsp<input type='submit' value='remove'>");
            out.println("</form>");
        
        } else if (!(validated)) { //if teacher not logged in...
            out.println("<font face='Arial, Helvetica, sans-serif'>");
            out.println(
                    "<p>Either your session expired or you are somewhere you shouldn't be</p>");
            out.println(
                    "<p><a href='loginteacher'>Please click here to log in as teacher</a></p>");
            out.println(
                    "<p><a href='welcome'>or cick here to go to welcome page</a></p>");
            out.println("</font>");
    
        }
            
        lastBit(request, response); //html print, replica minimisation method located in 'common' 
    }
}
