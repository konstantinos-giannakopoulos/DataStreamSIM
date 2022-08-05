 package simulation.statistics;

import java.io.*;
import java.util.StringTokenizer;

import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.xy.*;
import org.jfree.ui.RefineryUtilities;

import util.exceptions.GraphDataNotFoundException;

/**
 * This class creates an XY chart using the data in gc.csv.
 *
 * @author kostas
 */
public class ChartCreator {

	private static final boolean SHOW_LEGEND = false;
    private static final boolean SHOW_TOOLTIPS = false;
    private static final boolean GENERATE_URLS = false;
    int width = 600, height = 300;
    
//	###########################################################################################
//	###########################################################################################
//	###########################################################################################
//	###########################################################################################
//	###########################################################################################
    
    /*
     * Single charts.
     */
    
    public void singleChart(String csvFile, String pngPath) throws IOException {
        FileReader fr = new FileReader(csvFile);
        BufferedReader br = new BufferedReader(fr);

        // Get the x-axis label from the first token in the first line
        // and the y-axis label from the last token in the first line.
        String line = br.readLine();
        StringTokenizer st = new StringTokenizer(line, ",");
        String xLabel = st.nextToken();
        String yLabel = st.nextToken();
        while (st.hasMoreTokens()) yLabel = st.nextToken();

        String title = yLabel + " by " + xLabel;

        // Get the data to plot from the remaining lines.
        float minY = Float.MAX_VALUE;
        float maxY = -Float.MAX_VALUE;
        XYSeries series = new XYSeries("?");
        while (true) {
            line = br.readLine();
            if (line == null) break;
            st = new StringTokenizer(line, ",");

            // The first token is the x value.
            String xValue = st.nextToken();

            // The last token is the y value.
            String yValue = "";
            while (st.hasMoreTokens()) yValue = st.nextToken();

            float x = Float.parseFloat(xValue);
            float y = Float.parseFloat(yValue);
            series.add(x, y);

            minY = Math.min(y, minY);
            maxY = Math.max(y, maxY);
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
            title, xLabel, yLabel, dataset,
            PlotOrientation.VERTICAL,
            SHOW_LEGEND, SHOW_TOOLTIPS, GENERATE_URLS);

        XYPlot plot = chart.getXYPlot();
        plot.getRangeAxis().setRange(minY, maxY);

        /* Save chart as png */
//        int width = 600, height = 300;
        String filename = new String(pngPath);
        ChartUtilities.saveChartAsPNG(new File(filename), chart, width, height);
    }//end method singleChart()
    
//    public void totalStoreChart(String mode) throws IOException {
//    	String curDir = System.getProperty("user.dir");
//        String csvFile = new String(curDir + "/output/statistics/" + mode + "-totalStore.csv");
//        String pngPath = new String(curDir + "/output/statistics/graphs/" + mode +  "-totalStore.png");
//        
//        singleChart(csvFile, pngPath);
//    }//end method totalStoreChart()
//    
//    public void storeChart(String mode) throws IOException {
//    	String curDir = System.getProperty("user.dir");
//        String csvFile = new String(curDir + "/output/statistics/" + mode + "-store.csv");
//        String pngPath = new String(curDir + "/output/statistics/graphs/" + mode + "-store.png");
//        
//        singleChart(csvFile, pngPath);
//    }//end method storeChart()
    
//    public void totalRouteChart(String mode) throws IOException {
//    	String curDir = System.getProperty("user.dir");
//        String csvFile = new String(curDir + "/output/statistics/" + mode + "-totalRoute.csv");
//        String pngPath = new String(curDir + "/output/statistics/graphs/" + mode + "-totalRoute.png");
//        
//        singleChart(csvFile, pngPath);
//    }//end method totalProbeChart()
//    
//    public void routeChart(String mode) throws IOException {
//    	String curDir = System.getProperty("user.dir");
//        String csvFile = new String(curDir + "/output/statistics/" + mode + "-route.csv");
//        String pngPath = new String(curDir + "/output/statistics/graphs/" + mode + "-route.png");
//        
//        singleChart(csvFile, pngPath);
//    }//end method probeChart()
    
//    public void totalProcessingTimeChart(String mode) throws IOException {
//    	String curDir = System.getProperty("user.dir");
//        String csvFile = new String(curDir + "/output/statistics/" + mode + "-totalProcessingTime.csv");
//        String pngPath = new String(curDir + "/output/statistics/graphs/" + mode + "-totalProcessingTime.png");
//        
//        singleChart(csvFile, pngPath);
//    }//end method totalProcessingTimeChart()
//    
//    public void throughputChart(String mode) throws IOException {
//    	String curDir = System.getProperty("user.dir");
//        String csvFile = new String(curDir + "/output/statistics/" + mode + "-throughput.csv");
//        String pngPath = new String(curDir + "/output/statistics/graphs/" + mode + "-throughput.png");
//        
//        singleChart(csvFile, pngPath);
//    }//end method throughputChart()
    
