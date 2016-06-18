import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Used to retrieve server options from a textfile such as RMI binding
 * address and the maximum history size of the client and pass the values
 * onto the calling method
 * 
 * @author Jamie Brindle (06352322), Enterprise Programming, Level 3
 *
 */
public class ServerOptions {
	 private String[] input;
	 private String address, maxHistory;
	 private String fileLocation;
	 private int maxHistoryInt;
		 
	public ServerOptions() {
		 input = new String[8];
		 fileLocation="serverOptions.txt";
	}
	
	@SuppressWarnings("deprecation")	
	/**
	 * Used to retrieve RMI binding address from a textFile
	 *
	 */
	public String getBindingAddress() {
		//load server address from serverOptions.txt file
        File file = new File(fileLocation);
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        DataInputStream dis = null;
		    
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            dis = new DataInputStream(bis);
		  
            for (int n = 0; dis.available() != 0; n++) {
                input[n] = dis.readLine();        	
            }
		      
            address = input[3].toString();
		  
            fis.close();
            bis.close();
            dis.close();
            
		   
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        
        return address;
        
	}
	
	@SuppressWarnings("deprecation")	
	/**
	 * Used to retrieve the maxHistorySizw from a textFile
	 *
	 */
	public int getMaxHistorySize() {
		    maxHistory = "";
	        maxHistoryInt = 0;
			
	        File file = new File(fileLocation);
	        FileInputStream fis = null;
	        BufferedInputStream bis = null;
	        DataInputStream dis = null;
		    
	        try {
	            fis = new FileInputStream(file);
	            bis = new BufferedInputStream(fis);
	            dis = new DataInputStream(bis);
		  
	            for (int n = 0; dis.available() != 0; n++) {
	                input[n] = dis.readLine();        	
	            }
		      
	            maxHistory = input[6].toString();
		  
	            fis.close();
	            bis.close();
	            dis.close();
		   
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        maxHistoryInt = Integer.parseInt(maxHistory);
	        return maxHistoryInt;
	    }
	}

