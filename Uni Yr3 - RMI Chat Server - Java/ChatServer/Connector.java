import java.sql.*;

/**
 * This class deals with the connection to the mysql database. The Chatter
 * GUI will connect to the database in order to retrieve a list of available
 * chat rooms, to save the history, to retrieve the history and a method
 * that translates a room name to a specific id in order to allow the correct
 * transfer of messages to the correct room.
 * 
 * @author Jamie Brindle (06352322), Enterprise Programming, Level 3
 */
public class Connector {
 
private String[] serverListArray;
public String[] serverIDArray;
private Connection conn;
public String serverList;
private String history;
 
 /**
  * Constructs the connector
  */
 public Connector(){
  serverListArray = new String[100];
  serverIDArray = new String[100];
  conn = null; // Create connection object
  serverList = null;
    
 }
 
 /**
  * Connects to the database
  */
 public void connect() {
  String database = "brindlej"; // Name of database
        String user = "brindlej"; // username for mysql connection
        String password = "helloThere123"; // password for mysql connection
        String url = "jdbc:mysql://jbnet.no-ip.org/" + database; // where to connect

        try { // where the mysql driver is found
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception e) {
            System.err.println(e);
        }
 
        // connecting to database
        try {
            conn = DriverManager.getConnection(url, user, password);
  
        } catch (SQLException se) {
            System.err.println(se);
        }
 }

 /**
  * retrieves a list of servers from the mysql database
  */
 public String[] receiveServers() {
  connect();
        
        try {
            String selectSQL = "select * from servers";

            System.err.println("DEBUG: Query: " + selectSQL);
    
            Statement stmt = conn.createStatement();
            ResultSet rs1 = stmt.executeQuery(selectSQL);
            
            int i=0;
            
            //stores the servers into an array
            while (rs1.next()) {
             
             serverListArray[i] = rs1.getString("server");
             i++;
            }
            
            //closes the mysql connection
            conn.close();
            
        } catch (Exception se) {
            System.out.println("Error: unable to connect");
            System.err.println(se);
            return serverListArray;
        }
        
       
     
        return serverListArray;
 }
 
 /**
  * retrieves the chat room history if the user didn't select manual
  * server entry
  */
 public String retrieveHistory(int roomID) {
  //connect to database
  connect();
  try {
   //if there is a room
   if(!(roomID==0)) {
   
            String selectSQL = "select * from history where ID='"+roomID+"'";

            System.err.println("DEBUG: Query: " + selectSQL);
    
            Statement stmt = conn.createStatement();
            ResultSet rs1 = stmt.executeQuery(selectSQL);
            int i=99;
            
          while(rs1.next()) { 
             i=1;      
           history=rs1.getString("historytext");
          }
          //if there are history entries
          if(i==1) {
          
          }
          //if there aren't history entries
          else {
           history="<-There is no history prior to this point->";
          }
           //close the connection
            conn.close();
  }
            
        } catch (Exception se) {
            System.out.println("Error: unable to connect");
            System.err.println(se);
            return history;
        }
   
  return history;
 }
 
 /**
  * Stores the history into the database. It first looks up to see
  * if history for a particular room already exists. if so the databases
  * is edited and not inserted. if there aren't history entries, the history
  * is inserted
  */
 public void storeHistory(int roomID, String text) {
  //connect to database
  connect();
  try {
   
   if(!(roomID==0)) {
    
    //first see if entries exist
    int i=99;
    String selectSQL = "select * from history where ID='"+roomID+"'";

             System.err.println("DEBUG: Query: " + selectSQL);
           
             Statement stmt = conn.createStatement();
             ResultSet rs1 = stmt.executeQuery(selectSQL);
             while(rs1.next()) {
              i=1;
             }
            
             //if entries do exists, update rather than insert
             if (i==1) {
             
    PreparedStatement pstmt;
    
                pstmt = conn.prepareStatement(
                        "update history SET historytext ='" + text
                        + "' where ID='" + roomID + "'");
               
                @SuppressWarnings("unused")
    int insert;
                insert = pstmt.executeUpdate();
                pstmt.close(); 
               
                System.err.println("DEBUG: insert via update");
                
             }
             
             //if entries don't exist, insert rather than update
             else {
              PreparedStatement pstmt;
              
                 pstmt = conn.prepareStatement(
                   
                         "insert into history(id,historytext) values('"+roomID+"','" +text+"')");
                 
                 @SuppressWarnings("unused")
     int insert;
                 insert = pstmt.executeUpdate();
                 pstmt.close(); 
                 System.err.println("DEBUG: insert via insert");
                
             }
            //close the connection
            conn.close();
  }
            
        } catch (Exception se) {
            System.out.println("Error: unable to connect");
            System.err.println(se);
           
        }
        
    
  
 }
 
 /**
  * matches the room name to the room id, sending an integer rather
  * than a string to determine where messages go is a sounder way
  * of doing things. This methods looks up the mysql database and
  * matches the name with its corresponding ID
  */ 
 public int matchServertoID(String serverName) {
  int id = 0;
  connect();
  
  try {
   //if manual entry selected, room is set to number 99 and
   //mysql database is not looked up as there won't be an entry
   if(serverName.equalsIgnoreCase("Manual Server Entry")) {
    return 99;
   }
   //lookup database
   else {
            String selectSQL = "select * from servers where server='"+serverName+"'";

            System.err.println("DEBUG: Query: " + selectSQL);
    
            Statement stmt = conn.createStatement();
            ResultSet rs1 = stmt.executeQuery(selectSQL);
            
            rs1.next();
            id=rs1.getInt("ID");
            
            //close connection
            conn.close();
  }
            
        } catch (Exception se) {
            System.out.println("Error: unable to connect");
            System.err.println(se);
            return id;
        }
        
         
  return id;
  
 }
 

}
