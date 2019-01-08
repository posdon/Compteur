package model;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import model.exception.HostAlreadyHostingException;
import model.exception.IllegalGameStateException;
import model.exception.NameAlreadyExistException;
import model.exception.NullOrEmptyException;
import model.exception.OutOfBoundException;

class ScenarioManagerTest {

	private static ScenarioManager scenarioManager = ScenarioManager.getInstance();
	private final String NAME_CREATION_CLASSIC = "classic";
	private final String NAME_CREATION_DOUBLE = "double";
	private final String NAME_EMPTY = "";
	private final String NAME_COMPTEUR = "compteur";
	private final static String NAME_GAME_CLASSIC = "game_classic";
	private final String NAME_HOSTER_COMPTEUR = "hoster_compteur";
	private final String NAME_HOSTER_CLASSIC = "hoster_classic";
	private final String NAME_HOSTER_DOUBLE = "hoster_double";
	
	
	@BeforeAll
	public static void setup() {
		try {
			createGame(NAME_GAME_CLASSIC);
		} catch (OutOfBoundException | NullOrEmptyException | NameAlreadyExistException e) {
			fail();
		}
	}
	
	@Test
	public void testSingleton() {
		ScenarioManager scenarioManager2 = ScenarioManager.getInstance();
		assertEquals(scenarioManager, scenarioManager2);
		scenarioManager2.newCreation();
		assertEquals(scenarioManager, scenarioManager2);
	}
	
	@Test
	public void testCreationClassic() {
		try {
			ScenarioBuilder scenarioBuilder = createGame(NAME_CREATION_CLASSIC);
			Scenario scenarioBuilt = scenarioBuilder.build();
			Scenario scenarioInManager = scenarioManager.getScenario(NAME_CREATION_CLASSIC);
			assertEquals(scenarioBuilt, scenarioInManager);
		}catch(OutOfBoundException | NameAlreadyExistException | NullOrEmptyException | HostAlreadyHostingException e) {
			fail();
		}
	}
	
	@Test
	public void testCreationDouble() {
		try {
			createGame(NAME_CREATION_DOUBLE);
		} catch(NullOrEmptyException |OutOfBoundException | NameAlreadyExistException e) {
			fail();
		} 
		
		try {
			createGame(NAME_CREATION_DOUBLE);
			fail();
		} catch(NullOrEmptyException | OutOfBoundException e) {
			fail();
		} catch (NameAlreadyExistException e) {
		}
	}
	
	@Test
	public void testCreationGameClassic() {
		try {
			scenarioManager.newGame(NAME_HOSTER_CLASSIC, NAME_GAME_CLASSIC);
			assertEquals(scenarioManager.getScenario(NAME_GAME_CLASSIC), scenarioManager.getScenarioIG(NAME_HOSTER_CLASSIC).getScenario());
		} catch (HostAlreadyHostingException | NameAlreadyExistException e) {
			fail();
		}
	}
	
	@Test
	public void testExceptionCreationGame() {
		try {
			scenarioManager.getScenarioIG(NAME_HOSTER_DOUBLE);
			fail();
		} catch (HostAlreadyHostingException e) {
		}
		try {
			scenarioManager.endGame(NAME_HOSTER_DOUBLE);
			fail();
		} catch (HostAlreadyHostingException e) {
		} catch (IllegalGameStateException e) {
			fail();
		}
		try {
			scenarioManager.newGame(NAME_HOSTER_DOUBLE, NAME_CREATION_DOUBLE);
			fail();
		} catch (NameAlreadyExistException e) {
		} catch (HostAlreadyHostingException e) {
			fail();
		}
		try {
			scenarioManager.newGame(NAME_HOSTER_DOUBLE, NAME_CREATION_CLASSIC);
		} catch (HostAlreadyHostingException | NameAlreadyExistException e) {
		}
		try {
			scenarioManager.newGame(NAME_HOSTER_DOUBLE, NAME_CREATION_CLASSIC);
		} catch (HostAlreadyHostingException e) {
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
			createGame(NAME_EMPTY);
			fail();
		} catch(OutOfBoundException | NameAlreadyExistException e2) {
			fail();
		} catch (NullOrEmptyException e) {
		}
	}
	
	@Test
	public void testCompteur() {
		try {
			int beforeCreationUnderconstruction = scenarioManager.getUnderconstructionSize();
			int beforeCreationScenario = scenarioManager.getScenarioKeySet().size();
			int beforeCreationGame = scenarioManager.getScenarioRunningKeySet().size();
			scenarioManager.newCreation();
			int duringCreationUnderconstruction = scenarioManager.getUnderconstructionSize();
			int duringCreationScenario = scenarioManager.getScenarioKeySet().size();
			int duringCreationGame = scenarioManager.getScenarioRunningKeySet().size();
			ScenarioBuilder scenarioBuilder = scenarioManager.getScenarioUnderconstruction(beforeCreationUnderconstruction);
			scenarioBuilder.setName(NAME_COMPTEUR);
			scenarioManager.endCreation(beforeCreationUnderconstruction);
			int afterCreationUnderconstruction = scenarioManager.getUnderconstructionSize();
			int afterCreationScenario = scenarioManager.getScenarioKeySet().size();
			int afterCreationGame = scenarioManager.getScenarioRunningKeySet().size();
			scenarioManager.newGame(NAME_HOSTER_COMPTEUR, NAME_COMPTEUR);
			int beforeUnderconstruction = scenarioManager.getUnderconstructionSize();
			int beforeScenario = scenarioManager.getScenarioKeySet().size();
			int beforeGame = scenarioManager.getScenarioRunningKeySet().size();	
			scenarioManager.startGame(NAME_HOSTER_COMPTEUR);
			scenarioManager.endGame(NAME_HOSTER_COMPTEUR);
			int afterUnderconstruction = scenarioManager.getUnderconstructionSize();
			int afterScenario = scenarioManager.getScenarioKeySet().size();
			int afterGame = scenarioManager.getScenarioRunningKeySet().size();
			// Compteur part : Underconstruction
			assertEquals(beforeCreationUnderconstruction+1, duringCreationUnderconstruction);
			assertEquals(duringCreationUnderconstruction-1, afterCreationUnderconstruction);
			assertEquals(afterCreationUnderconstruction, beforeUnderconstruction);
			assertEquals(beforeUnderconstruction, afterUnderconstruction);		
			// Compteur part : Scenario
			assertEquals(beforeCreationScenario, duringCreationScenario);
			assertEquals(duringCreationScenario+1, afterCreationScenario);
			assertEquals(afterCreationScenario, beforeScenario);
			assertEquals(beforeScenario, afterScenario);		
			// Compteur part : Game
			assertEquals(beforeCreationGame, duringCreationGame);
			assertEquals(duringCreationGame, afterCreationGame);
			assertEquals(afterCreationGame+1, beforeGame);
			assertEquals(beforeGame-1, afterGame);		
		} catch(NullOrEmptyException |OutOfBoundException | NameAlreadyExistException | HostAlreadyHostingException | IllegalGameStateException e) {
			fail();
		} 
	}
	
	private static ScenarioBuilder createGame(String gameName) throws OutOfBoundException, NullOrEmptyException, NameAlreadyExistException {
		int deltaUnderconstruction = scenarioManager.getUnderconstructionSize();
		scenarioManager.newCreation();
		ScenarioBuilder scenarioBuilder = scenarioManager.getScenarioUnderconstruction(deltaUnderconstruction);
		scenarioBuilder.setName(gameName);
		scenarioManager.endCreation(deltaUnderconstruction); 
		return scenarioBuilder;
	}
}

