package datastorage.structures;

import java.util.ArrayList;

/**
 * The implementation of a tuple.
 * 
 * @version 1.0
 * @author kostas
 */
public class Tuple {
	
	/* The timestamp of the tuple. */
	private double timestamp;
	
	/* The name of the stream. */
	private String stream;
	
	/* The actual data of the tuple */
	private ArrayList<String> tupleData;
	
	/* Info for scheduling tuple amongst SteMs and predicates*/
	private ArrayList<Integer> SteMsToBeStoredTo;
	private ArrayList<Integer> SteMsForProbe;
	private ArrayList<Integer> predicatesToBeScheduledTo;
	
	private ArrayList<Integer> SteMsAlreadyBeenTo;
	private ArrayList<Integer> predicatessAlreadyBeenTo;
	
	private boolean doneSteMs;
	private boolean donePredicates;
	
	/* Indicates the joined tuples. */
	private ArrayList<String> arderivesFrom;
	
	private double tupleRouteCost;
	private double tupleStoreCost;
	private double tupleComputationalCost;
	
	/**
	 * Constructor of class <class>Tuple</class>.
	 */
	public Tuple() {
		this.timestamp = 0;
		this.stream = new String();
		this.tupleData = new ArrayList<String>();
		this.SteMsToBeStoredTo = new ArrayList<Integer>();
		this.SteMsForProbe = new ArrayList<Integer>();
		this.predicatesToBeScheduledTo = new ArrayList<Integer>();
		this.SteMsAlreadyBeenTo = new ArrayList<Integer>();
		this.predicatessAlreadyBeenTo = new ArrayList<Integer>();
		this.donePredicates = false;
		this.doneSteMs = false;
		this.arderivesFrom = new ArrayList<String>();
		
		this.tupleRouteCost = 0;
		this.tupleStoreCost = 0;
		this.tupleComputationalCost = 0;
	}//end constructor Tuple()
	
	public void setStream(String s) {
		this.stream = s;
	}//end method setStream()
	
	public String getStream() {
		return this.stream;
	}//end method getStream()
	
	public void setTupleData(String[] str) {
		int i;
		for(i=1; i<str.length;i++) {
			this.tupleData.add(str[i]);
		}
	}//end method setTupleData()
	
	public ArrayList<String> getTupleData() {
		return tupleData;
	}//end method getTupleData()

	public void setTimestamp(double ts) {
		this.timestamp = ts;
	}//end method setTupleData()
	
	public double getTimestamp() {
		return this.timestamp;
	}//end method getTimestamp()
	
	public ArrayList<Integer> getSteMsToBeStoredTo() {
		return SteMsToBeStoredTo;
	}

	public void setSteMsToBeStoredTo(ArrayList<Integer> steMsToBeScheduledTo) {
		SteMsToBeStoredTo = steMsToBeScheduledTo;
	}
	
	public void addSteMsToBeStoredTo(int operatorPos) {
		this.SteMsToBeStoredTo.add(operatorPos);
	}
	
	public void removeSteMsToBeStoredTo(int value) {
		int i;
		for(i=0; i<SteMsToBeStoredTo.size(); i++) {
			if(SteMsToBeStoredTo.get(i) == value) {
				SteMsToBeStoredTo.remove(i);
				break;
			}
		}
	}//end method removeSteMsToBeScheduledTo()
	
	public ArrayList<Integer> getSteMsForProbe() {
		return SteMsForProbe;
	}

	public void setSteMsForProbe(ArrayList<Integer> steMsForProbe) {
		SteMsForProbe = steMsForProbe;
	}
	
	public void removeSteMsForProbe(Integer value) {
		int i;
		for(i=0; i<SteMsForProbe.size(); i++) {
			if(SteMsForProbe.get(i) == value) {
				SteMsForProbe.remove(i);
				break;
			}
		}
	}//end method removeSteMsForProbe()

	public void joinListsSteMsToBeScheduledTo(ArrayList<Integer> list1, ArrayList<Integer> list2) {
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		tmp.addAll(list1);
		tmp.addAll(list2);
		this.SteMsToBeStoredTo = tmp;
	}

	public ArrayList<Integer> getPredicatesToBeScheduledTo() {
		return predicatesToBeScheduledTo;
	}

	public void setPredicatesToBeScheduledTo(
			ArrayList<Integer> predicatesToBeScheduledTo) {
		this.predicatesToBeScheduledTo = predicatesToBeScheduledTo;
	}

	public void addPredicatesToBeScheduledTo(int operatorPos) {
		this.predicatesToBeScheduledTo.add(operatorPos);
	}
	
