package simulation.brain;

import java.util.ArrayList;
import java.util.Random;

import operators.Join;
import operators.Select;

import datastorage.catalog.Catalog;
import datastorage.catalog.CatalogJoinBookkeepingInfo;
import datastorage.catalog.CatalogStream;
import datastorage.structures.Tuple;
import datastorage.structures.Window;

import simulation.core.Clock;
import simulation.core.EventList;
import simulation.core.Plan;
import simulation.core.SimulationParameters;
import simulation.policies.routing.PriorityPolicy;
import simulation.policies.routing.TicketPolicy;
import simulation.structures.SteM;
import util.constantsdef.ConstantsDefinition;
import util.exceptions.RoutingToPredicateException;
import util.exceptions.SchedulingTupleException;
import util.exceptions.RunEngineException;
import util.exceptions.RunSchedulerException;
import util.exceptions.SystemInitilallizationException;

/**
 * The actual implementation of per-tuple scheduling
 * for the adaptive model.
 * It is an Eddy implementation with SteMs.
 * 
 * @version 1.0
 * @author kostas
 */
public class Scheduler implements iDataStreamSIM {
	
	/* The mode of the system from the configuration file. */
	private String mode;
	
	/* The current mode of the system. */
	private String currentMode;
	
	private Clock clock;
	
	/* The catalog. */
	private Catalog catalog;
	
	/* The execution plan. */
	private Plan plan;
	
	private SimulationParameters simulationParameters;
	
	/*The windows that have to exist according to the execution plan.*/
	private ArrayList<Window> windowList;
	
	/*The windows that have to exist according to the execution plan.*/
	private ArrayList<Window> originalWindowList;
	
	private ArrayList<SteM> stemsList;
	private ArrayList<Select> predicatesList;
	
	/* The multiple join results from probing a SteM. */
	private ArrayList<Tuple> joinResultTupleList;
	
	/* The current tuple that is processed. */
	private Tuple currentTuple;
	
	/* The current SteM */
	private SteM currentSteM;
	
	/* The result of the scheduling. */
	private ArrayList<Tuple> output;
	
	/* The routing policies the simulation supports. */
	private TicketPolicy ticketPolicy;
	private PriorityPolicy priorityPolicy;
	
	/**
	 * Constructor of class <class>Scheduler</class>.
	 */
	public Scheduler() {
		this.clock = new Clock();
		
		this.catalog = new Catalog();
		this.plan = new Plan();
		this.simulationParameters = new SimulationParameters();
		this.windowList = new ArrayList<Window>();
		this.originalWindowList = new ArrayList<Window>();
		
		this.stemsList = new ArrayList<SteM>();
		this.predicatesList = new ArrayList<Select>();
		this.joinResultTupleList = new ArrayList<Tuple>();
		this.currentTuple = new Tuple();
		this.currentSteM = new SteM();
		
		this.output = new ArrayList<Tuple>();
		
		this.ticketPolicy = new TicketPolicy();
		this.priorityPolicy = new PriorityPolicy();
	}//end constructor Scheduler()
	
	/**
	 * Constructor of class <class>Scheduler</class>.
	 */
	@SuppressWarnings("unchecked")
	public Scheduler(Clock clock, Plan plan, ArrayList<Window> arWinLst, 
						ArrayList<SteM> arSteMsLst, ArrayList<Select> arPredicateLst) {
		this.clock = clock;
		this.catalog = new Catalog();
		this.plan = plan;
		this.originalWindowList = arWinLst; 
		this.windowList = (ArrayList<Window>) this.originalWindowList.clone();
		this.stemsList = arSteMsLst;
		this.predicatesList = arPredicateLst;
		this.currentTuple = new Tuple();
		this.output = new ArrayList<Tuple>();
		this.ticketPolicy = new TicketPolicy();
		this.priorityPolicy = new PriorityPolicy();
	}//end constructor Scheduler()

	public Clock getClock() {
		return clock;
	}

	public void setClock(Clock clock) {
		this.clock = clock;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getCurrentMode() {
		return currentMode;
	}

	public void setCurrentMode(String currentMode) {
		this.currentMode = currentMode;
	}

	public Catalog getCatalog() {
		return catalog;
	}

	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}
	
	public SimulationParameters getSimulationParameters() {
		return simulationParameters;
	}

	public void setSimulationParameters(SimulationParameters simulationParameters) {
		this.simulationParameters = simulationParameters;
	}

	public ArrayList<Window> getWindowList() {
		return windowList;
	}

	public void setWindowList(ArrayList<Window> windowList) {
		this.windowList = windowList;
	}
	
	public ArrayList<Window> getOriginalWindowList() {
		return originalWindowList;
	}

	public void setOriginalWindowList(ArrayList<Window> originalWindowList) {
		this.originalWindowList = originalWindowList;
	}

	public void removearwindowList(int index) {
		this.windowList.remove(index);
	}
	
	public int getarwindowsListSize() {
		return this.windowList.size();
	}

	public ArrayList<SteM> getStemsList() {
		return stemsList;
	}

	public void setStemsList(ArrayList<SteM> arSteMs) {
		this.stemsList = arSteMs;
	}
	
	public ArrayList<Select> getPredicatesList() {
		return predicatesList;
	}

	public ArrayList<Tuple> getJoinResultTupleList() {
		return joinResultTupleList;
	}

	public void setJoinResultTupleList(ArrayList<Tuple> joinResultTupleList) {
		this.joinResultTupleList = joinResultTupleList;
	}

	public void setPredicatesList(ArrayList<Select> arPredicates) {
		this.predicatesList = arPredicates;
	}
	
