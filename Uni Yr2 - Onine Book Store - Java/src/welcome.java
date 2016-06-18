//this servlet is the welcome page (or home/greetings page), the first page that will be viewed after index.html

import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Random;

// imports for date and time
import java.util.Calendar;
import java.text.SimpleDateFormat;


public class welcome extends common {
    public static final String DATE_FORMAT_NOW = "HH:mm";
 
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

		  //simple 'time' function to set the current time
        String  timeString = new String();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);

        timeString = sdf.format(cal.getTime());
     
     	  //check to see if user has visited this page before (i.e. a long term cookie exists)
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

        if (newbie) { //if user is new to the page then set a long term cookie and print a greeting
            Cookie returnVisitorCookie = new Cookie("repeatVisitor", "yes");

            returnVisitorCookie.setMaxAge(60 * 60 * 24 * 31);
            response.addCookie(returnVisitorCookie);
            ifVisitor = "Welcome Aboard New User";
        } else { //if cookie has returned to the page is not new, then display other random greeting
            Random randgen = new Random();
            int randnum = randgen.nextInt(7);

            switch (randnum) { //random greetinds
            case 0:
                ifVisitor = "Welcome Back to Online Books!";
                break; 

            case 1:
                ifVisitor = "Hello Again, Nice To See You Again";
                break;

            case 2:
                ifVisitor = "Back Again Already?";

            case 4:
                ifVisitor = "What Are You After This Time?";
                break;

            case 5:
                ifVisitor = "Greatings Existing User";
                break;

            case 6:
                ifVisitor = "Well Hello Again!";
            }
        }

        docTypeState(request, response); //html print, replica minimisation method located in 'common' 
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Welcome Page</title>");
        out.println(
                "<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<table width='100%' border='0'>");
        out.println("<tr>");
        out.println(
                "<td width='73%'><img src='pics/title.jpg' width='359' height='58'></td>");
        out.println(
                "<td width='27%' align='right' valign='middle'><font face='Arial, Helvetica, sans-serif'>current time: "
                        + timeString + "</font></td>");
        out.println("</tr>");
        out.println("</table>");
        out.println("<table width='100%' height='281' border='0'>");
        out.println("<tr align='center'>");
        out.println(
                "<td height='60' align='center' valign='middle' colspan='5'><font size='5' face='Arial, Helvetica, sans-serif'>"
                        + ifVisitor + "</font></td>");
        navigation(request, response);
        out.println(
                "<td width='310' align='left' valign='middle'><img src='pics/books.jpg' width='278' height='219' /></td>");
        out.println("<td width='408' align='left' valign='middle'><p>");		
		
        out.println(
                "<font face='Arial, Helvetica, sans-serif'>This webpage has been designed by Jamie Brindle at Manchester Metropolitan University. </font></p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'>It has been created as part of a second year website development project.</font></p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'>The project demonstrates both java programming techniques and remotely access mysql database usability, integrated into a simple 'online bookshop' website.</font></p>");
        out.println("<td width='71' align='left' valign='middle'>&nbsp;</td>");
        out.println(
                "<td width='155' align='center' valign='top'><p><font face='Arial, Helvetica, sans-serif'>new user?</font></p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'><a href='registeruser'>click here to register</a></font></p></td>");
        out.println("</tr>");
        
        out.println(
                "<tr><td></td><td colspan='3'><font face='Arial, Helvetica, sans-serif'>");
        out.println(
                "For help and information on this website please go to 'Online Help'.<br>");
        out.println(
                "Here you will also find example usernames and passwords so you can fully explore this website");
        out.println("</font></td></tr>");
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");

    }
}
