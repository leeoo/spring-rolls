package org.dily.log;

public class LogConfig {
	
	/**
	 * 判断是否通过控制输出
	 */
	public static boolean isConsole = false;
	
	/**
	 * 判断是否通过文件输出
	 */
	public static boolean isFile = false;
	
	/**
	 * 控制台日志级别
	 */
	public static int consoleLoggerLevel = Log.LEVEL_DEBUG;
	/**
	 * 文件日志级别
	 */
	public static int fileLoggerLevel = Log.LEVEL_WARN;
	
	/**
	 * 日志文件名
	 */
	public static String loggerFileName;
	
	/**
	 * 日志文件大小
	 */
	public static int loggerFileMax = 10;
	
	/**
	 * 是否启用sql语句格式化输出
	 */
	public static boolean sqlIsFormat = false;
	
}
