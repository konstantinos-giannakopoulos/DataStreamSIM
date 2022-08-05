package simulation.policies.routing;

import java.util.ArrayList;

import operators.Select;
import simulation.structures.SteM;
import datastorage.structures.Tuple;

/**
 * Scheduling policy for tuple
 * routing used by the static framework.
 * 
 * @version 1.0
 * @author kostas
 * 
 */
public class PriorityPolicy {
	
	/**
	 * Constructor of class <class>PriorityPolicy</class>. 
	 */
	public PriorityPolicy() {
		
	}
	
	/**
	 * Find the non-join predicate with the highest priority.
	 * 
	 * @param currentTuple the tuple that the Eddy routes.
	 * @param predicatesList a list with all the non-join predicates
	 * @return The non-join predicate with the highest priority.
	 */
	public int findPredicateWithHighestPriority(Tuple currentTuple, ArrayList<Select> predicatesList) {
		float max = -1;
		int i, pos = -1;
		
		for(i=0; i< currentTuple.getPredicatesToBeScheduledTo().size(); i++) {
			if(max < predicatesList.get(currentTuple.getPredicatesToBeScheduledTo().get(i)).getPriority()) {
				max = predicatesList.get(currentTuple.getPredicatesToBeScheduledTo().get(i)).getPriority();
				pos = i;
			}
		}//end for-loop
		
		return pos;
	}//end of method findPredicateWithHighestPriority()
	
	/**
	 * Find the SteM with the highest priority.
	 * @param currentTuple the tuple that the Eddy routes.
	 * @param stemsList a list with all the SteMs.
	 * @return The SteM with the highest priority.
	 */
	public int findSteMWithHighestPriority(Tuple currentTuple, ArrayList<SteM> stemsList) {
		float max = -1;
		int i, pos = -1;
		
		for(i=0; i< currentTuple.getSteMsForProbe().size(); i++) {
			if(max < stemsList.get(currentTuple.getSteMsForProbe().get(i)).getPriority()) {
				max = stemsList.get(currentTuple.getSteMsForProbe().get(i)).getPriority();
				pos = i;
			}
		}//end for-loop
		
		return pos;
	}//end of method findSteMWithHighestPriority()
	
}//end class PriorityPolicy
