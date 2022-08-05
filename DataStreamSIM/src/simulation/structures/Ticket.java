package simulation.structures;

/**
 * The implementation of the ticket that is 
 * used in the ticketing policy of the 
 * adaptive framework. 
 * 
 * @version 1.0
 * @author kostas
 */
public class Ticket {

	private int numOfTickets;
	
	/**
	 * Constructor of class <class>Ticket</class>.
	 */
	public Ticket() {
		this.numOfTickets = 0;
	}//end constructor Ticket()

	public int getNumOfTickets() {
		return numOfTickets;
	}

	public void setNumOfTickets(int numOfTickets) {
		this.numOfTickets = numOfTickets;
	}
	
	public void incNumOfTickets() {
		this.numOfTickets++;
	}
	
	public void decNumOfTickets() {
		this.numOfTickets--;
		if(this.numOfTickets < 0)
			this.numOfTickets = 0;
	}
	
	public void decNumOfTickets(int num) {
		this.numOfTickets -= num;
		if(this.numOfTickets < 0)
			this.numOfTickets = 0;
	}

	/**
     * Textual representation.
	 */
	@Override
	public String toString() {
		return "Ticket [numOfTickets=" + numOfTickets + "]";
	}
	
}
