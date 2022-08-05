package simulation.policies.timing;
import dssystem.DSSystem;
import simulation.core.SimulationParameters;

/**
 * Abstract class for the time-policy of 
 * the system. 
 * It is initiallized in the {@link dssystem.DSSystem} class.
 * 
 * @version 1.0
 * @author kostas
 */
public abstract class TimePolicy {
	
	/* The time that the clock shows. */
	protected double systemClock;
	
	/* The system mode: static or adaptive. */
	private String systemMode;
	
	/* The parameters of the simulation. */
	private SimulationParameters simulationParameters;
	
	/**
	 * Constructor of class <class>TimePolicy</class>.
	 */
	public TimePolicy() {
		this.systemClock = 0;
//		this.simulationParameters = new SimulationParameters();
	}//end constructor TimePolicy()
	
	public SimulationParameters getSimulationParameters() {
		return simulationParameters;
	}
	
	public void setSimulationParameters(SimulationParameters simulationParameters) {
		this.simulationParameters = simulationParameters;
	}

	public String getSystemMode() {
		return systemMode;
	}

	public void setSystemMode(String systemMode) {
		this.systemMode = systemMode;
	}

	public abstract double getSystemClock();
	public abstract void setSystemClock(double time);

	public abstract double specifyTimeIntervalForIncomingTuple(SimulationParameters params);
	
	//costs for the processing of each tuple.
	public abstract double storeCost();
	public abstract double routeCost(int noStreams);
	public abstract double probeCost();
	public abstract double predicateCost();
	public abstract double computationalCost();
	
	public abstract void startTupleProcessingTime();
	public abstract double endTupleProcessingTime();

	/**
     * Textual representation.
	 */
	@Override
	public String toString() {
		return "TimePolicy [systemClock=" + systemClock
				+ ", simulationParameters=" + simulationParameters + "]";
	}
	
}//end class TimePolicy
