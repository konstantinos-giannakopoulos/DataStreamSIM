package simulation.core;

import simulation.policies.timing.ProbablisticTimePolicy;
import simulation.policies.timing.RealTimePolicy;
import simulation.policies.timing.TimePolicy;
import simulation.statistics.Statistics;

/**
 * The implementation of system clock.
 * 
 * @version 1.0
 * @author kostas
 */
public class Clock {
	
	private TimePolicy timePolicy;
	
	private Statistics statistics;
	
	private String type;

	/**
	 * Constructor of class <class>Clock</class>.
	 */
	public Clock() {
		this.statistics = new Statistics();
		this.type = null;
	}//end constructor Clock()
	
	public TimePolicy getTimePolicy() {
		return timePolicy;
	}

	public void setTimePolicy(TimePolicy timePolicy) {
		this.timePolicy = timePolicy;
	}

	public Statistics getStatistics() {
		return statistics;
	}

	public void setStatistics(Statistics statistics) {
		this.statistics = statistics;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Initiallize the time policy according to the 
	 * configuration file, by calling the 
	 * constructor of the policy.
	 */
	public void initTimePolicy(String mode, SimulationParameters params) {
		if(getType().equals("probabilistic")) {
			this.timePolicy = new ProbablisticTimePolicy();
		}
		else if(getType().equals("real")) {
			this.timePolicy = new RealTimePolicy();
		}
		getTimePolicy().setSystemMode(mode);
		getTimePolicy().setSimulationParameters(params);
	}//end method initTimePolicy()

	/**
	 * Textual representation.
	 */
	@Override
	public String toString() {
		return "Clock [timePolicy=" + timePolicy + ", statistics=" + statistics
				+ ", type=" + type + "]";
	}

}//end class Clock
