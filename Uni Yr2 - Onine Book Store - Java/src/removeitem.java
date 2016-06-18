//this servlet removes a particular selected item from the shopping cart array list

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.sql.*;


public class removeitem extends common {
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
	
        response.setContentType("text/html"); 
        PrintWriter out = response.getWriter();

		  //get the book id of the book to remove from the shopping cart array from 'shoppingcart'
        String bookid = request.getParameter("bookid");

        HttpSession session = request.getSession();
	
        ArrayList<String> shoppingcart;

		  //select from array
        if (session.isNew()) {
            
            shoppingcart = new ArrayList<String>(); 
            session.setAttribute("shoppingCart", shoppingcart); // add array to session 
        } else {
            shoppingcart = (ArrayList<String>) session.getAttribute(
                    "shoppingCart");
        }
	
		  //remove selected book from array
        shoppingcart.remove(bookid);
        response.sendRedirect("shoppingcart"); //redirect to 'shopping cart' servlet
		
    }
}	

