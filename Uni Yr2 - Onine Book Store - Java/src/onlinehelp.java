//this servlet simply shows the user some useful help tips and information for 'online help' link

import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;


public class onlinehelp extends common {

    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
        throws ServletException, IOException {
        doPost(request, response);
    }
  
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        docTypeState(request, response); //html print, replica minimisation method located in 'common' 
                       
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Online Help</title>");
        firstBit(request, response); //html print, replica minimisation method located in 'common' 
        out.println(
                "<td height='60' colspan='4'><font size='5' face='Arial, Helvetica, sans-serif'>Online Help</font></td>");
        navigation2(request, response); //html print, replica minimisation method located in 'common'  for navigation bar
        out.println(
                "<td width='29' height='153' align='center' valign='top'><p>&nbsp;</p></font></td>");
        out.println("<td width='650' align='left' valign='top'>");
		  
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'><strong>Welcome to Online Help</strong></font></p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'>In order to fully explore this website"); 
        out.println(
                "example user id's and teacher id's have been created. <br>You'll need two types"); 
        out.println("of username and password to log in:</font></p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'><strong>User Level:</strong><br>");
        out.println(
                "<em>User Name:</em> pretenduser <em>Password: </em>1234</font></p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'><strong>Teacher Level:</strong><br>");
        out.println(
                "<em>User Name:<strong> </strong></em>pretendteacher <em>Password:<strong> </strong></em>1234</font><br></p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'><strong>General Help and Information</strong></font></p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'><a href='#L1'>Searching Books</a></font></p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'><a href='#L2'>Security</a></font></p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'><a href='#L3'>Shopping Cart</a></font></p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'><a href='#L4'>Adding / Removing / Editing User"); 
        out.println("Accounts</a></font></p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'><a href='#L5'>Reading List</a></font></p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'><a href='#L6'>Teachers Area</a></font></p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'><a href='#L7'>Adding Books</a></font></p>");
        out.println("<p>&nbsp;</p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'><strong><a name='L1'>Searching Books</a></strong><br>");
        out.println(
                "There are two ways to search for books. The first is accessed by the 'search"); 
        out.println(
                "books' link on the navigation bar to the left. Here you can quickly search for"); 
        out.println(
                "books providing search information on title, author or category and there's"); 
        out.println(
                "no need to type the exact words, the firsts 3 letters for example would do.</font></p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'>After you have made your search if"); 
        out.println(
                "you are unsuccessful no books will appear and it will ask you to try again,"); 
        out.println(
                "if you are successful your books will show and you will also be able to add"); 
        out.println(
                "the selected book to a shopping basket to later order.</font></p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'>The second way is via the teachers"); 
        out.println(
                "area, however you will have to log in to do this. Upon submitting your search"); 
        out.println(
                "the teacher has more rights and is able to add, remove or edit the selected"); 
        out.println(
                "book or add the book to a specific course reading list.</font></p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'><strong><a name='L2'>Security</a></strong><br>");
        out.println(
                "Upon logging in to either the user area or the teachers area a cookie will be"); 
        out.println(
                "added to your browser. These cookies allow you to navigate around your area"); 
        out.println(
                "without you having to log in again, however this cookie expires when the session"); 
        out.println(
                "expires (close browser) or if your log out or try to log in again.</font></p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'>Of course there are many pages within"); 
        out.println(
                "the users or teachers area that you can only get to via a link from within that"); 
        out.println(
                "area, however some smarter people may try to by-pass the login and go straight"); 
        out.println(
                "to a particular servlet. That's why validation checks area made by every servlet"); 
        out.println(
                "within that area, therefore security of this website is quite substantial.</font></p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'><strong><a name='L3'>Shopping Cart</a></strong><br>");
        out.println(
                "The shopping cart temporarily stores selected books that a you may want to order."); 
        out.println(
                "Of course you cannot order books until you have loggin in. You can also remove"); 
        out.println(
                "selected items from the shopping cart or clear the whole shopping cart in one"); 
        out.println("go.</font></p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'>Once you are happy with your cart"); 
        out.println(
                "and you are logged in, you can then place the order by clicking the 'order'"); 
        out.println(
                "button. A page will appear telling you what you have ordered and the order will"); 
        out.println(
                "be permanently placed in a database so you can if you wish view previous orders</font></p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'><strong><a name='L4'>Adding / Removing / Editing"); 
        out.println("User Accounts</a></strong><br>");
        out.println(
                "You can always add a user account where ever you are on the website via the"); 
        out.println(
                "link to the right of the page. If your user name already exists on our database,"); 
        out.println("a message will appear asking you to try again.</font></p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'>You can also remove your user account"); 
        out.println(
                "details if you wish by going into the login as user area and clicking the link"); 
        out.println("at the button of the page.</font></p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'>Once you are are logged in as a user,"); 
        out.println(
                "if you need to change any of your details you can do via the users area page.</font></p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'><strong><a name='L5'>Reading List</a></strong><br>");
        out.println(
                "A course may have a book reading list, in which a teacher sets up. This is to"); 
        out.println(
                "give users ease of searching, whereby all their course material can be found"); 
        out.println(
                "in one place. A reading list can be added or removed by teachers and if a new"); 
        out.println(
                "course pops up or changes a teacher can add or remove courses too.</font></p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'>Users can search for a particular"); 
        out.println(
                "course reading list via the navigation links to the left of the website and");
        out.println(
                "if you wish you can add the specified book to a shopping cart</font></p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'><strong><a name='L6'>Teachers Area</a></strong><br>");
        out.println(
                "If you are a teacher you have the ability to add, remove or edit books, or reading"); 
        out.println(
                "lists and also your personal details. You cannot add new teachers or remove"); 
        out.println(
                "them though, this is a job for the administrator who will sort this out for"); 
        out.println("you.</font></p>");
        out.println(
                "<p><font face='Arial, Helvetica, sans-serif'><strong><a name='L7'>Adding Books</a></strong><br>");
        out.println(
                "A teacher may add a book via their area page. If the book ID you type is already"); 
        out.println(
                "in the database then you will be asked to try again. The reason you have to"); 
        out.println(
                "enter a book id and it's not generated for you is to make sure you cannot add"); 
        out.println(
                "the same book twice, it's ok to check titles and not book id's but it's very"); 
        out.println(
                "easy to make a slight typing mistake. Generally a book id would be the book");
        out.println("serial number.</font></p>");
        out.println("<p></p>");
        out.println("<p></p>");
        out.println("<p>&nbsp;</p>");
        
        lastBit(request, response); //html print, replica minimisation method located in 'common' 
    }
}
