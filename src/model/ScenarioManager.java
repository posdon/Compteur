package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.exception.HostAlreadyHostingException;
import model.exception.IllegalGameStateException;
import model.exception.NameAlreadyExistException;
import model.exception.NullOrEmptyException;
import model.exception.OutOfBoundException;

public class ScenarioManager {

	private Map<String, Scenario> scenarios;
	private List<ScenarioBuilder> scenariosUnderconstruction;
	private Map<String, ScenarioIG> scenariosRunning;
	
	private static ScenarioManager INSTANCE = null;
	
	public static ScenarioManager getInstance() {
		if(INSTANCE == null) INSTANCE = new ScenarioManager();
		return INSTANCE;
	}
	
	private ScenarioManager() {
		scenarios = new HashMap<>();
		scenariosUnderconstruction = new ArrayList<>();
		scenariosRunning = new HashMap<>();
	}
	
	
	/**
	 * Start a scenario creation.
	 */
	public void newCreation() {
		scenariosUnderconstruction.add(new ScenarioBuilder());
	}
	
	/**
	 * End a scenario creation.
	 * @param index
	 * @throws NullOrEmptyException
	 * @throws NameAlreadyExistException
	 * @throws OutOfBoundException
	 */
	public void endCreation(int index) throws NullOrEmptyException, NameAlreadyExistException, OutOfBoundException {
		ScenarioBuilder scenarioBuilder = getScenarioUnderconstruction(index);
		Scenario scenario = scenarioBuilder.build();
		String name = scenario.getName();
		if(scenarios.containsKey(name)) throw new NameAlreadyExistException(name, true);
		scenarios.put(name, scenario);
		scenariosUnderconstruction.remove(index);
	}
	
	/**
	 * Start a game instance.
	 * @param host
	 * @param scenarioName
	 * @throws HostAlreadyHostingException
	 * @throws NameAlreadyExistException
	 */
	public void newGame(String host, String scenarioName) throws HostAlreadyHostingException, NameAlreadyExistException {
		if(scenariosRunning.containsKey(host)) throw new HostAlreadyHostingException(host, true);
		if(!scenarios.containsKey(scenarioName)) throw new NameAlreadyExistException(scenarioName, false);
		ScenarioIG scenarioIG = new ScenarioIG(scenarios.get(scenarioName));
		scenariosRunning.put(host, scenarioIG);
	}
	
	
	public void startGame(String host) throws HostAlreadyHostingException, IllegalGameStateException {
		if(!scenariosRunning.containsKey(host)) throw new HostAlreadyHostingException(host, false);
		ScenarioIG scenarioIG = scenariosRunning.get(host);
		scenarioIG.start();
	}
	
	/**
	 * End a game instance.
	 * @param host
	 * @throws HostAlreadyHostingException
	 * @throws IllegalGameStateException 
	 */
	public void endGame(String host) throws HostAlreadyHostingException, IllegalGameStateException {
		if(!scenariosRunning.containsKey(host)) throw new HostAlreadyHostingException(host, false);
		ScenarioIG scenarioIG = scenariosRunning.get(host);
		scenarioIG.end();
		scenariosRunning.remove(host);
	}

	/**
	 * Get a specific scenario underconstruction.
	 * @param index
	 * @return
	 * @throws OutOfBoundException
	 */
	public ScenarioBuilder getScenarioUnderconstruction(int index) throws OutOfBoundException {
		if(scenariosUnderconstruction.size() <= index || index < 0) throw new OutOfBoundException();
		return scenariosUnderconstruction.get(index);
	}
	
	/**
	 * Get a specific scenario.
	 * @param name
	 * @return
	 * @throws HostAlreadyHostingException 
	 */
	public Scenario getScenario(String name) throws HostAlreadyHostingException {
		if(!scenarios.containsKey(name)) throw new HostAlreadyHostingException(name, false);
		return scenarios.get(name);
	}
	
	/**
	 * Get a specific game.
	 * @param name
	 * @return
	 * @throws HostAlreadyHostingException 
	 */
	public ScenarioIG getScenarioIG(String name) throws HostAlreadyHostingException {
		if(!scenariosRunning.containsKey(name)) throw new HostAlreadyHostingException(name, false);
		return scenariosRunning.get(name);
	}
	
	/**
	 * Get iterating data underconstruction.
	 * @return
	 */
	public int getUnderconstructionSize() {
		return scenariosUnderconstruction.size();
	}
	
	/**
	 * Get iterating data scenario.
	 * @return
	 */
	public Set<String> getScenarioKeySet() {
		return scenarios.keySet();
	}
	
	/**
	 * Get iterating data game.
	 * @return
	 */
	public Set<String> getScenarioRunningKeySet() {
		return scenariosRunning.keySet();
	}
}
