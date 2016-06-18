//this servlet adds the course entered in 'editreading' serlvet to mysql database

import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class addcourse extends common {

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
        docTypeState(request, response);
        
        String coursename = request.getParameter("coursename"); //get coursename from 'editreading' form
        
        try { // add course to database
            PreparedStatement pstmt;

            pstmt = conn.prepareStatement(
                    "insert into courses(CourseName) values('" + coursename
                    + "')");
	      
            int insert;

            insert = pstmt.executeUpdate();
            pstmt.close();  
                    
            closeConnection(request, response); //close mysql connection method located in 'common'
        } catch (SQLException se) {
            System.err.println(se);
            response.sendRedirect("editreading");
        }
                    
        response.sendRedirect("editreading"); //redirect to 'editreading' servlet
         
    }
}
