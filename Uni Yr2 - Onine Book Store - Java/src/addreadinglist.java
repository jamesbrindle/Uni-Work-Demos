//this servlet is to enter details of which course a selected book is to be added for the reading list

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;


public class addreadinglist extends common {
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
	
        response.setContentType("text/html"); 
        PrintWriter out = response.getWriter();

        //gets the book id from the teacher book search results
        String bookid = request.getParameter("bookid");
	
        docTypeState(request, response);        
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Add To Reading List</title>");
        firstBit(request, response); //html print, replica minimisation method located in 'common'
        out.println(
                "<td height='60' colspan='4'><font size='5' face='Arial, Helvetica, sans-serif'>Add To Reading List</font></td>");
        navigation(request, response); //html print, replica minimisation method located in 'common' for navigation bar
        out.println(
                "<td width='29' height='153' align='center' valign='top'><p>&nbsp;</p></font></td>");
        out.println("<td width='716' align='left' valign='top'>");
        
        //checks to see if a teacher is loggin in, in order to view this servlet
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
	  	
        if (validated) { // if teacher is logged in..
       
            connect(request, response); //connect to mysql method located in 'common'
          
            try { //get details of the book with a particular book id got from teachers search for confirmation purposes
                String selectSQL = "select * from books WHERE bookid ='"
                        + bookid + "'";

                System.err.println("DEBUG: Query: " + selectSQL);
                Statement stmt = conn.createStatement();
                ResultSet rs1 = stmt.executeQuery(selectSQL);

                rs1.next(); //show results
            
                out.println("<font face='Arial, Helvetica, sans-serif'>");    
                out.println(
                        "<p>You wish to add the following book to a course reading list:</p>");
                out.print("BookID: " + rs1.getInt("BookID") + "<br>");
                out.print("Title: " + rs1.getString("Title") + "<br>");
                out.print("Author: " + rs1.getString("Author") + "<br>");
                out.print("Category:" + rs1.getString("Category") + "<br>");
                out.print("Price: £" + rs1.getString("Price") + "<br>");
            
            } catch (SQLException e) {
                System.err.println(e);
            }
        
            out.println(
                    "<br><font face='Arial, Helvetica, sans-serif'><br>Please select a course reading list you wish to add the book to<br><br></font>");
            out.println(
                    "<font face='Arial, Helvetica, sans-serif'></font><font face='Arial, Helvetica, sans-serif'>Course:&nbsp</font>"
                            + "<form name='readinglistadd' method='get' action='addreadinglistdo'>"
                            + "<select name='selectcourse'>");
  				  
            try { //get a list of all the courses from the courses database to view as a drop down list
                String selectSQLcourses = "select CourseName from courses";

                System.err.println("DEBUG: Query: " + selectSQLcourses);
		  
                Statement stmt2 = conn.createStatement();
                ResultSet rs2 = stmt2.executeQuery(selectSQLcourses);
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
  				    
            out.println(
                    "<input type=\"hidden\" name=\"bookid\" value=\"" + bookid
                    + "\">");
                                       
            out.println("&nbsp&nbsp<input type='submit' value='add'><br><br>");
            out.println("</form>");
        
            out.println("<font face='Arial, Helvetica, sans-serif'>");
            out.println(
                    "<br><a href=\"teachsearch\">Click here to search more books</a><br>");
            out.println(
                    "<br><a href=\"teachersarea\">Click here to go to teachers area</a>");
            out.println("</font>");
                    
        } else if (!(validated)) { //if not logged in
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

