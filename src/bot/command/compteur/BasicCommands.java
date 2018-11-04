package bot.command.compteur;

import bot.command.Command;
import bot.command.ExecutorType;
import bot.compteur.CompteurBotDiscord;
import net.dv8tion.jda.core.entities.MessageChannel;

public class BasicCommands {

	private final CompteurBotDiscord compteurBotDiscord;
	private final String helperMessage = "Compteur v1.0.0 command's list :\n"
			+"- !help : Show you the helper.";
	
	public BasicCommands(CompteurBotDiscord compteurBotDiscord) {
		this.compteurBotDiscord = compteurBotDiscord;
	}
	
	@Command(name="stop", description="Stop the bot Compteur", type=ExecutorType.CONSOLE)
	public void stopCompteur() {
		this.compteurBotDiscord.setRunning(false);
	}
	
	@Command(name="help", description="Print the helper", type=ExecutorType.USER)
	public void showHelper(MessageChannel channel) {
		sendMessage(helperMessage, channel);
	}
	
	private void sendMessage(String message, MessageChannel channel) {
		if(message == null || "".equals(message)) return;
		channel.sendMessage(message).complete();
	}
}
