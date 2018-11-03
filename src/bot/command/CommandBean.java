package bot.command;

import java.lang.reflect.Method;

public class CommandBean {

	private final String name;
	private final String description;
	private final ExecutorType executorType;
	private final Object object;
	private final Method method;
	
	public CommandBean(String name, String description, bot.command.ExecutorType executorType, Object object,
			Method method) {
		super();
		this.name = name;
		this.description = description;
		this.executorType = executorType;
		this.object = object;
		this.method = method;
	}
	
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public ExecutorType getExecutorType() {
		return executorType;
	}
	public Object getObject() {
		return object;
	}
	public Method getMethod() {
		return method;
	}
	
}
