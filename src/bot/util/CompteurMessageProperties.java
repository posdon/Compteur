package bot.util;

public enum CompteurMessageProperties {

	ALREADY_HOSTING_GAME ("Sorry, you are already hosting a game."),
	UNKNOWN_GAME("Sorry, the game '@{0}' doesn't exist."),
	UNKNOWN_HOST("Sorry, you aren't hosting a game."),
	CANT_START("Sorry, the game can't be started. Verify you complete all the prerequisites"),
	CANT_END("Sorry, the game can't be ended. Verify you complete all the prerequisites."),
	GAME_CREATION("You are now hosting the game '@{0}'."),
	LAUNCH_GAME("You launch your hosted game @{0}."),
	END_GAME("Your hosted game @{0} is ended."),
	
	SCENARIO_INFO("@{0} : No description yet."),
	
	GAME_INFO_SCENARIO("You are hosting a game based on scenario '@{0}'."),
	GAME_INFO_WAITING(" The game isn't running yet."),
	GAME_INFO_RUNNING(" The game is running."),
	GAME_INFO_NONE("You aren't hosting any game");
	
	private String message;

	private CompteurMessageProperties(String message) {
		this.message = message;
	}
	
	public static String getMessage(CompteurMessageProperties message, String... params) {
		int indice = 0;
		String messageContent = message.message;
		for(String param : params) {
			messageContent = messageContent.replace("@{"+indice+"}", param);
			indice++;
		}
		return messageContent;
	}
	
	public static String getMessage(CompteurMessageProperties message) {
		return getMessage(message, "");
	}
}
