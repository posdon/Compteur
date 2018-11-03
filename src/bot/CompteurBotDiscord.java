package bot;



import java.util.Scanner;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;


public class CompteurBotDiscord implements Runnable {

	private JDA jda;
	private String token;
	private CompteurLogger LOG = CompteurLogger.getLogger(this.getClass().getName());
	private boolean running;
	private Scanner scanner = new Scanner(System.in);
	
	
	public CompteurBotDiscord(String token) {
		this.token = token;
		
	}
	
	@Override
	public void run() {
		try {
			start();
		} catch (LoginException e) {
			running = false;
			LOG.error("An error occured while logging.");
			e.printStackTrace();
		}
		while(running) {
			if(scanner.hasNextLine() && "quit".equals(scanner.nextLine())) setRunning(false);
		}
		stop();
	}
	
	public void start() throws LoginException {
		LOG.info("Bot Compteur starting...");
		running = true;
		jda = new JDABuilder(AccountType.BOT).setToken(token).build();
		LOG.info("Bot Compteur started with the token '"+token+"'");
	}
	
	public void stop() {
		LOG.info("Bot Compteur will stop soon...");
		scanner.close();
		jda.shutdown();
		LOG.info("Bot Compteur shut down.");
	}
	
	public void setRunning(boolean running) {
		this.running = running;
	}
}