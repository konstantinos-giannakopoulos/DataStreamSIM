package simulation.statistics;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

import simulation.structures.SteM;
import util.handlefile.FileHandler;

import datastorage.structures.Tuple;

/**
 * The gathering of statistics for evaluation.
 * 
 * @version 1.0
 * @author kostas
 */
public class Statistics {
	
	public String mode;
	
	private FileHandler fileHandler;
	
	/* Cost of scheduling a tuple. */
	private double totalPredicateCost;
	private double totalStoreCost;
	private double totalRouteCost;
	private double totalComputationalCost;
	private double totalProcessingTime;
	
	/* System statistics. */
	private double throughput;
	private double totalResponseTime;
	
	/* Total information about tuples. */
	private int numOfInitialTuples;
	private int numOfInflightTuples;
	private int numOfResultTuples;
	
	/**
	 * Constructor of class <class>Statistics</class>.
	 */
	public Statistics() {
		this.fileHandler = new FileHandler();
		
		this.totalPredicateCost = 0;
		this.totalStoreCost = 0;
		this.totalRouteCost = 0;
		this.totalComputationalCost = 0;
		this.totalProcessingTime = 0;
		
		this.throughput = 0;
		this.totalResponseTime = 0;
		
		this.numOfInitialTuples = 0;
		this.numOfInflightTuples = 0;
		this.numOfResultTuples = 0;
	}//end constructor Statistics()

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public FileHandler getFileHandler() {
		return fileHandler;
	}

	public void setFileHandler(FileHandler fileHandler) {
		this.fileHandler = fileHandler;
	}

	public double getTotalPredicateCost() {
		return totalPredicateCost;
	}

	public void setTotalPredicateCost(double totalPredicateCost) {
		this.totalPredicateCost = totalPredicateCost;
	}

	public double getTotalStoreCost() {
		return totalStoreCost;
	}

	public void setTotalStoreCost(double totalStoreCost) {
		this.totalStoreCost = totalStoreCost;
	}

	public double getTotalRouteCost() {
		return totalRouteCost;
	}

	public void setTotalRouteCost(double totalRouteCost) {
		this.totalRouteCost = totalRouteCost;
	}

	public double getTotalProcessingTime() {
		return totalProcessingTime;
	}

	public void setTotalProcessingTime(double totalProcessingTime) {
		this.totalProcessingTime = totalProcessingTime;
	}

	public double getTotalComputationalCost() {
		return totalComputationalCost;
	}

	public void setTotalComputationalCost(double totalComputationalCost) {
		this.totalComputationalCost = totalComputationalCost;
	}

	public double getThroughput() {
		return throughput;
	}

	public void setThroughput(double throughput) {
		this.throughput = throughput;
	}

	public double getTotalResponseTime() {
		return totalResponseTime;
	}

	public void setTotalResponseTime(double response) {
		this.totalResponseTime = response;
	}

	public int getNumOfInitialTuples() {
		return numOfInitialTuples;
	}

	public void setNumOfInitialTuples(int numOfTuples) {
		this.numOfInitialTuples = numOfTuples;
	}
	
	public int getNumOfInflightTuples() {
		return numOfInflightTuples;
	}

	public void setNumOfInflightTuples(int numOfInflightTuples) {
		this.numOfInflightTuples = numOfInflightTuples;
	}

	public int getNumOfResultTuples() {
		return numOfResultTuples;
	}

	public void setNumOfResultTuples(int numOfResultTuples) {
		this.numOfResultTuples = numOfResultTuples;
	}
	
	//end of getters-setters.
	
	public double calculateThroughput() {
		double res =  getNumOfResultTuples() / getTotalProcessingTime();
		setThroughput(res);
		return res;
	}
	
//	public double calculateStaticFrameworkDeltaTime(double avgArrivalTime) {
////		double res = getTotalProcessingTime() - avgArrivalTime * (getNumOfInitialTuples() - 1);
//		return res;
//	}
	
	public void incTotalResponseTime(double value) {
		setTotalResponseTime(getTotalResponseTime() + value);
	}
	