	public void removearPredicates(int index) {
		this.predicatesList.remove(index);
	}
	
	public Tuple getCurrentTuple() {
		return currentTuple;
	}

	public void setCurrentTuple(Tuple currentTuple) {
		this.currentTuple = currentTuple;
	}
	
	public SteM getCurrentSteM() {
		return currentSteM;
	}

	public void setCurrentSteM(SteM currentSteM) {
		this.currentSteM = currentSteM;
	}

	public ArrayList<Tuple> getOutput() {
		return this.output;
	}

	public void setOutput(ArrayList<Tuple> output) {
		this.output = output;
	}

	public TicketPolicy getTicketPolicy() {
		return ticketPolicy;
	}

	public void setTicketPolicy(TicketPolicy ticketPolicy) {
		this.ticketPolicy = ticketPolicy;
	}

	public PriorityPolicy getPriorityPolicy() {
		return priorityPolicy;
	}

	public void setPriorityPolicy(PriorityPolicy priorityPolicy) {
		this.priorityPolicy = priorityPolicy;
	}

	/**
	 * Check if there are more tuples for processing 
	 * in the windows.
	 * 
	 * @return <code>true</code> if windows have more 
	 * tuples for processing, <code>false</code> otherwise.
	 */
	public boolean hasTuples() {
		boolean empty = false;
		int size = 0;
		for(Window window : getWindowList()) {
			size += window.getWindowSize();
		}
		if(size == 0) 
			empty = true;
		return (!empty);
	}//end method hasTuples()

	/**
	 * Initiallize the scheduler. 
	 * 
	 * @param plan the execution plan.
	 * @param arWinLst the windows of tuples
	 * @param arSteMsLst the SteMs
	 */
	@SuppressWarnings("unchecked")
	public void initiallize(Clock clock, String mode, Plan plan, SimulationParameters simulationParameters, Catalog catalog, ArrayList<Window> arWinLst, ArrayList<SteM> arSteMsLst, ArrayList<Select> arPredicateLst) {
		this.clock = clock;
		this.mode = mode;
		this.currentMode = ConstantsDefinition.STATIC;
		this.catalog = catalog;
		this.plan = plan;
		this.simulationParameters = simulationParameters;
		this.originalWindowList = arWinLst; 
		this.windowList = (ArrayList<Window>) originalWindowList.clone();
		this.stemsList = arSteMsLst;
		this.predicatesList = arPredicateLst;
	}//end method initiallize()
	
	/**
	 * Fill windows from tuples from the queue of events.
	 * 
	 * @param eventList the Queue of events
	 */
	@SuppressWarnings("unchecked")
	public void loadWindowsWithTuples(EventList eventList) {
		int i; int val = 0;
		boolean in = false;
		setWindowList((ArrayList<Window>) originalWindowList.clone());
		
		for(i=0; i<eventList.getEventQueue().size(); i++) {
			Tuple tuple = new Tuple();
			tuple = eventList.getEvent(i);
			for(Window window : getWindowList()) {
				if(tuple.getStream().equals(window.getwStream())) {
					if(window.hasRoom()){//if the window has room
						window.addTuple(tuple);//add the new tuple
						eventList.removeEvent(i);//remove the tuple from the queue of events
						i--; val = 0; in = true;
					} else {/*isFull*/}
				} else {/*other stream window*/}
			}//end for-loop
			
			if(in) {
				in = false;
				for(Window window : getWindowList()) {
					val += window.noEmptySlots();
				}
				if(val == 0) break;
			}
		}//end for-loop
	}//end method loadWindowsWithTuples()
	
	/**
	 * Choose the next tuple for processing from windows.
	 * 
	 * @return a tuple
	 */
	public Tuple nextTuple() {
		boolean out = false;
		Tuple tuple = new Tuple();
		Window tmpWindow = new Window();
		double timestamp = Double.MAX_VALUE;
		int i;
		for(i = 0; i < getarwindowsListSize(); i++) {
			if(getWindowList().get(i).getTupleList().isEmpty()) {
				removearwindowList(i); i--;
				continue;
			}
			/* find the tuple w/ the minimum timestamp */
			if(getWindowList().get(i).getTupleList().get(0).getTimestamp() < timestamp){
				timestamp = getWindowList().get(i).getTupleList().get(0).getTimestamp();
				tmpWindow = getWindowList().get(i);
				tuple = getWindowList().get(i).getTupleList().get(0);
				out = true;
			}
		}//end for-loop
		if (out) {
			tmpWindow.getTupleList().remove(0);
			return tuple;
		}
		else return null;
	}//end method nextTuple()
	
	/**
	 * Identify the operators the tuple should be scheduled to.
	 */
	@SuppressWarnings("unchecked")
	public void getTupleReadyForScheduling() {
		for(CatalogStream catStream : getCatalog().getCatalogStreams()) {
			ArrayList<Integer> tmpSteMStore = new ArrayList<Integer>();
			ArrayList<Integer> tmpSteMProbe = new ArrayList<Integer>();
			ArrayList<Integer> tmpPred = new ArrayList<Integer>();
			
			if( getCurrentTuple().getStream().equals(catStream.getStreamName()) ) {
				
				tmpSteMStore = (ArrayList<Integer>) catStream.getSteMsToBeStored().clone();
				getCurrentTuple().setSteMsToBeStoredTo(tmpSteMStore);
				
				tmpSteMProbe = (ArrayList<Integer>) catStream.getSteMsToProbe().clone();
				getCurrentTuple().setSteMsForProbe(tmpSteMProbe);
				
				if(tmpSteMProbe.isEmpty())
					getCurrentTuple().changeDoneSteMs();
				
				tmpPred = (ArrayList<Integer>) catStream.getPredicatesToBeScheduled().clone();
				getCurrentTuple().setPredicatesToBeScheduledTo(tmpPred); 
				
				if(tmpPred.isEmpty())
					getCurrentTuple().changeDonePredicates();
		
				break;
			}//end if
		}//end for-loop
		
	}//end method getTupleReadyForScheduling()

