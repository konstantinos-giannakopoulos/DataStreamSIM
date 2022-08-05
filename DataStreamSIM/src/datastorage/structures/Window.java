package datastorage.structures;

import java.util.ArrayList;


/**
 * The implementation of a tumbling window for stream processing.
 * 
 * @version 1.0
 * @author kostas
 */
public class Window {
	
	private String wstream;
	
	/* The size of the time-window */
	private int windowSize;
	
	/* The sliding interval (step) of the sliding window */
	private int stepInterval;
	
	/* The actual data */
	private ArrayList<Tuple> tupleList; 
	
	/**
	 * Constructor of class <class>Window</class>.
	 */
	public Window() {
		this.tupleList = new ArrayList<Tuple>();
		this.windowSize = 0;
		this.stepInterval = 0;
	}//end constructor TimeWindow()
	
	/**
	 * Constructor of class <class>Window</class>.
	 */
	public Window(int windonwSize, int stepInterval) {
		this.tupleList = new ArrayList<Tuple>();
		this.windowSize = windonwSize;
		this.stepInterval = stepInterval;
	}//end constructor Window()
	
	public void setwStream(String str) {
		this.wstream = str;
	}
	
	public String getwStream() {
		return this.wstream;
	}
	
	public void setWindowSize(int size) {
		this.windowSize = size;
	}
	
	public int getWindowSize() {
		return this.windowSize;
	}
	
	public void setStepInterval(int interval) {
		this.stepInterval = interval;
	}
	
	public int getStepInterval() {
		return this.stepInterval;
	}
	
	public ArrayList<Tuple> getTupleList() {
		return this.tupleList;
	}
	
	public void parseWinInfodata(String infodata) {
		String[] fields = infodata.split(" ");
		setwStream(fields[1]);
		setWindowSize(Integer.parseInt(fields[2]));
	}
	
	/**
	 * Check if the window has room to accept more tuples.
	 * 
	 * @return <code>true</code> if it has room, <code>false</code> otherwise.
	 */
	public boolean hasRoom() {
		if(tupleList.size() < windowSize)
			return true;
		return false;
	}//end method hasRoom()
	
	public int noEmptySlots() {
		return windowSize - tupleList.size();
	}
	
	public void addTuple(Tuple tuple) {
		this.tupleList.add(tuple);
	}
	
	/**
	 * Expire invalid tuples from the window.
	 */
	public void expireTuples() {
		
	}//end method expireTuples()

	/**
     * Textual representation.
	 */
	@Override
	public String toString() {
		return "Window [wstream=" + wstream + ", windowSize=" + windowSize
				+ ", stepInterval=" + stepInterval + ", tupleList=" + tupleList
				+ "]";
	}

}//end class Window
