package simulation.brain;


import java.util.ArrayList;
import java.util.Iterator;

import operators.Join;
import operators.Operator;
import operators.Project;
import operators.Select;

import datastorage.catalog.Catalog;
import datastorage.catalog.CatalogJoinBookkeepingInfo;
import datastorage.structures.Sink;
import datastorage.structures.Tuple;
import datastorage.structures.Window;

import simulation.core.Clock;
import simulation.core.EventList;
import simulation.core.Plan;
import simulation.core.SimulationParameters;
import simulation.structures.SteM;
import util.exceptions.RunEngineException;
import util.exceptions.RunSchedulerException;

/**
 * The engine of the system.
 * 
 * @version 1.0
 * @author kostas
 */
public class Engine implements iDataStreamSIM {
	
	private Clock clock;
	
	/* The mode of the system from the configuration file. */
	private String mode;
	
	/* An instance of the scheduler */
	private Scheduler scheduler;
	
	/* An instance of the system catalog. */
	private Catalog catalog;
	
	/* The execution plan. */
	private Plan plan;
	
	private SimulationParameters simulationParameters;
	
	/*The windows that have to exist according to the execution plan.*/
	private ArrayList<Window> windowList;
	
	/* The list of predicates from the execution plan. */
	private ArrayList<Select> attributeToValueList;
	
	/* The list of join operators from the execution plan. */
	private ArrayList<Join> joinList;
	
	/* The list of projections from the exection plan */
	private ArrayList<Project> projectList;
	
	/* The list of SteMs operators that are used for processing. */
	private ArrayList<SteM> stemsList;
	private ArrayList<Select> predicatesList;
	
	/* The output of the scheduler. w/ all the projections. */
	private ArrayList<Tuple> output;
	
	/* The final result. */
	private ArrayList<Sink> result;
	
	/**
	 * Constructor of class <class>Engine</class>.
	 */
	public Engine() {
		this.clock = new Clock();
		this.scheduler = new Scheduler();
		this.catalog = new Catalog();
		this.plan = new Plan();
		this.simulationParameters = new SimulationParameters();
		
		this.windowList = new ArrayList<Window>();
		this.attributeToValueList = new ArrayList<Select>();
		this.joinList = new ArrayList<Join>();
		this.projectList = new ArrayList<Project>();
		this.stemsList = new ArrayList<SteM>();
		this.predicatesList = new ArrayList<Select>();
		
		this.output = new ArrayList<Tuple>();
		this.result = new ArrayList<Sink>();
	}//end constructor Engine()
	
	/**
	 * Constructor of class <class>Engine</class>.
	 */
	public Engine(Scheduler scheduler, ArrayList<Window> windowList) {
		this.scheduler = scheduler;
		this.windowList = windowList;
	}//end constructor Engine()
	
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

	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
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

	public void setWindowList(ArrayList<Window> arList) {
		this.windowList = arList;
	}
	
	public ArrayList<Window> getWindowList() {
		return this.windowList;
	}
	
	public ArrayList<Select> getAttributeToValueList() {
		return attributeToValueList;
	}

	public void setAttributeToValueList(ArrayList<Select> arattributeToValueList) {
		this.attributeToValueList = arattributeToValueList;
	}

	public ArrayList<Join> getJoinList() {
		return joinList;
	}

