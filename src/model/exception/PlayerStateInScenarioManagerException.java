package model.exception;

import model.Player;

public class PlayerStateInScenarioManagerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PlayerStateInScenarioManagerException(Player player) {
		super("The player '"+player.getID()+"' isn't into the good state. Check out if he have a scenario underconstruction.");
	}
}