	public void incTotalPredicateCost(double cost) {
		this.totalPredicateCost += cost;
	}
	
	public void incTotalStoreCost(double cost) {
		this.totalStoreCost += cost;
	}
	
	public void incTotalRouteCost(double cost) {
		this.totalRouteCost += cost;
	}
	
	public void incTotalProcessingTime(double time) {
		this.totalProcessingTime += time;
	}
	
	public void incTotalComputationalCost(double cost) {
		this.totalComputationalCost += cost;
	}

	public void incNumOfInitialTuples() {
		this.numOfInitialTuples++;
	}
	
	public void incNumOfResultTuples() {
		this.numOfResultTuples++;
	}
	
	public void incNumOfInflightTuples() {
		this.numOfInflightTuples++;
	}
	
	public void incNumOfInflightTuples(int val) {
		this.numOfInflightTuples += val;
	}
	
	
	public void resetTupleProcessingTime() {
		
	}
	
//	###########################################################################################
//	###########################################################################################
//	###########################################################################################
//	###########################################################################################
//	###########################################################################################
	
	/*
	 * Methods for the log file.
	 */
	
//	public String logTuplePredicateTime(double time) {
//		String str = "\nTuple Predicate time: " + time;
//		return str;
//	}//end method logTuplePredicateTime()
//	
//	public void printLogFileTuplePredicateTime(double time) {
//		String curDir = System.getProperty("user.dir");
//        String filename = new String(curDir + "/output/log/" + getMode() + "-log.txt");
//        try{    
//	        FileWriter fstream = new FileWriter(filename,true);
//	        BufferedWriter out = new BufferedWriter(fstream);
//
//	        /* The log file. */
//			out.append(logTuplePredicateTime(time));
//			
//			out.close();
//			fstream.close();
//		}catch (Exception e){
//			System.err.println("Error: " + e.getMessage());
//		}//end try-catch
//	}//end method printLogFileTuplePredicateTime()
	
//	public String logTupleStoreTime(double time) {
//		String str = "\nTuple Store time: " + time;
//		return str;
//	}//end method logTupleStoreTime()
//	
//	public void printLogFileTupleStoreTime(double time) {
//		String curDir = System.getProperty("user.dir");
//        String filename = new String(curDir + "/output/log/" + getMode() + "-log.txt");
//        try{
//	        FileWriter fstream = new FileWriter(filename,true);
//	        BufferedWriter out = new BufferedWriter(fstream);
//
//	        /* The log file. */
//			out.append(logTupleStoreTime(time));
//			
//			/* The statistics for the graphs. */
////			updateStoreTimesFile(time);
////			updateTotalStoreTimesFile(getTotalStoreTime());
//
//			out.close();
//			fstream.close();
//		}catch (Exception e){
//			System.err.println("Error: " + e.getMessage());
//		}//end try-catch
//	}//end method printLogFileTupleStoreTime()
	
//	public String logTupleRouteTime(double time) {
//		String str = "\nTuple Route time: " + time;
//		return str;
//	}//end method logTupleProbeTime()
//	
//	public void printLogFileTupleRouteTime(double time) {
//		String curDir = System.getProperty("user.dir");
//        String filename = new String(curDir + "/output/log/" + getMode() + "-log.txt");
//        try{
//	        FileWriter fstream = new FileWriter(filename,true);
//	        BufferedWriter out = new BufferedWriter(fstream);
//
//	        /* The log file. */
//			out.append(logTupleRouteTime(time));
//			
//			/* The statistics for the graphs. */
////			updateRouteTimesFile(time);
////			updateTotalRouteTimesFile(getTotalRouteTime());
//	 
//			out.close();
//			fstream.close();
//		}catch (Exception e){
//			System.err.println("Error: " + e.getMessage());
//		}//end try-catch
//	}//end method printLogFileTupleProbeTime()
	
//	public String logTupleComputationalTime(double time) {
//		String str = "\nTuple Computational time: " + time;
//		return str;
//	}//end method logTupleProbeTime()
//	
//	public void printLogFileTupleComputationalTime(double time) {
//		String curDir = System.getProperty("user.dir");
//        String filename = new String(curDir + "/output/log/" + getMode() + "-log.txt");
//        try{
//	        FileWriter fstream = new FileWriter(filename,true);
//	        BufferedWriter out = new BufferedWriter(fstream);
//
//	        /* The log file. */
//			out.append(logTupleComputationalTime(time));
//			
//			/* The statistics for the graphs. */
//	 
//			out.close();
//			fstream.close();
//		}catch (Exception e){
//			System.err.println("Error: " + e.getMessage());
//		}//end try-catch
//	}//end method printLogFileTupleComputationalTime()
	
//	public String logTupleProccessingTime(double time) {
//		String str = "\nTuple Processing time: " + time;
//		return str;
//	}//end method logTuplePredicateTime()
//	
//	public void printLogFileTupleProccessingTime(double time) {
//		String curDir = System.getProperty("user.dir");
//        String filename = new String(curDir + "/output/log/" + getMode() + "-log.txt");
//        try{
//	        FileWriter fstream = new FileWriter(filename,true);
//	        BufferedWriter out = new BufferedWriter(fstream);
//
//	        /* The log file. */
//			out.append(logTupleProccessingTime(time));
//
//			/* The statistics for the graphs. */
//			updateTotalProcessingTimeFile(getTotalProcessingTime());
//
//			out.close();
//			fstream.close();
//		}catch (Exception e){
//			System.err.println("Error: " + e.getMessage());
//		}//end try-catch
//	}//end method printLogFileTupleProccessingTime()
	
//	public String logThroughput(double throughput) {
//		String str = "\nSystem Throughput: " + throughput;
//		return str;
//	}//end method logThroughput()
//	
//	public void printLogFileThroughput(double throughput) {
//		String curDir = System.getProperty("user.dir");
//        String filename = new String(curDir + "/output/log/" + getMode() + "-log.txt");
//        try{
//	        FileWriter fstream = new FileWriter(filename,true);
//	        BufferedWriter out = new BufferedWriter(fstream);
//
//	        /* The log file. */
//			out.append(logThroughput(throughput));
//			/* The statistics for the graphs. */
//			updateThroughputFile(throughput);
//
//			out.close();
//			fstream.close();
//		}catch (Exception e){
//			System.err.println("Error: " + e.getMessage());
//		}//end try-catch
//	}//end method printLogFileThroughput()
	
