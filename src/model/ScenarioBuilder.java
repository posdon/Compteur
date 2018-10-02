package model;

import model.exception.NullOrEmptyException;

public class ScenarioBuilder {

	private String name;
	
	public ScenarioBuilder() {
		name = null;
	}

	public String getName() {
		return name;
	}

	public ScenarioBuilder setName(String name) {
		this.name = name;
		return this;
	}
	
	public Scenario build() throws NullOrEmptyException {
		if(name != null && ! "".equals(name)) {
			return new Scenario(name);
		}
		throw new NullOrEmptyException("name");
	}
	
}
