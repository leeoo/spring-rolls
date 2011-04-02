package org.cy.core.log;
/**
 * 功 能 描 述:<br>
 * 日志输出接口
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-2上午11:22:09
 * <br>项 目 信 息:paramecium:org.cy.core.log.Log.java
 */
public interface Log{
	
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