	public String logThroughputVsArrivalRateStatistics(double throughput, double avg) {
		String str = "\nSystem Throughput: " + throughput 
						+ " Arrival Rate: " + avg;
		return str;
	}//end method logThroughputVsAvgArrivalTimeStatistics()
	
	public void printLogFileThroughputVsArrivalRateStatistics(double throughput, double avg) {
//		String curDir = System.getProperty("user.dir");
//        String filename = new String(curDir + "/output/log/" + getMode() + "-log.txt");
//        try{
//	        FileWriter fstream = new FileWriter(filename,true);
//	        BufferedWriter out = new BufferedWriter(fstream);
//
//	        /* The log file. */
//	        out.append("\n -|/-|/-|/-|/-|/-|/-|/-\n");
//			out.append(logThroughputVsArrivalRateStatistics(throughput, avg));
			/* The statistics for the graphs. */
			updateThroughputVsArrivalRateStatisticsFile(throughput, avg);

//			out.close();
//			fstream.close();
//		}catch (Exception e){
//			System.err.println("Error: " + e.getMessage());
//		}//end try-catch
	}//end method printLogFileThroughputVsAvgArrivalTimeStatistics()

	public String logThroughputVsAvgRoutingCostStatistics(double throughput, double avg) {
		String str = "\nSystem Throughput: " + throughput 
						+ " Avg Routing Cost: " + avg;
		return str;
	}//end method logThroughputVsAvgRoutingCostStatistics()
	
