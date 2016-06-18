//this servlet shows options for the teacher

import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class teachersarea extends common {

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
        out.println("<title>Teachers Area</title>");
        firstBit(request, response); //html print, replica minimisation method located in 'common' 
        out.println(
                "<td height='60' colspan='4'><font size='5' face='Arial, Helvetica, sans-serif'>Teachers Area</font></td>");
        navigation(request, response); //html print, replica minimisation method located in 'common' for navigation bar
        out.println( 
                "<td width='29' height='153' align='center' valign='top'><p>&nbsp;</p></font></td>");
        out.println("<td width='716' align='left' valign='top'>");
		
		  //declare variables that will be changed
        String teachername = "";
        String teacherpassword = "";
        boolean validated = false;        
        
        //get cookies for validation purposes and get cookies for teacher username and password
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
        
        if (validated == false) { //if no teacher is not logged in...
        		//then get details from 'login' servlet if applicable
            teachername = request.getParameter("nameteacher");
            teacherpassword = request.getParameter("passwordteacher");  
        
        }
 		
        int rowCount = 1; 		

        try { //get teachers details from database with username and password
            String selectSQL = "select * from teachers where Username ='"
                    + teachername + "' && Password ='" + teacherpassword + "'";

            System.err.println("DEBUG: Query: " + selectSQL);
		  
            Statement stmt = conn.createStatement();
            ResultSet rs1 = stmt.executeQuery(selectSQL);
	     
            ResultSetMetaData resultsMetaData = rs1.getMetaData();
            int columnCount = resultsMetaData.getColumnCount(); 
        
            while (rs1.next()) { //get teachers name from database
                rowCount++;
                out.println("<p></p>");
                out.println("<p></p>");
                String greeting = " " + rs1.getString("Name") + "";
                
                //set cookies for username and password and set validation to true
                Cookie validationCookie = new Cookie("validation", "yes");

                validationCookie.setMaxAge(-1);
                response.addCookie(validationCookie);
	  		
                Cookie detailsCookie1 = new Cookie("detailname", teachername);

                detailsCookie1.setMaxAge(-1);
                response.addCookie(detailsCookie1);
	  		
                Cookie detailsCookie2 = new Cookie("detailpassword",
                        teacherpassword);

                detailsCookie2.setMaxAge(-1);
                response.addCookie(detailsCookie2);
	  	                                  
                out.println(
                        "<p><font face='Arial, Helvetica, sans-serif'><strong>Welcome"
                                + greeting + "</strong></font></p>");
	     
                rowCount++; //increment rowcount to see if a teacher exists on database;
                out.println("<p></p>");
                out.println("<font face='Arial, Helvetica, sans-serif'>");
                out.println("<p>Please pick a task</font></p>");
                out.println("<p></p>");
                out.println(
                        "<form action=\"addbook\" method=\"get\">"
                                + "<input type=\"submit\" value=\"add book\" &rarr;\">"
                                + "</form>");
                out.println("<p></p>");    			
                out.println(
                        "<form action=\"teachsearch\" method=\"get\">"
                                + "<input type=\"submit\" value=\"view, edit or remove books\" &rarr;\">"
                                + "</form>");
                out.println("<p></p>");
                out.println(
                        "<form action=\"editreading\" method=\"get\">"
                                + "<input type=\"submit\" value=\"view or edit reading list\" &rarr;\">"
                                + "</form>");
                out.println("<p></p>");
                out.println(
                        "<form action=\"changeteacherdetails\" method=\"get\">"
                                + "<input type=\"submit\" value=\"change your details\" &rarr;\">"
                                + "</form>");
                out.println("<p></p>");
                out.println(
                        "<form action=\"usersearch\" method=\"get\">"
                                + "<input type=\"submit\" value=\"Manage Users\" &rarr;\">"
                                + "</form>");
            }
	          
            closeConnection(request, response); //close mysql connection method located in 'common'
	    
        } catch (SQLException se) {
            System.err.println(se);
        }   
	 
        if (rowCount == 1) { //if rowcount is equivalent to 1, then no teacher with entered username exists and...
            out.println("<font face='Arial, Helvetica, sans-serif'>");
            out.println("<p>Sorry, the details you provided were invalid</p>");
            out.println(
                    "<p><a href='loginteacher'>Please click here to try again</a></p>");
                   
            out.println("</font>");
        } else {}
	    	
        lastBit(request, response); //html print, replica minimisation method located in 'common' 
  
    } 
}