    public void throughputVsArrivalRateChart(String mode) throws IOException {
    	String curDir = System.getProperty("user.dir");
        String csvFile = new String(curDir + "/output/statistics/csvfiles/" + mode + "-throughputVsArrivalRate.csv");
        String pngPath = new String(curDir + "/output/statistics/graphs/" + mode + "-throughputVsArrivalRate.png");
        
        singleChart(csvFile, pngPath);
    }//end method throughputVsAvgArrivalTimeChart()
    
    public void avgThroughputVsArrivalRateChart(String mode) throws IOException {
    	String curDir = System.getProperty("user.dir");
        String csvFile = new String(curDir + "/output/statistics/csvfiles/" + mode + "-avgThroughputVsArrivalRate.csv");
        String pngPath = new String(curDir + "/output/statistics/graphs/" + mode + "-avgThroughputVsArrivalRate.png");
        
        singleChart(csvFile, pngPath);
    }//end method avgThroughputVsArrivalRateChart()
    
    public void throughputVsAvgRoutingCostChart(String mode) throws IOException {
    	String curDir = System.getProperty("user.dir");
        String csvFile = new String(curDir + "/output/statistics/csvfiles/" + mode + "-throughputVsAvgRoutingCost.csv");
        String pngPath = new String(curDir + "/output/statistics/graphs/" + mode + "-throughputVsAvgRoutingCost.png");
        
        singleChart(csvFile, pngPath);
    }//end method throughputVsAvgRoutingCostChart()
    
    public void avgThroughputVsAvgRoutingCostChart(String mode) throws IOException {
    	String curDir = System.getProperty("user.dir");
        String csvFile = new String(curDir + "/output/statistics/csvfiles/" + mode + "-avgThroughputVsAvgRoutingCost.csv");
        String pngPath = new String(curDir + "/output/statistics/graphs/" + mode + "-avgThroughputVsAvgRoutingCost.png");
        
        singleChart(csvFile, pngPath);
    }//end method avgThroughputVsAvgRoutingCostChart()
    
//    public void totalResponseTimeVSArrivalRateChart(String mode) throws IOException {
//    	String curDir = System.getProperty("user.dir");
//        String csvFile = new String(curDir + "/output/statistics/" + mode + "-totalResponseTimeVSArrivalRate.csv");
//        String pngPath = new String(curDir + "/output/statistics/graphs/" + mode + "-totalResponseTimeVSArrivalRate.png");
//        
//        singleChart(csvFile, pngPath);
//    }//end method totalResponseTimeVSAvgArrivalTimeChart()
    
//    public void totalResponseTimeVSThroughputChart(String mode) throws IOException {
//    	String curDir = System.getProperty("user.dir");
//        String csvFile = new String(curDir + "/output/statistics/" + mode + "-totalResponseTimeVSThroughput.csv");
//        String pngPath = new String(curDir + "/output/statistics/graphs/" + mode + "-totalResponseTimeVSThroughput.png");
//        
//        singleChart(csvFile, pngPath);
//    }//end method totalResponseTimeVSThroughputChart()
    
//	###########################################################################################
//	###########################################################################################
//	###########################################################################################
//	###########################################################################################
//	###########################################################################################
    
