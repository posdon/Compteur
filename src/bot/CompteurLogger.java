package bot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CompteurLogger {

	private String className;
	private final String dateFormat = "yyyy-MM-dd HH:mm:ss";
	
	public static CompteurLogger getLogger(String className) {
		return new CompteurLogger(className);
	}
	
	private CompteurLogger(String className) {
		this.className = className;
	}
	
	public void info(String message) {
		System.out.println(formateMessage(LoggerType.INFO, message));		
	}
	
	public void error(String message) {
		System.out.println(formateMessage(LoggerType.ERROR, message));		
	}
	
	public void warning(String message) {
		System.out.println(formateMessage(LoggerType.WARN, message));
	}
	
	private String formateMessage(LoggerType type, String message) {
		String result = type+" - "+LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateFormat))+" :: ";
		if (!LoggerType.INFO.equals(type)) {
			result += className+" :: ";
		}
		result += message;
		return result;
	}
	
	private enum LoggerType {
		INFO, WARN, ERROR;
	}
}
