package simulation.brain;

import simulation.core.EventList;
import util.exceptions.RunEngineException;
import util.exceptions.RunSchedulerException;
import util.exceptions.RunSystemException;

/**
 * The interface specification of the 
 * <code>Engine</code> and the <code>Scheduler</code>
 * of the simulation.
 * 
 * @version 1.0
 * @author kostas
 */
public interface iDataStreamSIM {
	
	public void run(EventList eventList) throws RunEngineException, RunSchedulerException, RunSystemException;
	
	public void reset();
	
	public void terminate();
	
}//end interface iDataStream
