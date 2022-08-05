package simulation.statistics;

//package org.jfree.chart.demo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.StringTokenizer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * A simple demonstration application showing how to create a line chart using data from a
 * {@link CategoryDataset}.
 * 
 * @author kostas
 */
@SuppressWarnings("serial")
public class Charts extends ApplicationFrame {

//	 private static final boolean SHOW_LEGEND = false;
//	 private static final boolean SHOW_TOOLTIPS = false;
//	 private static final boolean GENERATE_URLS = false;
	
	private String xLabel;
	private String yLabel;
	    
    /**
     * Creates a new demo.
     *
     * @param title  the frame title.
     * @throws IOException 
     */
    public Charts(final String title, String filename1, 
    				String filename2, String filename) throws IOException {
        super(title);
        final CategoryDataset dataset = createDataset(filename1, filename2);
        final JFreeChart chart = createChart(title, dataset);
        File chartFile = new File(filename);
        OutputStream out = new BufferedOutputStream(new FileOutputStream(chartFile));
        float quality = 1;
        
        /* Save chart as png */
        int width = 700, height = 350;
//        ChartUtilities.saveChartAsPNG(chartFile, chart, width, height);
        ChartUtilities.writeChartAsPNG(out, chart, width, height, true, 0);
//        ChartUtilities.saveChartAsJPEG(chartFile, quality, chart, width, height);
    }

    /**
     * Creates a sample dataset.
     * 
     * @return The dataset.
     * @throws IOException 
     */
    private CategoryDataset createDataset(String filename1, String filename2) throws IOException {
        
        // row keys...
    	final String series1 = "Adaptive";
        final String series2 = "Static";
//        final String series3 = "Third";

        // column keys...
//        final String type1 = "Type 1";
//        final String type2 = "Type 2";
//        final String type3 = "Type 3";
//        final String type4 = "Type 4";
//        final String type5 = "Type 5";
//        final String type6 = "Type 6";
//        final String type7 = "Type 7";
//        final String type8 = "Type 8";

        // create the dataset...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        
        FileReader fr = new FileReader(filename1);
        BufferedReader br = new BufferedReader(fr);

        // Get the x-axis label from the first token in the first line
        // and the y-axis label from the last token in the first line.
        String line = br.readLine();
        StringTokenizer st = new StringTokenizer(line, ",");
        xLabel = st.nextToken();
        yLabel = st.nextToken();
        while (st.hasMoreTokens()) yLabel = st.nextToken();

//        String title = yLabel + " by " + xLabel;

        // Get the data to plot from the remaining lines.
        float minY = Float.MAX_VALUE;
        float maxY = -Float.MAX_VALUE;
        while (true) {
            line = br.readLine();
            if (line == null) break;
            st = new StringTokenizer(line, ",");

            // The first token is the x value.
            String xValue = st.nextToken();

            // The last token is the y value.
            String yValue = "";
//            while (st.hasMoreTokens()) yValue = st.nextToken();
            yValue = st.nextToken();

            float x = Float.parseFloat(xValue);
            float y = Float.parseFloat(yValue);
            dataset.addValue(y, series1, xValue);

            minY = Math.min(y, minY);
            maxY = Math.max(y, maxY);
        }
        
        fr = new FileReader(filename2);
        br = new BufferedReader(fr);

        // Get the x-axis label from the first token in the first line
        // and the y-axis label from the last token in the first line.
        line = br.readLine();
        st = new StringTokenizer(line, ",");
        xLabel = st.nextToken();
        yLabel = st.nextToken();
        if(yLabel.equals("Average Throughput")) {
        	yLabel = yLabel + " (tuples/sec)";
        }
        if(xLabel.equals("Arrival Rate")) {
        	xLabel = xLabel + " (tuples/sec)";
        } 
        else if(xLabel.equals("Avg Routing Cost")) {
        	xLabel = xLabel + " (sec)";
        }
//        while (st.hasMoreTokens()) yLabel = st.nextToken();

//        title = yLabel + " by " + xLabel;

        // Get the data to plot from the remaining lines.
        minY = Float.MAX_VALUE;
        maxY = -Float.MAX_VALUE;
        while (true) {
            line = br.readLine();
            if (line == null) break;
            st = new StringTokenizer(line, ",");

            // The first token is the x value.
            String xValue = st.nextToken();

            // The last token is the y value.
            String yValue = "";
//            while (st.hasMoreTokens()) yValue = st.nextToken();
            yValue = st.nextToken();

            float x = Float.parseFloat(xValue);
            float y = Float.parseFloat(yValue);
            dataset.addValue(y, series2, xValue);

            minY = Math.min(y, minY);
            maxY = Math.max(y, maxY);
        }
        
        br.close();
        fr.close();
        
//        dataset.addValue(1.0, series1, type1);
//        dataset.addValue(4.0, series1, type2);
//        dataset.addValue(3.0, series1, type3);
//        dataset.addValue(5.0, series1, type4);
//        dataset.addValue(5.0, series1, type5);
//        dataset.addValue(7.0, series1, type6);
//        dataset.addValue(7.0, series1, type7);
//        dataset.addValue(8.0, series1, type8);

//        dataset.addValue(5.0, series2, type1);
//        dataset.addValue(7.0, series2, type2);
//        dataset.addValue(6.0, series2, type3);
//        dataset.addValue(8.0, series2, type4);
//        dataset.addValue(4.0, series2, type5);
//        dataset.addValue(4.0, series2, type6);
//        dataset.addValue(2.0, series2, type7);
//        dataset.addValue(1.0, series2, type8);
//
//        dataset.addValue(4.0, series3, type1);
//        dataset.addValue(3.0, series3, type2);
//        dataset.addValue(2.0, series3, type3);
//        dataset.addValue(3.0, series3, type4);
//        dataset.addValue(6.0, series3, type5);
//        dataset.addValue(3.0, series3, type6);
//        dataset.addValue(4.0, series3, type7);
//        dataset.addValue(3.0, series3, type8);

        return dataset;
                
    }//end method createDataset()
    
