package util.exceptions;

/**
 * Exception for handling errors during 
 * the routing of a tuple amongst the predicates.
 * 
 * @author kostas
 * @version 1.0
 */
public class RoutingToPredicateException extends Exception {
	public RoutingToPredicateException(String msg){ 
		super(msg); 
    } 
}//end class RoutingToPredicateException