	public void printLogFileThroughputVsAvgRoutingCostStatistics(double throughput, double avg) {
//		String curDir = System.getProperty("user.dir");
//        String filename = new String(curDir + "/output/log/" + getMode() + "-log.txt");
//        try{
//	        FileWriter fstream = new FileWriter(filename,true);
//	        BufferedWriter out = new BufferedWriter(fstream);
//
//	        /* The log file. */
//	        out.append("\n -|/-|/-|/-|/-|/-|/-|/-\n");
//			out.append(logThroughputVsAvgRoutingCostStatistics(throughput, avg));
			/* The statistics for the graphs. */
			updateThroughputVsAvgRoutingCostStatisticsFile(throughput, avg);

//			out.close();
//			fstream.close();
//		}catch (Exception e){
//			System.err.println("Error: " + e.getMessage());
//		}//end try-catch
	}//end method printLogFileThroughputVsAvgRoutingCostStatistics()
	
	public String logTotalResponseTimeVSArrivalRateStatistics(double response, double avg) {
		String str = "\nTotal Response Time: " + response 
						+ " Arrival Rate: " + avg;
		return str;
	}//end method logTotalResponseTimeVSArrivalRateStatistics()
	
	//not in use
	public void printLogFileTotalResponseTimeVSArrivalRateStatisticsFile(double response, double avg) {
		String curDir = System.getProperty("user.dir");
        String filename = new String(curDir + "/output/log/" + getMode() + "-log.txt");
        try{
	        FileWriter fstream = new FileWriter(filename,true);
	        BufferedWriter out = new BufferedWriter(fstream);

	        /* The log file. */
	        out.append("\n\n -|/-|/-|/-|/-|/-|/-|/-");
			out.append(logTotalResponseTimeVSArrivalRateStatistics(response, avg));
			/* The statistics for the graphs. */
//			updateTotalResponseTimeVSArrivalRateStatisticsFile(error, avg);
			
			out.close();
			fstream.close();
		}catch (Exception e){
			System.err.println("Error: " + e.getMessage());
		}//end try-catch
	}//end method printLogFileTotalResponseTimeVSArrivalRateStatisticsFile()
	
	//not in use
	public String logTotalResponseTimeVSThroughputStatistics(double response, double throughput) {
		String str = "\nStatic Framework Error: " + response 
						+ " System Throughput: " + throughput;
		return str;
	}//end method logTotalResponseTimeVSAvgArrivalTimeStatistics()
	
	//not in use
	public void printLogFileTotalResponseTimeVSThroughputStatisticsFile(double error, double throughput) {
		String curDir = System.getProperty("user.dir");
        String filename = new String(curDir + "/output/log/" + getMode() + "-log.txt");
        try{
	        FileWriter fstream = new FileWriter(filename,true);
	        BufferedWriter out = new BufferedWriter(fstream);

	        /* The log file. */
	        out.append("\n\n -|/-|/-|/-|/-|/-|/-|/-");
			out.append(logTotalResponseTimeVSThroughputStatistics(error, throughput));
			/* The statistics for the graphs. */
//			updateTotalResponseTimeVSThroughputStatisticsFile(error, throughput);
			
			out.close();
			fstream.close();
		}catch (Exception e){
			System.err.println("Error: " + e.getMessage());
		}//end try-catch
	}//end method printLogFileTotalResponseTimeVSAvgArrivalTimeStatisticsFile()

//	//not in use.
//	public void printLogFileCurrentTuple(Tuple curTuple) {
//		String curDir = System.getProperty("user.dir");
//        String filename = new String(curDir + "/output/log/" + getMode() + "-log.txt");
//        try{
//	        FileWriter fstream = new FileWriter(filename,true);
//	        BufferedWriter out = new BufferedWriter(fstream);
//
//	        out.append("\n");
//	        out.append("\t-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-." +
//	        			"-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.\n");
//			out.append("|-->\tCurrent Tuple: " + curTuple.toString());
//			out.append("\n");
//
//			out.close();
//			fstream.close();
//		}catch (Exception e){
//			System.err.println("Error: " + e.getMessage());
//		}//end try-catch
//	}//end method printLogFileCurrentTuple()
	
