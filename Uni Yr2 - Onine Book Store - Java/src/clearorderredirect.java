//this servlet clears the shopping cart before 'viewing previous orders' after an order has been placed

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Locale;


public class clearorderredirect extends common {

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
        
        response.sendRedirect("vieworders"); //redirects to 'vieworders' servlet
       
    }
        
}
        
