//this servlet removes course select in 'editreading' from the database

import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class removecourse extends common {

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
        docTypeState(request, response); //html print, replica minimisation method located in 'common' 
        
        //get the coursename selected in 'editreading'
        String coursename = request.getParameter("coursename");
        
        try { // delete the course from the database
            PreparedStatement pstmt;

            pstmt = conn.prepareStatement(
                    "delete from courses where coursename='" + coursename + "'");
	      
            int insert;

            insert = pstmt.executeUpdate();
            pstmt.close();  
                    
            closeConnection(request, response); //close connection method located in 'common'
        } catch (SQLException se) { 
            System.err.println(se);
          }
                    
        response.sendRedirect("editreading"); //go back to 'editreading' servlet
         
    }
}
