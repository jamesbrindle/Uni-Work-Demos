import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * MIDletServlet is a serlvet acting for a MIDlet which connects to this class remotely, communicating via http request/responses
 * The prime purpose for this servlet is to allow the MIDlet to connect to a database through an intermediatory. As the MIDlet
 * is limited to bandwidth and network speeds, this servlet can do the more demanding network tasks such as querying database
 * and rely the information back to the MIDlet in the form of a string in a similar way to using Sockets
 * 
 * @author Jamie Brindle (06352322) - Msc - Manchester Metropolitan University
 */
public class MIDletServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private String serverInput = "";	
    private DatabaseConnector connector;
    private ManFunctions manFun;

    /**
     * Constructor for MIDletServlet, calling instances of other associated class
     */
    public MIDletServlet() {
        manFun = new ManFunctions();
        connector = new DatabaseConnector();
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

        if (request.getAttributeNames() != null) {

            serverInput = request.getParameter("MIDletOutput");

            response.setContentType("text/plain");
            PrintWriter out = response.getWriter();

            if (serverInput.equalsIgnoreCase("connect")) {
                out.println("connected");
                System.out.println("A MIDlet has Connected");

            } else if (serverInput.contains("login")) {

                String[] loginQuery = manFun.splitString(serverInput);

                if (loginQuery.length < 2) {
                    System.out.println("No Login Details Entered for Query");
                    System.out.println("Login Failed");
                    out.println("loginFailed");
                } else {
                    String responseString = connector.queryLogin(loginQuery[1],
                            loginQuery[2], false);

                    if (responseString.length() < 3) {
                        out.println("loginFailed");
                        System.out.println("No UserID or Password, Login Failed");
                    } else {
                        out.println(responseString);
                    }
                }

            } else if (serverInput.contains("querySubjects")) {
                String responseString = connector.querySubjects();

                if (responseString.length() < 3) {
                    out.println("nosubjects");
                    System.out.println("No Subjects Found");
                } else {
                    out.println("subjectList:" + responseString);
                }
            } else if (serverInput.contains("queryWeeks")) {

                String[] input = manFun.splitString(serverInput);

                String responseString = connector.queryWeeks(input[1], 0);

                if (responseString.length() < 3) {
                    out.println("noweeks");
                    System.out.println("No Weeks Found");
                } else {
                    out.println("weekList:" + responseString);
                }
            } else if (serverInput.contains("queryResultWeeks")) {

                String[] input = manFun.splitString(serverInput);

                String responseString = connector.queryWeeks(input[1], 1);

                if (responseString.length() < 3) {
                    out.println("noweeks");
                    System.out.println("No Weeks Found");
                } else {
                    out.println("weekList:" + responseString);
                }
            } else if (serverInput.contains("retrieveQuestion")) {
                String[] input = manFun.splitString(serverInput);

                String responseString = connector.retrieveQuestion(input[1],
                        input[2], input[3]);

                if (responseString.length() < 3) {
                    out.println("noquestion");
                    System.out.println("No Question Found");
                } else {
                    out.println("retrievedQuestion:" + responseString);
                }				
            } else if (serverInput.contains("retrieveAnsweredQuestion")) {
                String[] input = manFun.splitString(serverInput);

                String responseString = connector.retrieveAnsweredQuestion(
                        input[1], input[2], input[3], input[4]);

                if (responseString.length() < 6) {
                    out.println("noquestion");
                    System.out.println("No Question Found");
                } else {
                    out.println("retrievedQuestion:" + responseString);
                }				
            } else if (serverInput.contains("saveQuestion")) {
                String[] input = manFun.splitString(serverInput);

                connector.saveAnswers(input[1], input[2], input[3], input[4],
                        input[5]);
            } else if (serverInput.contains("retrieveWeekStatistics")) {
                String[] input = manFun.splitString(serverInput);
                String responseString = connector.retrieveWeekStatistics(
                        input[1], input[2], input[3]);

                out.println(responseString);
					
            } else if (serverInput.contains("retrieveStatistics")) {
                String[] input = manFun.splitString(serverInput);
                String responseString = connector.retrieveStatistics(input[1],
                        input[2]);

                out.println(responseString);
					
            } else {
                out.println("unknown command");
            }
        } else {
            System.out.println("No MIDlet Connection");
        }
    }
}
