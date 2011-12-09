package org.paramecium.log;

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
	 * 判断是否通过数据库持久化
	 */
	public static boolean isDb = false;
	
	/**
	 * 控制台日志级别
	 */
	public static int consoleLoggerLevel = Log.LEVEL_DEBUG;
	/**
	 * 文件日志级别
	 */
	public static int fileLoggerLevel = Log.LEVEL_WARN;
	
	/**
	 * 数据库持久化日志级别
	 */
	public static int dbLoggerLevel = Log.LEVEL_ERROR;
	
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
	
	/**
	 * bean调用日志是否启用
	 */
	public static boolean beanLogCollector = false;
	
	/**
	 * jdbc执行日志是否启用
	 */
	public static boolean jdbcLogCollector = false;
	
	/**
	 * 不被过滤的表
	 */
	public static String notLogTableNames;

	/**
	 * 不被过滤的Bean
	 */
	public static String notLogBeans;
	
	/**
	 * log长度
	 */
	public static int logLength;
	
	/**
	 *  web请求日志是否启用
	 */
	public static boolean webLogCollector = false;

}
