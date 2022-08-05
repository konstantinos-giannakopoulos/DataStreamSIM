package datastorage.structures;

import java.util.ArrayList;

/**
 * The implementation of the sink for collecting the results.
 * 
 * @version 1.0
 * @author kostas
 *
 */
public class Sink {

	private ArrayList<String> output;
	
	/**
	 * Constructor of class <code>Sink</code>
	 */
	public Sink() {
		this.output = new ArrayList<String>();
	}//end constructor Sink()

	public ArrayList<String> getOutput() {
		return output;
	}

	public void setOoutput(ArrayList<String> output) {
		this.output = output;
	}

	/**
     * Textual representation.
	 */
	@Override
	public String toString() {
		return "Sink [result=" + output + "]";
	}
	
}//end class Sink
