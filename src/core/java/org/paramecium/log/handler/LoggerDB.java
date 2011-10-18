package org.paramecium.log.handler;
/**
 * 功 能 描 述:<br>
 * 将系统日志持久化入数据库的接口
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-10-18下午02:35:42
 * <br>项 目 信 息:paramecium:org.paramecium.log.handler.LoggerDB.java
 */
public interface LoggerDB {
	
	/**
	 * 保存普通输出信息
	 * @param log
	 */
	public void saveLogger(String log);
	
	/**
	 * 保存错误及异常信息
	 * @param log
	 */
	public void saveErrorLogger(String log);
	
}