	/**
	 * Store the tuple into the SteMs it should be stored.
	 */
	public void storeTupleIntoSteMs() {
		//<<!-- TreeMap
		int pos = findAttributePosInSingleTuple(getCurrentSteM());
		String key = getCurrentTuple().getTupleData().get(pos);
		
//		getCurrentSteM().putTuple(Integer.parseInt(key), getCurrentTuple());
		getCurrentSteM().putTuple(key, getCurrentTuple());
		//TreeMap--!>
	}//end method storeTupleIntoSteMs()
	
	/**
	 * Find the index of the attribute in the tuple, when it does not
	 * derive from a join.
	 * 
	 * @param stem the current SteM.
	 * @return The index of the attribute.
	 */
	public int findAttributePosInSingleTuple(SteM stem) {
		String streamname = getCurrentTuple().getStream();
		String attributename = stem.getAttribute();
		int pos;		
		pos = getCatalog().findIndexOfAttributeInStream(streamname, attributename);

		return pos;		
	}//end method findAttributePosInSingleTuple()
	
	/**
	 * In the case the tuple has already been to an index of the
	 * current stream, find the stream in the list of the 
	 * SteMs it has already been to.
	 * 
	 * @param list the list of the SteMs the tuple has already been to.
	 * @param str the stream name we search for.
	 * @return The position in the list.
	 */
	public int findPosOfStreamInTheDerivesFromList(ArrayList<String> list, String str) {
		int i, pos = 0;
		for(i=0; i < list.size(); i++) {
			if( list.get(i).equals(str) ) {
				return pos;
			}
			pos++;
		}
		return pos;
	}//end method findPosOfStreamInTheDerivesFromList()
	
	/**
	 * Find the index position of the attribute that the join is performed on.
	 * 
	 * @param streamname the stream of the current SteM.
	 * @param attributename the attribute of the current SteM.
	 * @return The attribute position in the stream.
	 */
	public int findAttributePosInTheStream(String streamname, String attributename) {
		int res = -1;
		res = getCatalog().findIndexOfAttributeInStream(streamname, attributename);
		
		return res;
	}//end method findAttributePosInTheStream()
	
	/**
	 * Find the index position of a given attribute, when the tuple 
	 * derives from two other tuples, ie. has already been in a join.
	 * 
	 * @param pos the index position of the stream in the derivesFrom list
	 * @param res the index position of the attribute in the stream it belongs to.
	 * @return The index position of the attribute in the tuple.
	 */
	public int findAttributePosInTheCurrentTuple(int pos, int res) {
		int i;
		for(i = 0; i < pos; i++) {
			String streamname = getCurrentTuple().getarderivesFrom().get(i);
			res += getCatalog().findNoAttributeInStream(streamname);
		}//end for-loop
		return res;
	}//end method findAttributePosInTheCurrentTuple()

	/**
	 * Find the attribute index position in the current tuple,
	 * for the join to be performed with the given SteM.
	 * 
	 * @param stem the current SteM
	 * @return The index position of the composite tuple that 
	 * should be checked for join. 
	 * <br> -1 if we drop the tuple </br>
	 * <br> -2 for Cartesian Product </br>
	 * <br> -3 if no join is required. </br>
	 */
	public int findAttributePosInJoinedTuple(SteM stem) {
		int res = 0;
		ArrayList<String> derivesFromList = new ArrayList<String>();
		derivesFromList = getCurrentTuple().getarderivesFrom();
		//if it has already been probed in an index of the same SteM before...
		//if it contains the info for the join from the tuple
		if( getCurrentTuple().getarderivesFrom().contains(stem.getStream()) ) {
			
			//find the attribute index position in the current tuple
			res = posInCurrentTuple(derivesFromList, stem.getStream(), stem);
			
			//find the Stream for the join from the execution plan.
			ArrayList<String> stream = new ArrayList<String>();
			stream = findFromJoinListStreams(stem.getStream(), stem.getAttribute());//list of streams
			stream = findInStemsBeenList(getCurrentTuple().getSteMsAlreadyBeenTo(), stem, stream);
			
			if(stream.isEmpty()) {
				//keep the tuple
				return -3;
			}
			
			//find index positions for comparisons
			ArrayList<Integer> streamPos = new ArrayList<Integer>();
			ArrayList<Integer> valuesToCheck = new ArrayList<Integer>();
			for(String s : stream) {
				if(getCurrentTuple().getarderivesFrom().contains(s)) {
					//find the position of the stream in the current tuple
					int sp = findPosOfStreamInTheDerivesFromList(derivesFromList, s);
					streamPos.add(sp);
					
					//find the index of attributes in stream
					int iv = getCatalog().findIndexOfAttributeInStream(s, stem.getAttribute());
					iv = findAttributePosInTheCurrentTuple(sp, iv);
					valuesToCheck.add(iv);
				}//end if
			}//end for-loop
			
			//compare the attributes within the tuple
			boolean drop = false;
			for(int v : valuesToCheck) {
				if(!getCurrentTuple().getTupleData().get(res).
						equals(getCurrentTuple().getTupleData().get(v))) {
					drop = true;
					//drop the tuple
					return -1;
				}//end if
			}//end for-loop
			if(!drop) {
				//keep the tuple
				return -3;
			}
			
		} else {//this tuple has NOT been probed in an index of the same SteM before....
			//the tuple does NOT contain the info of the current Stem
			
			//get the info from the joins
			ArrayList<String> stream = new ArrayList<String>();
			ArrayList<String> tmpList = new ArrayList<String>();
			stream = findFromJoinListStreams(stem.getStream(), stem.getAttribute());
			//check if the current tuple has been through these SteMs.
			tmpList = findStemNotBeenWithSameStream(stream, stem);
			
			int indexPos = 0;
			while(stream.isEmpty() && indexPos<tmpList.size()) {
				stream = findFromJoinListStreams(tmpList.get(indexPos), stem.getAttribute());
				//check if the current tuple has been through these SteMs.
				tmpList.addAll(findStemNotBeenWithSameStream(stream, stem));
				indexPos++;
			}//end while-loop
			
			//It has not been in an index of the same SteM before, so... Cartesian Product
			if(stream.isEmpty()) {
				//Cartesian Product...
				return -2;
			}
			
			//find the attribute index position in the current tuple
			res = posInCurrentTuple(derivesFromList, stream.get(0), stem);

		}//end if-else

		return res;
	}//end method findAttributePosInJoinedTuple()
	
