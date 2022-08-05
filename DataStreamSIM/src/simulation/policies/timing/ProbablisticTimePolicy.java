package simulation.policies.timing;

import java.util.Random;

import simulation.core.SimulationParameters;
import util.constantsdef.ConstantsDefinition;

/**
 * Perform the probabilistic-time policy.
 * 
 * @version 1.0
 * @author kostas
 */
public class ProbablisticTimePolicy extends TimePolicy {
	
	private double startTupleProcessingTime;
	
	/**
	 * Constructor of class <class>ProbablisticTimePolicy</class>.
	 */
	public ProbablisticTimePolicy() {
		super();
		this.startTupleProcessingTime = 0;
	}//end constructor ProbablisticTimePolicy()
	
	public double getStartTupleProcessingTime() {
		return startTupleProcessingTime;
	}

	public void setStartTupleProcessingTime(double startTupleProcessingTime) {
		this.startTupleProcessingTime = startTupleProcessingTime;
	}

	@Override
	public double specifyTimeIntervalForIncomingTuple(SimulationParameters params) {
		Random ran = new Random();
//		int interval = ran.nextInt(2* params.getArrivalRate()) +1;
		double val = 1/params.getArrivalRate();
		double min = val/2;
		double max = val + min;
//		int interval = min + (int)(Math.random() * ((max - min) + 1));
		double interval = min + (double)(Math.random() * (max - min));
		return interval;
	}//end method specifyTimeIntervalForIncomingTuple()
	
	@Override
	public double storeCost() {
		double v = 0;
		//6.02*Math.pow(10, -7);
//		v = 6.0;
		v = 1.75*Math.pow(10,-7) + 4.27*Math.pow(10, -7); //hash + move
		return v;
	}//end method storeCost()
	
	@Override
	public double probeCost() {
		double v = 0;
//		v = 0.5;
		v = 4.91*Math.pow(10, -8); //comp
		return v;
	}//end method probeCost()
	
	@Override
	public double routeCost(int noStreams) {
		double v = 0;
		if(super.getSystemMode().equals(ConstantsDefinition.ADAPTIVE)) {
			double val = getSimulationParameters().getAvgRoutingCost();
			if(val > 0) {
//				v = val;
				double min = val/2;
				double max = val + min;
				v = min + (double)(Math.random() * (max - min));
			} else {
				//0.3*4.91*Math.pow(10, -8);
				v = (noStreams - 1)*(Math.log(noStreams - 1)/Math.log(2)*probeCost());
			}//end if-else
		} 
		return v;
	}//end method tupleRouteTime()

	@Override
	public double predicateCost() {
		double v = 0;
		
		return v;
	}//end method predicateCost()
	
	@Override
	public double computationalCost() {
		double v = 0;
		//4.91*Math.pow(10, -8) + 6.02*Math.pow(10, -7);
		v = probeCost() + storeCost();
		return v;
	}//end method computationalCost()

	@Override
	public void startTupleProcessingTime() {
		setStartTupleProcessingTime(getSystemClock());
	}//end method startTupleProcessingTime()

	@Override
	public double endTupleProcessingTime() {
		double v = 0;
		v = getSystemClock() - getStartTupleProcessingTime();
		return v;
	}//end method endTupleProcessingTime()

	@Override
	public double getSystemClock() {
		return this.systemClock;
	}

	@Override
	public void setSystemClock(double time) {
		this.systemClock = time;
	}

	/**
	 * Textual representation.
	 */
	@Override
	public String toString() {
		return "ProbablisticTimePolicy [startTupleProcessingTime="
				+ startTupleProcessingTime + "]";
	}
	
}//end class ProbablisticTimePolicy
