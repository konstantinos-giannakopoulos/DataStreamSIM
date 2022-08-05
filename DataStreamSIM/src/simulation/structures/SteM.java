package simulation.structures;

import java.util.ArrayList;
import java.util.TreeMap;

import datastorage.structures.Tuple;

/**
 * The implementation of a SteM. 
 * 
 * @version 1.0
 * @author kostas
 */
public class SteM {
	
	/* Priority for scheduling policy. */
	private float priority;
	
	private Ticket ticket;
	
	private Selectivity selectivity;
	
	/* The name of the stream. */
	private String stream;
	
	/* The name of the attribute. */
	private String attribute;
	
	/* Use of TreeMap as an indexing technique. */
	private TreeMap< String, ArrayList<Tuple> > tupleData;

	/**
	 * Constructor of class <class>SteM</class>.
	 */
	public SteM() {
		this.priority = -1;
		this.ticket = new Ticket();
		this.selectivity = new Selectivity();
		
		this.stream = new String();
		this.attribute = new String();
		this.tupleData = new TreeMap< String, ArrayList<Tuple> >();
	}//end constructor SteM()

	public float getPriority() {
		return priority;
	}

	public void setPriority(float priority) {
		this.priority = priority;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public Selectivity getSelectivity() {
		return selectivity;
	}

	public void setSelectivity(Selectivity selectivity) {
		this.selectivity = selectivity;
	}

	public String getStream() {
		return stream;
	}

	public void setStream(String stream) {
		this.stream = stream;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public TreeMap< String, ArrayList<Tuple> > getTupleData() {
		return tupleData;
	}

	public void setTupleData(TreeMap< String, ArrayList<Tuple> > tupleData) {
		this.tupleData = tupleData;
	}
	
	public void putTuple(String key, Tuple tuple) {	
		ArrayList<Tuple> tmp = new ArrayList<Tuple>();
		tmp = this.tupleData.get(key);
		if( tmp != null) {
			tmp.add(tuple);
		} else {
			tmp = new ArrayList<Tuple>();
			tmp.add(tuple);
			this.tupleData.put(key, tmp);
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Tuple> retrieveAll() {
		ArrayList<Tuple> data = new ArrayList<Tuple>();
		ArrayList<String> keys = new ArrayList<String>(this.getTupleData().keySet());

	    for (String key : keys) {
	    	data.addAll((ArrayList<Tuple>) this.getTupleData().get(key).clone());
	    }
		return data;
	}//end method retrieveAll()
	
	public ArrayList<Tuple> getTuple(String key) {
		return this.tupleData.get(key);
	}
	
	public void resetSteM() {
		this.tupleData = new TreeMap< String, ArrayList<Tuple> >();
	}//end method resetSteM

	/**
     * Textual representation.
	 */
	@Override
	public String toString() {
		return "SteM [priority=" + priority + ", ticket=" + ticket
				+ ", stream=" + stream + ", attribute=" + attribute
				+ ", tupleData=" + tupleData + "]";
	}
}//end class SteM
