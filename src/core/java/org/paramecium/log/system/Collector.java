package org.paramecium.log.system;


/**
 * 功 能 描 述:<br>
 * 系统日志信息收集器
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-9-15上午10:24:17
 * <br>项 目 信 息:paramecium:org.paramecium.log.system.Collector.java
 */
public interface Collector {
	
	public final static String ANONYMOUS = "匿名用户";
	
	public final static String UNDEFIN = "(无描述)";
	
	/**
	 * 放入日志收集器队列
	 * @param log
	 */
	public void put(String log);
	
}
