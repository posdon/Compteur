package bot.command.compteur;

import java.lang.reflect.Method;

import bot.command.Command;
import bot.command.ExecutorType;
import bot.compteur.CompteurBotDiscord;
import bot.compteur.CompteurLogger;
import bot.util.CompteurMessageProperties;
import model.GameState;
import model.Scenario;
import model.ScenarioIG;
import model.ScenarioManager;
import model.exception.HostAlreadyHostingException;
import model.exception.IllegalGameStateException;
import model.exception.NameAlreadyExistException;
import model.storage.StorageManager;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class CompteurCommands extends BasicCommands {

	private ScenarioManager scenarioManager = ScenarioManager.getInstance();
	private CompteurLogger LOG = CompteurLogger.getLogger(this.getClass().getName());
	
	public CompteurCommands(CompteurBotDiscord compteurBotDiscord) {
		super(compteurBotDiscord);
		StorageManager.getInstance().addAllFileToManager();
	}
	
	@Command(name="listAllGame", type=ExecutorType.USER, description="List all the possible games")
	public void listAllGame(MessageChannel channel) {
		String message = "This is all disponible  games :";
		for(String scenarioName : scenarioManager.getScenarioKeySet()) {
			message += "\n- "+scenarioName;
		}
		sendMessage(message, channel);
	}
	
	@Command(name="infoGame", type=ExecutorType.USER, description="Give you a description of the given scenario")
	public void infoGame(MessageChannel channel, String scenarioName) {
		String message = "";
		try {
			Scenario scenario = scenarioManager.getScenario(scenarioName);
			message = CompteurMessageProperties.getMessage(CompteurMessageProperties.SCENARIO_INFO, scenario.getName());
		} catch (HostAlreadyHostingException e) {
			LOG.warning(e.getMessage());
			message = CompteurMessageProperties.getMessage(CompteurMessageProperties.UNKNOWN_GAME, scenarioName);
		}
		sendMessage(message, channel);
	}
	
	@Command(name="infoMyGame", type=ExecutorType.USER, description="Give you a description of the game you are hosting")
	public void infoMyGame(MessageChannel channel, User hoster) {
		String message = "";
		try {
			ScenarioIG scenarioIG = scenarioManager.getScenarioIG(hoster.getId());
			message = CompteurMessageProperties.getMessage(CompteurMessageProperties.GAME_INFO_SCENARIO, scenarioIG.getScenario().getName());
			if(GameState.BEFORE_START.equals(scenarioIG.getState())) {
				message += CompteurMessageProperties.getMessage(CompteurMessageProperties.GAME_INFO_WAITING);
			}else if(GameState.RUNNING.equals(scenarioIG.getState())) {
				message += CompteurMessageProperties.getMessage(CompteurMessageProperties.GAME_INFO_RUNNING);
			}
		} catch (HostAlreadyHostingException e) {
			LOG.warning(e.getMessage());
			message = CompteurMessageProperties.getMessage(CompteurMessageProperties.GAME_INFO_NONE);
		}
		sendMessage(message, channel);
	}
	
	@Command(name="newGame", type=ExecutorType.USER, description="Start a new game from a given scenario's name")
	public void newGame(MessageChannel channel, User hoster, String scenarioName) {
		String message = "";
		try {
			scenarioManager.newGame(hoster.getId(), scenarioName);
			message = CompteurMessageProperties.getMessage(CompteurMessageProperties.GAME_CREATION, scenarioName);
		} catch (HostAlreadyHostingException e) {
			LOG.warning(e.getMessage());
			message = CompteurMessageProperties.getMessage(CompteurMessageProperties.ALREADY_HOSTING_GAME, scenarioName);
		} catch (NameAlreadyExistException e) {
			LOG.warning(e.getMessage());
			message = CompteurMessageProperties.getMessage(CompteurMessageProperties.UNKNOWN_GAME, scenarioName);
		}
		sendMessage(message, channel);
	}
	
	@Command(name="startGame", type=ExecutorType.USER, description="Launch the game you are instancing")
	public void startGame(MessageChannel channel, User hoster) {
		String message = "";
		try {
			String scenarioName = scenarioManager.getScenarioIG(hoster.getId()).getScenario().getName();
			scenarioManager.startGame(hoster.getId());
			message = CompteurMessageProperties.getMessage(CompteurMessageProperties.LAUNCH_GAME, scenarioName);
		} catch (HostAlreadyHostingException e) {
			LOG.warning(e.getMessage());
			message = CompteurMessageProperties.getMessage(CompteurMessageProperties.UNKNOWN_HOST);
		} catch (IllegalGameStateException e) {
			LOG.warning(e.getMessage());
			message = CompteurMessageProperties.getMessage(CompteurMessageProperties.CANT_START);
		}
		sendMessage(message, channel);
	}
	
	@Command(name="endGame", type=ExecutorType.USER, description="Close the game you are instancing")
	public void closeGame(MessageChannel channel, User hoster) {
		String message = "";
		try {
			String scenarioName = scenarioManager.getScenarioIG(hoster.getId()).getScenario().getName();
			scenarioManager.endGame(hoster.getId());
			message = CompteurMessageProperties.getMessage(CompteurMessageProperties.END_GAME, scenarioName);
		} catch (HostAlreadyHostingException e) {
			LOG.warning(e.getMessage());
			message = CompteurMessageProperties.getMessage(CompteurMessageProperties.UNKNOWN_HOST);
		} catch(IllegalGameStateException e) {
			LOG.warning(e.getMessage());
			message = CompteurMessageProperties.getMessage(CompteurMessageProperties.CANT_END);
		}
		sendMessage(message, channel);
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
	
	@Command(name="help", type=ExecutorType.USER, description="List all the command helper")
	public void showHelper(MessageChannel mChannel) {
		String helperMessage = generateHelperMessage();
		sendMessage(helperMessage, mChannel);
	}
}
