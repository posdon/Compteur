package model.exception;

public class NameAlreadyExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 944136947584427630L;
	
	public NameAlreadyExistException(String name) {
		super("Scenario's name '"+name+"' already exist.");
	}

}
