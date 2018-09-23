package model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.exception.NullOrEmptyException;
import model.exception.PlayerStateInScenarioManagerException;
import model.exception.ScenarioNameAlreadyExistException;

class ScenarioManagerTest {

	private ScenarioManager scenarioManager;
	private Player playerTestCreateNewScenario;
	private String nameTestCreateNewScenario;
	private Player playerTestCreateNewScenarioWithAnotherScenarioUnderconstruction;
	private String nameTestCreateNewScenarioWithAnotherScenarioUnderconstruction;
	private Player playerTestEndScenarioWithoutScenarioUnderconstruction;
	private String nameTestEndScenarioWithoutScenarioUnderconstruction;
	
	@BeforeEach
	public void initialize() {
		scenarioManager = ScenarioManager.getInstance();
		playerTestCreateNewScenario = new Player("TestPlayerCreateNewScenario", -1);
		nameTestCreateNewScenario = "TestNameCreateNewScenario";
		playerTestCreateNewScenarioWithAnotherScenarioUnderconstruction = new Player("TestPlayerCreateNewScenarioWithAnotherScenarioUnderconstruction", -2);
		nameTestCreateNewScenarioWithAnotherScenarioUnderconstruction = "TestNameCreateNewScenarioWithAnotherScenarioUnderconstruction";
		playerTestEndScenarioWithoutScenarioUnderconstruction = new Player("TestPlayerEndScenarioWithoutScenarioUnderconstruction", -3);
		nameTestEndScenarioWithoutScenarioUnderconstruction = "TestNameEndScenarioWithoutScenarioUnderconstruction";
	}
	
	@Test
	public void createNewScenario() {
		try {
			ScenarioBuilder scenarioBuilder = scenarioManager.newScenario(playerTestCreateNewScenario);
			scenarioBuilder.setName(nameTestCreateNewScenario);
			Scenario scenarioDirect = scenarioBuilder.build();
			try {
				scenarioManager.endScenario(playerTestCreateNewScenario);
			} catch (ScenarioNameAlreadyExistException e) {
				fail("Player should have a scenario underconstruction");
			}
			Map<String, Scenario> allScenario = scenarioManager.getCopyScenarios();
			assertTrue(allScenario.keySet().contains(nameTestCreateNewScenario));
			Scenario scenarioIndirect = allScenario.get(nameTestCreateNewScenario);
			assertEquals(scenarioDirect, scenarioIndirect);
		} catch (PlayerStateInScenarioManagerException e) {
			fail("Another scenario is underconstruction.");
		} catch (NullOrEmptyException e) {
			fail("Scenario name should be defined.");
		}
	}
	
	@Test
	public void createNewScenarioWithAnotherScenarioUnderconstruction() {
		try {
			scenarioManager.newScenario(playerTestCreateNewScenarioWithAnotherScenarioUnderconstruction);
		} catch (PlayerStateInScenarioManagerException e1) {
			fail();
		}
		try {
			scenarioManager.newScenario(playerTestCreateNewScenarioWithAnotherScenarioUnderconstruction);
			fail("We should have an exception");
		} catch (PlayerStateInScenarioManagerException e) {
			
		}
	}
	
	@Test
	public void endScenarioWithoutScenarioUnderconstruction() {
		try {
			scenarioManager.endScenario(playerTestEndScenarioWithoutScenarioUnderconstruction);
			fail("Player should not be able to end a scenairo without having any underconstruction");
		} catch (NullOrEmptyException | ScenarioNameAlreadyExistException e) {
			fail("This exception shouldn't be raised.");
		} catch (PlayerStateInScenarioManagerException e) {
			try {
				ScenarioBuilder scenarioBuilder = scenarioManager.newScenario(playerTestEndScenarioWithoutScenarioUnderconstruction);
				scenarioBuilder.setName(nameTestEndScenarioWithoutScenarioUnderconstruction);
				scenarioManager.endScenario(playerTestEndScenarioWithoutScenarioUnderconstruction);
			} catch (NullOrEmptyException | ScenarioNameAlreadyExistException | PlayerStateInScenarioManagerException e1) {
				fail();
			}
			try {
				scenarioManager.endScenario(playerTestEndScenarioWithoutScenarioUnderconstruction);
				fail("Player should not be able to end a scenairo without having any underconstruction");
			} catch (NullOrEmptyException | ScenarioNameAlreadyExistException e2) {
				fail("This exception shouldn't be raised.");
			} catch (PlayerStateInScenarioManagerException e2) {
				
			}
		} 
	}	
}
