import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A servlet which acts as a dynamically created html web page for the quiz administration panel
 * and includes user authentication via created browser cookie values. It supposed both Get and Post
 * requests
 * 
 * @author Jamie Brindle (06352322) - Msc - Manchester Metropolitan University
 */
public class home extends common {

    private String inputString = "";
    private String inputArray[];
    private String firstName = "";
    private String secondName = "";

    private static final long serialVersionUID = 1L;

    protected DatabaseConnector connector;
    protected ManFunctions manFun;

    /**
     * Constructor which creates instances of other associated classes
     */
    public home() {
        connector = new DatabaseConnector();
        manFun = new ManFunctions();
    }

    /**
     * Method to execute upon a Get request/response, this simply calls the doPost method
     * This is common work-around no not have to uniquely specify with the request/request is
     * a Get or a Post
     * @param request
     * @param response
     * @throws IOException
     */
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * Method to execute upon a Post request/response
     * @param request
     * @param response
     * @throws IOException
     */
    public void doPost(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {

        response.setContentType("text/html");
        out = response.getWriter();

        boolean newbie = true;
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie c: cookies) {
                if ((c.getName().equals("repeatVisitor"))
                        && (c.getValue().equals("yes"))) {
                    newbie = false;
                    break;
                }   
            }        
        }

        String ifVisitor = "I'm Glad You Returned";

        if (newbie) { //if no validation cookies found
            Cookie returnVisitorCookie = new Cookie("repeatVisitor", "yes");

            returnVisitorCookie.setMaxAge(60 * 60 * 24 * 31); //one month
            response.addCookie(returnVisitorCookie);
            ifVisitor = "Hello Stranger";
			
        } else { 
            Random randgen = new Random();
            int randnum = randgen.nextInt(7);

            switch (randnum) { //For displaying different messages each time the page is displayed
            case 0:
                ifVisitor = "Welcome Back!";
                break; 

            case 1:
                ifVisitor = "Hello Again, Nice To See You Again";
                break;

            case 2:
                ifVisitor = "Back Again Already?";
                break;

            case 4:
                ifVisitor = "What Are You After This Time?";
                break;

            case 5:
                ifVisitor = "Greatings";
                break;

            case 6:
                ifVisitor = "Well Hello Again!";
                break;
            }
        }

        boolean validated = false;

        //validate the user by searching cookies
        if (request.getParameter("userid") != null) {
            clearCookies(request, response);
        } else {
            if (cookies != null) {		
                for (Cookie cookie: cookies) {
                    if ((cookie.getName().equals("validation")
                            && (cookie.getValue().equals("yes")))) {	  				
                        validated = true;
                    }
                    if ((cookie.getName().equals("firstname"))) {
                        firstName = cookie.getValue();
                    }
                    if ((cookie.getName().equals("secondname"))) {
                        secondName = cookie.getValue();
                    }
                }
            }
        }  		

        if (validated == false) { //get userID and password from login text fields
            String userID = request.getParameter("userid");
            String password = request.getParameter("password"); 

            inputString = connector.queryLogin(userID, password, true);

            if (!(inputString.contains("userdetails"))
                    || inputString.length() < 12) {
                loginFailureHTML(request, response); //redirect to login page
            } else {
                validated = true;
                inputArray = manFun.splitString(inputString);
                firstName = inputArray[1];
                secondName = inputArray[2];

                //create new validation cookies
                Cookie validationCookie = new Cookie("validation", "yes");

                validationCookie.setMaxAge(-1);
                response.addCookie(validationCookie);                
                Cookie detailsCookie1 = new Cookie("firstname", firstName);

                detailsCookie1.setMaxAge(-1);
                response.addCookie(detailsCookie1);  		
                Cookie detailsCookie2 = new Cookie("secondname", secondName);

                detailsCookie2.setMaxAge(-1);
                response.addCookie(detailsCookie2);
            }
        }
		
        //if user exists...
        if (inputString.contains("userdetails") || validated == true) {

            docTypeState(request, response);
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Home</title>");
			
            startHTMLCode(request, response);
			
            out.println(
                    "<td height='60' align='center' valign='middle' colspan='5'>"
                            + "<font size='5' face='Arial, Helvetica, sans-serif'>"
                            + ifVisitor + "</font></td>");
			
            navigationHTML(request, response);

            out.println(
                    "<td valign='top'><br><font size='3' face='Arial, Helvetica, sans-serif'>");
            out.println(
                    "Hello " + firstName + " " + secondName
                    + " and welcome to the online mobile quiz management site. "
                    + "<br><br>Please select a task from the menu to the left");
            out.println("</font></td>");

            endHTMLCode(request, response);
        }
    }
}
