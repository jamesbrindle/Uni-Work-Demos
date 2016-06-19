import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Vector;

/**
 * DatabaseConnector is the class which connects to a mysql database on some network and
 * contains a list of methods to be used by the MIDlet application and the quiz administration panel
 *  
 * @author Jamie Brindle (06352322) - Msc - Manchester Metropolitan University
 */
public class DatabaseConnector {
	//declare global variables
    private Connection conn;
    private ManFunctions manFun;
    private String responseString;
    private String database, user, password, url, mysqlClass;
    private String[] serverDetails;

    /**
     * DatabaseConnector constructor - also uses a ManFunctions methods to read
     * a text file which contains database location and authorisation details
     */
    public DatabaseConnector() {
    	//initialise global variables
        manFun = new ManFunctions();
        responseString = "";        
        serverDetails=manFun.readFile("serverOptions.txt");
      
        database = serverDetails[5];
        user = serverDetails[8];
        password =  serverDetails[11];
        url =  serverDetails[14] + database;  
        mysqlClass =  serverDetails[17];
    }

    /**
     * Makes the connection to the database
     * @throws IOException
     */
    protected void connect()
        throws IOException {        

        try { // where the mysql driver is found
            Class.forName(mysqlClass).newInstance();
        } catch (Exception e) {
            System.err.println(e);
        }

        // connecting to database
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to MySQL Database");

        } catch (SQLException se) {
            System.err.println(se);
        }
    }

    /**
     * Queries a login details in database
     * @param userID
     * @param password
     * @param isTutor
     * @throws IOException
     * @return responseString - response from database
     */
    protected String queryLogin(String userID, String password, boolean isTutor) throws IOException {
        connect();
        System.out.println("Querying Users");
        responseString = "";
        String selectSQL = "";

        if (isTutor) {
            selectSQL = "select * from tutors where userid ='" + userID
                    + "' && Password ='" + password + "'";
        } else {
            selectSQL = "select * from users where userid ='" + userID
                    + "' && Password ='" + password + "'";      
        }

        Statement stmt;

        try {
            stmt = conn.createStatement();		
            ResultSet rs1 = stmt.executeQuery(selectSQL);

            while (rs1.next()) { 
                responseString = "userdetails:" + rs1.getString("firstName")
                        + ":" + rs1.getString("secondName");
            }
			
            stmt.close();
			
        } catch (SQLException e) {
            responseString = "";
        }
        closeConnection();
        return responseString;

    }

    /**
     * Queries a subject list in database
     * @throws IOException
     * @return responseString - response from database
     * 
     */
    protected String querySubjects() throws IOException {
        connect();

        System.out.println("Querying Subjects");		
        responseString = "";
        String selectSQL = "select * from subjects";
        Statement stmt;

        try {
            stmt = conn.createStatement();		
            ResultSet rs1 = stmt.executeQuery(selectSQL);

            while (rs1.next()) { 
                responseString = responseString + rs1.getString("subjectname")
                        + ":";
            }
            stmt.close();
        } catch (SQLException e) {
            responseString = "";
        }
        closeConnection();
        return responseString;
    }

    /**
     * Queries a week list in database
     * @throws IOException
     * @return responseString - response from database
     * @param subjectName
     * @param selector 
     */
    protected String queryWeeks(String subjectName, int selector) throws IOException {
        connect();

        String selectSQL2 = "";
        String subjectID = "";

        System.out.println("Querying Week");
        Vector<Integer> weeksVector = new Vector<Integer>();		

        responseString = "";

        String selectSQL = "select * from subjects where subjectname='"
                + subjectName + "'";
        Statement stmt;

        try {
            stmt = conn.createStatement();		
            ResultSet rs1 = stmt.executeQuery(selectSQL);

            while (rs1.next()) { 
                subjectID = rs1.getString("subjectid");
            }
            stmt.close();
        } catch (SQLException e) {
            responseString = "";
        }

        if (selector == 0) {

            selectSQL2 = "select * from questions where subjectid = '"
                    + subjectID + "'";		
        } else {
            selectSQL2 = "select * from results where subjectid = '" + subjectID
                    + "'";		
        }
        Statement stmt2;

        try {
            stmt2 = conn.createStatement();		
            ResultSet rs2 = stmt2.executeQuery(selectSQL2);

            while (rs2.next()) { 
                weeksVector.add(rs2.getInt("weekno"));
            }

            stmt2.close();
            responseString = manFun.sortWeeks(weeksVector);

        } catch (SQLException e) {
            responseString = "";
        }		

        closeConnection();

        return responseString;
    }

    /**
     * Queries questions for a particular question in database
     * @throws IOException
     * @return responseString - response from database
     * @param weekNo
     * @param questionNo
     * @param subjectName 
     */
    protected String retrieveQuestion(String weekNo, String questionNo, String subjectName) 
    throws IOException {
        connect();

        String theQuestion = "";
        Vector<String> answers = new Vector<String>();

        responseString = "";

        String subjectID = "";

        System.out.println("Querying Questions");

        String selectSQL = "select * from subjects where subjectname='"
                + subjectName + "'";
        Statement stmt;

        try {
            stmt = conn.createStatement();		
            ResultSet rs1 = stmt.executeQuery(selectSQL);

            while (rs1.next()) { 
                subjectID = rs1.getString("subjectid");
            }
			
            stmt.close();
        } catch (SQLException e) {
            responseString = "";
        }

        String selectSQL2 = "select * from questions where subjectid = '"
                + subjectID + "' && weekno = '" + weekNo + "' && questionno='"
                + questionNo + "'";		
        Statement stmt2;

        try {
            stmt2 = conn.createStatement();		
            ResultSet rs2 = stmt2.executeQuery(selectSQL2);

            while (rs2.next()) { 
                theQuestion = rs2.getString("question");
                answers.add(rs2.getString("answers"));

            }
            stmt2.close();
			
        } catch (SQLException e) {
            responseString = "";
        }		

        closeConnection();

        for (int i = 0; i < answers.size(); i++) {
            responseString = responseString + answers.get(i) + ":";
        }
        responseString = theQuestion + ":" + responseString;

        if (responseString.equalsIgnoreCase(":")) {
            responseString = "";
        }

        return responseString;
    }

    /**
     * Queries a results for a particular question in database
     * @throws IOException
     * @return responseString - response from database
     * @param weekNo
     * @param questionNo
     * @param subjectName 
     * @param userID
     */
    protected String retrieveAnsweredQuestion(String weekNo, String questionNo, String subjectName, String userID) 
    throws IOException {
        connect();

        String theQuestion = "";
        int answerChosen = 0;
        int correctAnswer = 0;

        Vector<String> answers = new Vector<String>();

        responseString = "";

        String subjectID = "";

        System.out.println("Querying Results");

        String selectSQL = "select * from subjects where subjectname='"
                + subjectName + "'";
        Statement stmt;

        try {
            stmt = conn.createStatement();		
            ResultSet rs1 = stmt.executeQuery(selectSQL);

            while (rs1.next()) { 
                subjectID = rs1.getString("subjectid");
            }
            stmt.close();
        } catch (SQLException e) {
            responseString = "";
        }

        String selectSQL2 = "select * from results, questions where results.subjectid = '"
                + subjectID + "' && results.weekno = '" + weekNo
                + "' && results.questionno='" + questionNo
                + "' && questions.questionno='" + questionNo
                + "' && questions.weekno = '" + weekNo
                + "' && questions.subjectid= '" + subjectID
                + "' && results.userid = '" + userID + "'";		
        Statement stmt2;

        try {
            stmt2 = conn.createStatement();		
            ResultSet rs2 = stmt2.executeQuery(selectSQL2);

            int i = 0;

            while (rs2.next()) { 
                i++;
                theQuestion = rs2.getString("question");
                answers.add(rs2.getString("answers"));
                answerChosen = rs2.getInt("results.answerno");
                if (rs2.getInt("correctanswer") == 1) {
                    correctAnswer = i;
                }

            }
            stmt2.close();
        } catch (SQLException e) {
            responseString = "";

        }		

        closeConnection();

        for (int i = 0; i < answers.size(); i++) {
            responseString = responseString + answers.get(i) + ":";
        }
        responseString = correctAnswer + ":" + answerChosen + ":" + theQuestion
                + ":" + responseString;

        if (!(responseString.length() > 3)) {
            responseString = "";
        }

        return responseString;
    }

    /**
     * inserts details of an answered question in database
     * @throws IOException
     * @param weekNo
     * @param questionNo
     * @param subjectName 
     * @param userID
     * @param answerNo
     */
    protected void saveAnswers(String subjectName, String userID, String weekNo, String questionNo, String answerNo) 
    throws IOException {
        connect();
        int selectorInt = 0;
        String isCorrect = "0";

        responseString = "";

        String subjectID = "";

        System.out.println("Inserting Results");

        String selectSQL = "select * from subjects where subjectname='"
                + subjectName + "'";
        Statement stmt;

        try {
            stmt = conn.createStatement();		
            ResultSet rs1 = stmt.executeQuery(selectSQL);

            while (rs1.next()) { 
                subjectID = rs1.getString("subjectid");
            }
            stmt.close();
        } catch (SQLException e) {}

        String selectSQL2 = "select * from results where subjectid = '"
                + subjectID + "' && weekno = '" + weekNo + "' && questionno='"
                + questionNo + "'" + "&& userid='" + userID + "'";		
        Statement stmt2;

        try {
            stmt2 = conn.createStatement();		
            ResultSet rs2 = stmt2.executeQuery(selectSQL2);

            while (rs2.next()) { 
                selectorInt = 1;

            }
            stmt2.close();
        } catch (SQLException e) {}		

        String selectSQL3 = "select answerno from questions WHERE questionno='"
                + questionNo + "' && weekno='" + weekNo + "' && subjectID='"
                + subjectID + "' && correctanswer='1'";

        ;

        Statement stmt3;

        try {
            stmt3 = conn.createStatement();		
            ResultSet rs3 = stmt3.executeQuery(selectSQL3);

            while (rs3.next()) { 
                if (answerNo.contains(rs3.getString("answerno"))) {
                    isCorrect = "1";
                }

            }
            stmt3.close();
        } catch (SQLException e) {}		

        if (selectorInt == 0) {
            try {
                PreparedStatement pstmt;

                pstmt = conn.prepareStatement(
                        "insert into results (subjectid, userid, weekno, questionno, answerno, iscorrect) VALUES ('"
                                + subjectID + "', '" + userID + "', '" + weekNo
                                + "', '" + questionNo + "', '" + answerNo
                                + "', '" + isCorrect + "')");

                int insert = 0;

                selectorInt = 0;

                insert = pstmt.executeUpdate();
                if (insert == 0) {
                    System.out.println(
                            insert + ": Insert answered question failure");
                } else {
                    System.out.println(insert + ": Inserting answered question");
                }
                pstmt.close();
            } catch (SQLException e) {
                System.out.println("Insert answered question failure");
            } 

        } else {
            try {
                PreparedStatement pstmt;

                pstmt = conn.prepareStatement(
                        "update results SET answerno = '" + answerNo
                        + "', iscorrect='" + isCorrect + "' where subjectid='"
                        + "" + subjectID + "' && userid='" + userID
                        + "' && weekno='" + weekNo + "' && questionno='"
                        + questionNo + "'");

                int insert = 0;

                selectorInt = 0;

                insert = pstmt.executeUpdate();
				
                if (insert == 0) {
                    System.out.println(
                            insert + ": Update answered question failure");
                } else {
                    System.out.println(insert + ": Updating answered question");
                }
				
                pstmt.close();
            } catch (SQLException e) {
                System.out.println("Update answered question failure");
            } 
        }

        closeConnection();
    }

    /**
     * Inserts or updates a question into dabase
     * @throws IOException
     * @param weekNo
     * @param questionNo
     * @param subjectName
     * @param theQuestion
     * @param answerNo
     * @param answerOption
     * @param correctAnswer
     */
    protected void insertQuestion(String questionNo, String weekNo, String subjectName, 
            String theQuestion, String answerNo, String answerOption, String correctAnswer) 
    throws IOException {

        connect();

        String subjectID = "nonSubject";

        String selectSQL = "select * from subjects where subjectname='"
                + subjectName + "'";
        Statement stmt;

        try {
            stmt = conn.createStatement();		
            ResultSet rs1 = stmt.executeQuery(selectSQL);

            while (rs1.next()) { 
                subjectID = rs1.getString("subjectid");

            }
			
            stmt.close();
        } catch (SQLException e) {}

        if (subjectID.equalsIgnoreCase("nonSubject")) {
            try {
                PreparedStatement pstmt;

                pstmt = conn.prepareStatement(
                        "insert into subjects(subjectname) values ('"
                                + subjectName + "')");
                int insert = 0;
				
                insert = pstmt.executeUpdate();
				
                if (insert == 0) {
                    System.out.println(insert + ": Insert subject failure");
                } else {
                    System.out.println(insert + ": Inserting Subject");
                }
				
                pstmt.close();

                stmt = conn.createStatement();		
                ResultSet rs1 = stmt.executeQuery(selectSQL);

                while (rs1.next()) { 
                    subjectID = rs1.getString("subjectid");

                }
				
                stmt.close();
            } catch (SQLException e) {
                System.out.println("Insert subject failure");
            }
						
        }

        String selectSQL2 = "select * from questions where questionno='"
                + questionNo + "'" + "&& weekno = '" + weekNo
                + "' && subjectid = '" + subjectID + "'" + "&& answerno = '"
                + answerNo + "'";
        Statement stmt2;

        boolean doesExist = false;

        try {
            stmt2 = conn.createStatement();		
            ResultSet rs2 = stmt2.executeQuery(selectSQL2);

            while (rs2.next()) { 
                doesExist = true;

            }
            stmt2.close();
        } catch (SQLException e) {}

        if (doesExist == false) {

            try {
                PreparedStatement pstmt;

                pstmt = conn.prepareStatement(
                        "insert into questions (questionno, weekno, answerno, answers, " +
                        "correctanswer, subjectid, question)"
                                + " values ('" + questionNo + "', " + "'"
                                + weekNo + "', " + "'" + answerNo + "', " + "'"
                                + answerOption + "', " + "'" + correctAnswer
                                + "', " + "'" + subjectID + "', " + "'"
                                + theQuestion + "')");
                int insert = 0;
				
                insert = pstmt.executeUpdate();
				
                if (insert == 0) {
                    System.out.println(insert + ": Insert question failure");
                } else {
                    System.out.println(insert + ": Inserting question");
                }
                pstmt.close();
				
            } catch (SQLException e) {
                System.out.println("Insert question failure");
            } 	

        } else {
            try {
                PreparedStatement pstmt;

                pstmt = conn.prepareStatement(
                        "update questions set answers='" + answerOption
                        + "', correctanswer='" + correctAnswer + "', question='"
                        + theQuestion + "'" + " where questionno='" + questionNo
                        + "' && weekno='" + weekNo + "' && subjectid='"
                        + subjectID + "' && answerno ='" + answerNo + "'");
                int insert = 0;

                insert = pstmt.executeUpdate();
				
                if (insert == 0) {
                    System.out.println(insert + ": Update question failure");
                } else {
                    System.out.println(insert + ": Updating question");
                }
				
                pstmt.close();
				
            } catch (SQLException e) {
                System.out.println("Update question failure");
            } 
						
        }

    }
    
    /**
     * Queries statistics of the results of a particular week for a particular user from the database
     * @throws IOException
     * @return responseString - response from database
     * @param weekNo
     * @param subjectName
     * @param userID
     */
    protected String retrieveWeekStatistics(String weekNo, String subjectName, String userID) 
    throws IOException {
        String responseString = "";

        connect();

        String subjectID = "";

        responseString = "";

        String selectSQL = "select * from subjects where subjectname='"
                + subjectName + "'";
        Statement stmt;

        try {
            stmt = conn.createStatement();		
            ResultSet rs1 = stmt.executeQuery(selectSQL);

            while (rs1.next()) { 
                subjectID = rs1.getString("subjectid");

            }
            stmt.close();
        } catch (SQLException e) {
            responseString = "";
        }

        String selectSQL2 = "select * from results, questions where results.subjectid = '"
                + subjectID + "' && questions.subjectid= '" + subjectID
                + "' && results.weekno = '" + weekNo
                + "' && questions.weekno = '" + weekNo + "' && results.userid='"
                + userID + "' && questions.questionno=results.questionno";		
        Statement stmt2;

        System.out.println(selectSQL2);

        try {
            stmt2 = conn.createStatement();		
            ResultSet rs2 = stmt2.executeQuery(selectSQL2);

            int i = 0;
            int correctAnswerCount = 0;
            int resultsCount = 0;
            int tempInt = 0;

            while (rs2.next()) {
                i++;

                if (!(rs2.getInt("resultid") == tempInt)) {
                    resultsCount++;
                    tempInt = rs2.getInt("resultid");
                    i = 1;
                }

                if (rs2.getInt("correctAnswer") == 1) {
                    if (rs2.getInt("results.answerno") == i) {
                        correctAnswerCount++;
                    }
                }

            }
            stmt2.close();

            double percentage = 0.00;
            DecimalFormat deci = new DecimalFormat("##.##");

            percentage = ((double) correctAnswerCount / (double) resultsCount)
                    * 100;

            responseString = correctAnswerCount + ":" + resultsCount + ":"
                    + deci.format(percentage) + "%";

        } catch (SQLException e) {
            responseString = "";
        }		

        closeConnection();

        return responseString;
    }

    /**
     * Queries statistics of the results of a particular subject for for all users
     * @throws IOException
     * @return responseString - response from database
     * @param subjectName
     * @param userID
     */
    public String retrieveStatistics(String subjectName, String userID) throws IOException {
        connect();
        String responseString = "";

        String subjectID = "";

        String selectSQL = "select * from subjects where subjectname='"
                + subjectName + "'";
        Statement stmt;

        try {
            stmt = conn.createStatement();		
            ResultSet rs1 = stmt.executeQuery(selectSQL);

            while (rs1.next()) { 
                subjectID = rs1.getString("subjectid");

            }
            stmt.close();
        } catch (SQLException e) {
            responseString = "";
        }

        String selectSQL2 = "select * from results where subjectid='"
                + subjectID + "'";		
        Statement stmt2;

        DecimalFormat deci = new DecimalFormat("##.##");
        double userAverage = 0.00;
        double totalAverage = 0.00;
        double highestAverage = 0.00;
        double lowestAverage = 100.00;
        
        int userResultsCount = 0;
        int userCorrectCount = 0;
        int tempUserID = 0;
        int tempCorrectCount = 0;
        int tempTotalCount = 0;
        
        Vector<Integer> userIDs = new Vector<Integer>();
        Vector<Integer> results = new Vector<Integer>();
        Vector<Integer> sortedUserIDs = new Vector<Integer>();
        Vector<Integer> sortedCorrectCount = new Vector<Integer>();
        Vector<Integer> sortedTotalCount = new Vector<Integer>();
        Vector<Double> allAverages = new Vector<Double>();

        try {
            stmt2 = conn.createStatement();		
            ResultSet rs2 = stmt2.executeQuery(selectSQL2);

            while (rs2.next()) {
                userIDs.add(rs2.getInt("userid"));
                results.add(rs2.getInt("iscorrect"));

                if (rs2.getString("userid").contains(userID)) {
                    userResultsCount++;
                    if (rs2.getString("iscorrect").contains("1")) {
                        userCorrectCount++;
                    }
                }
            }
			
            stmt2.close();               

            for (int i = 0; i < userIDs.size(); i++) {
                tempUserID = userIDs.get(i);
                if (!(sortedUserIDs.contains(tempUserID))) {
                    sortedUserIDs.add(tempUserID);
                    for (int x = 0; x < userIDs.size(); x++) {
                        if (userIDs.get(x) == tempUserID) {
                        	tempTotalCount++;
                            if (results.get(x) == 1) {
                                tempCorrectCount++;
                            }

                        }
                    }
                    sortedCorrectCount.add(tempCorrectCount);
                    sortedTotalCount.add(tempTotalCount);
                    tempCorrectCount = 0;
                    tempTotalCount = 0;
                }				
            }
            
            for (int i = 0; i < sortedUserIDs.size(); i++) {
            allAverages.add(((double)sortedCorrectCount.get(i) / 
            		(double)sortedTotalCount.get(i)) * 100);                
            } 
            
            for (int i = 0; i < allAverages.size(); i++) {
            	totalAverage = totalAverage + allAverages.get(i);
            	if(allAverages.get(i)<lowestAverage) {
            		lowestAverage = allAverages.get(i);            		
            	}
            	if(allAverages.get(i)>highestAverage) {
            		highestAverage = allAverages.get(i);
            	}           	
            }
            
            totalAverage = totalAverage / allAverages.size();

            userAverage = ((double) userCorrectCount / (double) userResultsCount)
                    * 100;

            responseString = (deci.format(userAverage) + ":"
                    + deci.format(totalAverage) + ":"
                    + deci.format(highestAverage) + ":"
                    + deci.format(lowestAverage) + "%");

        } catch (SQLException e) {
            responseString = "";
        }		

        return responseString;
    }

    /**
     * Closes the connection to the dabase
     * @throws IOException
     */
    protected void closeConnection()
        throws IOException {
        try {
            conn.close();
            System.out.println("Connection Closed to MySQL Database");

        } catch (SQLException se) {
            System.err.println(se);
        }
    }
}
