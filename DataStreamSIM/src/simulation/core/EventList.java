package simulation.core;

import datastorage.structures.Tuple;

import java.util.ArrayList;

/**
 * The implementation of event list, the queue of events
 * 
 * @version 1.0
 * @author kostas
 */
public class EventList {
	
	private ArrayList<Tuple> eventQueue;
	
	private int curTimestamp;
	
	/**
	 * Constructor of class <class>EventList</class>.
	 */
	public EventList() {
		this.eventQueue = new ArrayList<Tuple>();
		this.curTimestamp = 0;
	}//end constructor EventList()
	
	/**
	 * Add a new event (tuple) in the event list. 
	 */
	public void addEvent(Tuple tuple, double ts) {
//		incCurTimestamp();
		tuple.setTimestamp(ts);//assign a timestamp to the tuple
		this.eventQueue.add(tuple);
	}//end method addEvent()
	
	public ArrayList<Tuple> getEventQueue() {
		return this.eventQueue;
	}
	
	/**
	 * Find the first tuple in the list; the one with 
	 * the highest priority.
	 * 
	 * @return The tuple.
	 */
	public Tuple getFirstEvent() {
		return this.eventQueue.get(0);
	}//end method getEvent()
	
	public Tuple getEvent(int index) {
		return this.eventQueue.get(index);
	}//end method getEvent()
	
	public void removeEvent(int index) {
		this.eventQueue.remove(index);
	}//end method removeEvent()
	
	public void incCurTimestamp() {
		this.curTimestamp++;
	}
	
	public int getTimestamp() {
		return this.curTimestamp;
	}

	/**
     * Textual representation.
	 */
	@Override
	public String toString() {
		return "EventList [eventQueue=" + eventQueue + "]";
	}
	
}//end class EventList
