package util.exceptions;

/**
 * Exception for handling errors while collecting the data 
 * for generating the graphs.
 * 
 * @author kostas
 * @version 1.0
 */
public class GraphDataNotFoundException extends Exception {
	public GraphDataNotFoundException(String msg){ 
		super(msg); 
    } 
}//end class GraphDataNotFoundException