	/**
	 * Find the attribute position in the current tuple.
	 * 
	 * @param derivesFromList the list of the streams that composite the current tuple
	 * @param streamname the stream from those that composite the tuple.
	 * @param stem the current SteM
	 * @return The position of the attribute.
	 */
	public int posInCurrentTuple(ArrayList<String> derivesFromList, String streamname, SteM stem) {
		int res = 0, pos = 0;
		
		//the pos of the stream name in the derivesFrom list.
		pos = findPosOfStreamInTheDerivesFromList(derivesFromList, streamname);	
		//the attribute pos in the stream.
		res = findAttributePosInTheStream(streamname, stem.getAttribute());
		//the attribute pos in the current tuple. 
		res = findAttributePosInTheCurrentTuple(pos, res);
		
		return res;
	}//end method posInCurrentTuple()
	
	/**
	 * Find from a list of stream names the names of the 
	 * Streams of the SteMs, this tuple has been to.
	 * 
	 * @param stemsBeenTo the list of the SteMs, this tuple has been through.
	 * @param stem the current SteM.
	 * @param curStreams a list of streams from the join conditions
	 * @return The list of the common names of the lists.
	 */
	public ArrayList<String> findInStemsBeenList(ArrayList<Integer> stemsBeenTo, SteM stem, ArrayList<String> curStreams) {
		ArrayList<String> result = new ArrayList<String>();
		for(String cur : curStreams) {
			int i;
			for(i=0; i< stemsBeenTo.size()-1; i++) {
				int istem = stemsBeenTo.get(i);
				SteM s = getStemsList().get(istem);
				if(s.getAttribute().equals(stem.getAttribute()) && 
						s.getStream().equals(cur)){
					result.add(s.getStream());
				}
			}//end for-loop
		}//end for-loop
		return result;
	}//end method findInStemsBeenList()
	
	public ArrayList<String> findStemNotBeenWithSameStream(ArrayList<String> stream, SteM stem) {
		ArrayList<String> tmpList = new ArrayList<String>();
		int p = 0;
		for(p=0; p< stream.size(); p++) {
			String st = stream.get(p);
			int k;
			for(k=0; k<getCurrentTuple().getSteMsAlreadyBeenTo().size() - 1; k++) {
				int istem = getCurrentTuple().getSteMsAlreadyBeenTo().get(k);
				SteM s = getStemsList().get(istem);
				if(st.equals(s.getStream()) && 
						stem.getAttribute().equals(s.getAttribute())) {
					return tmpList;
				}
			}//end for-loop
			tmpList.add(st);
			stream.remove(p); p--;
		}//end for-loop
		return tmpList;
	}//end method findStemNotBeenWithSameStream()
	
	/**
	 * Find from the execution plan the streams that are joined, 
	 * with the info from the given current SteM. It also checks if 
	 * the result streams composite the current tuple.
	 * 
	 * @param stemStream the stream name of the current SteM.
	 * @param stemAttribute the attribute name of the current SteM.
	 * @return The list of the streams.
	 */
	public ArrayList<String> findFromJoinListStreams(String stemStream, String stemAttribute) {
		ArrayList<String> result = new ArrayList<String>();
		String str = new String();
		for(Join jl : getCatalog().getJoinList()) {
			if(jl.getjleftStream().equals(stemStream)) {
				if( jl.getjattribute().equals(stemAttribute) && 
						getCurrentTuple().getarderivesFrom().contains(jl.getjrightStream()) ) {
					str = jl.getjrightStream();
					result.add(str);
				}
			} else if (jl.getjrightStream().equals(stemStream)) {
				if( jl.getjattribute().equals(stemAttribute) &&
						getCurrentTuple().getarderivesFrom().contains(jl.getjleftStream()) ) {
					str = jl.getjleftStream();
					result.add(str);
				}
			}//end if-else
		}//end for-loop
		return result;
	}//end method findFromJoinListStreams()
	
