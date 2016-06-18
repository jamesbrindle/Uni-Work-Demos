//this servlet completely clears all the information in the shoppingcart array, thus completely clears the shopping cart
//but instead of redirecting back to the shopping cart, redirects to the 'welcome page'

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Locale;


public class clearcartredirect extends common {

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
        
        response.sendRedirect("welcome"); //redirects to 'welcome' servlet
       
    }
}
        
