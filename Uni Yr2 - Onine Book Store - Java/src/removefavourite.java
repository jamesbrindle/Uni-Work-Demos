//this servlet removes a particular selected item from the favourites list

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;
import java.util.Locale;


public class removefavourite extends common {
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
	
        response.setContentType("text/html"); 
        PrintWriter out = response.getWriter();

		  //get the book id of the book to remove from the favourite database
        String bookid = request.getParameter("bookid");
        connect(request, response);

        boolean validated = false;
        String username = "";
        String userpassword = "";
    		
		  //check to see if a user if logged in and if so get their username and password
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {		
            for (Cookie cookie: cookies) {
                if ((cookie.getName().equals("validationuser")
                        && (cookie.getValue().equals("yes")))) {	  				
                    validated = true;
                }
                if ((cookie.getName().equals("detailnameuser"))) {
                    username = cookie.getValue();
                }
                if ((cookie.getName().equals("detailpassworduser"))) {
                    userpassword = cookie.getValue();
	  				
                }
            }
        }
    	  	    
        if(validated) { //if user is logged in...
        
        try { //remove favourite to database
        		PreparedStatement pstmt;

            pstmt = conn.prepareStatement(
                        "delete from favourites where bookid="+bookid+" && username='"+username+"'");
	      
            int insert;

            insert = pstmt.executeUpdate();
            pstmt.close();
                
            closeConnection(request, response); //close mysql connection method located in 'common'
                   
        } catch (SQLException e) {
            System.err.println(e);
        }
        
        response.sendRedirect("favourites"); //redirect to 'shopping cart' servlet
        
        } else if(!(validated)) {
        		response.sendRedirect("welcome"); //redirect to 'welcome page'
        	 }
		
    }
}	

