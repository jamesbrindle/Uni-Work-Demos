//this servlet searches books under a particular course reading list in order to delete particular books from it

import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class editreadingresult extends common {

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
        out.println("<title>Edit Reading List</title>");
        firstBit(request, response); //html print, replica minimisation method located in 'common' 
        out.println(
                "<td height='60' colspan='4'><font size='5' face='Arial, Helvetica, sans-serif'>Edit Reading List</font></td>");
        navigation(request, response); //html print, replica minimisation method located in 'common' for navigation bar  
        out.println(
                "<td width='29' height='153' align='center' valign='top'><p>&nbsp;</p></font></td>");
        out.println("<td width='716' align='left' valign='top'>");  
        
        //check to see if teacher is logged in...
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
        
        		//get the coursename from the coursename selection in 'editreading'
            String selectcourse = request.getParameter("selectcourse");
            String courseid = "";
        
            out.println(
                    "<font face='Arial, Helvetica, sans-serif'><br>The <strong>"
                            + selectcourse
                            + "</strong> course reading list is as follows:<br><br></font>"); 
        		
            int rowCount = 1; 		
 		
            try { //check the detbase for all books under a particualar coursename selected in 'editreading'
                String selectSQL = "select * from reading_list, courses, books where reading_list.CourseID=courses.CourseID"
                        + "&& reading_list.bookID=books.bookid && courses.CourseName='"
                        + selectcourse + "'";
            
                System.err.println("DEBUG: Query: " + selectSQL);
		  
                Statement stmt = conn.createStatement();
                ResultSet rs1 = stmt.executeQuery(selectSQL);
                // Retrieve the results
              
                ResultSetMetaData resultsMetaData = rs1.getMetaData();
                int columnCount = resultsMetaData.getColumnCount();
        
                out.println();
                out.println("<table width='100%'>");
        
                out.println(
                        "<table align='centre' border=\"1\" cellspacing=\"1\" cellpadding=\"1\">"
                                + "<tr><th align='left'>BookID</th><th align='left'>Title</th><th align='left'>"
                                + "Author</th><th align='left'>Category</th><th align='left'>Price (GBP)</th><th align='left'>&nbsp</th></tr>");
            
                while (rs1.next()) {   //show results
                    rowCount++; //increment row count if a row has returned (i.e. books do exist under selected course)
                    out.println("<form action='removereading' method='get'>");    	
                    out.println("<tr>");
                    out.println("<td>" + rs1.getInt("BookID") + "</td>");
                    out.println("<td>" + rs1.getString("Title") + "</td>");
                    out.println("<td>" + rs1.getString("Author") + "</td>");
                    out.println("<td>" + rs1.getString("Category") + "</td>");
                    out.println(
                            "<td align='center'>" + rs1.getString("Price")
                            + "</td>");
                    courseid = rs1.getString("courseid");
                    out.println(
                            "<input type=\"hidden\" name=\"bookid\" value=\""
                                    + rs1.getString("bookid") + "\">");
                    out.println(
                            "<input type=\"hidden\" name=\"selectcourse\" value=\""
                                    + selectcourse + "\">");
                    out.println(
                            "<input type=\"hidden\" name=\"courseid\" value=\""
                                    + courseid + "\">");
                                        
                    out.println(
                            "<td align='center'><input type=\"submit\" value=\"Remove\" &rarr;\"></td>");
                    out.println("</form>");
                    rowCount++;
                    
                }
                
                closeConnection(request, response); //close mysql connection method located in 'common'
            } catch (SQLException se) {
                System.err.println(se);
            }   
        
            out.println("</table>");
            out.println("<font face='Arial, Helvetica, sans-serif'>");
            if (rowCount == 1) { //if no rows were returned (i.e. there were no books found)...
            
                out.println(
                        "<br>There are no books in the reading list for this course<br><br>");
            }
            
            out.println(
                    "<br><a href='editreading'>back to select course</a></font>");
            
        } else if (!(validated)) { //if teacher is not logged in...
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