	public void removePredicatesToBeScheduledTo(int value) {
		int i;
		for(i=0; i<predicatesToBeScheduledTo.size(); i++) {
			if(predicatesToBeScheduledTo.get(i) == value) {
				predicatesToBeScheduledTo.remove(i);
				break;
			}
		}
	}//end method removePredicatesToBeScheduledTo()
	
	public void joinListsPredicatesToBeScheduledTo(ArrayList<Integer> list1, ArrayList<Integer> list2) {
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		tmp.addAll(list1);
		tmp.addAll(list2);
		this.predicatesToBeScheduledTo = tmp;
	}

	public ArrayList<String> getarderivesFrom() {
		return arderivesFrom;
	}

	public void setarderivesFrom(ArrayList<String> arderivesFrom) {
		this.arderivesFrom = arderivesFrom;
	}
	
	public ArrayList<Integer> getSteMsAlreadyBeenTo() {
		return SteMsAlreadyBeenTo;
	}
	
	public void addSteMsAlreadyBeenTo(int val) {
		SteMsAlreadyBeenTo.add(val);
	}

	public void setSteMsAlreadyBeenTo(ArrayList<Integer> steMsAlreadyBeenTo) {
		SteMsAlreadyBeenTo = steMsAlreadyBeenTo;
	}
	
	public ArrayList<Integer> getPredicatesAlreadyBeenTo() {
		return predicatessAlreadyBeenTo;
	}

	public void setPredicatesAlreadyBeenTo(
			ArrayList<Integer> predicatessAlreadyBeenTo) {
		this.predicatessAlreadyBeenTo = predicatessAlreadyBeenTo;
	}

	public void setDoneSteMs(boolean bool) {
		this.doneSteMs = bool;
	}

	public boolean getDoneSteMs() {
		return doneSteMs;
	}
	
	public double getTupleRouteCost() {
		return tupleRouteCost;
	}

	public void setTupleRouteCost(double tupleRouteCost) {
		this.tupleRouteCost = tupleRouteCost;
	}

	public double getTupleStoreCost() {
		return tupleStoreCost;
	}

	public void setTupleStoreCost(double tupleStoreCost) {
		this.tupleStoreCost = tupleStoreCost;
	}

	public double getTupleComputationalCost() {
		return tupleComputationalCost;
	}

	public void setTupleComputationalCost(double tupleComputationalCost) {
		this.tupleComputationalCost = tupleComputationalCost;
	}
	
	public void incTupleStoreCost(double val) {
		this.tupleStoreCost += val;
	}
	
	public void incTupleRouteCost(double val) {
		this.tupleRouteCost += val;
	}
	
	public void incTupleComputationalCost(double val) {
		this.tupleComputationalCost += val;
	}

	public void setDonePredicates(boolean bool) {
		this.donePredicates = bool;
	}
	
	public boolean getDonePredicates() {
		return donePredicates;
	}

	/**
	 * When done processing with predicates, 
	 * change the value of the <it>donePredicates</it>
	 * variable.
	 */
	public void changeDonePredicates() {
		this.donePredicates = true;
	}
	
	/**
	 * When done processing with SteMs, 
	 * change the value of the <it>doneSteMs</it>
	 * variable.
	 */
	public void changeDoneSteMs() {
		this.doneSteMs = true;
	}
	
	public boolean predicatesIsDone() {
		if(donePredicates)
			return true;
		return false;
	}
	
	public boolean SteMsIsDone() {
		if(doneSteMs)
			return true;
		return false;
	}

	public void addarderivesFrom(String str) {
		this.arderivesFrom.add(str);
	}
	
	public void removearderivesFrom(int index) {
		this.arderivesFrom.remove(index);
	}

	/**
	 * Create a new tuple. Used when parsing the dataset.
	 * @param fields
	 */
	public void create(String[] fields) {
		setStream(fields[0]);
		setTupleData(fields);
	}//end method createTuple()
	
	/**
     * Textual representation.
	 */
	@Override
	public String toString() {
		return "Tuple [timestamp=" + timestamp + ", stream=" + stream
				+ ", tupleData=" + tupleData + ", SteMsToBeStoredTo="
				+ SteMsToBeStoredTo + ", SteMsForProbe=" + SteMsForProbe
				+ ", predicatesToBeScheduledTo=" + predicatesToBeScheduledTo
				+ ", SteMsAlreadyBeenTo=" + SteMsAlreadyBeenTo
				+ ", predicatessAlreadyBeenTo=" + predicatessAlreadyBeenTo
				+ ", doneSteMs=" + doneSteMs + ", donePredicates="
				+ donePredicates + ", arderivesFrom=" + arderivesFrom
				+ ", tupleRouteCost=" + tupleRouteCost + ", tupleStoreCost="
				+ tupleStoreCost + ", tupleComputationalCost="
				+ tupleComputationalCost + "]";
	}
	
}//end class Tuple
