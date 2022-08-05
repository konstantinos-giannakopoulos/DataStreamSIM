package dssystem;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import simulation.brain.Engine;
import simulation.core.Clock;
import simulation.core.EventList;
import simulation.core.Plan;
import simulation.core.SimulationParameters;
import util.exceptions.ParseExecutionPlanException;
import util.exceptions.LoadExecutionPlanException;
import util.exceptions.RunEngineException;
import util.exceptions.RunSystemException;
import util.exceptions.SystemInitilallizationException;
import util.handlefile.FileHandler;
import util.handlefile.ReadXMLFile;
import datastorage.catalog.Catalog;
import datastorage.catalog.CatalogStream;
import datastorage.structures.*;

/**
 * The implementation of the system.
 * 
 * @version 1.0
 * @author kostas
 */
public class DSSystem {
	
	enum status { RUNNING, STOP };
	
	boolean running;
	
	private FileHandler fileHandler;
	
	/* The system mode from the configuration file. */
	private String mode;
	
	private Clock clock;
	
	private EventList eventList;
	
	private Catalog catalog;
	
	private Plan plan;
	
	private Engine engine;
	
	private SimulationParameters simulationParameters;
	
	/* The result tuples. */
	private ArrayList<Sink> results;
	
	/**
	 * Constructor of class <code>DSSystem</code>
	 */
	public DSSystem() {
		this.running = false;
		this.fileHandler = new FileHandler();
		
		this.clock = new Clock();
		this.eventList = new EventList();
		this.catalog = new Catalog();
		this.plan = new Plan();
		this.engine = new Engine();
		this.simulationParameters = new SimulationParameters();
		
		this.results = new ArrayList<Sink>();
	}//end constructor DSSystem()
	
	public FileHandler getFileHandler() {
		return fileHandler;
	}

	public void setFileHandler(FileHandler fileHandler) {
		this.fileHandler = fileHandler;
	}

