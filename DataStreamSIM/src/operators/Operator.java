package operators;

import simulation.structures.Selectivity;
import simulation.structures.Ticket;
import util.exceptions.AttributeToValuePredicateException;

/**
 * The implementation of an operator.
 * 
 * @version 1.0
 * @author kostas
 */
public abstract class Operator {
	
	private Selectivity selectivity;
	
	private Ticket ticket;
	
	protected float priority;
	
	private String opInfodata;
	
	/**
	 * Constructor of class <class>Operator</class>.
	 */
	public Operator() {
		this.selectivity = new Selectivity();
		this.ticket = new Ticket();
		this.priority = -1;
		this.opInfodata = new String();
	}//end constructor Operator()
	
	public void setOpInfodata(String info) {
		this.opInfodata = info;
	}
	
	public String getOpInfodata() {
		return this.opInfodata;
	}
	
//	public abstract void setSelectivity(Selectivity sel);
	
	public Selectivity getSelectivity(){
		return this.selectivity;
	}
	
	@Override
	public String toString() {
		return "Operator [selectivity=" + selectivity + ", ticket=" + ticket
				+ ", priority=" + priority + ", opInfodata=" + opInfodata + "]";
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public abstract void setPriority(float priority);
	
	public abstract float getPriority();
	
	public abstract void parseOpInfodata() throws AttributeToValuePredicateException;

}//end class Operator
