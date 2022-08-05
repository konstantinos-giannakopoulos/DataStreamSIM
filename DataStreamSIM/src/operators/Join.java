package operators;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;


import simulation.core.Clock;
import simulation.structures.SteM;
import datastorage.catalog.Catalog;
import datastorage.catalog.CatalogJoinBookkeepingInfo;
import datastorage.catalog.CatalogStream;
import datastorage.structures.Tuple;

/**
 * The implementation of the join operator.
 * 
 * @version 1.0
 * @author kostas
 */
public class Join extends Operator {
	
	/* The left operand. */
    private String jleftStream;
    
    /* The left operand. */
    private String jrightStream;
	
    /* The attribute on which join is performed. */
	private String jattribute;
	
	/**
	 * Constructor of class <class>Join</class>.
	 */
	public Join() {
		super();
		this.jleftStream = new String();
		this.jrightStream = new String();
		this.jattribute = new String();
	}//end constructor Join()

	public String getjleftStream() {
		return jleftStream;
	}

	public void setjleftStream(String jleftStream) {
		this.jleftStream = jleftStream;
	}

	public String getjrightStream() {
		return jrightStream;
	}

	public void setjrightStream(String jrightStream) {
		this.jrightStream = jrightStream;
	}

	public String getjattribute() {
		return jattribute;
	}

	public void setjattribute(String jattribute) {
		this.jattribute = jattribute;
	}
	
	public void removeSteMsAlreadyBeenTo(Tuple curTuple, ArrayList<Integer> tmp) {
		tmp.removeAll(curTuple.getSteMsAlreadyBeenTo());
	}//end method removeSteMsAlreadyBeenTo

	/**
	 * Perform the join of two tuples.
	 * 
	 * @param leftTuple the left tuple of the join.
	 * @param rightTuple the right tuple of the join.
	 * @param catalog the catalog.
	 * @return The result of the join.
	 */
	public Tuple createJoinedTuple(Tuple leftTuple, Tuple rightTuple, Catalog catalog, double clockTime) {
		Tuple tmp = new Tuple();
		ArrayList<String> tmpTupleData = new ArrayList<String>();
		
		/* For not joining the same tuple. */
		if( leftTuple.getarderivesFrom().contains(rightTuple.getStream()) ) {
			return leftTuple;
		}
		/* Set the timestamp. */
//		tmp.setTimestamp(leftTuple.getTimestamp());
		tmp.setTimestamp((int) clockTime);
		
		/* Set streamName */
		tmp.setStream(leftTuple.getStream());
		
		/* Set tupleData */
		createJoinedTupleSetTupleData(tmp, tmpTupleData, leftTuple, rightTuple);
		
		/* Set SteMsAlreadyBeenTo */
		createJoinedTupleSteMsAlreadyBeenTo(tmp, leftTuple);
		
		/* set predicatesAlreadyBeenTo */
//		tmp.setPredicatesAlreadyBeenTo(leftTuple.getPredicatesAlreadyBeenTo());
		createJoinedTuplePredicatesAlreadyBeenTo(tmp, leftTuple);
		
		/*set predicatesToBeScheduledTo */
		createJoinedTuplePredicatesToBeScheduledTo(tmp, leftTuple, rightTuple);
//		tmp.getPredicatesToBeScheduledTo().addAll(leftTuple.getPredicatesToBeScheduledTo());
//		tmp.getPredicatesToBeScheduledTo().addAll(rightTuple.getPredicatesToBeScheduledTo());
		
		/* Reset done values for SteMs and predicates */
		createJoinedTupleResetDoneValues(tmp, leftTuple);
		
		/* Set SteMsForProbe */
		createJoinedTupleSetSteMsForProbe(tmp, leftTuple, rightTuple, catalog);
		
		/* Set derivesFrom */
		createJoinedTupleSetDerivesFrom(tmp, leftTuple, rightTuple);
		
		/* Set costs */
		createJoinedTupleSetCosts(tmp, leftTuple);
		
		return tmp;
	}//end method createJoinedTuple()
	
	@SuppressWarnings("unchecked")
	public void createJoinedTuplePredicatesAlreadyBeenTo(Tuple tmp, Tuple leftTuple) {
		ArrayList<Integer> tmpPredicatesAlreadyBeenTo = new ArrayList<Integer>();
		tmpPredicatesAlreadyBeenTo = (ArrayList<Integer>) leftTuple.getPredicatesAlreadyBeenTo().clone();
		
		tmp.setPredicatesAlreadyBeenTo(tmpPredicatesAlreadyBeenTo);
	}//end method createJoinedTuplePredicatesAlreadyBeenTo()
	
