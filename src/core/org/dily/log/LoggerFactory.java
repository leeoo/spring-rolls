package org.dily.log;

import java.util.LinkedHashMap;
import java.util.Map;

import org.dily.commons.DateUtils;
import org.dily.commons.ExceptionUtils;
import org.dily.commons.PropertiesUitls;
import org.dily.log.handler.ConsoleLogHandler;
import org.dily.log.handler.FileLogHandler;

/**
 * 功 能 描 述:<br>
 * 日志创建
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-2下午03:07:28
 * <br>项 目 信 息:dily:org.dily.log.LoggerFactory.java
 */
public class LoggerFactory {
	
	private final static Map<String,Integer> levelMap = new LinkedHashMap<String,Integer>(){
		private static final long serialVersionUID = -5839983148167444784L;
	{
		put(Log.TRACE, Log.LEVEL_TRACE);
		put(Log.DEBUG, Log.LEVEL_DEBUG);
		put(Log.INFO, Log.LEVEL_INFO);
		put(Log.WARN, Log.LEVEL_WARN);
		put(Log.ERROR, Log.LEVEL_ERROR);
		put(Log.FATAL, Log.LEVEL_FATAL);
	}};
	private static LogHandler consoleLogHandler;
	private static LogHandler fileLogHandler;
	
	static{
		Map<String,String> properties = PropertiesUitls.get("/logger.properties");
		//注意：在某些情况下、某些服务器、某些编辑工具。会将properties的文件中第一行没有#注释的key读出带有？？？的字符，所以会失效。
		//建议在第一行加上注释#，哪怕什么也不写，血的教训。
		System.out.println("dily已装载logger.properties配置文件,日志功能将被启动!");
		{//控制台输出模式
			String loggerLevel = properties.get("consoleLoggerLevel");
			if(loggerLevel!=null){
				LogConfig.consoleLoggerLevel = levelMap.get(loggerLevel.toUpperCase());
				consoleLogHandler = new ConsoleLogHandler();
				LogConfig.isConsole = true;
				System.out.println("dily日志系统的控制台输出级别为"+loggerLevel);
			}
		}
		{//文件输出模式
			String loggerLevel = properties.get("fileLoggerLevel");
			if(loggerLevel!=null){
				LogConfig.fileLoggerLevel = levelMap.get(loggerLevel.toUpperCase());
				LogConfig.loggerFileName = properties.get("loggerFileName");
				if(LogConfig.loggerFileName==null){
					System.err.println("文件输出日志没有制定loggerFileName属性值!");
					System.exit(0);
				}
				String loggerFileMaxStr = properties.get("loggerFileMax");
				LogConfig.loggerFileMax = loggerFileMaxStr!=null?Integer.parseInt(loggerFileMaxStr):LogConfig.loggerFileMax;
				fileLogHandler = new FileLogHandler();
				LogConfig.isFile = true;
				System.out.println("dily日志系统的文件输出输出级别为"+loggerLevel);
			}
		}
		String sqlIsFormatStr = properties.get("sqlIsFormat");
		LogConfig.sqlIsFormat = sqlIsFormatStr != null ? Boolean.valueOf(sqlIsFormatStr) : false;
		properties.clear();
	}
	
