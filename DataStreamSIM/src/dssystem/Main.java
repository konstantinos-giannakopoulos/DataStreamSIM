package dssystem;

import java.io.File;
import java.util.ArrayList;

import simulation.core.SimulationParameters;
import util.exceptions.RunSystemException;
import util.exceptions.SystemInitilallizationException;
import util.handlefile.FileHandler;
import util.handlefile.ReadProperties;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/* Read the properties file. */
		ReadProperties readProperties = new ReadProperties();
//		Properties prop = new Properties();
		ArrayList<String> properties = new ArrayList<String>();
		properties = readProperties.loadPropertiesFile();
		String parameter = properties.get(0);
		String testingno = properties.get(1);
		String emptyDir = properties.get(2);
		String systemExecutionTimes = properties.get(3);
		String arrivalRate = properties.get(4);
		String arrivalRateStep = properties.get(5);
		String avgRoutingCost = properties.get(6);
		String avgRoutingCostStep = properties.get(7);
		
		/* Initiallize the parameters of the simulation. */
		SimulationParameters simulationParameters = new SimulationParameters();
		simulationParameters.setParam(parameter);
		simulationParameters.setTestingno(Integer.parseInt(testingno));
		simulationParameters.setEmptyDir(emptyDir);
		simulationParameters.setArrivalRate(Double.parseDouble(arrivalRate));
		simulationParameters.setArrivalRateStep(Double.parseDouble(arrivalRateStep));
		simulationParameters.setAvgRoutingCost(Double.parseDouble(avgRoutingCost));
		simulationParameters.setAvgRoutingCostStep(Double.parseDouble(avgRoutingCostStep));
		int iterations = Integer.parseInt(systemExecutionTimes);
		
		/* Delete any existing files from previous executions. */		
		if(emptyDir.equals("y")){
			FileHandler fhandler = new FileHandler();
			String curDir = System.getProperty("user.dir");
			File csvpath = new File(curDir + "/output/statistics/csvfiles/");
			fhandler.deleteDirectory(csvpath);
			File graphpath = new File(curDir + "/output/statistics/graphs/");
			fhandler.deleteDirectory(graphpath);
		}
		
		int i=0;
		for(i=0; i < iterations; i++){
//			for (int s = 0; s < 100; s++) System.gc();
			
			//execute DataStreamSIM
			execute(simulationParameters);
			
			//modify any simulation parameters needed --one at a time!
			String param = simulationParameters.getParam();
			double arrivalr = simulationParameters.getArrivalRate();
			double arrivalrStep = simulationParameters.getArrivalRateStep();
			double avgroutingc = simulationParameters.getAvgRoutingCost();
			double avgroutingcStep = simulationParameters.getAvgRoutingCostStep();
			
			if(param.equals("ArrivalRate")) {
				//the parameter is the arrival rate
				simulationParameters.setArrivalRate(arrivalr + arrivalrStep);
			} else if(param.equals("AvgRoutingCost")) {
				//the parameter is the average routing cost...
				simulationParameters.setAvgRoutingCost(avgroutingc + avgroutingcStep);
			}//end if-else
		}//end for-loop
		
		System.out.println("Terminate.");
		System.exit(0);
	}//end method main()
	
	public static void execute(SimulationParameters params) {
		DSSystem system = new DSSystem();
		try{
			int i;
			for(i = 0; i < params.getTestingno(); i++){
//				for (int s = 0; s < 10; s++) System.gc();
				
				system = new DSSystem();
				system.initiallize(params);
				system.run();
				
				/* Display at console. */
				system.displayResults();
			}
			/* estimate the averages for the statistics. */
			system.estimateAverages();
			system.reset();
			
			/* Create the charts.*/
			system.showCharts();
			
		}catch(SystemInitilallizationException ex) {
			System.err.println(ex.getMessage());
		}catch(RunSystemException ree) {
			System.err.println(ree.getMessage());
		}finally {
			/* Terminate the execution of the system. */
			system.terminate();
		}//end try-catch
	}//end method execute()
	
	public static void deleteFile(ArrayList<String> files) {
		for(String filename: files) {
			File file = new File(filename);
			if(file.exists()) {
				file.delete();
			}
		}
	}//end method deleteFile()

}//end class Main