	/**
	 * Check if the given tuple can be processed any further.
	 * 
	 * @param tuple the current tuple.
	 * @return <code>true</code> if we cannot process this tuple any further,
	 * <code>false</code> otherwise.
	 */
	public boolean endOfTupleProcessing(Tuple tuple) {
		if(tuple.getSteMsForProbe().isEmpty() && tuple.getPredicatesToBeScheduledTo().isEmpty())
			return true;
		return false;
	}//end method endOfTupleProcessing()
	
	/**
	 * Store the rest of the tuples of the list in the output.
	 */
	public void joinResultTupleListToOutput(){
		while(joinResultTupleList.size() > 0) {
			setCurrentTuple(joinResultTupleList.get(0));
			joinResultTupleList.remove(0);
			getCurrentTuple().changeDoneSteMs();
			fillOutput();
		}//end while-loop
	}//end method joinResultTupleListToOutput()
	
	/**
	 * Check if the current tuple is ready to be 
	 * added to the output.
	 */
	public void checkForOutput() {
		if(getCurrentTuple().getSteMsForProbe().isEmpty()) {
			getCurrentTuple().changeDoneSteMs();
			/* Check for output */
			fillOutput();
			/* All the other tuples also belong to the output... */
			joinResultTupleListToOutput();
		}
	}//end method checkForOutput()
	
	/**
	 * Find the index position of the attribute that the join 
	 * is going to performed on, in the current tuple.
	 * 
	 * @param curTuple the current tuple
	 * @return the position of the attribute.
	 */
	public int findAttributePosInTuple(Tuple curTuple) {
		int pos;
		
		if(getCurrentTuple().getarderivesFrom().isEmpty()) {
			pos = findAttributePosInSingleTuple(getCurrentSteM());
		} else {
			pos = findAttributePosInJoinedTuple(getCurrentSteM());		
		}
		
		return pos;
	}//end method attributePositionInTuple()
	
	/**
	 * Probe for matches.
	 * 
	 * @param pos a switch for 
	 * @return all the matches from the SteM.
	 */
	public ArrayList<Tuple> fillJoinList(int pos) {
		Join join = new Join();
		ArrayList<Tuple> joinList = new ArrayList<Tuple>();
		double time = getClock().getTimePolicy().getSystemClock();
		
		if(pos == -2) {
			//Cartesian Product in the SteM...
			joinList = join.evaluate(getCurrentTuple(), getCurrentSteM(), getCatalog(), time);
		} else if (pos == -3) {
			//keep the tuple
			joinList.add(getCurrentTuple());
		} else {
			//join with the matches from the SteM
			joinList = join.evaluate(getCurrentTuple(), pos, getCurrentSteM(), getCatalog(), time);
		}//end if-else
		
		return joinList;
	}//end method fillJoinList()
	
	/**
	 * The implementation of probing a tuple into a SteM.
	 * Find all the matches in the SteM, and route each one 
	 * of them into the Eddy.
	 * 
	 * @return <code>true</code> if match is found, <code>false</code> otherwise.
	 */
	public boolean probeTupleIntoSteMs() {
		int attributePositionInTuple = -1;
		
		attributePositionInTuple = findAttributePosInTuple(getCurrentTuple());

		if (attributePositionInTuple != -1) {//keep this tuple
			/* Scan the tuples of the SteM to find matches... */
			//<<!-- TreeMap
			ArrayList<Tuple> joinList = new ArrayList<Tuple>();
			
			/* probe for matches */
			joinList = fillJoinList(attributePositionInTuple);
			if(joinList != null) { 
				getClock().getStatistics().incNumOfInflightTuples(joinList.size());
				
				//for ticketing policy
				if(getCurrentMode().equals(ConstantsDefinition.ADAPTIVE)) {
					getCurrentSteM().getTicket().decNumOfTickets();
				}
				
				/* add all the matches*/
				joinResultTupleList.addAll(joinList);
				
			}//end if -- end of processing the joinList
		}//end if -- we have processed the incoming tuple.
		
					
		if( !joinResultTupleList.isEmpty() ) {
			setCurrentTuple(joinResultTupleList.get(0));
			joinResultTupleList.remove(0);
			
			/* If we cannot process this tuple any further in the SteMs. */
			checkForOutput();
		
			return true;
		} else {
			//do-nothing
		}
		//TreeMap--!>>
		
		return false;
	}//end method probeTupleIntoSteMs()
	
	/**
	 * Choose the next non-join predicate with the highest priority.
	 * 
	 * @return The position of the non-join predicate.
	 */
	public int chooseNextPredicate() {
		int pos = -1;
		
		if(getCurrentMode().equals(ConstantsDefinition.STATIC)) {
			//higher priority
			pos = getPriorityPolicy().findPredicateWithHighestPriority(getCurrentTuple(), getPredicatesList());
		} else if(getCurrentMode().equals(ConstantsDefinition.ADAPTIVE)) {
			//hold a lottery
			pos = getTicketPolicy().predicatesLottery(getCurrentTuple(), getPredicatesList());
		}//end if-else
		return pos;
	}//end method chooseNextPredicate()
	
