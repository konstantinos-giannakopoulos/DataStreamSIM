package simulation.policies.timing;

import simulation.core.SimulationParameters;

/**
 * Perform the real-time policy.
 * 
 * @version 1.0
 * @author kostas
 */
public class RealTimePolicy extends TimePolicy {
	
	private long captureCurrentTime;
	private double elapsedTime;
	private long startTupleProcessingTime;
//	private long endTupleProcessingTime;
	
	/**
	 * Constructor of class <class>RealTimePolicy</class>.
	 */
	public RealTimePolicy() {
		super();
		this.captureCurrentTime = 0;
		this.elapsedTime = 0;
	}//end constructor RealTimePolicy()
	
	public long getCaptureCurrentTime() {
		return captureCurrentTime;
	}
	
	public long getStartTupleProcessingTime() {
		return this.startTupleProcessingTime;
	}
	
	public void setElapsedTime() {
		long elapsedTime = System.nanoTime() - getCaptureCurrentTime();
		double seconds = (double)elapsedTime / 1000000000.0;
		
		this.elapsedTime = seconds;
	}
	
	public double getElapsedTime() {
		return this.elapsedTime;
	}
	
	public double currentTimeToDouble() {
		double time = getCaptureCurrentTime()/1000000000.0;
		return time;
	}
	
	@Override
	public double specifyTimeIntervalForIncomingTuple(SimulationParameters params) {
		double val = System.nanoTime()/100000000000000000.0;
		return val;
	}
	
	@Override
	public double storeCost() {
		setElapsedTime();
		return getElapsedTime();
	}
	
	@Override
	public double routeCost(int noStreams) {
		setElapsedTime();
		return getElapsedTime();
	}

	@Override
	public double predicateCost() {
		setElapsedTime();
		return getElapsedTime();
	}
	
	@Override
	public double computationalCost() {
		setElapsedTime();
		return getElapsedTime();
	}
	
	@Override
	public double probeCost() {
		return 0;
	}

	@Override
	public void startTupleProcessingTime() {
		this.startTupleProcessingTime = System.nanoTime();
	}

	@Override
	public double endTupleProcessingTime() {
		long time = System.nanoTime() - getStartTupleProcessingTime();
		double seconds = (double)time / 1000000000.0;
		
		return seconds;
	}

	@Override
	public double getSystemClock() {
		this.captureCurrentTime = System.nanoTime();
		double time = currentTimeToDouble();
		return time;
	}

	@Override
	public void setSystemClock(double systemClock) {
		//do-nothing
	}
	
	/**
     * Textual representation.
	 */
	@Override
	public String toString() {
		return "RealTimePolicy [captureCurrentTime=" + captureCurrentTime
				+ ", elapsedTime=" + elapsedTime
				+ ", startTupleProcessingTime=" + startTupleProcessingTime
				+ "]";
	}
	
}//end class RealTimePolicy
