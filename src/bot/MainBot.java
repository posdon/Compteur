package bot;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import bot.compteur.CompteurBotDiscord;

public class MainBot {
	
	private static Properties conf;
	
	private static void loadConfiguration() throws IOException {
		conf = new Properties();
		InputStream input = new FileInputStream("src/properties/conf.properties");
		conf.load(input);
	}

	public static void main(String[] args) throws IOException {
		loadConfiguration();
		
		CompteurBotDiscord compteurBot = new CompteurBotDiscord(conf.getProperty("token"));
		new Thread(compteurBot, "botDiscordCompteur").start();
	}
	
	
}
