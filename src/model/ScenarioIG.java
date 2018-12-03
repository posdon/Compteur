package model;

import model.exception.IllegalGameStateException;

public class ScenarioIG {

	private final Scenario scenario;
	private GameState state;
	
	public ScenarioIG(Scenario scenario) {
		this.scenario = scenario;
		this.state = GameState.BEFORE_START;
	}

	public Scenario getScenario() {
		return scenario;
	}
	
	public void start() throws IllegalGameStateException {
		if(!GameState.BEFORE_START.equals(this.state)) throw new IllegalGameStateException(GameState.BEFORE_START, this.state);
		this.state = GameState.RUNNING;
	}
	
	public void end() throws IllegalGameStateException {
		if(!GameState.RUNNING.equals(this.state)) throw new IllegalGameStateException(GameState.RUNNING, this.state);
		this.state = GameState.ENDED;
	}
	
	public GameState getState() {
		return this.state;
	}
	
}
