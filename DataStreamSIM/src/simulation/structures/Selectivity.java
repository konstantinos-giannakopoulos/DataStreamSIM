package simulation.structures;

/**
 * The implementation of the selectivity of the operators. 
 * 
 * @version 1.0
 * @author kostas
 */
public class Selectivity {
	
	/* num of selected tuples. */
	private float received;
	
	/* num of total tuples. */
	private float sent;
	
	private double result;
	
	/**
	 * Constructor of class <class>Selectivity</class>.
	 */
	public Selectivity() {
		this.received = 1;
		this.sent = 1;
		this.result = 1;
	}//end constructor Selectivity()

	public float getReceived() {
		return received;
	}

	public void setReceived(float received) {
		this.received = received;
	}

	public float getSent() {
		return sent;
	}

	public void setSent(float sent) {
		this.sent = sent;
	}
	
	public void tuplePass() {
		this.received++;
		this.sent++;
		calculateResult();
	}
	
	public void tupleNotPass() {
		this.sent++;
		calculateResult();
	}
	
	public void setResult(double res) {
		this.result = res;
	}
	
	public double getResult() {
		calculateResult();
		return this.result;
	}
	
	public void calculateResult() {
		this.result = received/sent;
	}

	/**
     * Textual representation.
	 */
	@Override
	public String toString() {
		return "Selectivity [received=" + received + ", sent=" + sent
				+ ", result=" + result + "]";
	}
	
}
