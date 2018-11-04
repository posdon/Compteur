package bot.compteur;

import bot.command.CommandMap;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;

public class CompteurListener implements EventListener {

	private CommandMap commandMap;
	
	public CompteurListener(CompteurBotDiscord compteurBotDiscord) {
		commandMap = CommandMap.getCommandMap(compteurBotDiscord);
	}
	
	@Override
	public void onEvent(Event event) {
		if(event instanceof MessageReceivedEvent) onMessage((MessageReceivedEvent) event);
	}
	
	
	private void onMessage(MessageReceivedEvent event) {
		if(event.getAuthor().equals(event.getJDA().getSelfUser())) return;

		String message = event.getMessage().getContentDisplay();
		if(message.startsWith(commandMap.getTag())) {
			message = message.replaceFirst(commandMap.getTag(), "");
			commandMap.commandUser(event.getAuthor(), message, event.getMessage());
		}
	}
	
	public void onConsole(String message) {
		commandMap.commandConsole(message);
	}
	
}