	/**
	 * Route the tuple to a non-join predicate.
	 * 
	 * @param pos the position of the non-join predicate in the list
	 * that contains all the predicates.
	 * @return The result of the evaluation of the select operator.
	 * <code>true</code> if the predicate holds, <code>false</code> otherwise.
	 * @throws RoutingToPredicateException 
	 */
	public boolean toPredicate(int pos) throws RoutingToPredicateException {
		Select select;
		int next = getCurrentTuple().getPredicatesToBeScheduledTo().get(pos);
		
		select = getPredicatesList().get(next);
		getCurrentTuple().removePredicatesToBeScheduledTo(next);
		getCurrentTuple().getPredicatesAlreadyBeenTo().add(next);
		
		//for ticketing policy
		if(getMode().equals(ConstantsDefinition.ADAPTIVE)) {
			select.getTicket().incNumOfTickets();
		}

		try{
			if (select.evaluate(getCurrentTuple(), getPredicatesList().get(next).getSelpredicate(), getCatalog())) {
				//tuple added to output
				//for ticketing policy
				if(getMode().equals(ConstantsDefinition.ADAPTIVE)) {
					select.getTicket().decNumOfTickets();
				}
				if(getCurrentTuple().getPredicatesToBeScheduledTo().isEmpty()) {
					getCurrentTuple().changeDonePredicates();
				}
				select.getSelectivity().tuplePass();
				getClock().getStatistics().incNumOfInflightTuples();
				return true;
			} else {
				//tuple dropped
				getCurrentTuple().setDonePredicates(false);
				getCurrentTuple().setDoneSteMs(false);
				select.getSelectivity().tupleNotPass();
				return false;
			}//end if-else
		}catch(RoutingToPredicateException see) {
			System.err.println(see.getMessage());
			String errormsg = "Error: Routing to a non-join predicate failure.";
			throw new RoutingToPredicateException(errormsg);
		}//end try-catch
	}//end method toPredicate()
	
	/**
	 * Choose the next SteM with the highest priority for routing.
	 * 
	 * @return The position of the SteM.
	 */
	public int chooseNextSteM() {
		int pos = -1;
		
		if(getCurrentMode().equals(ConstantsDefinition.STATIC)) {
			//higher priority
			pos = getPriorityPolicy().findSteMWithHighestPriority(currentTuple, stemsList);
		} else if(getCurrentMode().equals(ConstantsDefinition.ADAPTIVE)) {
			//hold a lottery
			pos = getTicketPolicy().stemsLottery(getCurrentTuple(), getStemsList());
		}
		return pos;
	}//end method chooseNextSteM()
	
	/**
	 * Probe current tuple to a given SteM.
	 * 
	 * @param pos the position of the SteM in the list
	 * that contains all the SteMs.
	 * @return The result of the evaluation of the join operator.
	 * <code>true</code> if the probe returns matches, <code>false</code> otherwise.
	 */
	public boolean toSteM(int pos){
		
		int next = getCurrentTuple().getSteMsForProbe().get(pos);
		setCurrentSteM(getStemsList().get(next));
		
		/* Remove from arrayList of tuple */
		getCurrentTuple().removeSteMsForProbe(next);
		getCurrentTuple().getSteMsAlreadyBeenTo().add(next);
		
		//for ticketing policy
		if(getMode().equals(ConstantsDefinition.ADAPTIVE)) {
			getCurrentSteM().getTicket().incNumOfTickets();
		}
				
		/* Probe to other SteMs & evaluate */
		if(probeTupleIntoSteMs()) {
			getCurrentSteM().getSelectivity().tuplePass();
			return true;
		} else {
			//do-nothing
			getCurrentSteM().getSelectivity().tupleNotPass();
			return false;
		}
	}//end method toSteM()
	
	/**
	 * Perform the routing of the tuple through the non-join predicates.
	 * 
	 * @return <code>true</code> if the predicate holds, <code>false</code> otherwise.
	 * @throws RoutingToPredicateException 
	 */
	public boolean routeThroughPredicates() throws RoutingToPredicateException {
		int nextPredicate = -1;//alternative
		boolean drop = false;
		/* Check predicates in higher-priority order. */
		while(!getCurrentTuple().getPredicatesToBeScheduledTo().isEmpty()){
			try{
				nextPredicate = chooseNextPredicate();
				if(!toPredicate(nextPredicate)) {
					drop = true;
					return drop;
				} else {
					//do-nothing
				}
			}catch(RoutingToPredicateException rpe) {
				System.err.println(rpe.getMessage());
				String errormsg = "Error: Routing to a non-join predicate failure.";
				throw new RoutingToPredicateException(errormsg);
			}//end try-catch
		}//end while-loop
		return drop;
	}//end method routeThroughPredicates()
	
	/**
	 * Perform the storage into SteMs.
	 */
	public void storeIntoSteMs() {
		/* Store current tuple in the proper SteMs. */
		while(getCurrentTuple().getSteMsToBeStoredTo().size() > 0) {
			int next = getCurrentTuple().getSteMsToBeStoredTo().get(0);

			setCurrentSteM(getStemsList().get(next));
			
			/* Remove from arrayList of tuple */
			getCurrentTuple().removeSteMsToBeStoredTo(next);
			getCurrentTuple().getSteMsAlreadyBeenTo().add(next);
			
			//for ticketing policy
			if(getMode().equals(ConstantsDefinition.ADAPTIVE)) {
				getCurrentSteM().getTicket().incNumOfTickets();
			}
					
			/* Store tuple to the proper SteM */
			storeTupleIntoSteMs();
		}//end while-loop
	}//end method storeIntoSteMs()
	
