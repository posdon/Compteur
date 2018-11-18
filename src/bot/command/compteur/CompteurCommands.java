package bot.command.compteur;

import java.lang.reflect.Method;

import bot.command.Command;
import bot.command.ExecutorType;
import bot.compteur.CompteurBotDiscord;
import model.ScenarioManager;
import model.storage.StorageManager;
import net.dv8tion.jda.core.entities.MessageChannel;

public class CompteurCommands extends BasicCommands {

	private ScenarioManager scenarioManager = ScenarioManager.getInstance();
	
	public CompteurCommands(CompteurBotDiscord compteurBotDiscord) {
		super(compteurBotDiscord);
		StorageManager.getInstance().addAllFileToManager();
	}
	
	@Command(name="listAllGame", type=ExecutorType.USER, description="List all the possible games")
	public void listAllGame(MessageChannel channel) {
		String message = "Ths is all disponible  games :";
		for(String scenarioName : scenarioManager.getScenarioKeySet()) {
			message += "\n- "+scenarioName;
		}
		sendMessage(message, channel);
	}
	
	@Command(name="help", type=ExecutorType.USER, description="List all the command helper")
	public void showHelper(MessageChannel mChannel) {
		String helperMessage = generateHelperMessage();
		sendMessage(helperMessage, mChannel);
	}
	
	private String generateHelperMessage() {
		String message = "This is all the command enable :";
		for(Method method : this.getClass().getMethods()) {
			if(method.isAnnotationPresent(Command.class)) {
				Command command = method.getAnnotation(Command.class);
				if(ExecutorType.USER.equals(command.type())) message += "\n - !"+command.name()+" : "+command.description();
			}
		}
		return message;
	}
}
