package model;

import java.util.HashMap;
import java.util.Map;

import model.exception.NullOrEmptyException;
import model.exception.PlayerStateInScenarioManagerException;
import model.exception.ScenarioNameAlreadyExistException;

public class ScenarioManager {

	private Map<String, Scenario> scenarios;
	private Map<Player, ScenarioBuilder> scenarioBuilders;
	
	private static ScenarioManager INSTANCE = null;
			
	private ScenarioManager() {
		scenarios = new HashMap<>();
		scenarioBuilders = new HashMap<>();
	}
	
	public static synchronized ScenarioManager getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new ScenarioManager();
		}
		return INSTANCE;
	}
	
	public boolean isCreating(Player player) {
		return scenarioBuilders.containsKey(player);
	}
	
	public ScenarioBuilder newScenario(Player player) throws PlayerStateInScenarioManagerException {
		if(isCreating(player)) throw new PlayerStateInScenarioManagerException(player);
		ScenarioBuilder scenarioBuilder = new ScenarioBuilder();
		scenarioBuilders.put(player, scenarioBuilder);
		return scenarioBuilder;
	}
	
	/**
	 * 
	 * @param player
	 * @throws NullOrEmptyException Case the scenario's name is null or empty
	 * @throws PlayerStateInScenarioManagerException Case the player doesn't have any scenario underconstruction
	 * @throws ScenarioNameAlreadyExistException Case the scenario's name already exist into the scenario's manager
	 */
	public void endScenario(Player player) throws NullOrEmptyException, PlayerStateInScenarioManagerException, ScenarioNameAlreadyExistException {
		if(!isCreating(player)) throw new PlayerStateInScenarioManagerException(player);
		ScenarioBuilder scenarioBuilder = scenarioBuilders.get(player);
		Scenario scenario = scenarioBuilder.build();
		String name = scenario.getName();
		if(scenarios.containsKey(name)) throw new ScenarioNameAlreadyExistException(name);
		scenarios.put(name, scenario);
		scenarioBuilders.remove(player);
	}
	
	public Map<String, Scenario> getCopyScenarios(){
		Map<String, Scenario> copyScenario = new HashMap<>();
		copyScenario.putAll(scenarios);
		return copyScenario;
	}
}