	public static Map<String,Integer> getLoggerLevel(){
		return levelMap;
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

	/**
	 * 获取打印日志用的信息
	 * @param throwable
	 * @param level
	 * @param className
	 * @return
	 */
	private static String getMessage(Throwable throwable,String level,String className,String methodName){
		StringBuffer sb = new StringBuffer();
		sb.append(DateUtils.getCurrentDateTimeStr()).append(' ');
		sb.append(className).append('.').append(methodName);
		sb.append("\r\n");
		sb.append(level).append(":");
		sb.append(ExceptionUtils.getExceptionString(throwable));
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
	 * <br>项 目 信 息:dily:org.dily.log.LoggerFactory.java
	 */
	private final class Logger implements Log{
		
		private String className;
		
		public void error(String message) {
			putLog(Log.ERROR, message, true);
		}

		public void error(Throwable throwable) {
			putLog(Log.ERROR, throwable, true);
		}
		
		public void fatal(String message) {
			putLog(Log.FATAL, message, true);
		}

		public void fatal(Throwable throwable) {
			putLog(Log.FATAL, throwable, true);
		}
		
		public void warn(String message) {
			putLog(Log.WARN, message, true);
		}

		public void warn(Throwable throwable) {
			putLog(Log.WARN, throwable, true);
		}
		
		public void info(String message) {
			putLog(Log.INFO, message, false);
		}
		
		public void info(Throwable throwable) {
			putLog(Log.INFO, throwable, false);
		}

		public void trace(String message) {
			putLog(Log.TRACE, message, false);
		}

		public void trace(Throwable throwable) {
			putLog(Log.TRACE, throwable, false);
		}
		
		public void debug(String message) {
			putLog(Log.DEBUG, message, false);
		}

		
		public void debug(Throwable throwable) {
			putLog(Log.DEBUG, throwable, false);
		}
		
		private void putLog(String level,Throwable throwable,boolean isError){
			String methodName = null;
			if(isError){
				methodName = getMethodNameSpecial(throwable);
			}else{
				methodName = getMethodNameGeneral(throwable);
			}
			int levelN = levelMap.get(level);
			if(LogConfig.isConsole && LogConfig.consoleLoggerLevel <= levelN){
				consoleLogHandler.log(getMessage(throwable, level, className,methodName),isError);
			}
			if(LogConfig.isFile && LogConfig.fileLoggerLevel <= levelN){
				fileLogHandler.log(getMessage(throwable, level, className,methodName),isError);
			}
		}
		
		private void putLog(String level,String message,boolean isError){
			String methodName = null;
			if(isError){
				methodName = getMethodNameSpecial();
			}else{
				methodName = getMethodNameGeneral();
			}
			int levelN = levelMap.get(level);
			if(LogConfig.isConsole && LogConfig.consoleLoggerLevel <= levelN){
				consoleLogHandler.log(getMessage(message, level, className,methodName),isError);
			}
			if(LogConfig.isFile && LogConfig.fileLoggerLevel <= levelN){
				fileLogHandler.log(getMessage(message, level, className,methodName),isError);
			}
		}
		
		private String getMethodNameGeneral(){
			Exception exception = new Exception();
			String methodName = exception.getStackTrace()[3].getMethodName();// 获得调用者的方法名
			if(this.className==null){
				className = exception.getStackTrace()[3].getClassName();// 获得调用者的类名
			}
			return methodName;
		}

		private String getMethodNameGeneral(Throwable throwable){
			String methodName = throwable.getStackTrace()[3].getMethodName();// 获得调用者的方法名
			if(this.className==null){
				className = throwable.getStackTrace()[3].getClassName();// 获得调用者的类名
			}
			return methodName;
		}
		
		private String getMethodNameSpecial(){
			Exception exception = new Exception();
			String methodName = exception.getStackTrace()[3].getMethodName();// 获得调用者的方法名
			methodName = methodName+"("+exception.getStackTrace()[3].getFileName()+":"+exception.getStackTrace()[3].getLineNumber()+")";// 获得调用者的文件名和行号
			if(this.className==null){
				className = exception.getStackTrace()[3].getClassName();// 获得调用者的类名
			}
			return methodName;
		}

		private String getMethodNameSpecial(Throwable throwable){
			String methodName = throwable.getStackTrace()[3].getMethodName();// 获得调用者的方法名
			methodName = methodName+"("+throwable.getStackTrace()[3].getFileName()+":"+throwable.getStackTrace()[3].getLineNumber()+")";// 获得调用者的文件名和行号
			if(this.className==null){
				className = throwable.getStackTrace()[3].getClassName();// 获得调用者的类名
			}
			return methodName;
		}
		
	}
	
}
