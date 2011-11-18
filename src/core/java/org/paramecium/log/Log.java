package org.paramecium.log;
/**
 * 功 能 描 述:<br>
 * 日志输出接口
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-2上午11:22:09
 * <br>项 目 信 息:paramecium:org.paramecium.log.Log.java
 */
public interface Log{
	
	public static final int LEVEL_TRACE = 1;
	public static final int LEVEL_DEBUG = 3;
	public static final int LEVEL_INFO = 5;
	public static final int LEVEL_WARN = 7;
	public static final int LEVEL_ERROR = 9;
	public static final int LEVEL_FATAL = 11;

	public static final String TRACE = "TRACE";
	public static final String DEBUG = "DEBUG";
	public static final String INFO = "INFO";
	public static final String WARN = "WARN";
	public static final String ERROR = "ERROR";
	public static final String FATAL = "FATAL";
	
	/**
	 * 跟踪级别输出
	 * @param message
	 */
	public void trace(String message);

	/**
	 * 跟踪级别输出
	 * @param throwable
	 */
	public void trace(Throwable throwable);

	/**
	 * 调试级别输出
	 * @param message
	 */
	public void debug(String message);
	
	/**
	 * 调试级别输出
	 * @param throwable
	 */
	public void debug(Throwable throwable);
	
	/**
	 * 信息级别输出
	 * @param message
	 */
	public void info(String message);
	
	/**
	 * 信息级别输出
	 * @param throwable
	 */
	public void info(Throwable throwable);
	
	/**
	 * 警告级别输出
	 * @param message
	 */
	public void warn(String message);
	
	/**
	 * 警告级别输出
	 * @param throwable
	 */
	public void warn(Throwable throwable);
	
	/**
	 * 错误级别输出
	 * @param message
	 */
	public void error(String message);
	
	/**
	 * 错误级别输出
	 * @param throwable
	 */
	public void error(Throwable throwable);

	/**
	 * 致命级别输出
	 * @param message
	 */
	public void fatal(String message);
	
	/**
	 * 致命级别输出
	 * @param throwable
	 */
	public void fatal(Throwable throwable);
	
}
