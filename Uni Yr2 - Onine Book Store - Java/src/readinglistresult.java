//this servlet shows the results of the reading list selection in 'reading list' and gives the option to add to cart

import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class readinglistresult extends common {

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
        docTypeState(request, response);
	     
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Reading List</title>");
        firstBit(request, response); //html print, replica minimisation method located in 'common' 
        out.println(
                "<td height='60' colspan='4'><font size='5' face='Arial, Helvetica, sans-serif'>Reading List</font></td>");
        navigation(request, response); //html print, replica minimisation method located in 'common' for navigation bar
        out.println(
                "<td width='29' height='153' align='center' valign='top'><p>&nbsp;</p></font></td>");
        out.println("<td width='716' align='left' valign='top'>");  
        
        //gets the coursename to search for from a form 'readinglist'
        String selectcourse = request.getParameter("selectcourse");
        
        out.println(
                "<font face='Arial, Helvetica, sans-serif'><br>The <strong>"
                        + selectcourse
                        + "</strong> course reading list is as follows:<br><br></font>"); 
        		
        int rowCount = 1; 		
 		
        try { //searches all books on selected courses from databse
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
                rowCount++;
                out.println("<form action='addtocart2' method='get'>");    	
                out.println("<tr>");
                out.println("<td>" + rs1.getInt("BookID") + "</td>");
                out.println("<td>" + rs1.getString("Title") + "</td>");
                out.println("<td>" + rs1.getString("Author") + "</td>");
                out.println("<td>" + rs1.getString("Category") + "</td>");
                out.println(
                        "<td align='center'>" + rs1.getString("Price") + "</td>");
                out.println(
                        "<input type=\"hidden\" name=\"bookid\" value=\""
                                + rs1.getString("bookid") + "\">");
                out.println(
                        "<input type=\"hidden\" name=\"selectcourse\" value=\""
                                + selectcourse + "\">");
                                        
                out.println(
                        "<td align='center'><input type=\"submit\" value=\"add to cart\" &rarr;\"></td>");
                out.println("</form>");
                rowCount++; //increments the rowcount for everyrow returned (i.e. if books exist)
                    
            }
                
            closeConnection(request, response); //close mysql connection method located in 'common'
        } catch (SQLException se) {
            System.err.println(se);
        }  
            
        out.println("</table>");
        out.println("<font face='Arial, Helvetica, sans-serif'>");
        if (rowCount == 1) { //if rowcount is equivalent to 1 (i.e. no rows returned) then no books have been found and...
            
            out.println(
                    "<br>There are no books in the reading list for this course<br><br>");
        }
             
        out.println("<br><a href='readinglist'>back to select course</a></font>");
                           		    
        lastBit(request, response); //html print, replica minimisation method located in 'common' 
	 
    }
}
