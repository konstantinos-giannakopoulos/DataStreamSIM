package simulation.statistics;
import java.io.*;
import java.util.Random;
import org.jfree.chart.*;
import org.jfree.data.statistics.*;
import org.jfree.chart.plot.PlotOrientation;

 public class HistogramExample {
	 
       public void showHistogram() {
    	   double[] value = new double[100];
    	   Random generator = new Random();
	       for (int i=1; i < 100; i++) {
		       value[i] = generator.nextDouble();
		       int number = 10;
		       HistogramDataset dataset = new HistogramDataset();
		       dataset.setType(HistogramType.RELATIVE_FREQUENCY);
		       dataset.addSeries("Histogram",value,number);
		       String plotTitle = "Histogram"; 
		       String xaxis = "number";
		       String yaxis = "value"; 
		       PlotOrientation orientation = PlotOrientation.VERTICAL; 
		       boolean show = false; 
		       boolean toolTips = false;
		       boolean urls = false; 
		       JFreeChart chart = ChartFactory.createHistogram(plotTitle, xaxis, yaxis, 
		                dataset, orientation, show, toolTips, urls);
		       
		       int width = 500, height = 300; 
		        try {
		        	String curDir = System.getProperty("user.dir");
		        	String filename = new String(curDir + "/output/histogram.png");
		        	ChartUtilities.saveChartAsPNG(new File(filename), chart, width, height);
		        } 
		        catch (IOException e) {}
	        }
       }
 }