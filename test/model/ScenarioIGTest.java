package model;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.exception.IllegalGameStateException;

class ScenarioIGTest {

	private final String SCENARIO_NAME = "SCENARIO_NAME";
	private Scenario scenario = new Scenario(SCENARIO_NAME);
	
	@Test
	public void testGameStateWorkflow() {
		try {
			ScenarioIG scenarioIG = new ScenarioIG(scenario);
			assertEquals(GameState.BEFORE_START, scenarioIG.getState());
			scenarioIG.start();
			assertEquals(GameState.RUNNING, scenarioIG.getState());
			scenarioIG.end();
			assertEquals(GameState.ENDED, scenarioIG.getState());	
		}catch (IllegalGameStateException e) {
			fail();
		}
	}

	@Test
	public void testGameStateException() {
		ScenarioIG scenarioIG = new ScenarioIG(scenario);
		try {
			scenarioIG.end();
			fail();
		}catch (IllegalGameStateException e) {
		}
		try {
			scenarioIG.start();
		} catch (IllegalGameStateException e) {
			fail();
		}
		try {
			scenarioIG.start();
			fail();
		} catch (IllegalGameStateException e) {
		}
		try {
			scenarioIG.end();
		} catch (IllegalGameStateException e) {
			fail();
		}
		try {
			scenarioIG.start();
			fail();
		} catch (IllegalGameStateException e) {
		}
		try {
			scenarioIG.end();
			fail();
		} catch (IllegalGameStateException e) {
		}
	}
	
}
