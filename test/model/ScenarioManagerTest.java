package model;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.exception.NameAlreadyExistException;
import model.exception.NullOrEmptyException;
import model.exception.OutOfBoundException;

class ScenarioManagerTest {

	private ScenarioManager scenarioManager = ScenarioManager.getInstance();
	private final String NAME_CLASSIC = "classic";
	private final String NAME_DOUBLE = "double";
	private final String NAME_EMPTY = "";
	
	@Test
	public void testSingleton() {
		ScenarioManager scenarioManager2 = ScenarioManager.getInstance();
		assertEquals(scenarioManager, scenarioManager2);
		scenarioManager2.newCreation();
		assertEquals(scenarioManager, scenarioManager2);
	}
	
	@Test
	public void creationClassic() {
		try {
			int deltaUnderconstruction = scenarioManager.getUnderconstructionSize();
			scenarioManager.newCreation();
			ScenarioBuilder scenarioBuilder = scenarioManager.getScenarioUnderconstruction(deltaUnderconstruction);
			scenarioBuilder.setName(NAME_CLASSIC);
			scenarioManager.endCreation(deltaUnderconstruction); 
			Scenario scenarioBuilt = scenarioBuilder.build();
			Scenario scenarioInManager = scenarioManager.getScenario(NAME_CLASSIC);
			assertEquals(scenarioBuilt, scenarioInManager);
		}catch(OutOfBoundException | NameAlreadyExistException | NullOrEmptyException e) {
			fail();
		}
	}
	
	@Test
	public void creationDouble() {
		int deltaUnderconstruction = scenarioManager.getUnderconstructionSize();
		try {
			scenarioManager.newCreation();
			ScenarioBuilder scenarioBuilder = scenarioManager.getScenarioUnderconstruction(deltaUnderconstruction);
			scenarioBuilder.setName(NAME_DOUBLE);
			scenarioManager.endCreation(deltaUnderconstruction);
		} catch(NullOrEmptyException |OutOfBoundException | NameAlreadyExistException e) {
			fail();
		} 
		
		try {
			scenarioManager.newCreation();
			ScenarioBuilder scenarioBuilder2 = scenarioManager.getScenarioUnderconstruction(deltaUnderconstruction);
			scenarioBuilder2.setName(NAME_DOUBLE);
			scenarioManager.endCreation(deltaUnderconstruction);
			fail();
		} catch(NullOrEmptyException | OutOfBoundException e) {
			fail();
		} catch (NameAlreadyExistException e) {
		}
	}

	@Test
	public void testOutOfBoundExceptionUnderconstruction() {
		int deltaUnderconstruction = scenarioManager.getUnderconstructionSize();
		try {
			scenarioManager.getScenarioUnderconstruction(deltaUnderconstruction);
			fail();
		} catch (OutOfBoundException e) {
		}
	}
	
	@Test
	public void testNameEmptyException() {
		try {
			int deltaUnderconstruction = scenarioManager.getUnderconstructionSize();
			scenarioManager.newCreation();
			ScenarioBuilder scenarioBuilder = scenarioManager.getScenarioUnderconstruction(deltaUnderconstruction);
			scenarioBuilder.setName(NAME_EMPTY);
			scenarioManager.endCreation(deltaUnderconstruction);
			fail();
		} catch(OutOfBoundException | NameAlreadyExistException e2) {
			fail();
		} catch (NullOrEmptyException e) {
		}
	}
}

