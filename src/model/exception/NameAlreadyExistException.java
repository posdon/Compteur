package model.exception;

public class NameAlreadyExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 944136947584427630L;
	
	public NameAlreadyExistException(String name, boolean isTrue) {
		super((isTrue) ? "Scenario's name '"+name+"' already exist." : "Scenario's name '"+name+"' doesn't exist.");
	}

}
