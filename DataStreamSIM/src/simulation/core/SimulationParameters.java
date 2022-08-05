package simulation.core;

/**
 * It contains the assumptions we have taken 
 * for the simulation of the frameworks.
 * 
 * @version 1.0
 * @author kostas
 */
public class SimulationParameters {
	
	private String param;
	
	private int testingno;
	
	private String emptyDir;

	/* The ratio of the incoming tuples. */
	private double arrivalRate;
	
	private double arrivalRateStep;
	
	/* The average routing time for the adaptive framework. */
	private double avgRoutingCost;
	
	private double avgRoutingCostStep;

	/**
	 * Constructor of class <class>Statistics</class>.
	 */
	public SimulationParameters() {
//		this.avgTupleArrivalTime = 4;
//		this.avgRoutingTime = 0;
	}
	
	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public int getTestingno() {
		return testingno;
	}

	public void setTestingno(int testingno) {
		this.testingno = testingno;
	}

	public String getEmptyDir() {
		return emptyDir;
	}

	public void setEmptyDir(String emptyDir) {
		this.emptyDir = emptyDir;
	}

	public double getArrivalRate() {
		return arrivalRate;
	}

	public void setArrivalRate(double arrivalRate) {
		this.arrivalRate = arrivalRate;
	}

	public double getAvgRoutingCost() {
		return avgRoutingCost;
	}

	public void setAvgRoutingCost(double avgRoutingCost) {
		this.avgRoutingCost = avgRoutingCost;
	}

	public double getArrivalRateStep() {
		return arrivalRateStep;
	}

	public void setArrivalRateStep(double arrivalRateStep) {
		this.arrivalRateStep = arrivalRateStep;
	}

	public double getAvgRoutingCostStep() {
		return avgRoutingCostStep;
	}

	public void setAvgRoutingCostStep(double avgRoutingCostStep) {
		this.avgRoutingCostStep = avgRoutingCostStep;
	}

	/**
     * Textual representation.
	 */
	@Override
	public String toString() {
		return "SimulationParameters [param=" + param + ", emptyDir="
				+ emptyDir + ", arrivalRate=" + arrivalRate
				+ ", arrivalRateStep=" + arrivalRateStep + ", avgRoutingCost="
				+ avgRoutingCost + ", avgRoutingCostStep=" + avgRoutingCostStep
				+ "]";
	}
	
}//end class SimulationParameters
