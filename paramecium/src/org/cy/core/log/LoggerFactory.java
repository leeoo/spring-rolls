package org.cy.core.log;

import java.util.HashMap;
import java.util.Map;

import org.cy.core.commons.DateUtils;
import org.cy.core.commons.PropertiesUitls;
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
	public static boolean sqlIsFormat = false;
	
	static{
		levelMap.put("trace", Log.LEVEL_TRACE);
		levelMap.put("debug", Log.LEVEL_DEBUG);
		levelMap.put("info", Log.LEVEL_INFO);
		levelMap.put("warn", Log.LEVEL_WARN);
		levelMap.put("error", Log.LEVEL_ERROR);
		levelMap.put("fatal", Log.LEVEL_FATAL);
		Map<String,String> properties = PropertiesUitls.get("/logger.properties");
		String loggerLevel = properties.get("loggerLevel");
		if(loggerLevel!=null){
			loggerLevel = loggerLevel.toLowerCase();
			currentLevel = levelMap.get(loggerLevel);
		}
		String loggerMode = properties.get("loggerMode");
		if(loggerMode!=null){
			loggerMode = loggerMode.toLowerCase();
			if(loggerMode.indexOf("file")>-1){
				logHandler = new FileLogHandler();
				loggerFileName = properties.get("loggerFileName");
			}else if(loggerMode.indexOf("db")>-1||loggerMode.indexOf("database")>-1){
				logHandler = new DataBaseLogHandler();
				loggerDbInterface = properties.get("loggerDbInterface");
			}else{
				logHandler = new ConsoleLogHandler();
			}
		}
		String sqlIsFormatStr = properties.get("sqlIsFormat");
		sqlIsFormat = sqlIsFormatStr != null ? Boolean.valueOf(sqlIsFormatStr) : false;
	}
	
	/**
	 * 获取打印日志用的信息
	 * @param message
	 * @param level
	 * @param className
	 * @return
	 */
	private static String getMessage(String message,String level,String className,String methodName){
		StringBuffer sb = new StringBuffer();
		sb.append(DateUtils.getCurrentDateTimeStr()).append(' ');
		sb.append(className).append('.').append(methodName);
		sb.append("\r\n");
		sb.append(level).append(":");
		sb.append(message);
		sb.append("\r\n");
		return sb.toString();
	}
	
	public static Log getLogger(){
		return (new LoggerFactory()).new Logger();
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
		
		public void debug(String message) {
			if(currentLevel <= Log.LEVEL_DEBUG){
				Exception exception = new Exception();
				String methodName = exception.getStackTrace()[1].getMethodName();// 获得调用者的方法名
				if(this.className==null){
					className = exception.getStackTrace()[1].getClassName();// 获得调用者的类名
				}
				logHandler.log(getMessage(message, "DEBUG", className,methodName),false);
			}
		}

		
		public void debug(Throwable throwable) {
			if(currentLevel <= Log.LEVEL_DEBUG){
				String methodName = throwable.getStackTrace()[1].getMethodName();// 获得调用者的方法名
				if(this.className==null){
					className = throwable.getStackTrace()[1].getClassName();// 获得调用者的类名
				}
				logHandler.log(getMessage(throwable.getMessage(), "DEBUG", className,methodName),false);
			}
		}

		
		public void error(String message) {
			if(currentLevel <= Log.LEVEL_ERROR){
				Exception exception = new Exception();
				String methodName = exception.getStackTrace()[1].getMethodName();// 获得调用者的方法名
				methodName = methodName+"("+exception.getStackTrace()[1].getFileName()+":"+exception.getStackTrace()[1].getLineNumber()+")";// 获得调用者的文件名和行号
				if(this.className==null){
					className = exception.getStackTrace()[1].getClassName();// 获得调用者的类名
				}
				logHandler.log(getMessage(message, "ERROR", className,methodName),true);
			}
		}

		
		public void error(Throwable throwable) {
			if(currentLevel <= Log.LEVEL_ERROR){
				String methodName = throwable.getStackTrace()[1].getMethodName();// 获得调用者的方法名
				methodName = methodName+"("+throwable.getStackTrace()[1].getFileName()+":"+throwable.getStackTrace()[1].getLineNumber()+")";// 获得调用者的文件名和行号
				if(this.className==null){
					className = throwable.getStackTrace()[1].getClassName();// 获得调用者的类名
				}
				logHandler.log(getMessage(throwable.getMessage(), "ERROR", className,methodName),true);
			}
		}

		
		public void fatal(String message) {
			if(currentLevel <= Log.LEVEL_FATAL){
				Exception exception = new Exception();
				String methodName = exception.getStackTrace()[1].getMethodName();// 获得调用者的方法名
				methodName = methodName+"("+exception.getStackTrace()[1].getFileName()+":"+exception.getStackTrace()[1].getLineNumber()+")";// 获得调用者的文件名和行号
				if(this.className==null){
					className = exception.getStackTrace()[1].getClassName();// 获得调用者的类名
				}
				logHandler.log(getMessage(message, "FATAL", className,methodName),true);
			}
		}

		public void fatal(Throwable throwable) {
			if(currentLevel <= Log.LEVEL_FATAL){
				String methodName = throwable.getStackTrace()[1].getMethodName();// 获得调用者的方法名
				methodName = methodName+"("+throwable.getStackTrace()[1].getFileName()+":"+throwable.getStackTrace()[1].getLineNumber()+")";// 获得调用者的文件名和行号
				if(this.className==null){
					className = throwable.getStackTrace()[1].getClassName();// 获得调用者的类名
				}
				logHandler.log(getMessage(throwable.getMessage(), "FATAL", className,methodName),true);
			}
		}

		public void info(String message) {
			if(currentLevel <= Log.LEVEL_INFO){
				Exception exception = new Exception();
				String methodName = exception.getStackTrace()[1].getMethodName();// 获得调用者的方法名
				if(this.className==null){
					className = exception.getStackTrace()[1].getClassName();// 获得调用者的类名
				}
				logHandler.log(getMessage(message, "INFO", className,methodName),false);
			}
		}

		public void info(Throwable throwable) {
			if(currentLevel <= Log.LEVEL_INFO){
				String methodName = throwable.getStackTrace()[1].getMethodName();// 获得调用者的方法名
				if(this.className==null){
					className = throwable.getStackTrace()[1].getClassName();// 获得调用者的类名
				}
				logHandler.log(getMessage(throwable.getMessage(), "INFO", className,methodName),false);
			}
		}

		public void trace(String message) {
			if(currentLevel <= Log.LEVEL_TRACE){
				Exception exception = new Exception();
				String methodName = exception.getStackTrace()[1].getMethodName();// 获得调用者的方法名
				if(this.className==null){
					className = exception.getStackTrace()[1].getClassName();// 获得调用者的类名
				}
				logHandler.log(getMessage(message, "TRACE", className,methodName),false);
			}
		}

		public void trace(Throwable throwable) {
			if(currentLevel <= Log.LEVEL_TRACE){
				String methodName = throwable.getStackTrace()[1].getMethodName();// 获得调用者的方法名
				if(this.className==null){
					className = throwable.getStackTrace()[1].getClassName();// 获得调用者的类名
				}
				logHandler.log(getMessage(throwable.getMessage(), "TRACE", className,methodName),false);
			}
		}
		
		public void warn(String message) {
			if(currentLevel <= Log.LEVEL_WARN){
				Exception exception = new Exception();
				String methodName = exception.getStackTrace()[1].getMethodName();// 获得调用者的方法名
				if(this.className==null){
					className = exception.getStackTrace()[1].getClassName();// 获得调用者的类名
				}
				logHandler.log(getMessage(message, "WARN", className,methodName),true);
			}
		}

		public void warn(Throwable throwable) {
			if(currentLevel <= Log.LEVEL_WARN){
				String methodName = throwable.getStackTrace()[1].getMethodName();// 获得调用者的方法名
				if(this.className==null){
					className = throwable.getStackTrace()[1].getClassName();// 获得调用者的类名
				}
				logHandler.log(getMessage(throwable.getMessage(), "WARN", className,methodName),true);
			}
		}
		
	}
	
}
