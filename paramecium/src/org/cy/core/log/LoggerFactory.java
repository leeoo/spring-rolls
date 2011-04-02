package org.cy.core.log;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.cy.core.commons.DateUtils;
import org.cy.core.log.handler.ConsoleLogHandler;
import org.cy.core.log.handler.DataBaseLogHandler;
import org.cy.core.log.handler.FileLogHandler;

/**
 * 功 能 描 述:<br>
 * 日志创建
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-2下午03:07:28
 * <br>项 目 信 息:paramecium:org.cy.core.log.LoggerFactory.java
 */
public class LoggerFactory {
	private static LogHandler logHandler;
	private static Map<String,Integer> levelMap = new HashMap<String,Integer>();
	private static int currentLevel = Log.LEVEL_INFO;
	public static String loggerFileName;
	public static String loggerDbInterface;
	
	static{
		levelMap.put("trace", Log.LEVEL_TRACE);
		levelMap.put("debug", Log.LEVEL_DEBUG);
		levelMap.put("info", Log.LEVEL_INFO);
		levelMap.put("warn", Log.LEVEL_WARN);
		levelMap.put("error", Log.LEVEL_ERROR);
		levelMap.put("fatal", Log.LEVEL_FATAL);
		Properties properties = new Properties();
		InputStream inputStream = System.class.getResourceAsStream("/logger.properties");
		try {
			properties.load(inputStream);
		} catch (IOException e) {}
		String loggerLevel = properties.getProperty("loggerLevel");
		if(loggerLevel!=null){
			loggerLevel = loggerLevel.toLowerCase();
			currentLevel = levelMap.get(loggerLevel);
		}
		String loggerMode = properties.getProperty("loggerMode");
		if(loggerMode!=null){
			loggerMode = loggerMode.toLowerCase();
			if(loggerMode.indexOf("file")>-1){
				logHandler = new FileLogHandler();
				loggerFileName = properties.getProperty("loggerFileName");
			}else if(loggerMode.indexOf("db")>-1||loggerMode.indexOf("database")>-1){
				logHandler = new DataBaseLogHandler();
				loggerDbInterface = properties.getProperty("loggerDbInterface");
			}else{
				logHandler = new ConsoleLogHandler();
			}
		}
	}
	
	/**
	 * 获取打印日志用的信息
	 * @param message
	 * @param level
	 * @param className
	 * @return
	 */
	private static String getMessage(String message,String level,String className){
		StringBuffer sb = new StringBuffer();
		sb.append(DateUtils.getCurrentDateTimeStr()).append(' ');
		sb.append(className).append("\r\n");
		sb.append(level).append(":");
		sb.append(message);
		sb.append("\r\n");
		return sb.toString();
	}
	
	public static Log getLogger(String name){
		return (new LoggerFactory()).new Logger(name);
	}

	public static Log getLogger(Class<?> clazz){
		return (new LoggerFactory()).new Logger(clazz.getName());
	}
	
	/**
	 * 功 能 描 述:<br>
	 * 日志实现类
	 * <br>代 码 作 者:曹阳(CaoYang)
	 * <br>开 发 日 期:2011-4-2下午04:07:51
	 * <br>项 目 信 息:paramecium:org.cy.core.log.LoggerFactory.java
	 */
	private final class Logger implements Log{
		private String className;
		
		public Logger(String className){
			this.className = className;
		}

		public void debug(String message) {
			if(currentLevel <= Log.LEVEL_DEBUG){
				logHandler.log(getMessage(message, "DEBUG", className),false);
			}
		}

		
		public void debug(Throwable throwable) {
			if(currentLevel <= Log.LEVEL_DEBUG){
				logHandler.log(getMessage(throwable.getMessage(), "DEBUG", className),false);
			}
		}

		
		public void error(String message) {
			if(currentLevel <= Log.LEVEL_ERROR){
				logHandler.log(getMessage(message, "ERROR", className),true);
			}
		}

		
		public void error(Throwable throwable) {
			if(currentLevel <= Log.LEVEL_ERROR){
				logHandler.log(getMessage(throwable.getMessage(), "ERROR", className),true);
			}
		}

		
		public void fatal(String message) {
			if(currentLevel <= Log.LEVEL_FATAL){
				logHandler.log(getMessage(message, "FATAL", className),true);
			}
		}

		public void fatal(Throwable throwable) {
			if(currentLevel <= Log.LEVEL_FATAL){
				logHandler.log(getMessage(throwable.getMessage(), "FATAL", className),true);
			}
		}

		public void info(String message) {
			if(currentLevel <= Log.LEVEL_INFO){
				logHandler.log(getMessage(message, "INFO", className),false);
			}
		}

		public void info(Throwable throwable) {
			if(currentLevel <= Log.LEVEL_INFO){
				logHandler.log(getMessage(throwable.getMessage(), "INFO", className),false);
			}
		}

		public void trace(String message) {
			if(currentLevel <= Log.LEVEL_TRACE){
				logHandler.log(getMessage(message, "TRACE", className),false);
			}
		}

		public void trace(Throwable throwable) {
			if(currentLevel <= Log.LEVEL_TRACE){
				logHandler.log(getMessage(throwable.getMessage(), "TRACE", className),false);
			}
		}
		
		public void warn(String message) {
			if(currentLevel <= Log.LEVEL_WARN){
				logHandler.log(getMessage(message, "WARN", className),true);
			}
		}

		public void warn(Throwable throwable) {
			if(currentLevel <= Log.LEVEL_WARN){
				logHandler.log(getMessage(throwable.getMessage(), "WARN", className),true);
			}
		}
	}
	
}
