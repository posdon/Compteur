package bot.command.compteur;

import bot.command.Command;
import bot.command.ExecutorType;
import bot.compteur.CompteurBotDiscord;
import net.dv8tion.jda.core.entities.MessageChannel;

public class BasicCommands {

	private final CompteurBotDiscord compteurBotDiscord;
	
	public BasicCommands(CompteurBotDiscord compteurBotDiscord) {
		this.compteurBotDiscord = compteurBotDiscord;
	}
	
	@Command(name="stop", description="Stop the bot Compteur", type=ExecutorType.CONSOLE)
	public void stopCompteur() {
		this.compteurBotDiscord.setRunning(false);
	}
	
	protected void sendMessage(String message, MessageChannel channel) {
		if(message == null || "".equals(message)) return;
		channel.sendMessage(message).complete();
	}
}
