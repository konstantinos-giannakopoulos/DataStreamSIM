package util.exceptions;

/**
 * Exception for handling errors during 
 * the parsing of the execution plan.
 * 
 * @author kostas
 * @version 1.0
 */
public class ParseExecutionPlanException extends Exception {
	public ParseExecutionPlanException(String msg){ 
		super(msg); 
    } 
}//end class ParseExecutionPlanException
