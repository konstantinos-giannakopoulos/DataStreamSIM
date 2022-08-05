package predicates;

/**
 * The implementation of a predicate.
 * 
 * @version 1.0
 * @author kostas
 */
public abstract class Predicate {
	
private String predicateInfodata;

	/**
	 * Constructor of class <class>Predicate</class>.
	 */
	public Predicate() {
		this.predicateInfodata = new String();
	}//end constructor Predicate()
	
	public void setpredicateInfodata(String info) {
		this.predicateInfodata = info;
	}
	
	public String getpredicateInfodata() {
		return this.predicateInfodata;
	}

	public abstract void parsePredicateInfodata();
}//end class Predicate
