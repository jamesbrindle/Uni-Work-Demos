//this servlet seaches for users with details put into a form in 'usersearch' and diplays the results

import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class usersearchresult extends common {

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
        
        out.println("<html>");
        out.println("<head>");
        out.println("<title>User Search - Teachers Area</title>");
        firstBit(request, response); //html print, replica minimisation method located in 'common' 
        out.println(
                "<td height='60' colspan='4'><font size='5' face='Arial, Helvetica, sans-serif'>User Search - Teachers Area</font></td>");
        navigation(request, response); //html print, replica minimisation method located in 'common'  for navigation bar
        out.println(
                "<td width='29' height='153' align='center' valign='top'><p>&nbsp;</p></td>");
        out.println("<td width='716' align='left' valign='top'>");
		
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
	  	
        if (validated) { //if teacher is logged in...
		
            int rowCount = 1;
            //get details entered into a form in 'usersearch'
            String searchname = request.getParameter("getname");
            String searchaddress = request.getParameter("getaddress");
            String searchpostcode = request.getParameter("getpostcode");

            try { //get user details from database
                String selectSQL = "select * from users where name like'%"
                        + searchname + "%' && address like '%" + searchaddress
                        + "%' && postcode like '%" + searchpostcode + "%'";

                System.err.println("DEBUG: Query: " + selectSQL);
		  
                Statement stmt = conn.createStatement();
                ResultSet rs1 = stmt.executeQuery(selectSQL);
                // Retrieve the results
              
                ResultSetMetaData resultsMetaData = rs1.getMetaData();
                int columnCount = resultsMetaData.getColumnCount();
        
                out.println();
                out.println("<table width='100%'>");
        
                while (rs1.next()) { //show results...
                    rowCount++;        	
                    for (int i = 1; i < columnCount + 1; i++) {
                        out.println("<tr>");
                        out.println(
                                "<td width='20%'><font size='3' face='Arial, Helvetica, sans-serif'><strong>"
                                        + resultsMetaData.getColumnName(i)
                                        + ":</strong></font></td>");
                        out.println(
                                "<td width='50%'><font size='3' face='Arial, Helvetica, sans-serif'>"
                                        + rs1.getString(i) + "</font></td>");
                        if (i == 1) {
                            out.println(
                                    "<td rowspan='5' valign='middle'><form action=\"removeuserteach\" method=\"get\">"
                                            + "<input type=\"hidden\" name=\"username\" value=\""
                                            + rs1.getString("username") + "\">"
                                            + "<input type=\"submit\" value=\"remove\" &rarr;\">"
                                            + "</form>" + "<br>"
                                            + "<form action=\"viewuserhistory\" method=\"get\">"
                                            + "<input type=\"hidden\" name=\"username\" value=\""
                                            + rs1.getString("username") + "\">"
                                            + "<input type=\"submit\" value=\"view history\" &rarr;\">"
                                            + "</form></t>");
                                           
                            out.println("</tr>");
                        } else {
                            out.println("</tr>");
                        }
                        rowCount++; //increment rowcount if rows are returned (if search found users)
        			
                    }
                    out.println("<tr><td>&nbsp;</td></tr>");
                }
        
                out.println("</table>");
        
                if (rowCount == 1) { //if rows returned (users found) ...
                    out.println("<font face='Arial, Helvetica, sans-serif'>");
                    out.println(
                            "<br>your request had no matches, try using fewer conditions</font>");
                } else { //if no users found...
                    out.println(
                            "<br><font face='Arial, Helvetica, sans-serif'>please refine your search if your requested user does not appear");
                }
                out.println("<br><a href='usersearch'>back to search</a>");
                out.println("</font>");
        
                closeConnection(request, response); //close mysql method located in 'common'
            } catch (SQLException se) {
                System.err.println(se);
            }   
        } else if (!(validated)) { //if teacher not logged in...
            out.println(
                    "<p><font face='Arial, Helvetica, sans-serif'>Either your session expired or you are somewhere you shouldn't be</font></p>");
            out.println(
                    "<p><font face='Arial, Helvetica, sans-serif'><a href='loginteacher'>Please click here to log in as teacher</a></font></p>");
            out.println(
                    "<p><font face='Arial, Helvetica, sans-serif'><a href='welcome'>or cick here to go to welcome page</a></font></p>");
        }
			
        out.println("</td>");	
        out.println("<td width='155' align='center>&nbsp;</td>");
        out.println("</tr>");
        out.println("</table>");		
        out.println("</body>");
        out.println("</html>");
    }
}
