package model.exception;

public class NullOrEmptyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NullOrEmptyException(String attribute) {
		this(attribute,"Unknown");
	}
	
	public NullOrEmptyException(String attribute, String className) {
		super("The attribute '"+attribute+"' form the class '"+className+"' is null or empty.");
	}
}
