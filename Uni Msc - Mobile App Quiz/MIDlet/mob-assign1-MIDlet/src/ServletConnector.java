import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

/**
 * ServletConnector is the class that connects to a servlet on some network via HTTP requests and responses.
 * This class simply outputs a String, which is the request which in this case has been created similarly to
 * Sockets, the string will contain a command followed by parameters, seperated by the colon symbol (:).
 * @author Jamie Brindle (06352322) - Msc - Manchester Metropolitan University
 */
public class ServletConnector {
	//declare global variables
    private String MIDletOutput;
    protected String serverInput;
    protected ManFunctions manFun;
    private String URL, fileName;
    private String[] fileInput;
    
    /**
     * ServletConnector constructor. Upon constructing, it will open a text file to seek
     * an url, which is the location on the network of where to connect to
     */
    public ServletConnector() {
    	//initialise global variables
        manFun = new ManFunctions(); //our ManFunctions class instance
        
        fileName = "serverOptions.txt";
        fileInput = manFun.readFile(fileName);
        
        MIDletOutput = "";
        serverInput = "";
        URL = "";
       
		for (int i = 0; i < fileInput.length; i++) {
            if (manFun.contains(fileInput[i], "http")) {
                URL = fileInput[i];
            }			
        }		
    }
	
    /**
     * The method which send the quest String to the servlet on some network
     * @param s is the String sent to the servlet
     * @return response - the response string from the servlet
     */
    protected String request(String s) {	  
        String response = "";	  
		  
        MIDletOutput = "MIDletOutput=" + s;      
	      
        HttpConnection hc = null;
        InputStream in = null;
        OutputStream out = null;
	      
        try {      
            hc = (HttpConnection) Connector.open(URL);
            hc.setRequestMethod(HttpConnection.POST);
            hc.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
	      
            out = hc.openOutputStream();
            out.write(MIDletOutput.getBytes()); //convert to byes and send
	      
            in = hc.openInputStream();
            int length = (int) hc.getLength();
            byte[] serverInput = new byte[length];

            in.read(serverInput); //the response from the serlvet
	      
            response = new String(serverInput);
            return response;
	    
        } catch (IOException ioe) {
            System.out.println("Servlet Connection Failure");
            return "Connection Failure";
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                if (hc != null) {
                    hc.close();
                }
            } catch (IOException ioe) {}
        }		  	  
    }
}
