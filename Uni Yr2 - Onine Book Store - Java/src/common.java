//this is not a servlet that gets viewed by the user, instead other servlets call opon this in order to reduce
// replication of text in each sevlet. For example the navigation bar is the exact same on every page, therefore
// instread of writing it for each page, you just write the method that calls the navigation bar

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;


public class common extends HttpServlet {
    Connection conn = null; // Create connection object
	
	 //connects to the mysql database and selects a database to use along with a username and password
    public void connect(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
	  	
        String database = "brindlej"; // Name of database
        String user = "brindlej"; //username for mysql connection
        String password = "grespter"; //password for mysql connection
        String url = "jdbc:mysql://mudfoot.doc.stu.mmu.ac.uk/" + database; //where to connect

        try { //where the mysql driver is found
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {
            System.err.println(e);
        }
	
        // connecting to database
        try {
            conn = DriverManager.getConnection(url, user, password);
		
        } catch (SQLException se) {
            System.err.println(se);
        }
    }
	
	 //closes connected to the mysql database
    public void closeConnection(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
        try {
            conn.close();
		
        } catch (SQLException se) {
            System.err.println(se);
        }
    }
   
    //typical html prints that appear in the start of most pages
    public void firstBit(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
   
        out.println(
                "<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<table width='100%' border='0'>");
        out.println("<tr>");
        out.println(
                "<td width='73%'><img src='pics/title.jpg' width='359' height='58'></td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("<table width='100%' height='281' border='0'>");
        out.println("<tr align='center'>");
    }
    
    //typical html prints that appear at the end of most pages
    public void lastBit(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
   
        out.println("</td>");	
        out.println(
                "<td width='155' align='center' valign='top'><p><font face='Arial, Helvetica, sans-serif'>new user?</font></p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'><a href='registeruser'>click here to register</a></font></p></td>");
        out.println("</tr>");
        out.println("</table>");		
        out.println("</body>");
        out.println("</html>");
    }
   
  	 //the document type declaration that appears in every page
    public void docTypeState(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String docType = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 "
                + "Transitional//EN\">\n";

        out.println(docType);
    }
	
	 //typical html prints that appear in some pages
    public void lastBitTwo(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println(
                "<td width='121' align='center' valign='top'><p><font face='Arial, Helvetica, sans-serif'>new user?</font></p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'><a href='registeruser'>click here to register</a></font></p></td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }
    
    //method to log out, or rather 'clears' all session cookies
    public void logOut(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
    
        String[] cookieList = { // array list of cookies to clear
            "validation", "validationuser", "detailnameuser",
            "detailpassworduser" + "detailname", "detailpassword"};
   	
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {		
            for (Cookie cookie: cookies) {
                for (int i = 0; i < cookieList.length; i++) { //selects each cookie in array and expires it
                    if ((cookie.getName().equals(cookieList[i]))) {	  				
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
	  			
                }
            }
        }
    }
    
    //html prints for the naviation bar that appears in all pages
    public void navigation(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("</tr>");
        out.println("<tr align='left'>");
        out.println("<td height='153' width='268' valign='top'><blockquote>");
        out.println(
                "<p><font size='4' face='Arial, Helvetica, sans-serif'><a href='welcome'>Welcome</a></p>"); 
        out.println(
                "<p><font size='4' face='Arial, Helvetica, sans-serif'><a href='login'>Log In</font></a></p>");
        out.println(
                "<p><font size='4' face='Arial, Helvetica, sans-serif'><a href='booksearchmain'>Search for Books</a></font></p>");
        out.println(
                "<p><font size='4' face='Arial, Helvetica, sans-serif'><a href='readinglist'>Reading List</a></font></p>");
        out.println(
                "<p><font size='4' face='Arial, Helvetica, sans-serif'><a href='shoppingcart'>Shopping Cart</a></font></p>");
        out.println(
                "<p><font size='4' face='Arial, Helvetica, sans-serif'><a href='onlinehelp'>Online Help</a></font></p>");
        out.println(
                "<p><font size='4' face='Arial, Helvetica, sans-serif'><a href='logout'>Log Out</a></font></p>");
        out.println("</blockquote></td>");
        
    }  
    
    //html prints for navigation bar with a slighty different size
    public void navigation2(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("</tr>");
        out.println("<tr align='left'>");
        out.println("<td height='153' width='190' valign='top'><blockquote>");
        out.println(
                "<p><font size='4' face='Arial, Helvetica, sans-serif'><a href='welcome'>Welcome</a></p>"); 
        out.println(
                "<p><font size='4' face='Arial, Helvetica, sans-serif'><a href='login'>Log In</font></a></p>");
        out.println(
                "<p><font size='4' face='Arial, Helvetica, sans-serif'><a href='booksearchmain'>Search for Books</a></font></p>");
        out.println(
                "<p><font size='4' face='Arial, Helvetica, sans-serif'><a href='readinglist'>Reading List</a></font></p>");
        out.println(
                "<p><font size='4' face='Arial, Helvetica, sans-serif'><a href='shoppingcart'>Shopping Cart</a></font></p>");
        out.println(
                "<p><font size='4' face='Arial, Helvetica, sans-serif'><a href='onlinehelp'>Online Help</a></font></p>");
        out.println(
                "<p><font size='4' face='Arial, Helvetica, sans-serif'><a href='logout'>Log Out</a></font></p>");
        out.println("</blockquote></td>");
        
    }  
    
}

