package model.exception;

import model.GameState;

public class IllegalGameStateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5934422830954674514L;

	public IllegalGameStateException(GameState expected, GameState given) {
		super("Expected : "+expected+" ; Given : "+given);
	}
}