	public void printEndOfLogFile(ArrayList<SteM> stemList) {
		String curDir = System.getProperty("user.dir");
        String filename = new String(curDir + "/output/log/" + getMode() + "-log.txt");
        try{
	        FileWriter fstream = new FileWriter(filename,true);
	        BufferedWriter out = new BufferedWriter(fstream);
	        
	        out.append("\n");
	        for(SteM stem: stemList){
	        	String stemname = stem.getStream();
	        	String stemattribute = stem.getAttribute();
	        	int tickets = stem.getTicket().getNumOfTickets();
	        	double selectivity = stem.getSelectivity().getResult();
	        	
	        	out.append("\n" + stemname + "\t" + stemattribute + 
	        			"\tTickets: " + tickets + "\tSelectivity: " + selectivity);
	        }
	        
	        out.append("\n");
	        out.append("\n************************************");
	        out.append("\n\t<<-- To wrap it up... -->>");
	        out.append("\nTotal predicate time: " + getTotalPredicateCost());
			out.append("\nTotal store time: " + getTotalStoreCost());
			out.append("\nTotal route time: " + getTotalRouteCost());
			out.append("\nTotal computational time: " + getTotalComputationalCost());
			out.append("\n");
			out.append("\nTotal number of initial tuples: " + getNumOfInitialTuples());
			out.append("\nTotal number of inflight tuples: " + getNumOfInflightTuples());
			out.append("\nTotal number of result tuples: " + getNumOfResultTuples());
			out.append("\nTotal processing time: " + getTotalProcessingTime());
			out.append("\nSystem throughput: " + getThroughput());
			out.append("\n************************************");

			out.close();
			fstream.close();
		}catch (Exception e){
			System.err.println("Error: " + e.getMessage());
		}//end try-catch
	}//end method printEndOfLogFile()
	
//	###########################################################################################
//	###########################################################################################
//	###########################################################################################
//	###########################################################################################
//	###########################################################################################
	
	/*
	 * Methods for the graphs of the simulation
	 */
	
//	public void updateRouteTimesFile(double time) {
//		String curDir = System.getProperty("user.dir");
//		String statsFile = new String(curDir + "/output/statistics/" + getMode() + "-route.csv");
//		
//		getFileHandler().setFilename(statsFile);
//		//X axis, Y axis
//		getFileHandler().updateStatisticsFile(getNumOfInitialTuples(), time);
//	}//end method updateProbeTimesFile()
//	
//	public void updateTotalRouteTimesFile(double time) {
//		String curDir = System.getProperty("user.dir");
//		String statsFile = new String(curDir + "/output/statistics/" + getMode() + "-totalRoute.csv");
//        
//		getFileHandler().setFilename(statsFile);
//		//X axis, Y axis
//		getFileHandler().updateStatisticsFile(getNumOfInitialTuples(), time);
//	}//end method updateProbeTimesFile()
//	
//	public void updateStoreTimesFile(double time) {
//		String curDir = System.getProperty("user.dir");
//		String statsFile = new String(curDir + "/output/statistics/" + getMode() + "-store.csv");
//        
//		getFileHandler().setFilename(statsFile);
//		//X axis, Y axis
//		getFileHandler().updateStatisticsFile(getNumOfInitialTuples(), time);
//	}//end method updateStoreTimesFile()
//	
//	public void updateTotalStoreTimesFile(double time) {
//		String curDir = System.getProperty("user.dir");
//		String statsFile = new String(curDir + "/output/statistics/" + getMode() + "-totalStore.csv");
//        
//		getFileHandler().setFilename(statsFile);
//		//X axis, Y axis
//		getFileHandler().updateStatisticsFile(getNumOfInitialTuples(), time);
//	}//end method updateTotalStoreTimesFile()
	
//	public void updateTotalProcessingTimeFile(double time) {
//		String curDir = System.getProperty("user.dir");
//		String statsFile = new String(curDir + "/output/statistics/" + getMode() + "-totalProcessingTime.csv");
//        
//		getFileHandler().setFilename(statsFile);
//		//X axis, Y axis
//		getFileHandler().updateStatisticsFile(getNumOfInitialTuples(), time);
//	}//end method updateTotalProcessingTimeFile()
	
//	public void updateThroughputFile(double throughput) {
//		String curDir = System.getProperty("user.dir");
//		String statsFile = new String(curDir + "/output/statistics/" + getMode() + "-throughput.csv");
//        
//		getFileHandler().setFilename(statsFile);
//		//X axis, Y axis
//		getFileHandler().updateStatisticsFile(getNumOfInitialTuples(), throughput);
//	}//end method updateThroughputFile()
	