    /*
     * Comparison charts.
     */
    
//    public void compareRouteCharts() throws IOException, GraphDataNotFoundException {
//    	String curDir = System.getProperty("user.dir");
//        String filename1 = new String(curDir + "/output/statistics/adaptive-route.csv");
//        String filename2 = new String(curDir + "/output/statistics/static-route.csv");
//        String filename = new String(curDir + "/output/statistics/graphs/compare-route.png");
//    	String title = "Compare Route Time";
//    	if(!filesExist(filename1,filename2)) {
//    		String msg = "Error: Data not found for generating graphs.";
//    		throw new GraphDataNotFoundException(msg);
//    	}
//    	Charts charts = new Charts(title, filename1, filename2, filename);
////    	charts.pack();
////      RefineryUtilities.centerFrameOnScreen(charts);
////      charts.setVisible(true);
//    }//end method compareProbeCharts()
//    
//    public void compareTotalRouteCharts() throws IOException, GraphDataNotFoundException {
//    	String curDir = System.getProperty("user.dir");
//        String filename1 = new String(curDir + "/output/statistics/adaptive-totalRoute.csv");
//        String filename2 = new String(curDir + "/output/statistics/static-totalRoute.csv");
//        String filename = new String(curDir + "/output/statistics/graphs/compare-totalRoute.png");
//    	String title = "Total Route Time Comparison";
//    	if(!filesExist(filename1,filename2)) {
//    		String msg = "Error: Data not found for generating graphs.";
//    		throw new GraphDataNotFoundException(msg);
//    	}
//    	Charts charts = new Charts(title, filename1, filename2, filename);
//    }//end method compareTotalProbeCharts()
    
//    public void compareStoreCharts() throws IOException, GraphDataNotFoundException {
//    	String curDir = System.getProperty("user.dir");
//        String filename1 = new String(curDir + "/output/statistics/adaptive-store.csv");
//        String filename2 = new String(curDir + "/output/statistics/static-store.csv");
//        String filename = new String(curDir + "/output/statistics/graphs/compare-store.png");
//    	String title = "Compare Store Time";
//    	if(!filesExist(filename1,filename2)) {
//    		String msg = "Error: Data not found for generating graphs.";
//    		throw new GraphDataNotFoundException(msg);
//    	}
//    	Charts charts = new Charts(title, filename1, filename2, filename);
//    }//end method compareStoreCharts()
//    
//    public void compareTotalStoreCharts() throws IOException, GraphDataNotFoundException {
//    	String curDir = System.getProperty("user.dir");
//        String filename1 = new String(curDir + "/output/statistics/adaptive-totalStore.csv");
//        String filename2 = new String(curDir + "/output/statistics/static-totalStore.csv");
//        String filename = new String(curDir + "/output/statistics/graphs/compare-totalStore.png");
//    	String title = "Total Store Time Comparison";
//    	if(!filesExist(filename1,filename2)) {
//    		String msg = "Error: Data not found for generating graphs.";
//    		throw new GraphDataNotFoundException(msg);
//    	}
//    	Charts charts = new Charts(title, filename1, filename2, filename);
//    }//end method compareTotalStoreCharts()
    
//	public void compareTotalProcessingTimeCharts() throws IOException, GraphDataNotFoundException {
//    	String curDir = System.getProperty("user.dir");
//        String filename1 = new String(curDir + "/output/statistics/adaptive-totalProcessingTime.csv");
//        String filename2 = new String(curDir + "/output/statistics/static-totalProcessingTime.csv");
//        String filename = new String(curDir + "/output/statistics/graphs/compare-totalProcessingTime.png");
//    	String title = "Total Processing Time Comparison";
//    	if(!filesExist(filename1,filename2)) {
//    		String msg = "Error: Data not found for generating graphs.";
//    		throw new GraphDataNotFoundException(msg);
//    	}
//    	Charts charts = new Charts(title, filename1, filename2, filename);
//    }//end method compareTotalProcessingTimeCharts()
//    
//    public void compareThroughputCharts() throws IOException, GraphDataNotFoundException {
//    	String curDir = System.getProperty("user.dir");
//        String filename1 = new String(curDir + "/output/statistics/adaptive-throughput.csv");
//        String filename2 = new String(curDir + "/output/statistics/static-throughput.csv");
//        String filename = new String(curDir + "/output/statistics/graphs/compare-throughput.png");
//    	String title = "Throughput Comparison";
//    	if(!filesExist(filename1,filename2)) {
//    		String msg = "Error: Data not found for generating graphs.";
//    		throw new GraphDataNotFoundException(msg);
//    	}
//    	Charts charts = new Charts(title, filename1, filename2, filename);
//    }//end method compareThroughputCharts()
    
    public void compareThroughputVsArrivalRateChart() throws IOException, GraphDataNotFoundException{
    	String curDir = System.getProperty("user.dir");
        String filename1 = new String(curDir + "/output/statistics/csvfiles/adaptive-throughputVsArrivalRate.csv");
        String filename2 = new String(curDir + "/output/statistics/csvfiles/static-throughputVsArrivalRate.csv");
        String filename = new String(curDir + "/output/statistics/graphs/compare-throughputVsArrivalRate.png");
    	String title = "Throughput by Arrival Rate";
    	if(!filesExist(filename1,filename2)) {
    		String msg = "Error: Data not found for generating graphs.";
    		throw new GraphDataNotFoundException(msg);
    	}
    	Charts charts = new Charts(title, filename1, filename2, filename);
    }//end method compareThroughputVsAvgArrivalTimeChart()
    
    public void compareAvgThroughputVsArrivalRateChart() throws IOException, GraphDataNotFoundException{
    	String curDir = System.getProperty("user.dir");
        String filename1 = new String(curDir + "/output/statistics/csvfiles/adaptive-avgThroughputVsArrivalRate.csv");
        String filename2 = new String(curDir + "/output/statistics/csvfiles/static-avgThroughputVsArrivalRate.csv");
        String filename = new String(curDir + "/output/statistics/graphs/compare-avgThroughputVsArrivalRate.png");
    	String title = "Average Throughput by Arrival Rate";
    	if(!filesExist(filename1,filename2)) {
    		String msg = "Error: Data not found for generating graphs.";
    		throw new GraphDataNotFoundException(msg);
    	}
    	Charts charts = new Charts(title, filename1, filename2, filename);
    }//end method compareAvgThroughputVsAvgArrivalTimeChart()
    