	/**
	 * Route the tuple through the SteMs, and collect 
	 * the routing cost of the tuple.
	 */
	public void routeThroughSteMs() {
		double routeTime = 0, computationalTime = 0, clock = 0;
		int nextSteM = -1;//alternative
		/* Apply a scheduling policy to choose the next SteM. */
		while(!getCurrentTuple().getSteMsForProbe().isEmpty()) {
			
			nextSteM = chooseNextSteM();
		
			//calculate the routing cost
			//calculate the time for routing through SteMs in Eddy.
			routeTime = getClock().getTimePolicy().routeCost(getCatalog().getCatalogStreams().size());
			getCurrentTuple().incTupleRouteCost(routeTime);
			getClock().getStatistics().incTotalRouteCost(routeTime);
			//set the system clock to the new value by adding the cost of routing through SteMs in Eddy.
			clock = getClock().getTimePolicy().getSystemClock() + routeTime;
			getClock().getTimePolicy().setSystemClock(clock);
				
			computationalTime = getClock().getTimePolicy().computationalCost();
			getCurrentTuple().incTupleComputationalCost(computationalTime);
			getClock().getStatistics().incTotalComputationalCost(computationalTime);
			clock = getClock().getTimePolicy().getSystemClock() + computationalTime;
			getClock().getTimePolicy().setSystemClock(clock);
			
			if(!toSteM(nextSteM)) {
				break;		
			} else {
				//do-nothing
			}
		}//end while-loop
	}//end method routeThroughSteMs()
	
	public int assignRandomPriority(int max) {
		Random ran = new Random();
		return ran.nextInt(max);
	}//end method assignRandomPriority()
	
	/**
	 * Check if current tuple is done with scheduling, 
	 * and if so, add it to the output list.
	 */
	public void fillOutput() {
		if(readyForOutput()) {//final result
			getOutput().add(getCurrentTuple());
			
			/* Get the statistics. */
//			getClock().getStatistics().incNumOfInflightTuples();
			getClock().getStatistics().incNumOfResultTuples();
		}
	}//end method fillOutput()
	
	/**
	 * The brain of the scheduler.
	 * It schedules the current tuple to the 
	 * SteMs and predicates.
	 * @throws SchedulingTupleException 
	 */
	public void scheduleTuple() throws SchedulingTupleException {
		boolean drop = false;
		/* Timers for the system clock.*/
		double clock = 0, predicateTime = 0, storeTime = 0;
		/* Statistics. */
		double tupleProcessingTime = 0, throughput = 0, responseTime = 0;
		
//		System.out.println("Current tuple: " + getCurrentTuple().toString());
		/* Start processing the tuple */
		getClock().getTimePolicy().startTupleProcessingTime();
		double start = getClock().getTimePolicy().getSystemClock();
		
//		if(getCurrentTuple().getStream().endsWith("R")){
//			if(getCurrentTuple().getTupleData().get(0).equals("1024"))
//				System.out.println("Scheduler.java: debug");
//		}
		
		/* Calculate the error of the static framework. */
		if(start > getCurrentTuple().getTimestamp()){
			responseTime = start - getCurrentTuple().getTimestamp();
			getClock().getStatistics().incTotalResponseTime(responseTime);
		}
		
		/* (a) Check predicates in higher-priority order. */
		//get the system clock...
		getClock().getTimePolicy().getSystemClock();
		try{
			drop = routeThroughPredicates();		
			//calculate the time for routing through the predicates.
			predicateTime = getClock().getTimePolicy().predicateCost();
//			getCurrentTuple().incTuplePredicateCost(predicateTime);
			getClock().getStatistics().incTotalPredicateCost(predicateTime);
			//set the system clock to the new value by adding the cost of routing through the predicates.
			clock = getClock().getTimePolicy().getSystemClock() + predicateTime;
			getClock().getTimePolicy().setSystemClock(clock);
			//statistics for per-tuple non-join predicate-time.
			
			if(!drop) {
				/* Check for output */
				fillOutput();
				
				/* (b) Store current tuple in the proper SteMs. */
				//get the system clock...
				getClock().getTimePolicy().getSystemClock();			
				storeIntoSteMs();//store
				//calculate the time for storage into SteMs.
				storeTime = getClock().getTimePolicy().storeCost();
				getCurrentTuple().incTupleStoreCost(storeTime);
				getClock().getStatistics().incTotalStoreCost(storeTime);
				//set the system clock to the new value by adding the cost of storing into SteMs.
				clock = getClock().getTimePolicy().getSystemClock() + storeTime;
				getClock().getTimePolicy().setSystemClock(clock);
				//statistics for per-tuple store-time.
				
				/* (c) Route the tuple amongst the SteMs.
				 * Apply a scheduling policy to choose the next SteM. */
				do {
					if(!joinResultTupleList.isEmpty()) {
						setCurrentTuple(joinResultTupleList.get(0));
						joinResultTupleList.remove(0);
					}
					routeThroughSteMs();//route in Eddy
				}while(!joinResultTupleList.isEmpty());
				
				
//				//calculate the time for routing through SteMs in Eddy.
//				routeTime = getClock().getTimePolicy().tupleRouteTime();
//				//set the system clock to the new value by adding the cost of routing through SteMs in Eddy.
//				clock = getClock().getTimePolicy().getSystemClock() + routeTime;
//				getClock().getTimePolicy().setSystemClock(clock);
			}//end if -- done with processing the tuple.
			
			/* end-of-processing of the tuple */
			
			/* collect tuple costs from statistics. */
			double processingTimeByEddy = getClock().getTimePolicy().endTupleProcessingTime();
			tupleProcessingTime = processingTimeByEddy + responseTime;
			getClock().getStatistics().incTotalProcessingTime(tupleProcessingTime);
			
			double cl = getClock().getTimePolicy().getSystemClock();
			double total = getClock().getStatistics().getTotalProcessingTime();
			/* collect throughput statistics. */
			throughput = getClock().getStatistics().calculateThroughput();
			
			/* re-adjust the priorities of SteMs and Predicates. 
			 * The most important part of adaptive processing. */
			if(getMode().equals(ConstantsDefinition.ADAPTIVE)) {
				setCurrentMode(getMode());
			}
		}catch(RoutingToPredicateException rpe){
			System.err.println(rpe.getMessage());
			String errormsg = "Error: Schedule tuple failure.";
			throw new SchedulingTupleException(errormsg);
		}//end try-catch
	}//end method scheduleTuple()
	
