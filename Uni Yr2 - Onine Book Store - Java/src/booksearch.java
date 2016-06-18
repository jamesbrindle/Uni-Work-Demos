//this servlet is to search for a book in the database of the details entered in 'booksearchmain'

import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class booksearch extends common {

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
        out.println("<title>Book Search</title>");
        firstBit(request, response); //html print, replica minimisation method located in 'common'
        out.println(
                "<td height='60' colspan='4'><font size='5' face='Arial, Helvetica, sans-serif'>Book Search</font></td>");
        navigation(request, response); //html print, replica minimisation method located in 'common' for navigation bar   
        out.println(
                "<td width='29' height='153' align='center' valign='top'><p>&nbsp;</p></font></td>");
        out.println("<td width='716' align='left' valign='top'>");    
		
		  //get book information from a form in 'booksearchmain'
        String searchtitle = request.getParameter("gettitle");
        String searchauthor = request.getParameter("getauthor");
        String searchcat = request.getParameter("getcat");
		
        int rowCount = 1; 		
 		
        try { //search for entered book information from database
            String selectSQL = "select * from books where author like'%"
                    + searchauthor + "%' && title like '%" + searchtitle
                    + "%' && category like '%" + searchcat + "%'";

            System.err.println("DEBUG: Query: " + selectSQL);
		  
            Statement stmt = conn.createStatement();
            ResultSet rs1 = stmt.executeQuery(selectSQL);
            // Retrieve the results
              
            ResultSetMetaData resultsMetaData = rs1.getMetaData();
            int columnCount = resultsMetaData.getColumnCount();
        
            out.println();
            out.println("<table width='100%'>");
        
            while (rs1.next()) { //what to do for each book returned...   
                rowCount++; //increment rowcount for each book return     	
                for (int i = 1; i < columnCount + 1; i++) {
                    out.println("<tr>");
                    out.println("</font>");
                    out.println(
                            "<td width='15%'><font size='3' face='Arial, Helvetica, sans-serif'><strong>"
                                    + resultsMetaData.getColumnName(i)
                                    + ":</strong></font></td>");
                    out.println(
                            "<td width='50%'><font size='3' face='Arial, Helvetica, sans-serif'>"
                                    + rs1.getString(i) + "</font></td>");
                    if (i == 1) {
                        out.println(
                                "<td rowspan='5' valign='middle'><form action=\"addtocart\" method=\"get\">"
                                        + "<input type=\"hidden\" name=\"bookid\" value=\""
                                        + rs1.getString("bookid") + "\">"
                                        + "<input type=\"submit\" value=\"add to cart\" &rarr;\">"
                                        + "</form>");
                        out.println(
                                "<form action=\"addtofavourites\" method=\"get\">"
                                        + "<input type=\"hidden\" name=\"bookid\" value=\""
                                        + rs1.getString("bookid") + "\">"
                                        + "<input type=\"submit\" value=\"add to favourites\" &rarr;\">"
                                        + "</form></td>");
                                        
                        out.println("</font>");
                        out.println("</tr>");
                    } else {
                        out.println("</tr>");
                    }
        			
                }
                out.println("<tr><td>&nbsp;</td></tr>");
                rowCount++;
            }
        
            out.println("</table>");
            if (rowCount == 1) { // if rowcount is equal to one (i.e. a book has not been found)...
                out.println("<font face='Arial, Helvetica, sans-serif'>"); 
                out.println(
                        "<br>your request had no matches, try using fewer conditions");
                out.println("</font>");
            } else { //if a book has been found...
                out.println("<font face='Arial, Helvetica, sans-serif'>");
                out.println(
                        "<br>please refine your search if your requested book does not appear");
            }
            out.println("<br><a href='booksearchmain'>back to search</a></font>");
                           		    
            closeConnection(request, response); //close mysql connection method located in 'common'
        } catch (SQLException se) {
            System.err.println(se);
        }   
        
        lastBit(request, response); //html print, replica minimisation method located in 'common'
	 
    }
}