	public void createJoinedTuplePredicatesToBeScheduledTo(Tuple tmp, Tuple leftTuple, Tuple rightTuple) {
		tmp.getPredicatesToBeScheduledTo().addAll(leftTuple.getPredicatesToBeScheduledTo());
		tmp.getPredicatesToBeScheduledTo().addAll(rightTuple.getPredicatesToBeScheduledTo());
	}//end method createJoinedTuplePredicatesToBeScheduledTo()
	
	@SuppressWarnings("unchecked")
	public void createJoinedTupleSteMsAlreadyBeenTo(Tuple tmp, Tuple leftTuple) {
		ArrayList<Integer> tmpSteMsAlreadyBeenTo = new ArrayList<Integer>();
		tmpSteMsAlreadyBeenTo = (ArrayList<Integer>) leftTuple.getSteMsAlreadyBeenTo().clone();
		
		tmp.setSteMsAlreadyBeenTo(tmpSteMsAlreadyBeenTo);
	}//end method createJoinedTupleSteMsAlreadyBeenTo()
	
	public void createJoinedTupleSetTupleData(Tuple tmp, ArrayList<String> tmpTupleData, Tuple leftTuple, Tuple rightTuple) {
		tmpTupleData.addAll(leftTuple.getTupleData());
		tmpTupleData.addAll(rightTuple.getTupleData());
		tmp.getTupleData().addAll(tmpTupleData);
	}//end method createJoinedTupleSetTupleData()
	
	public void createJoinedTupleResetDoneValues(Tuple tmp, Tuple leftTuple) {
		if(leftTuple.getDonePredicates() && tmp.getPredicatesToBeScheduledTo().isEmpty())
			tmp.setDonePredicates(true);
		else
			tmp.setDonePredicates(false);
		
		if(leftTuple.getDoneSteMs() && tmp.getSteMsForProbe().isEmpty())
			tmp.setDoneSteMs(true);
		else
			tmp.setDoneSteMs(false);
	}//end method createJoinedTupleResetDoneValues()
	
	@SuppressWarnings("unchecked")
	public void createJoinedTupleSetSteMsForProbe(Tuple tmp, Tuple leftTuple, Tuple rightTuple, Catalog catalog) {
		int i;
		for(CatalogStream cat  : catalog.getCatalogStreams()) {
//		for(i=0; i< catalog.getCatalogStreams().size(); i++) {
			ArrayList<Integer> tmpSteMProbe1 = new ArrayList<Integer>();
			ArrayList<Integer> tmpSteMProbe2 = new ArrayList<Integer>();
			if( rightTuple.getStream().equals(cat.getStreamName()) ) {
				
				tmpSteMProbe2 = (ArrayList<Integer>) cat.getSteMsToProbe().clone();
				tmpSteMProbe1 = (ArrayList<Integer>) leftTuple.getSteMsForProbe().clone();
				@SuppressWarnings("rawtypes")
				HashSet<Integer> set = new HashSet();
				set.addAll(tmpSteMProbe1);
				set.addAll(tmpSteMProbe2);
				tmp.getSteMsForProbe().addAll(set);
				
				/* Remove SteMs already been to */
				tmp.getSteMsForProbe().removeAll(leftTuple.getSteMsAlreadyBeenTo());
				break;
			}
		}//end for-loop
	}//end method createJoinedTupleSetSteMsForProbe()
	
	public void createJoinedTupleSetDerivesFrom(Tuple tmp, Tuple leftTuple, Tuple rightTuple) {
		if(leftTuple.getarderivesFrom().isEmpty()) {
			tmp.getarderivesFrom().add(leftTuple.getStream());
			tmp.getarderivesFrom().add(rightTuple.getStream());
		} else {
			tmp.getarderivesFrom().addAll(leftTuple.getarderivesFrom());
			tmp.getarderivesFrom().add(rightTuple.getStream());
		}
	}//end method createJoinedTupleSetDerivesFrom()
	
	public void createJoinedTupleSetCosts(Tuple tmp, Tuple leftTuple) {
		tmp.setTupleStoreCost(leftTuple.getTupleStoreCost());
		tmp.setTupleRouteCost(leftTuple.getTupleRouteCost());
		tmp.setTupleComputationalCost(leftTuple.getTupleComputationalCost());
	}//end method createJoinedTupleSetCosts()
	
