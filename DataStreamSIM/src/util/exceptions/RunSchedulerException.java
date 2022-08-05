package util.exceptions;

/**
 * Exception for handling errors during 
 * the run of the scheduler.
 * 
 * @author kostas
 * @version 1.0
 */
public class RunSchedulerException extends Exception{
	public RunSchedulerException(String msg){ 
		super(msg); 
    } 
}//end class RunSchedulerException
