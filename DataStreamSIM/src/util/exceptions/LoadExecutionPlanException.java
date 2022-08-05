package util.exceptions;

/**
 * Exception for handling errors during 
 * the loading of the execution plan.
 * 
 * @author kostas
 * @version 1.0
 */
public class LoadExecutionPlanException extends Exception {
	public LoadExecutionPlanException(String msg){ 
		super(msg); 
    } 
}//end class LoadExecutionPlanException