	/**
	 * Check if the tuple is ready to be added to the output.
	 * 
	 * @return <code>true</code> if the tuple is ready to 
	 * be added to the output, <code>false</code> otherwise.
	 */
	public boolean readyForOutput() {
		if(getCurrentTuple().SteMsIsDone()) {
			if(getCurrentTuple().predicatesIsDone()) {
				return true;
			}			
		}//end outer-if
		return false;
	}//end method readyForOutput()
	
	public ArrayList<Tuple> getResults() {
		return this.getOutput();
	}
	
	public void reset() {
		//<<--reset SteMs
		for(SteM stem : getStemsList()) {
			stem.resetSteM();
		}
		//end reset SteMs -->>
		
		//<<--reset statistics
//		statistics.resetTupleProcessingTime();
		//end reset statistics -->>
		
	}//end method reset
	
	/**
	 * Terminate the scheduler.
	 */
	@Override
	public void terminate() {
		/* Call the garbage collector. */
		Runtime r = Runtime.getRuntime();
		r.gc();
	}//end method terminate()
	
	/**
	 * Run the scheduler.
	 * 
	 * @param eventList the event-queue of the simulation.
	 * @throws RunSchedulerException 
	 */
	public void run(EventList eventList) throws RunSchedulerException {
		double sysTime = 0;
		try{
			/* start dataflow...
			   feed windows with tuples from event-queue */
			while(!eventList.getEventQueue().isEmpty()) {
	
				loadWindowsWithTuples(eventList);
	//			System.out.println("Windows loaded.");//<--! printf
				
				/* Start per-tuple processing...
				   choose tuple for processing from windows */
				while(hasTuples()) {
					
					setCurrentTuple(nextTuple()); 
					
					//assign to the system clock the timestamp of the first tuple
					if(sysTime == 0) {
						sysTime = getCurrentTuple().getTimestamp();
						getClock().getTimePolicy().setSystemClock(sysTime);
					}
					
					if(getCurrentTuple() == null) break;
					
					//assign to the system clock the timestamp of the current tuple - if it is smaller than the tuple's.
					double curClock = 0, curTs = 0;
					curClock = getClock().getTimePolicy().getSystemClock();
					curTs = getCurrentTuple().getTimestamp();
					if(curClock < curTs) {
						getClock().getTimePolicy().setSystemClock(curTs);
					}
					
					getClock().getStatistics().incNumOfInitialTuples();
//					getClock().getStatistics().printLogFileCurrentTuple(getCurrentTuple());
				
					/* Schedule the above tuple...
					   Identify the predicates and SteMs the tuple should be scheduled to. */
					getTupleReadyForScheduling();
					
					/* Apply a scheduling policy to the tuple... */
					scheduleTuple();		
					/* further scheduling */	
					
				}//end while-loop -- no more tuples in the windows.
	//			System.out.println("\nWindows need reload...");		
				/* Re-feed windows w/ tuples */
				System.out.println("Scheduler.java: New window loaded.");
				System.out.println("Scheduler.java: total output till now = " + getOutput().size());
				this.reset();
			}//end while-loop
			/* Processing done -- no more tuples in the Event Queue*/
			
			/* collect throughput vs. average tuple arrival time statistics. */
//			double throughput = getClock().getStatistics().calculateThroughput();
			double throughput = getClock().getStatistics().getThroughput();
			double avgArrivalRate = getSimulationParameters().getArrivalRate();
			getClock().getStatistics().printLogFileThroughputVsArrivalRateStatistics(throughput, avgArrivalRate);////
			
			/* collect throughput vs. average routing cost statistics. */
			double avgRoutingCost = getSimulationParameters().getAvgRoutingCost();
			getClock().getStatistics().printLogFileThroughputVsAvgRoutingCostStatistics(throughput, avgRoutingCost);
			
//			/* collect delta time vs. average tuple arrival time statistics. */
//			double dt = getClock().getStatistics().getStaticFrameworkDeltaTime();
//			getClock().getStatistics().printLogFileStaticDeltaTimeVSAvgArrivalTimeStatisticsFile(dt, avg);
//			
//			/* collect static error vs. system throughput statistics. */
//			getClock().getStatistics().printLogFileStaticDeltaTimeVSThroughputStatisticsFile(dt, throughput);
			
		}catch(SchedulingTupleException rte) {
			System.err.println(rte.getMessage());
			String errormsg = "Error: Run of the scheduler failure.";
			throw new RunSchedulerException(errormsg);
		}//end try-catch
	}//end method run()

	/**
     * Textual representation.
	 */
	@Override
	public String toString() {
		return "Scheduler [mode=" + mode + ", currentMode=" + currentMode
				+ ", clock=" + clock + ", catalog=" + catalog + ", plan="
				+ plan + ", windowList=" + windowList + ", originalWindowList="
				+ originalWindowList + ", stemsList=" + stemsList
				+ ", predicatesList=" + predicatesList
				+ ", joinResultTupleList=" + joinResultTupleList
				+ ", currentTuple=" + currentTuple + ", currentSteM="
				+ currentSteM + ", output=" + output + ", ticketPolicy="
				+ ticketPolicy + ", priorityPolicy=" + priorityPolicy + "]";
	}

}//end class Scheduler
