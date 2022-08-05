package util.handlefile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * The handling of the files.
 * 
 * @version 1.0
 * @author kostas
 */
public class FileHandler {
	
	private String filename;
	
	private String firstLine;
	
	/**
	 * Constructor of class <class>FileHandler</class>.
	 */
	public FileHandler(String fname) {
		this.filename = fname;
	}//end constructor FileHandler()
	
	/**
	 * Constructor of class <class>FileHandler</class>.
	 */
	public FileHandler() {
		
	}//end constructor FileHandler()
	
	public String getFirstLine() {
		return firstLine;
	}

	public void setFirstLine(String firstLine) {
		this.firstLine = firstLine;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	/**
	 * Delete a file.
	 * 
	 * @param filename the file.
	 */
	public void deleteFile(String filename) {
		File f = new File(filename);
		if (f.exists()){
			f.delete();
		}//end if
	}//end method deleteFile()
	
	/**
	 * Empty a directory. Delete its files.
	 * 
	 * @param path the directory
	 */
	public void deleteDirectory(File path) {
		if( path.exists() ) {
			File[] files = path.listFiles();
			for(int i=0; i<files.length; i++) {
				if(files[i].isDirectory()) {
					deleteDirectory(files[i]);
				}else{
		           files[i].delete();
		        }//end if-else
		     }//end for-loop
		}//end if
	}//end method deleteDirectory()
	
	/**
	 * Create the log file.
	 * 
	 * @param strMode 
	 */
	public void createLogFile(String strMode) {
		try{
			FileOutputStream fstream = new FileOutputStream(filename);
			Writer out = new OutputStreamWriter(fstream);
			out.write("Statistics for the " + strMode + " model: \n");
			out.write("Tuple info: ");

			out.close();
			fstream.close();
			System.out.println("Log file created.");//<--! printf 
		}catch (Exception e){
			System.err.println("Error: " + e.getMessage());
		}//end try-catch
	}//end method createLogFile()
	
	/**
	 * Create a file for collecting statistics. 
	 * It re-writes an existing file.
	 */
	public void createStatisticsFile() {
		try{
			FileOutputStream fstream = new FileOutputStream(filename);
			Writer out = new OutputStreamWriter(fstream);

			out.write(getFirstLine());
					
			out.close();
			fstream.close();
		}catch (Exception e){
			System.err.println("Error: " + e.getMessage());
		}//end try-catch
	}//end method createStatisticsFile();
	
	/**
	 * Append an existing file with statistics data.
	 */
	public void appendStatisticsFile() {
		File file=new File(filename);
		boolean exists = file.exists();
		if (!exists) {//create the file if it doesn't exist
			try {
				FileOutputStream fstream = new FileOutputStream(filename);
				Writer out = new OutputStreamWriter(fstream);
	
				out.write(getFirstLine());
				
				out.close();
				fstream.close();
			}catch (Exception e){
				System.err.println("Error: " + e.getMessage());
			}//end try-catch
		} else {
			//do-nothing
		}//end if-else
	}//end method appendStatisticsFile()
	
	public void updateStatisticsFile(double Xaxis, double Yaxis) {
		try {
			FileWriter statsfstream = new FileWriter(getFilename(),true);
			BufferedWriter statsout = new BufferedWriter(statsfstream);
			
			statsout.append(Xaxis + "," + Yaxis + "\n");
			
			statsout.close();
			statsfstream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}//end try-catch
	}//end method updateStatisticsFile()
	
	/**
	 * Extract the contents of an csv file to an ArrayList.
	 * 
	 * @param filename the csv file
	 * @return The list of the elements of the given csv file.
	 */
	public ArrayList<String> csvFileToArrayList(String filename) {
		ArrayList<String> tokens = new ArrayList<String>();
		try
		{
			//create BufferedReader to read csv file
			FileReader freader = new FileReader(filename);
			BufferedReader br = new BufferedReader(freader);
			String strLine;
			StringTokenizer st;;
 
			//read comma separated file line by line
			while( (strLine = br.readLine()) != null){
				st = new StringTokenizer(strLine, ",");
				while(st.hasMoreTokens()) {
					tokens.add(st.nextToken());
				}//end inner while-loop --line read.
			}//end while-loop --csv file read.
		}catch(Exception e){
			System.out.println("Exception while reading csv file: " + e);			
		}//end try-catch
		return tokens;
	}//end method csvFileToArrayList()
	
	/**
	 * Create a csv file from an arraylist
	 * 
	 * @param tokens the list of tokens
	 * @param filename The cvs file.
	 */
	public void csvFileFromArrayList(ArrayList<String> tokens, String filename){
		try
		{
			FileWriter writer = new FileWriter(getFilename(),true);
			BufferedWriter out = new BufferedWriter(writer);
			
			out.append("\n");
			out.append(tokens.get(2));
			out.append(",");
			
			double sum = 0, avg = 0;
			int i,s = 0;
			for(i=3; i<tokens.size(); i = i+2){
				sum += Double.parseDouble(tokens.get(i));
				s++;
			}
			avg = sum/s;	
			String stravg = Double.toString(avg);
			
			out.append(stravg);
//			out.append('\n');
			
			out.close();
		}catch(IOException e){
		     e.printStackTrace();
		}//end try-catch
	}//end method csvFileFromArrayList()

	/**
     * Textual representation.
	 */
	@Override
	public String toString() {
		return "HFile [filename=" + filename + "]";
	}

}//end class FileHandler