	public void updateThroughputVsArrivalRateStatisticsFile(double throughput, double avg) {
		String curDir = System.getProperty("user.dir");
		String statsFile = new String(curDir + "/output/statistics/csvfiles/" + getMode() + "-throughputVsArrivalRate.csv");
		
        getFileHandler().setFilename(statsFile);
        //X axis, Y axis
        getFileHandler().updateStatisticsFile(avg, throughput);
	}//end method updateThroughputVsAvgArrivalTimeStatisticsFile()
	
	public void updateThroughputVsAvgRoutingCostStatisticsFile(double throughput, double avg) {
		String curDir = System.getProperty("user.dir");
		String statsFile = new String(curDir + "/output/statistics/csvfiles/" + getMode() + "-throughputVsAvgRoutingCost.csv");
		
        getFileHandler().setFilename(statsFile);
        //X axis, Y axis
        getFileHandler().updateStatisticsFile(avg, throughput);
	}//end method updateThroughputVsAvgRoutingCostStatisticsFile()

//	public void updateTotalResponseTimeVSArrivalRateStatisticsFile(double response, double avg) {
//		String curDir = System.getProperty("user.dir");
//		String statsFile = new String(curDir + "/output/statistics/" + getMode() + "-totalResponseTimeVSAArrivalRate.csv");
//		
//        getFileHandler().setFilename(statsFile);
//        //X axis, Y axis
//        getFileHandler().updateStatisticsFile(avg, response);
//	}//end method updateTotalResponseTimeVSAvgArrivalTimeStatisticsFile()
//	
//	public void updateTotalResponseTimeVSThroughputStatisticsFile(double response, double throughput) {
//		String curDir = System.getProperty("user.dir");
//		String statsFile = new String(curDir + "/output/statistics/" + getMode() + "-totalResponseTimeVSThroughput.csv");
//		
//        getFileHandler().setFilename(statsFile);
//        //X axis, Y axis
//        getFileHandler().updateStatisticsFile(response, throughput);
//	}//end method updateTotalResponseTimeVSThroughputStatisticsFile()
	
//	###########################################################################################
//	###########################################################################################
//	###########################################################################################
//	###########################################################################################
//	###########################################################################################
	
	/**
	 * Estimate the average values from the previously 
	 * generated statistic files for multiple executions.
	 */
	public void printStatisticFilesWithAverages() {
		printAverageThroughputVsArrivalRateStatisticsFile();
		printAverageThroughputVsAvgRoutingCost();
	}
	
	public void printAverageThroughputVsArrivalRateStatisticsFile() {
		FileHandler fhandler = new FileHandler();
		ArrayList<String> tokens = new ArrayList<String>();
 		String curDir = System.getProperty("user.dir");
		String filename = new String(curDir + "/output/statistics/csvfiles/" + getMode() + "-throughputVsArrivalRate.csv");
		
		String newfilename = new String(curDir + "/output/statistics/csvfiles/" + getMode() + "-avgThroughputVsArrivalRate.csv");
		tokens = fhandler.csvFileToArrayList(filename);
		fhandler.setFilename(newfilename);
		fhandler.setFirstLine(tokens.get(0) + ','+ "Average" + tokens.get(1));
		//create a new file
		File f = new File(newfilename);
		if (f.exists()) {
			fileHandler.appendStatisticsFile();
		}else{
			fhandler.createStatisticsFile();
		}
		
		fhandler.csvFileFromArrayList(tokens, newfilename);
	}//end method printAverageThroughputVsArrivalRateStatisticsFile()
	
