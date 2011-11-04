package org.paramecium.log.system;
/**
 * 功 能 描 述:<br>
 * 日志收集器持久化接口
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-11-4下午02:45:49
 * <br>项 目 信 息:paramecium:org.paramecium.log.system.LogCollector.java
 */
public interface LogCollector {
	
	public void putBeanLog(String log);
	
	public void putJdbcLog(String log);
	
	public void putWebLog(String log);
	
}