	public void setJoinList(ArrayList<Join> arjoinList) {
		this.joinList = arjoinList;
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

	public void setPredicatesList(ArrayList<Select> arPredicates) {
		this.predicatesList = arPredicates;
	}
	
	public ArrayList<Project> getProjectList() {
		return projectList;
	}

	public void setProjectList(ArrayList<Project> projectList) {
		this.projectList = projectList;
	}

	public ArrayList<Tuple> getOutput() {
		return output;
	}

	public void setOutput(ArrayList<Tuple> output) {
		this.output = output;
	}

	public ArrayList<Sink> getResult() {
		return result;
	}

	public void setResult(ArrayList<Sink> result) {
		this.result = result;
	}

	public String findAttributeInJoinListAtIndex(int index) {
		return getJoinList().get(index).getjattribute();
	}
	
	public String findJLeftStreamAtIndex(int index) {
		return getJoinList().get(index).getjleftStream();
	}
	
	public String findJRightStreamAtIndex(int index) {
		return getJoinList().get(index).getjrightStream();
	}
	
//	public int assignRandomPriority(int max) {
//		Random ran = new Random();
//		return ran.nextInt(max);
//	}//end method assignRandomPriority()

	/**
	 * Initiallize the engine.
	 * 
	 * @param plan the execution plan.
	 * @param catalog the catalog of the system.
	 * @param mode the current mode of the system, ie. static or adaptive
	 * @param clock the system clock.
	 */
	public void initiallize(Plan plan, Catalog catalog, String mode, Clock clock, SimulationParameters simulationParameters) {
		setPlan(plan);
		setCatalog(catalog);
		setSimulationParameters(simulationParameters);
		
		/* Load the windows definitions from the execution plan. */
		windowList = plan.getWindowList();
		setMode(mode);
		setClock(clock);
		getClock().getStatistics().setMode(getMode());
		
		System.out.println("Engine initiallized.");
	}//end method initiallize
	
	/**
	 * Isolate the projections from the execution plan.
	 */
	public void extractProjectionsFromPlan() {
		Iterator<Operator> iterator = getPlan().getPlanOperatorsList().iterator(); 
		while(iterator.hasNext()) {
		    Operator op = iterator.next(); 
		    String str = op.getOpInfodata();
		    String[] fields = str.split(" ");
		    if(fields[0].equals("project")){
		    	projectList.add((Project) op);
		    }
		}//end while-loop
	}//end method extractProjectionsFromPlan()
	
	/**
	 * Isolate the predicates from execution plan.
	 */
	public void extractPredicatesFromPlan() {
		Iterator<Operator> iterator = getPlan().getPlanOperatorsList().iterator(); 
		while(iterator.hasNext()) {
			float priority = 0;
		    Operator op = iterator.next(); 
		    String str = op.getOpInfodata();
		    String[] fields = str.split(" ");
		    if(fields[0].equals("select")) {
		    	/*We setup the priorities; we give the parameters of
			     * the static model in the begining. */
		    	priority++;
			    op.setPriority(1/priority);
   
		    	attributeToValueList.add((Select) op);
		    }//end if
		}//end while-loop
	}//end method predicatesFromPlan()
	
	/**
	 * Isolate the joins from execution plan.
	 */
	public void extractJoinsFromPlan() {
		Iterator<Operator> iterator = getPlan().getPlanOperatorsList().iterator(); 
		while(iterator.hasNext()) {
		    Operator op = iterator.next(); 
		    String str = op.getOpInfodata();
		    String[] fields = str.split(" ");
		    if(fields[0].equals("join")){
		    	joinList.add((Join) op);
		    }
		}//end while-loop
	}//end method joinsFromPlan()
	
	public boolean existsInArJoinList(Join join) {
		if(joinList.contains(join))
			return true;
		return false;
	}//end method existsInArJoinList()	
	
	/**
	 * Check if the given SteM exists in the list that
	 * contains all the SteMs.
	 * 
	 * @param tmpSteM the given SteM.
	 * @return <code>true</code> if it exists, <code>false</code> otherwise.
	 */
	public boolean stemExists(SteM tmpSteM) {
//		Iterator<SteM> iterator = stemsList.iterator();
//		while (iterator.hasNext()){
//			SteM nextStem = (SteM) iterator.next();
//			if(nextStem.getStream().equals(tmpSteM.getStream()) && 
//	    			nextStem.getAttribute().equals(tmpSteM.getAttribute()) ) {
//	    		return true;
//	    	}
//		}
		
		for (SteM stem : stemsList) {
			if(stem.getStream().equals(tmpSteM.getStream()) && 
	    			stem.getAttribute().equals(tmpSteM.getAttribute()) ) {
	    		return true;
	    	}
		}
		return false;
	}//end method stemExists()
	
	/**
	 * Build SteMs from join conditions.
	 */
	public void buildSteMs() {
		float priority = 0, leftPriority = 0, rightPriority = 0;
		Iterator<Join> iterator = getJoinList().iterator(); 
		while(iterator.hasNext()) {
		    Join join = iterator.next(); 
		    /* for the left SteM*/
		    SteM leftSteM = new SteM();
		    priority++;
		    leftSteM.setStream(join.getjleftStream());
		    leftSteM.setAttribute(join.getjattribute());
		    leftPriority = priority;
		    
		    /* for the right SteM*/
		    SteM rightSteM = new SteM();
		    priority++;
		    rightSteM.setStream(join.getjrightStream());
		    rightSteM.setAttribute(join.getjattribute());
		    rightPriority = priority;
		    
		    /* Assign Priorities; we give the parameters of
		     * the static model in the begining. */
		    leftSteM.setPriority(1/leftPriority);
		   	rightSteM.setPriority(1/rightPriority);
		    
		    if(!stemExists(leftSteM)) {
		    	stemsList.add(leftSteM);
		    }
		    if(!stemExists(rightSteM)) {
		    	stemsList.add(rightSteM);
		    }
		}//end while-loop
	}//end method buildSteMs()

	/**
	 * Build predicates from the execution plan. 
	 */
	public void buildPredicates() {
		setPredicatesList(attributeToValueList);
	}//end method buildPredicates()
	
	/**
	 * Identify the SteMs the tuple should be scheduled 
	 * and stored to.
	 */
	public void whereToScheduleTuple() {

		int index = 0;
		for(SteM stem : getStemsList()) {
			String stemname = stem.getStream();
			String stemattribute = stem.getAttribute();
			
			/* SteMs store info */
			getCatalog().addSteMToBeStoredInStream(stemname, index);
			/* SteMs probe info */
			getCatalog().addSteMToProbeInStream(stemname, stemattribute, index);
			index++;
		}//end for --SteMs info are done
		
		/* Predicates info */
		index = 0;
		for(Select s : getPredicatesList()) {
			String predicatename = s.getSelstream();
			getCatalog().addPredicateToBeScheduledInStream(predicatename, index);
			index++;
		}
			
		/*Scheduling Info is completed.*/
	}//end method whereToScheduleTuple();
	
	/**
	 * Add bookkeeping information to the catalog.
	 */
	public void bookkeepJoinInfo() {
		getCatalog().setJoinList(getJoinList());
		
		CatalogJoinBookkeepingInfo tmpInfo1 = new CatalogJoinBookkeepingInfo();
		CatalogJoinBookkeepingInfo tmpInfo2 = new CatalogJoinBookkeepingInfo();
		int i;
		for(i=0; i < getJoinList().size(); i++) {
			/* left */
			tmpInfo1 = new CatalogJoinBookkeepingInfo();
			
			tmpInfo1.setStream(findJLeftStreamAtIndex(i));
			tmpInfo1.setNoAttributes(getCatalog().getStreamInfo(tmpInfo1.getStream()).findNoAttribute());
			tmpInfo1.setAttributeName(findAttributeInJoinListAtIndex(i));
			tmpInfo1.setAttributePos(getCatalog().findIndexOfAttributeInStream(tmpInfo1.getStream(), tmpInfo1.getAttributeName()));	
			getCatalog().addCatalogJoinBkkInfoList(tmpInfo1);
			
			/* right */
			tmpInfo2 = new CatalogJoinBookkeepingInfo();
			tmpInfo2.setStream(findJRightStreamAtIndex(i));
			tmpInfo2.setNoAttributes(getCatalog().getStreamInfo(tmpInfo2.getStream()).findNoAttribute());
			tmpInfo2.setAttributeName(findAttributeInJoinListAtIndex(i));
			tmpInfo2.setAttributePos(getCatalog().findIndexOfAttributeInStream(tmpInfo2.getStream(), tmpInfo2.getAttributeName()));
			getCatalog().addCatalogJoinBkkInfoList(tmpInfo2);
		}//end for-loop
		
		int j;
		for(i=0; i < getJoinList().size(); i++) {
			for(j=0; j<getCatalog().getCatalogJoinBkkInfoList().size(); j++) {
				if(findJLeftStreamAtIndex(i).equals(getCatalog().getCatalogJoinBkkInfoList().get(j).getStream())) {
					if(findAttributeInJoinListAtIndex(i).equals(getCatalog().findAttributeInBkkInfoListAtIndex(j)))  {
						getCatalog().findJoinStreamsInBkkInfoListAtIndex(j).add(findJRightStreamAtIndex(i));
					}//end if
				}
			}
		}//end for-loop
		
	}//end method bookkeepJoinInfo
	
	/**
	 * Apply the projections to the result tuples.
	 */
	public void applyProjectionsToOutput() {
		if(!getOutput().isEmpty()) {//if there are result tuples.
			
			int k,index;
			for(Tuple tuple : getOutput()) {
				Sink sink = new Sink();
				ArrayList<String> list = tuple.getarderivesFrom();//list of tuples that generated the result
				ArrayList<Integer> pos = new ArrayList<Integer>();//pos of projections in the above list
				
				/* In the case that no join has been performed. */
				if(list.isEmpty()) {
					for(Project proj : getProjectList()) {
						index = getCatalog().findIndexOfAttributeInStream(proj.getProjstream(), proj.getProjattribute());
						pos.add(index);
					}//end for-loop
				} else { //with the joins
					for(Project proj : getProjectList()) {
						index = findAttributePos(list, proj.getProjstream(), proj.getProjattribute());
						pos.add(index);
					}//end for-loop
				}//end if-else
				
				/* Fill the sink with the results. */
				ArrayList<String> data = tuple.getTupleData();
				for(Integer p : pos) {
					for(k=0; k<data.size(); k++) {
						if(p == k) {
							sink.getOutput().add(data.get(k));
						}
					}
				}
				result.add(sink);
			}//end for-loop
		}//end if
		
		System.out.println("The output tuples are: " + getOutput().toString());//printf
		System.out.println(result.toString());
	}//end method applyProjectionsToOutput()
	
	public int findAttributePos(ArrayList<String> list, String streamname, String attributename) {
		int i, j, index = -1, pos = 0;
		
		for(j=0; j<list.size(); j++) {
			if(list.get(j).equals(streamname)) {
				index = j;
				pos = getCatalog().findIndexOfAttributeInStream(streamname, attributename);
			}
		}//end for-loop
	
		for(i = 0; i < index; i++) {
			streamname = list.get(i);
			pos += getCatalog().findNoAttributeInStream(streamname);
		}//end for-loop
		
		return pos;
	}//end method findAttributePos()
	
	/**
	 * Terminates the engine of the system
	 */
	public void terminate() {
		/* Complete the log file, */
		getClock().getStatistics().printEndOfLogFile(getStemsList());
		
		/* Call the garbage collector. */
		Runtime r = Runtime.getRuntime();
		r.gc();
	}//end method terminate()
	
	/**
	 * Extract information about the projections, the predicates and the joins 
	 * from the execution plan.
	 */
	public void analyzeExecutionPlan() {
		/* Isolate projections from the execution plan. */
		extractProjectionsFromPlan();
		
		/* Isolate predicates from the execution plan. */
		extractPredicatesFromPlan();
		
		/* Isolate joins from the execution plan. */
		extractJoinsFromPlan();
	}//end method analyzeExecutionPlan()
	
	/**
	 * Build the bookkeeping structure, the 
	 * predicates and the SteMs.
	 */
	public void buildEngineStructures() {
		/* Store bookkeeping info for the joins. */
		bookkeepJoinInfo();
		
		/* Build the SteMs and the predicates. */
		buildSteMs();
		
		buildPredicates();
	}//end method buildEngineStructures()
	
	@Override
	public void reset() {
		
	}
	
	/**
	 * Run the engine.
	 * 
	 * @param eventList The event-queue of the simulation.
	 * @throws RunEngineException 
	 */
	@Override
	public void run(EventList eventList) throws RunEngineException {
		try{
			analyzeExecutionPlan();
			
			buildEngineStructures();
			
			/* Identify the operators each tuple should be scheduled to. */
			whereToScheduleTuple();
			
			/* Initiallize scheduler by feeding the execution plan,
			   window streams and SteMs. */
			scheduler.initiallize(getClock(), getMode(), getPlan(), getSimulationParameters(),
					getCatalog(), getWindowList(), getStemsList(), getPredicatesList());
			
			/* Run scheduler - start dataflow to the system. */
			scheduler.run(eventList);
			
			/* Get the results. */
			setOutput(scheduler.getResults());
			
			/* Stop the scheduler. */
			scheduler.terminate();
			
			/* Apply the projections to the result tuples. */
//			applyProjectionsToOutput();
			
	//		System.out.println("The SteMs are: ");
	//		System.out.println(getStemsList().toString());//just to see the tickets of each stem
		}catch(RunSchedulerException rse) {
			System.err.println(rse.getMessage());
			String errormsg = "Error: Run of the engine failure.";
			throw new RunEngineException(errormsg);
		}//end try-catch
		
	}//end method run()
	
	/**
     * Textual representation.
	 */
	@Override
	public String toString() {
		return "Engine [clock=" + clock + ", mode=" + mode + ", scheduler="
				+ scheduler + ", catalog=" + catalog + ", plan=" + plan
				+ ", windowList=" + windowList + ", attributeToValueList="
				+ attributeToValueList + ", joinList=" + joinList
				+ ", projectList=" + projectList + ", stemsList=" + stemsList
				+ ", predicatesList=" + predicatesList + ", output=" + output
				+ ", result=" + result + "]";
	}

}//end class Engine()