	public Catalog getCatalog() {
		return this.catalog;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Clock getClock() {
		return clock;
	}

	public void setClock(Clock clock) {
		this.clock = clock;
	}

	public SimulationParameters getSimulationParameters() {
		return simulationParameters;
	}

	public void setSimulationParameters(SimulationParameters simulationParameters) {
		this.simulationParameters = simulationParameters;
	}

	public ArrayList<Sink> getResults() {
		return results;
	}

	public void setResults(ArrayList<Sink> output) {
		this.results = output;
	}
	
	/**
	 * Display the output to the console.
	 */
	public void displayResults() {
		System.out.println("\n--> The output of the simulation for the " + getMode() + " framework is:");
//		System.out.println("no of tuples in the output: " + results.size());
//		System.out.println("Output :" + getResults().toString());
	}//end method displayResults()

	/**
	 * Check if the system is running.
	 * 
	 * @return <code>true</code>, if the system is running, <code>false</code> otherwise.
	 */
	public boolean isRunning() {
		if ( this.running == true )
			return true;
		else
			return false;
	}//end method isRunning;
	
	/**
	 * Terminate the system.
	 */
	public void terminate() {
		this.running = false;
		System.out.println("*** The program execution is completed!!! ***");
				
		/* Call the garbage collector. */
		Runtime r = Runtime.getRuntime();
		r.gc();

//		System.exit(0);
		
	}//end method terminate()
	
	/**
	 * Initiallize the system; the Catalog and the Execution Plan.
	 */
	public void initiallize(SimulationParameters params) throws SystemInitilallizationException {
		/* Load the configuration file. */
		loadConf();
		System.out.println("Configuration file loaded.");
		getClock().initTimePolicy(getMode(), params);
		
		/* Load the simulation parameters. */
		loadSimulationParameters(params);
		
		try {
			/* Load the catalog. */
			loadCatalog();
			System.out.println("Catalog created.");
			
			/* Load the execution plan. */
			loadExecutionPlan();
			System.out.println("Execution Plan loaded.");
		} catch (LoadExecutionPlanException e) {
			System.err.println(e.getMessage());
			throw new SystemInitilallizationException("Error: Initiallization of the system failure.");
		}//end try-catch
			
		System.out.println("System initiallization done.");
	}//end method initSystem();
	
	/**
	 * Start the system; initiallize the engine; 
	 * run the engine; save result tuples to a file.
	 * 
	 * @throws RunSystemException 
	 */
	public void run() throws RunSystemException {
		try{
			this.running = true;
			initFiles();
			
			loadWorkload(); //feed the queue of events
			engine.initiallize(plan, catalog, mode, clock, simulationParameters);
			engine.run(eventList);
			setResults(engine.getResult());
			
			/* Store the output results in a file. */
//			saveResults();//-!- many I/Os
			
			/* Sum-up the log file. */
			engine.terminate();

		}catch(RunEngineException ree) {
			System.err.println(ree.getMessage());
			String errormsg = "Error: Run of the system failure.";
			throw new RunSystemException(errormsg);
		}//end try-catch
		
	}//end method run()
	
	public void reset(){
		FileHandler fhandler = new FileHandler();
		String curDir = System.getProperty("user.dir");
		String filename1 = new String(curDir + "/output/statistics/csvfiles/" + getMode() + "-throughputVsArrivalRate.csv");
		String filename2 = new String(curDir + "/output/statistics/csvfiles/" + getMode() +"-throughputVsAvgRoutingCost.csv");
		
		fhandler.deleteFile(filename1);
		fhandler.deleteFile(filename2);
	}
	
	/**
	 * Show the graphs of the simulation.
	 */
	public void showCharts() {
		getClock().getStatistics().buildCharts();
		System.out.println("DSSystem.java: Graphs are built.");
	}//end method showCharts()
	
	public void estimateAverages() {
		getClock().getStatistics().printStatisticFilesWithAverages();
	}
	
	/**
	 * Load the configuration file.
	 */
	public void loadConf() {
		ArrayList<String> confValues = new ArrayList<String>();
		String curDir = System.getProperty("user.dir");
        String filepath = new String(curDir + "/conf/conf.xml");
		try {
	        ReadXMLFile conf = new ReadXMLFile(filepath);
	        confValues = conf.parseConfFile();
	        setMode(confValues.get(0));
	        clock.setType(confValues.get(1));
		}catch (Exception e){
			System.err.println("Error: " + e.getMessage());
		}//end try-catch
	}//end method loadConf()
	
	/**
	 * Load the parameters of the simulation.
	 * 
	 * @param params
	 */
	public void loadSimulationParameters(SimulationParameters params) {
		setSimulationParameters(params);
	}
	
//	@SuppressWarnings("static-access")
//	public void delayAdaptiveFramework() throws InterruptedException {
//		if(getClock().getType().equals("real")) {
//			Random ran = new Random();
//			int delay = ran.nextInt(2);
//			System.out.println(System.nanoTime());
//			Thread.currentThread().sleep(delay*1000);
//			System.out.println(System.nanoTime());
//		}//end if
//	}
	
	/**
	 * Check if the tuple that is retrieved from the input file
	 * is valid according to the catalog information about 
	 * this stream tuples. If it is not valid, ignore it.
	 * 
	 * @param fields the attributes of the tuple.
	 * @return <code>true</code> if the tuple is valid, <code>false</code> otherwise. 
	 */
	public boolean tupleIsValid(String[] fields) {
		boolean value = true;
		if( getCatalog().findNoAttributeInStream(fields[0]) != fields.length - 1) {
			value = false;
		}
		return value;
	}//end method tupleIsValid()
	
	/**
	 * Load the workload to the simulation. 
	 */
	public void loadWorkload() {
		String curDir = System.getProperty("user.dir");
        String filename = new String(curDir + "/input/dataset.txt");  

		try{
			FileInputStream fstream = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			
			while ( (strLine = br.readLine()) != null ) {
				Tuple tuple = new Tuple();
				String[] fields = strLine.split(",");
				tuple.create(fields);
				double interval = getClock().getTimePolicy().specifyTimeIntervalForIncomingTuple(getSimulationParameters());
				double clock = getClock().getTimePolicy().getSystemClock();
				double value =  interval + clock;
				double ts = value;
				
				//check if tuple is valid -- drop invalid ones.
				if( !tupleIsValid(fields) )
					continue;
				
				//add each tuple to the event list
				eventList.addEvent(tuple, ts); 
				//set the system clock
				getClock().getTimePolicy().setSystemClock(ts);
				
				/* Delay the simulation in purpose to 
				 * assign more realistic timestamps in 
				 * the case of the adaptive framework. */
//				delayAdaptiveFramework();
			}//end while-loop
			
			br.close();
			in.close();
			fstream.close();
//			System.out.println(eventList.toString());//<--! printf 
			System.out.println("Workload loaded.");//<--! printf 
		}catch (Exception e){
			System.err.println("Error: " + e.getMessage());
		}//end try-catch
	}//end method loadWorkload()
	
	/**
	 * Load the catalog into the simulaion.
	 */
	public void loadCatalog() {
		String curDir = System.getProperty("user.dir");
        String filename = new String(curDir + "/input/catalog.txt");
	        
		try{
			FileInputStream fstream = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			
			while ((strLine = br.readLine()) != null) {
				CatalogStream cs = new CatalogStream();
				String[] fields = strLine.split(",");
				cs.create(fields);
				getCatalog().addCatStream(cs);
			}
			
			br.close();
			in.close();
			fstream.close();
//			System.out.println(catalog.toString());//<--! printf 
		}catch (Exception e){
			System.err.println("Error: " + e.getMessage());
		}//end try-catch
	}//end method loadCatalog()
	
	/**
	 * Load the execution plan.
	 * 
	 * @throws LoadExecutionPlanException  
	 */
	public void loadExecutionPlan() throws LoadExecutionPlanException {
		String curDir = System.getProperty("user.dir");
		String filename = new String(curDir + "/input/plan.txt");	       
		
	    try {
			plan.parsePlan(filename);
		} catch (ParseExecutionPlanException e) {
			System.err.println(e.getMessage());
			throw new LoadExecutionPlanException("Loading the execution plan, failed.");
		}//end try-catch
//	    System.out.println(plan.toString()); //<--! printf 
	}//end method loadExecutionPlan()
	
	/**
	 * Save the results into a file.
	 */
	public void saveResults() {
		try{
			String curDir = System.getProperty("user.dir");
	        String filename = new String(curDir + "/output/results.txt");
	        
			FileOutputStream fstream = new FileOutputStream(filename);
			Writer out = new OutputStreamWriter(fstream);
			
			int i;
			for(i=0; i<getResults().size(); i++) {
				out.write(getResults().get(i).getOutput().toString());
				out.write("\n");
			}

			out.close();
			fstream.close();
			System.out.println("Results printed to a file.");//<--! printf 
		}catch (Exception e){
			System.err.println("Error: " + e.getMessage());
		}//end try-catch
	}//end method saveResults()
	
	/**
	 * Initiallize the files.
	 */
	public void initFiles() {
		String param = simulationParameters.getParam();
		/* The log file. */
		createLogFile();
		
		/* Files that collect the statistics. */
//		createRouteStatisticsFile();
//		createStoreStatisticsFile();
//		createTotalRouteStatisticsFile();
//		createTotalStoreStatisticsFile();
//		createTotalProcessingTimeStatisticsFile();
		
//		createThroughputStatisticsFile();
		
//		if(param.equals("ArrivalRate")) {
			createThroughputVsArrivalRateStatisticsFile();
//		} else if(param.equals("AvgRoutingCost")) {
			createThroughputVsAvgRoutingCostStatisticsFile();
//		}//end if-else
		
//		createTotalResponseTimeVSAvgArrivalTimeStatisticsFile();
//		createTotalResponseTimeVSThroughputStatisticsFile();
	}//end method initFiles()
	
	/**
	 * Create a log file.
	 */
	public void createLogFile() {
		String curDir = System.getProperty("user.dir");
        String filename = new String(curDir + "/output/log/" + getMode() + "-log.txt");
        
        getFileHandler().setFilename(filename);
        getFileHandler().createLogFile(getMode());
	}//end method createLogFile()
	
//	/**
//	 * Create a file that contains the 
//	 * statistics for the total store times of the tuples.
//	 */
//	public void createTotalStoreStatisticsFile() {
//		String curDir = System.getProperty("user.dir");
//        String filename = new String(curDir + "/output/statistics/" + getMode() +"-totalStore.csv");
//        
//        getFileHandler().setFilename(filename);
//        //X axis, Y axis
//        getFileHandler().setFirstLine("Tuple, Total Store Time till now\n");
//        getFileHandler().createStatisticsFile();
//	}//end method createTotalStoreStatisticsFile();
	
//	/**
//	 * Create a file with the statistics of the store time of each tuple.
//	 */
//	public void createStoreStatisticsFile() {
//		String curDir = System.getProperty("user.dir");
//        String filename = new String(curDir + "/output/statistics/" + getMode() +"-store.csv");
//        
//        getFileHandler().setFilename(filename);
//        //X axis, Y axis
//        getFileHandler().setFirstLine("Tuple, Store Time\n");
//        getFileHandler().createStatisticsFile();
//	}//end method createStoreStatisticsFile();
	
//	/**
//	 * Create a file that contains the statistics for the total route time of the tuples.
//	 */
//	public void createTotalRouteStatisticsFile() {
//		String curDir = System.getProperty("user.dir");
//        String filename = new String(curDir + "/output/statistics/" + getMode() +"-totalRoute.csv");
//   
//        getFileHandler().setFilename(filename);
//        //X axis, Y axis
//        getFileHandler().setFirstLine("Tuple, Total Route Time till now\n");
//        getFileHandler().createStatisticsFile();
//	}//end method createTotalRouteStatisticsFile()

//	/**
//	 * Create a file with the statistics of the route time of each tuple.
//	 */
//	public void createRouteStatisticsFile() {
//		String curDir = System.getProperty("user.dir");
//        String filename = new String(curDir + "/output/statistics/" + getMode() +"-route.csv");
//        
//        getFileHandler().setFilename(filename);
//      	//X axis, Y axis
//        getFileHandler().setFirstLine("Tuple, Route Time\n");
//        getFileHandler().createStatisticsFile();
//	}//end method createRouteStatisticsFile()
	
	/**
	 * Create a file that contains the statistics for the total processing time of the tuples.
	 */
	public void createTotalProcessingTimeStatisticsFile() {
		String curDir = System.getProperty("user.dir");
        String filename = new String(curDir + "/output/statistics/csvfiles/" + getMode() +"-totalProcessingTime.csv");
        
        getFileHandler().setFilename(filename);
        //X axis, Y axis
        getFileHandler().setFirstLine("Tuple, Total Processing Time till now\n");
        getFileHandler().createStatisticsFile();
	}//end method createTotalProcessingTimeStatisticsFile()
	
	/**
	 * Create a file that contains the statistics for the throughput of the system.
	 */
	public void createThroughputStatisticsFile() {
		String curDir = System.getProperty("user.dir");
        String filename = new String(curDir + "/output/statistics/csvfiles/" + getMode() +"-throughput.csv");
        
        getFileHandler().setFilename(filename);
        //X axis, Y axis
        getFileHandler().setFirstLine("Tuple, Throughput\n");
        getFileHandler().createStatisticsFile();
	}//end method createThroughputStatisticsFile()
	
	/**
	 * Create a file that contains the statistics 
	 * for the throughput vs. the tuple arrival rate.
	 */
	public void createThroughputVsArrivalRateStatisticsFile() {
		String curDir = System.getProperty("user.dir");
        String filename = new String(curDir + "/output/statistics/csvfiles/" + getMode() +"-throughputVsArrivalRate.csv");
        
        getFileHandler().setFilename(filename);
        //X axis, Y axis
        getFileHandler().setFirstLine("Arrival Rate, Throughput\n");
        getFileHandler().appendStatisticsFile();
	}//end method createThroughputVsArrivalRateStatisticsFile()
	
	/**
	 * Create a file that contains the statistics 
	 * for the throughput vs. the average routing cost.
	 */
	public void createThroughputVsAvgRoutingCostStatisticsFile() {
		String curDir = System.getProperty("user.dir");
        String filename = new String(curDir + "/output/statistics/csvfiles/" + getMode() +"-throughputVsAvgRoutingCost.csv");
        
        getFileHandler().setFilename(filename);
        //X axis, Y axis
        getFileHandler().setFirstLine("Avg Routing Cost, Throughput\n");
        getFileHandler().appendStatisticsFile();
	}//end method createThroughputVsAvgRoutingTimeStatisticsFile()
	
//	/**
//	 * Create a file that contains the statistics 
//	 * for the total response time vs. the average tuple arrival time,
//	 * in the static framework.
//	 */
//	public void createTotalResponseTimeVSArrivalRateStatisticsFile() {
//		String curDir = System.getProperty("user.dir");
//        String filename = new String(curDir + "/output/statistics/" + getMode() +"-totalResponseTimeVSArrivalRate.csv");
//        
//        getFileHandler().setFilename(filename);
//        //X axis, Y axis
//        getFileHandler().setFirstLine("Arrival Rate, Total Response Time\n");
//        getFileHandler().appendStatisticsFile();
//	}//end method createTotalResponseTimeVSArrivalRateStatisticsFile()
	
//	public void createTotalResponseTimeVSThroughputStatisticsFile() {
//		String curDir = System.getProperty("user.dir");
//        String filename = new String(curDir + "/output/statistics/" + getMode() +"-totalResponseTimeVSThroughput.csv");
//        
//        getFileHandler().setFilename(filename);
//        //X axis, Y axis
//        getFileHandler().setFirstLine("Total Response Time, Throughput\n");
//        getFileHandler().appendStatisticsFile();
//	}//end method createTotalResponseTimeVSThroughputStatisticsFile()
	
	/**
     * Textual representation.
	 */
	@Override
	public String toString() {
		return "DSSystem [running=" + running + ", fileHandler=" + fileHandler
				+ ", mode=" + mode + ", clock=" + clock + ", eventList="
				+ eventList + ", catalog=" + catalog + ", plan=" + plan
				+ ", engine=" + engine + ", results=" + results + "]";
	}
	
}//end class System
