package model;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.exception.NullOrEmptyException;

class ScenarioBuilderTest {

	private ScenarioBuilder scenarioBuilder;
	
	@BeforeEach
	public void initialize() {
		scenarioBuilder = new ScenarioBuilder();
	}
	
	@Test
	void testEmptyScenario() {
		try {
			scenarioBuilder.build();
			fail("Default ScenarioBuilder should not be able to build.");
		} catch(NullOrEmptyException e){
			assertNotNull(e.getMessage());
			assertTrue(e.getMessage().contains("name"));
		}
	}

	@Test
	void testNamedScenario() {
		try {
			String name = "Test";
			scenarioBuilder.setName(name);
			Scenario scenario = scenarioBuilder.build();
			assertNotNull(scenario);
			assertNotNull(scenario.getName());
			assertEquals(scenario.getName(),name);
		}catch(NullOrEmptyException e) {
			fail("Scenario built should keep the define name.");
		}
	}
	
}
