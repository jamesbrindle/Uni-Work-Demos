import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A servlet which acts as a dynamically created html web page for the quiz administration panel
 * and includes user authentication via created browser cookie values. It supposed both Get and Post
 * requests
 * 
 * @author Jamie Brindle (06352322) - Msc - Manchester Metropolitan University
 */
public class viewstatistics extends common {

	private static final long serialVersionUID = 1L;
	protected DatabaseConnector connector;
	protected ManFunctions manFun;

	/**
	 * Constructor which creates instances of other associated classes
	 */

	public viewstatistics() {
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
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		response.setContentType("text/html");
		out = response.getWriter();

		//authenticate user
		if (validateUser(request, response) == false) {
			loginFailureHTML(request, response);
		} else {		

			docTypeState(request, response);        
			out.println("<html>");
			out.println("<head>");
			out.println("<title>View Statistics</title>");
			startHTMLCode(request, response); 
			out.println(
					"<td height='60' align='center' colspan='5'>"
					+ "<font size='5' face='Arial, Helvetica, sans-serif'>View Statistics</font></td>");

			navigationHTML(request, response);

			out.println(
					"<td width='40%' align='center' valign='top'><p>&nbsp;</p>"); 

			if (validateUser(request, response) == false) {
				loginFailureHTML(request, response); //redirect to login page
			} else {
				out.println(
						"<font size='4' face='Arial, Helvetica, sans-serif'>");
				out.println("<br>Statistics are as follows:<br><br>");

				String subjectName = request.getParameter("subjectname");
				String inputString = connector.retrieveStatistics(subjectName,
				"0");

				String inputArray[] = manFun.splitString(inputString);

				if(manFun.containsOnlyNumbers(inputArray[1])==true) {

					out.println("Total Average: " + inputArray[1] + "%<br>");
					out.println("Highest Average: " + inputArray[2] + "%<br>");
					out.println("Lowest Average: " + inputArray[3] + "<br>");

				} else {
					out.println("There are no statistic available for this subject");
				}

				out.println(
				"<form name='form1' method='post' action='viewstatisticsps'>");
				out.println(
				"<p><input type='submit' name='Back' value='Back'></p></form>");

				out.println(
				"<td width='30%' align='center' valign='top'>&nbsp;</td>");

				endHTMLCode(request, response);
			}
		}
	}
}
