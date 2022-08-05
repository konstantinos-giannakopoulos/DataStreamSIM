package util.exceptions;

/**
 * Exception for handling errors while collecting the data from the 
 * predicates; during the loading of the execution plan.
 * 
 * @author kostas
 * @version 1.0
 */
public class AttributeToValuePredicateException extends Exception {
	public AttributeToValuePredicateException(String msg){ 
		super(msg); 
    } 
}//end class LoadExecutionPlanException
