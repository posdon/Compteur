package model.exception;

public class ScenarioNameAlreadyExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ScenarioNameAlreadyExistException(String name) {
		super("The scenario named '"+name+"' already exist. Please change the name of the scenario.");
	}

}
