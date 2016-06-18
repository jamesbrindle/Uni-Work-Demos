//this servlet asks user to select a course to view its reading list

import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class readinglist extends common {

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
        out.println("<title>Reading List</title>");
        firstBit(request, response); //html print, replica minimisation method located in 'common' 
        out.println(
                "<td height='60' colspan='4'><font size='5' face='Arial, Helvetica, sans-serif'>Reading List</font></td>");
        navigation(request, response); //html print, replica minimisation method located in 'common' for navigatio bar
        out.println(
                "<td width='29' height='153' align='center' valign='top'><p>&nbsp;</p></font></td>");
        out.println("<td width='716' align='left' valign='top'>");
        out.println(
                "<font face='Arial, Helvetica, sans-serif'><br>Please select a course to view it's book reading list<br><br></font>");
        out.println(
                "<font face='Arial, Helvetica, sans-serif'></font><font face='Arial, Helvetica, sans-serif'>Course:&nbsp</font>"
                        + "<form name='readinglistsearch' method='post' action='readinglistresult'>"
                        + "<select name='selectcourse'>");
  				  
        try { //gets list of courses for course drop down select menu
            String selectSQL = "select CourseName from courses order by coursename";

            System.err.println("DEBUG: Query: " + selectSQL);
		  
            Statement stmt = conn.createStatement();
            ResultSet rs1 = stmt.executeQuery(selectSQL);
            // Retrieve the results
              
            ResultSetMetaData resultsMetaData = rs1.getMetaData();
            
            while (rs1.next()) {	 //show results
            	   	  
                out.println(
                        "<option>" + rs1.getString("CourseName") + "</option>");
            }
    	  			 
            closeConnection(request, response); //close mysql connection method located in 'common'
    	  	 
        } catch (SQLException se) {
            System.err.println(se);
        }  
    	  			 
        out.println("</select>");
                
        out.println("&nbsp&nbsp<input type='submit'/>");
        out.println("</form>");
                    	
        lastBit(request, response); //html print, replica minimisation method located in 'common' 
    }
}
