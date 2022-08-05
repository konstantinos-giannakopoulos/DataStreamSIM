package operators;

import java.util.ArrayList;
import java.util.Iterator;

import com.sun.tools.example.debug.bdi.EvaluationException;

import predicates.AttributeToValuePredicate;
import util.exceptions.AttributeToValuePredicateException;
import util.exceptions.RoutingToPredicateException;
import datastorage.catalog.Catalog;
import datastorage.structures.Tuple;

/**
 * The implementation of select operator.
 * 
 * @version 1.0
 * @author kostas
 */
public class Select extends Operator {
	
	/* The stream on which the predicate should be checked. */
	private String selstream;
	
	/* The predicate. */
	private AttributeToValuePredicate selpredicate;

	/**
	 * Constructor of class <class>Select</class>.
	 */
	public Select() {
		super();
		this.selstream = new String();
		this.selpredicate = new AttributeToValuePredicate();
	}//end constructor Select()
	
	public void setSelstream(String str) {
		this.selstream = str;
	}
	
	public String getSelstream() {
		return this.selstream;
	}
	
	public AttributeToValuePredicate getSelpredicate() {
		return selpredicate;
	}

	public void setSelpredicate(AttributeToValuePredicate selpredicate) {
		this.selpredicate = selpredicate;
	}
	
	/**
	 * Identify the kind of comparison.
	 * @param tupleValue the left operand
	 * @param symbol the operator
	 * @param value the right operand
	 * @return <code>true</code> if the condition holds, <code>false</code> otherwise.
	 */
	public boolean compare(int tupleValue, String symbol, int value) {
		if(symbol.equals(">")) {
			if(tupleValue > value) return true;
		}
		else if(symbol.equals(">=")) {
			if(tupleValue >= value) return true;
		}
		else if(symbol.equals("=")) {
			if(tupleValue == value) return true;
		}
		else if(symbol.equals("<")) {
			if(tupleValue < value) return true;
		}
		else if(symbol.equals("<=")) {
			if(tupleValue <= value) return true;
		}//end if-else
		return false;	
	}//end method compare()
	
	/**
	 * Find the index position of a given attribute, when the tuple 
	 * derives from two other tuples, ie. has already been in a join.
	 * 
	 * @param pos the index position of the stream in the derivesFrom list
	 * @param res the index position of the attribute in the stream it belongs to.
	 * @return The index position of the attribute in the tuple.
	 */
	public int findAttributePosInCompositeTuple(int pos, int res, Tuple tuple, Catalog catalog) {
		int i;
		for(i = 0; i < pos; i++) {
			String streamname = tuple.getarderivesFrom().get(i);
			res += catalog.findNoAttributeInStream(streamname);
		}//end for-loop
		return res;
	}//end method findAttributePosInCompositeTuple()
	
	/**
	 * In the case the tuple has already been to an index of the
	 * current stream, find the stream in the list of the 
	 * SteMs it has already been to.
	 * 
	 * @param list the list of the SteMs the tuple has already been to.
	 * @param streamname the stream we are looking for in the list.
	 * @return The position in the list.
	 */
	public int findPosOfStreamInTheDerivesFromList(ArrayList<String> list, String streamname) {
		int i, pos = 0;
		for(i=0; i < list.size() - 1; i++) {
			if( !list.get(i).equals(streamname) ) {
				pos++;
			}
		}
		return pos;
	}//end method findPosOfStreamInTheDerivesFromList()

	public int findAttributeIndexInJoin(Tuple tuple, String attribute, Catalog catalog) {
		int pos = -1;
		int index = 0;
		ArrayList<String> list = tuple.getarderivesFrom();
		String streamname = this.getSelstream();
		
		pos = findPosOfStreamInTheDerivesFromList(list, streamname);
		
		index += catalog.findIndexOfAttributeInStream(streamname, attribute);
		
//		for(i=0; i<pos; i++){
//			for(j=0;j<catalog.getCatalogStreams().size(); j++) {
//				if( list.get(i).equals(catalog.getCatalogStreams().get(j).getStreamName()) ) {
//					index += catalog.getCatalogStreams().get(j).getAttributes().size();
//				}
//			}
//		}
		index = findAttributePosInCompositeTuple(pos, index, tuple, catalog);
		
		return index;
	}//end method findAttributeIndexInJoin()
	
	/**
	 * Perform the evaluation of select operator
	 * @param tuple the current tuple
	 * @param predAttrToValue the predicate
	 * @param catalog the system catalog
	 * @return <code>true</code> if the predicate holds, <code>false</code> otherwise.
	 * @throws RoutingToPredicateException 
	 */
	public boolean evaluate(Tuple tuple, AttributeToValuePredicate predAttrToValue, Catalog catalog) throws RoutingToPredicateException {
		boolean bool = false;
		String attribute = predAttrToValue.getAttribute();
		String symbol = predAttrToValue.getSymbol();
		int value = predAttrToValue.getValue();
		int tupleAttributeIndex;
		int tupleAttributeValue;
		
		/* Find pos of attribute in the attributeList of the stream */
		if(tuple.getarderivesFrom().isEmpty()) {
			tupleAttributeIndex = catalog.findIndexOfAttributeInStream(tuple.getStream(), attribute);
		} else {//if the tuple is a join result
			tupleAttributeIndex = findAttributeIndexInJoin(tuple, attribute, catalog);
		}
		
		try {
			tupleAttributeValue = Integer.parseInt(tuple.getTupleData().get(tupleAttributeIndex));
			
			/* Do the comparison */
			bool = compare(tupleAttributeValue, symbol, value);
		}catch (NumberFormatException nfe) {
			nfe.getMessage();
			throw new RoutingToPredicateException("Error: Evaluation of select predicate failure.");
		}
		return bool;
	}//end method evaluate()

	@Override
	public void parseOpInfodata() throws AttributeToValuePredicateException {
		String infodata = getOpInfodata();
		String[] fields = infodata.split(" ");
		
		setSelstream(fields[1]);
		if(fields.length < 5) {
			throw new AttributeToValuePredicateException("Error: Attribute to value predicate, not found.");
			/* Other kind of predicate - not attribute-to-value */
		}

		if (fields[4].matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) { 			
			selpredicate.setAttribute(fields[2]);
			selpredicate.setSymbol(fields[3]);
			selpredicate.setValue(Integer.valueOf(fields[4]));
		} else{
			throw new AttributeToValuePredicateException("Error: Attribute to value predicate, not found.");
			/* Other kind of predicate - not attribute-to-value */
		}
	}//end method parseOpInfodata()
	
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
		return "Select [selstream=" + selstream + ", selpredicate="
				+ selpredicate + ", priority=" + priority + "]";
	}

}//end class Select
