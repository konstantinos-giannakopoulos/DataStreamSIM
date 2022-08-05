package predicates;

/**
 * The implementation of an attribute-value predicate.
 * 
 * @version 1.0
 * @author kostas
 */
public class AttributeToValuePredicate {
	
	private String attribute;
	
	private String symbol;
	
	private int value;
	
	/**
	 * Constructor of class <class>AttributeToValuePredicate</class>.
	 */
	public AttributeToValuePredicate() {
//		super();
		this.attribute = new String();
		this.symbol = new String();
	}//end constructor AttributeToValuePredicate()
	
	/**
	 * Constructor of class <class>AttributeToValuePredicate</class>.
	 */
	public AttributeToValuePredicate(String strattribute, String strsymbol, int ival) {
		this.attribute = strattribute;
		this.symbol = strsymbol;
		this.value = ival;
	}//end constructor AttributeToValue()
	
	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	//@Override
//	public void parsePredicateInfodata() {
//		String infodata = getpredicateInfodata();
//		String[] fields = infodata.split(" ");
//		
//		setAttribute(fields[0]);
//		setSymbol(fields[1]);
//		setValue( Integer.parseInt(fields[2]) );
//	}

	/**
     * Textual representation.
	 */
	@Override
	public String toString() {
		return "AttributeToValuePredicate [attribute=" + attribute
				+ ", symbol=" + symbol + ", value=" + value + "]";
	}
	
}//end class AttributeToValue