    public void compareThroughputVsAvgRoutingCostChart() throws IOException, GraphDataNotFoundException{
    	String curDir = System.getProperty("user.dir");
        String filename1 = new String(curDir + "/output/statistics/csvfiles/adaptive-throughputVsAvgRoutingCost.csv");
        String filename2 = new String(curDir + "/output/statistics/csvfiles/static-throughputVsAvgRoutingCost.csv");
        String filename = new String(curDir + "/output/statistics/graphs/compare-throughputVsAvgRoutingCost.png");
    	String title = "Throughput by Avg Routing Cost";
    	if(!filesExist(filename1,filename2)) {
    		String msg = "Error: Data not found for generating graphs.";
    		throw new GraphDataNotFoundException(msg);
    	}
    	Charts charts = new Charts(title, filename1, filename2, filename);
    }//end method compareThroughputVsAvgRoutingCostChart()
    
    public void compareAvgThroughputVsAvgRoutingCostChart() throws IOException, GraphDataNotFoundException{
    	String curDir = System.getProperty("user.dir");
        String filename1 = new String(curDir + "/output/statistics/csvfiles/adaptive-avgThroughputVsAvgRoutingCost.csv");
        String filename2 = new String(curDir + "/output/statistics/csvfiles/static-avgThroughputVsAvgRoutingCost.csv");
        String filename = new String(curDir + "/output/statistics/graphs/compare-avgThroughputVsAvgRoutingCost.png");
    	String title = "Average Throughput by Avg Routing Cost";
    	if(!filesExist(filename1,filename2)) {
    		String msg = "Error: Data not found for generating graphs.";
    		throw new GraphDataNotFoundException(msg);
    	}
    	Charts charts = new Charts(title, filename1, filename2, filename);
    }//end method compareAvgThroughputVsAvgRoutingCostChart()
    			  
    /**
     * Check if the given files exist.
     * 
     * @param filename1 the first file
     * @param filename2 the second file
     * @return <code>true</code> if both files exist, 
     * <code>false</code> otherwise.
     */
    public boolean filesExist(String filename1, String filename2) {
    	boolean exist = true;
    	File file1 = new File(filename1);
    	File file2 = new File(filename2);
    	if(!file1.exists() || !file2.exists()) {
    		exist = false;
    	}
    	return exist;
    }//end method filesExist()
    
//	###########################################################################################
//	###########################################################################################
//	###########################################################################################
//	###########################################################################################
//	###########################################################################################
    
    /**
     * Generate charts that compare both frameworks.
     * 
     * @throws IOException
     */
    public void compareCharts() throws IOException {
    	try {
    		//<-- to be deleted
//	    	compareRouteCharts();
//	    	compareStoreCharts();
//	    	compareTotalRouteCharts();
//	    	compareTotalStoreCharts();
    		//to be deleted -->
    		
//	    	compareTotalProcessingTimeCharts();
//	    	compareThroughputCharts();

//	    	compareThroughputVsArrivalRateChart();
	    	compareAvgThroughputVsArrivalRateChart();
//	    	compareThroughputVsAvgRoutingCostChart();
	    	compareAvgThroughputVsAvgRoutingCostChart();

	    	System.out.println("ChartCreator.java: comparison charts done.");
    	}catch(GraphDataNotFoundException gdnfex) {
    		System.out.println("ChartCreator.java: comparison charts, failure.");
    		System.err.println(gdnfex.getMessage());
    	}//end try-catch
    }//end method compareCharts()

    /**
     * Generate charts for each framework.
     * 
     * @param mode
     * @throws IOException
     */
    public void showCharts(String mode) throws IOException {
    	//<-- to be deleted
//    	routeChart(mode);
//    	storeChart(mode);
//    	totalRouteChart(mode);
//    	totalStoreChart(mode);
    	//to be deleted -->
    	
//    	totalProcessingTimeChart(mode);
//    	throughputChart(mode);
    	
//    	throughputVsArrivalRateChart(mode);
    	avgThroughputVsArrivalRateChart(mode);
//    	throughputVsAvgRoutingCostChart(mode);
    	avgThroughputVsAvgRoutingCostChart(mode);
    	
//    	totalResponseTimeVSAvgArrivalTimeChart(mode);
//    	totalResponseTimeVSThroughputChart(mode);
    	
    	System.out.println("ChartCreator.java: single charts done.");
    }//end method showCharts()
}//end class ChartCreator