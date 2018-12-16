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
	
	public boolean isReady() {
		boolean stateIsOkay = GameState.BEFORE_START.equals(this.state);
		return stateIsOkay;
	}
	
	public boolean isEndable() {
		boolean stateIsOkay = GameState.RUNNING.equals(this.state);
		return stateIsOkay;
	}
	
	public void start() throws IllegalGameStateException {
		if(!isReady()) throw new IllegalGameStateException(true);
		this.state = GameState.RUNNING;
	}

	public void end() throws IllegalGameStateException {
		if(!isEndable()) throw new IllegalGameStateException(false);
		this.state = GameState.ENDED;
	}
	
	public GameState getState() {
		return this.state;
	}
	
}
