//this servlet actually adds the details entered in 'addreadinglist' to the actual database

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;


public class addreadinglistdo extends common {
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
	
        response.setContentType("text/html"); 
        PrintWriter out = response.getWriter();

        // get details of which book and course to add to reading list datbase from 'addreadinglist' form
        String bookid = request.getParameter("bookid");
        String selectcourse = request.getParameter("selectcourse");
        String courseid = "";	
        
        docTypeState(request, response); //html print, replica minimisation method located in 'common'        
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
        
        
        //check to see if a teacher is logged in, in order to view this servlet
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
	  	
        if (validated) { //if teacher is logged in...
       
            connect(request, response); //connect to mysql method located in 'common'
          
            try { //get book information from database for confirmation purposes
                String selectSQL = "select * from books WHERE bookid ='"
                        + bookid + "'";

                System.err.println("DEBUG: Query: " + selectSQL);
                Statement stmt = conn.createStatement();
                ResultSet rs1 = stmt.executeQuery(selectSQL);
            
                ResultSetMetaData resultsMetaData = rs1.getMetaData();

                rs1.next(); //show results
            
                out.println("<font face='Arial, Helvetica, sans-serif'>");    
                out.println("<p>The following book has been added:</p>");
                out.print("BookID: " + rs1.getInt("BookID") + "<br>");
                out.print("Title: " + rs1.getString("Title") + "<br>");
                out.print("Author: " + rs1.getString("Author") + "<br>");
                out.print("Category:" + rs1.getString("Category") + "<br>");
                out.print("Price: £" + rs1.getString("Price") + "<br>");
            
            } catch (SQLException e) {
                System.err.println(e);
            }
        
            try { //get course information from database for confirmation purposes
                String selectSQLcourseID = "select CourseID from courses WHERE CourseName ='"
                        + selectcourse + "'";

                System.err.println("DEBUG: Query: " + selectSQLcourseID);
                Statement stmt2 = conn.createStatement();
                ResultSet rs2 = stmt2.executeQuery(selectSQLcourseID);
                ResultSetMetaData resultsMetaData2 = rs2.getMetaData();

                rs2.next();
            
                courseid = rs2.getString("CourseID");
                        
                PreparedStatement pstmt;
                
                //actually entere the book and courses to the readinglist databse
                pstmt = conn.prepareStatement(
                        "insert into reading_list(bookid, courseid) values('"
                                + bookid + "','" + courseid + "')");
	      
                int insert;

                insert = pstmt.executeUpdate();
                pstmt.close(); 
                    
                closeConnection(request, response); //close connection to mysql method located in 'common'
	     	    
            } catch (SQLException e) {
                System.err.println(e);
            }
        
            out.println(
                    "<br><font face='Arial, Helvetica, sans-serif'><br>To the reading list of the course:<br></font>");
            out.println("<br>" + selectcourse + "<br><br>");
        
            out.println("<font face='Arial, Helvetica, sans-serif'>");
            out.println(
                    "<br><a href=\"teachsearch\">Click here to search more books</a><br>");
            out.println(
                    "<br><a href=\"teachersarea\">Click here to go to teachers area</a>");
            out.println("</font>");
                    
        } else if (!(validated)) { //if teacher is not logged in...
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

