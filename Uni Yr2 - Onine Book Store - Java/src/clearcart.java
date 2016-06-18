//this servlet completely clears all the information in the shoppingcart array, thus completely clears the shopping cart

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Locale;


public class clearcart extends common {

    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
        doPost(request, response);	
    }      

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
      
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
      
        HttpSession session = request.getSession();
	
        ArrayList<String> shoppingcart; 
        
        //simply 're-assignes' a new shopping cart session array with nothing in it
        String contextPath = request.getContextPath();
        String encodedUrl = response.encodeURL(contextPath + "/shoppingcart");
    	  shoppingcart = new ArrayList<String>(); 
        session.setAttribute("shoppingCart", shoppingcart);
        
        response.sendRedirect("shoppingcart"); //redirects to 'shoppingcart' servlet
        
    }
}
        
