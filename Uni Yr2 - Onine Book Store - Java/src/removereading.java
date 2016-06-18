//this servel removes a particular book from the reading list

import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class removereading extends common {

    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
        doPost(request, response);
    }
  
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        
        //check if teacher is logged in
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
        
        connect(request, response); //connect to mysql method located in 'common'
        docTypeState(request, response); //html print, replica minimisation method located in 'common' 
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Remove Book From Reading List</title>");
        firstBit(request, response); //html print, replica minimisation method located in 'common' 
        out.println(
                "<td height='60' colspan='4'><font size='5' face='Arial, Helvetica, sans-serif'>Remove Book From Reading List</font></td>");
        navigation(request, response); //html print, replica minimisation method located in 'common' for navigation bar
        out.println(
                "<td width='29' height='153' align='center' valign='top'><p>&nbsp;</p></font></td>");
        out.println("<td width='716' align='left' valign='top'>");
			
        if (validated) { //if teacher logged in...		
		
				//get details needed to remove from readinglist database from 'teachersearch'
            String bookid = request.getParameter("bookid");
            String selectcourse = request.getParameter("selectcourse");
            String courseid = request.getParameter("courseid");	
         		
            int rowCount = 1;
			
            try { //select details of book with bookid for confirmation purposes
                String selectSQL = "select * from books where bookid =" + bookid
                        + "";

                System.err.println("DEBUG: Query: " + selectSQL);
		  
                Statement stmt = conn.createStatement();
                ResultSet rs1 = stmt.executeQuery(selectSQL);
	     
                ResultSetMetaData resultsMetaData = rs1.getMetaData();
	                  
	             //prepare deletion of book from reading list
                PreparedStatement pstmt;

                pstmt = conn.prepareStatement(
                        "delete from reading_list where bookid =" + bookid
                        + " && courseid=" + courseid + ""); 
                int delete;
	      	                                    
                while (rs1.next()) { //show book information for confirmation
                    out.println("<font face='Arial, Helvetica, sans-serif'>");
                    out.println("<p>The Following Book has been removed:</p>");
                    out.print("BookID: " + rs1.getString("bookid") + "<br>");
                    out.print("Title: " + rs1.getString("title") + "<br>");
                    out.print("Author:" + rs1.getString("author") + "<br>");
                    out.print("Category: " + rs1.getString("category") + "<br>");
                    out.print("price: " + rs1.getString("price") + "<br>");
                                     
                }	     	          
                delete = pstmt.executeUpdate(); //execute deletion
                pstmt.close();             
                closeConnection(request, response); //close mysql method located in 'common'
            } catch (SQLException se) {
	     
                System.err.println(se);
            }   
            
            out.println("<br>From the course: " + selectcourse + "");
            
            out.println("</font>");
            	
            out.println(
                    "<br><br><form action='editreadingresult' action='post'>");
            out.println(
                    "<input type=\"hidden\" name=\"selectcourse\" value=\""
                            + selectcourse + "\">"
                            + "<input type=\"submit\" value=\"back\" &rarr;\">"
                            + "</form>");
            
        } else if (!(validated)) { //if teacher not logged in...
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