	/**
	 * Perform the join evaluation.
	 * 
	 * @param tuple the current processed tuple.
	 * @param leftPos the position of the attribute the join is performed on.
	 * @param curSteM the SteM in which the processed tuple is probed for matches.
	 * @param catalog the catalog of the system.
	 * @return The list of the matches found in the SteM.
	 */
	public ArrayList<Tuple> evaluate(Tuple tuple, int leftPos, SteM curSteM, Catalog catalog, double clockTime) {
		ArrayList<Tuple> stemTupleList = new ArrayList<Tuple>();//the matches in the SteM.
		ArrayList<Tuple> tupleList = new ArrayList<Tuple>();
		Tuple newTuple = new Tuple();
		String key = tuple.getTupleData().get(leftPos);
		if( curSteM.getTupleData().get(key) != null ) {
			stemTupleList.addAll(curSteM.getTuple(key));
			if( !stemTupleList.isEmpty() ) {
				int i;
				for( i=0; i< stemTupleList.size(); i++ ) {
					newTuple = createJoinedTuple(tuple, stemTupleList.get(i), catalog, clockTime);
					if( !tupleList.contains(newTuple) ) {//to be checked
						tupleList.add(newTuple);
					}
				}
				return tupleList;
			} else {
				//do-nothing
				return null;
			}//end if-else
		}
		return null;
	}//end method evaluate()
	
	/**
	 * Perform the join evaluation.
	 * 
	 * @param tuple the current processed tuple.
	 * @param curSteM the SteM in which the processed tuple is probed for matches.
	 * @param catalog the catalog of the system.
	 * @return The list of the matches found in the SteM.
	 */
	public ArrayList<Tuple> evaluate(Tuple tuple, SteM curSteM, Catalog catalog, double clockTime) {
		ArrayList<Tuple> stemTupleList = new ArrayList<Tuple>();
		ArrayList<Tuple> tupleList = new ArrayList<Tuple>();
		Tuple newTuple = new Tuple();
		stemTupleList = curSteM.retrieveAll();
		if( !stemTupleList.isEmpty() ) {
			int i;
			for( i=0; i< stemTupleList.size(); i++ ) {
				newTuple = createJoinedTuple(tuple, stemTupleList.get(i), catalog, clockTime);
				if( !tupleList.contains(newTuple) ) {//to be checked
					tupleList.add(newTuple);
				}
			}
			return tupleList;
		} else {
			//do-nothing
			return null;
		}
	}//end method evaluate()
	
	/**
	 * Get info for the join from the join-catalog.
	 * 
	 * @param leftTuple 
	 * @param rightTuple 
	 * @param attribute 
	 * @param catalog 
	 * @return The info.
	 */
	public CatalogJoinBookkeepingInfo[] loadJoinBkkInfo(Tuple leftTuple, Tuple rightTuple, String attribute, Catalog catalog) {
		CatalogJoinBookkeepingInfo[] tmp;
		tmp = new CatalogJoinBookkeepingInfo[2];
		
		Iterator<CatalogJoinBookkeepingInfo> iterator = catalog.getCatalogJoinBkkInfoList().iterator();
		while(iterator.hasNext()) {
			CatalogJoinBookkeepingInfo element = iterator.next();
			if( (element.getStream().equals(leftTuple.getStream())) && 
					(element.getAttributeName().equals(attribute)) ) {
				tmp[0] = new CatalogJoinBookkeepingInfo();
			} else if( (element.getStream().equals(rightTuple.getStream())) && 
					(element.getAttributeName().equals(attribute)) ) {
				tmp[1] = new CatalogJoinBookkeepingInfo();
			}
		}//end while -- bkk info loaded.
		
		return tmp;
	}//end method loadJoinBkkInfo()
	
	@Override
	public void parseOpInfodata() {
		String infodata = getOpInfodata();
		String[] fields = infodata.split(" ");
		
		setjleftStream(fields[1]);
		setjrightStream(fields[2]);
		setjattribute(fields[3]);
	}
	
	@Override
	public void setPriority(float priority) {
		this.priority = priority;
	}

	@Override
	public float getPriority() {
		return this.priority;
	}

	/**
     * Textual representation.
	 */
	@Override
	public String toString() {
		return "Join [jleftStream=" + jleftStream + ", jrightStream="
				+ jrightStream + ", jattribute=" + jattribute + "]";
	}

}//end class Join