	public void printAverageThroughputVsAvgRoutingCost() {
		FileHandler fhandler = new FileHandler();
		ArrayList<String> tokens = new ArrayList<String>();
 		String curDir = System.getProperty("user.dir");
		String filename = new String(curDir + "/output/statistics/csvfiles/" + getMode() + "-throughputVsAvgRoutingCost.csv");
		
		String newfilename = new String(curDir + "/output/statistics/csvfiles/" + getMode() + "-avgThroughputVsAvgRoutingCost.csv");
		tokens = fhandler.csvFileToArrayList(filename);
		fhandler.setFilename(newfilename);
		fhandler.setFirstLine(tokens.get(0) + ','+ "Average" + tokens.get(1));
		//create a new file
		File f = new File(newfilename);
		if (f.exists()) {
			fileHandler.appendStatisticsFile();
		}else{
			fhandler.createStatisticsFile();
		}
		
		fhandler.csvFileFromArrayList(tokens, newfilename);
	}//end method printAverageThroughputVsAvgRoutingCost()
	
//	###########################################################################################
//	###########################################################################################
//	###########################################################################################
//	###########################################################################################
//	###########################################################################################
	
	public void buildCharts() {
//		/* Approach #1 */
//		Charts charts = new Charts("DataStreamSIM", dataset);
//        charts.pack();
//        RefineryUtilities.centerFrameOnScreen(charts);
//        charts.setVisible(true);
		
		/* Approach #2 */
		try {
			ChartCreator chartCreator = new ChartCreator();
			/* Create the charts for current execution framework */
			chartCreator.showCharts(getMode());		
			/* Compare charts of different execution frameworks */
			chartCreator.compareCharts();
		} catch (IOException e) {
			System.out.println("Statictics.java: chart failure");
			e.printStackTrace();
		}//end try-catch
		
//		/* Approach #3 */
//		HistogramExample he = new HistogramExample();
//		he.showHistogram();
	}//end method buildCharts()
	
//	/**
//     * Creates a sample dataset.
//     * 
//     * @return The dataset.
//     */
//    private CategoryDataset createDataset() {
//        
//        // row keys...
//        final String series1 = "Probe time";
////        final String series2 = "Second";
////        final String series3 = "Third";
//
//        // column keys...
//        final String type1 = "tupleName";
////        final String type2 = "Type 2";
////        final String type3 = "Type 3";
////        final String type4 = "Type 4";
////        final String type5 = "Type 5";
////        final String type6 = "Type 6";
////        final String type7 = "Type 7";
////        final String type8 = "Type 8";
//
//        // create the dataset...
//        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//
////        dataset.addValue(1.0, series1, type1);
////        dataset.addValue(4.0, series1, type2);
////        dataset.addValue(3.0, series1, type3);
////        dataset.addValue(5.0, series1, type4);
////        dataset.addValue(5.0, series1, type5);
////        dataset.addValue(7.0, series1, type6);
////        dataset.addValue(7.0, series1, type7);
////        dataset.addValue(8.0, series1, type8);
//
//        return dataset;
//                
//    }//end method createDataset()
	
	/**
     * Textual representation.
	 */
	@Override
	public String toString() {
		return "Statistics [mode=" + mode + ", fileHandler=" + fileHandler
				+ ", totalPredicateTime=" + totalPredicateCost
				+ ", totalStoreTime=" + totalStoreCost + ", totalRouteTime="
				+ totalRouteCost + ", totalComputationalTime="
				+ totalComputationalCost + ", totalProcessingTime="
				+ totalProcessingTime + ", throughput=" + throughput
				+ ", staticFrameworkError=" + totalResponseTime
				+ ", numOfInitialTuples=" + numOfInitialTuples
				+ ", numOfInflightTuples=" + numOfInflightTuples
				+ ", numOfResultTuples=" + numOfResultTuples + "]";
	}
	
}//end class Statistics
