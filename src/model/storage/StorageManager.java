package model.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Set;
import java.util.logging.Logger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import model.Scenario;
import model.ScenarioBuilder;
import model.ScenarioManager;
import model.exception.NameAlreadyExistException;
import model.exception.NullOrEmptyException;
import model.exception.OutOfBoundException;
import model.util.Constante;

public class StorageManager {

	private static StorageManager INSTANCE = null;
	private ScenarioManager scenarioManager = ScenarioManager.getInstance();
	private static File folder = new File(Constante.STORAGE_PATH);
	private Logger logger = Logger.getLogger(this.getClass().getName());

	public static StorageManager getInstance() {
		if(INSTANCE == null) INSTANCE = new StorageManager();
		return INSTANCE;
	}
	
	private StorageManager() {
	}
	
	@SuppressWarnings("unchecked")
	private String convertScenarioToJson(Scenario scenario) {
		JSONObject result = new JSONObject();
		result.put("name", scenario.getName());
		return result.toString();
	}
	
	public void addFileToManager(File jsonFile) throws IOException, ParseException, OutOfBoundException, NullOrEmptyException, NameAlreadyExistException {
		int indiceScenario = scenarioManager.getUnderconstructionSize();
		scenarioManager.newCreation();
		ScenarioBuilder scenarioBuilder = scenarioManager.getScenarioUnderconstruction(indiceScenario);

		JSONParser parser = new JSONParser();
		String fileContent = readFile(jsonFile);
		JSONObject scenarioJSON = (JSONObject) parser.parse(fileContent);
		String scenarioName = (String) scenarioJSON.get("name");
		scenarioBuilder.setName(scenarioName);
		scenarioManager.endCreation(indiceScenario);
	}

	public String readFile(File jsonFile) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(jsonFile));
		String fileContent = "";
		String currentLine = reader.readLine();
		while(currentLine != null) {
			fileContent += currentLine;
			currentLine = reader.readLine();
		}
		reader.close();
		return fileContent;
	}
	
	public void addAllFileToManager() {
	    for (final File fileEntry : folder.listFiles()) {
	        if (!fileEntry.isDirectory() && fileEntry.getName().endsWith(Constante.SAVE_FILE_EXTENSION) && fileEntry.canRead()) {
	        	try {
					addFileToManager(fileEntry);
				} catch (IOException | ParseException | OutOfBoundException | NullOrEmptyException | NameAlreadyExistException e) {
					logger.info("Load failure for "+fileEntry.getName());
				}
	        }
	    }
	}
	
	public void saveScenarioAsFile(Scenario scenario) throws IOException {
		String scenarioJSON = convertScenarioToJson(scenario);
		File file = new File(Constante.STORAGE_PATH+"/"+scenario.getName()+Constante.SAVE_FILE_EXTENSION);
		file.createNewFile();
		Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
		writer.write(scenarioJSON);
		writer.close();
	}
	
	public void saveAllScenarioAsFile() {
		Set<String> scenariosName = scenarioManager.getScenarioKeySet();
		for(String scenarioName : scenariosName) {
			Scenario scenario = scenarioManager.getScenario(scenarioName);
			try {
				saveScenarioAsFile(scenario);
			} catch (IOException e) {
				logger.info("Save failure for "+scenarioName);
			}
		}
	}
	
	public static void main(String[] args) throws NullOrEmptyException, NameAlreadyExistException, OutOfBoundException {
		ScenarioManager scenarioManager = ScenarioManager.getInstance();
		StorageManager storageManager = StorageManager.getInstance();
		storageManager.addAllFileToManager();
		for(String scenarioName : scenarioManager.getScenarioKeySet()) {
			System.out.println("DONE "+scenarioName);
		}
		for(int i=0; i<scenarioManager.getUnderconstructionSize(); i++) {
			System.out.println("UNDERCONSTRUCTION "+scenarioManager.getScenarioUnderconstruction(i).getName());
		}
	}
}
