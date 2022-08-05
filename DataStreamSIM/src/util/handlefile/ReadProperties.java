package util.handlefile;

import java.util.ArrayList;
import java.util.Properties;  
import java.io.*;

/**
 * Load the <code>datastreamsim.properties</code> file.
 * 
 * @version 1.0
 * @author kostas
 *
 */
public class ReadProperties {
	private static String curDir = System.getProperty("user.dir");
	private static final String PROP_FILE = new String(curDir + "/datastreamsim.properties");  
	public ArrayList<String> loadPropertiesFile(){ 
		ArrayList<String> properties = new ArrayList<String>();
		try{  
			Properties prop = new Properties(); 
			FileInputStream fistream = new FileInputStream(PROP_FILE);  
			prop.load(fistream);  
			String parameter = prop.getProperty("Parameter"); 
			properties.add(parameter);
			String testno = prop.getProperty("TestingNo");
			properties.add(testno);
			String emptyDir = prop.getProperty("EmptyDir");
			properties.add(emptyDir);
			String systemExecutionTimes = prop.getProperty("SystemExecutionTimes"); 
			properties.add(systemExecutionTimes);
			String avgTupleArrivalRate = prop.getProperty("ArrivalRate"); 
			properties.add(avgTupleArrivalRate);
			String avgTupleArrivalRateStep = prop.getProperty("ArrivalRateStep"); 
			properties.add(avgTupleArrivalRateStep);
			String avgRoutingCost = prop.getProperty("AvgRoutingCost"); 
			properties.add(avgRoutingCost);
			String avgRoutingCostStep = prop.getProperty("AvgRoutingCostStep"); 
			properties.add(avgRoutingCostStep);
			fistream.close();   
			return properties;
		}catch(Exception e){  
			System.out.println("Failed to read from " + PROP_FILE + " file.");  
	    }//end try-catch
		return null;
	}//end method loadPropertiesFile()
}//end class ReadProperties
