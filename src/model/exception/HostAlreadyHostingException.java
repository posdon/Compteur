package model.exception;

public class HostAlreadyHostingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8757106671727711910L;
	
	public HostAlreadyHostingException(String hoster, boolean isTrue) {
		super((isTrue) ? "Hoster '"+hoster+"' is already hosting a game.":"Hoster '"+hoster+"' isn't hosting a game.");
	}

}