    /**
     * Creates a sample chart.
     * 
     * @param dataset  a dataset.
     * 
     * @return The chart.
     */
    private JFreeChart createChart(String title, final CategoryDataset dataset) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createLineChart(
            title,       				// chart title
           // "Type",                    	// domain axis label
           // "Value",                   	// range axis label
            xLabel,
            yLabel,
            dataset,                   	// data
            PlotOrientation.VERTICAL,  	// orientation
            true,                      	// include legend
            true,                      	// tooltips
            false                      	// urls
        );

//        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
//        final StandardLegend legend = (StandardLegend) chart.getLegend();
//        legend.setDisplaySeriesShapes(true);
//        legend.setShapeScaleX(1.5);
//        legend.setShapeScaleY(1.5);
//        legend.setDisplaySeriesLines(true);

        
//        //START OPTIONAL OPTIMIZATION.
        chart.setBackgroundPaint(Color.white);
//
        final CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.white);
//        plot.setRangeGridlinePaint(Color.gray);
//
//        // customise the range axis...
//        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
//        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
//        rangeAxis.setAutoRangeIncludesZero(true);
//        
        // customise the renderer...
        final LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
//        renderer.setDrawShapes(true);
//
        renderer.setSeriesStroke(
            0, new BasicStroke(
                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                1.0f, new float[] {6.0f, 5.0f}, 0.0f
            )
        );
//        renderer.setSeriesStroke(
//            1, new BasicStroke(
//                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
//                1.0f, new float[] {5.0f, 6.0f}, 0.0f
//            )
//        );
//        renderer.setSeriesStroke(
//            2, new BasicStroke(
//                2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
//                1.0f, new float[] {2.0f, 6.0f}, 0.0f
//            )
//        );
        // OPTIONAL CUSTOMISATION COMPLETED.
        
        return chart;
    }
    
}//end class Charts

