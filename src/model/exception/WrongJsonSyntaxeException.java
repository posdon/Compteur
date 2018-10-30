package model.exception;

public class WrongJsonSyntaxeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6533255633450209379L;

	public WrongJsonSyntaxeException(String expected, String given) {
		super("The JSON format is not as defined. Expected '"+expected+"' but got '"+given+"'.");
	}
}
