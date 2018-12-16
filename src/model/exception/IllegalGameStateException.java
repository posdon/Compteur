package model.exception;

public class IllegalGameStateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5934422830954674514L;

	public IllegalGameStateException(boolean isStarting) {
		super((isStarting)?"The game can't be launch":"The game can't be end");
	}
}
