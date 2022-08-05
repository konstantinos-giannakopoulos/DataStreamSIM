package simulation.policies.routing;

import java.util.ArrayList;
import java.util.Random;

import datastorage.structures.Tuple;
import operators.Select;
import simulation.structures.SteM;

/**
 * Scheduling policy for tuple
 * routing used by the adaptive framework.
 * 
 * @version 1.0
 * @author kostas
 */
public class TicketPolicy {
	
	/**
	 * Constructor of class <class>TicketPolicy</class>. 
	 */
	public TicketPolicy() {
		
	}
	
	/**
	 * Hold a lottery amongst the non-join predicates 
	 * that the tuple should be scheduled to.
	 * 
	 * @param currentTuple the tuple that the Eddy routes.
	 * @param predicatesList the list of non-join predicates the tuple can be routed to.
	 * @return The index position of the non-join predicate that wins the lottery.
	 */
	public int predicatesLottery(Tuple currentTuple, ArrayList<Select> predicatesList) {
		int i, sum = 0, tmpPredicate = 0, res = 0, pos = -1;
		Random ran = new Random();
		
		for(Integer predicate : currentTuple.getPredicatesToBeScheduledTo()) {
			tmpPredicate = predicate;
			sum += predicatesList.get(tmpPredicate).getTicket().getNumOfTickets();
		}
		//find the predicate that wins
		if(sum == 0) {
			pos = 0;
			return pos;
		}
		res = ran.nextInt(sum) + 1;
		for(i=0; i< currentTuple.getPredicatesToBeScheduledTo().size(); i++) {
			tmpPredicate = currentTuple.getPredicatesToBeScheduledTo().get(i);
			if(res <= predicatesList.get(tmpPredicate).getTicket().getNumOfTickets()) {
				pos = i;
				break;
			} else {
				res -= predicatesList.get(tmpPredicate).getTicket().getNumOfTickets();
			}
		}//end for-loop
		return pos;
	}//end of method predicatesLottery()
	
	/**
	 * Hold a lottery amongst the SteMs that the tuple should be scheduled to.
	 * 
	 * @param currentTuple the tuple that the Eddy routes.
	 * @param stemList the list of SteMs the tuple can be routed to for probe
	 * @return The index position of the SteM that wins the lottery.
	 */
	public int stemsLottery(Tuple currentTuple, ArrayList<SteM> stemList) {
		int i, sum = 0, tmpStem = 0, res = 0, pos = -1;
		Random ran = new Random();

		for(Integer stem : currentTuple.getSteMsForProbe()) {
			tmpStem = stem;
			sum += stemList.get(tmpStem).getTicket().getNumOfTickets();
		}
		//find the SteM that wins
		if(sum == 0) {
			pos = 0;
			return pos;
		}
		res = ran.nextInt(sum) + 1;
		for(i=0; i< currentTuple.getSteMsForProbe().size(); i++) {
			tmpStem = currentTuple.getSteMsForProbe().get(i);
			if(res <= stemList.get(tmpStem).getTicket().getNumOfTickets()) {
				pos = i;
				break;
			} else {
				res -= stemList.get(tmpStem).getTicket().getNumOfTickets();
			}
		}//end for-loop
		
		return pos;
	}//end of method stemsLottery()
	
}//end of class TicketPolicy
