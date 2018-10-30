package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.exception.NameAlreadyExistException;
import model.exception.NullOrEmptyException;
import model.exception.OutOfBoundException;

public class ScenarioManager {

	private Map<String, Scenario> scenarios;
	private List<ScenarioBuilder> scenariosUnderconstruction;
	
	private static ScenarioManager INSTANCE = null;
	
	public static ScenarioManager getInstance() {
		if(INSTANCE == null) INSTANCE = new ScenarioManager();
		return INSTANCE;
	}
	
	private ScenarioManager() {
		scenarios = new HashMap<>();
		scenariosUnderconstruction = new ArrayList<>();
	}
	
	public void newCreation() {
		scenariosUnderconstruction.add(new ScenarioBuilder());
	}
	
	public ScenarioBuilder getScenarioUnderconstruction(int index) throws OutOfBoundException {
		if(scenariosUnderconstruction.size() <= index || index < 0) throw new OutOfBoundException();
		return scenariosUnderconstruction.get(index);
	}
	
	public Scenario getScenario(String name) {
		return scenarios.get(name);
	}
	
	public void endCreation(int index) throws NullOrEmptyException, NameAlreadyExistException, OutOfBoundException {
		ScenarioBuilder scenarioBuilder = getScenarioUnderconstruction(index);
		Scenario scenario = scenarioBuilder.build();
		String name = scenario.getName();
		if(scenarios.containsKey(name)) throw new NameAlreadyExistException(name);
		scenarios.put(name, scenario);
		scenariosUnderconstruction.remove(index);
	}
	
	public int getUnderconstructionSize() {
		return scenariosUnderconstruction.size();
	}
	
	public Set<String> getScenarioKeySet() {
		return scenarios.keySet();
	}
}
