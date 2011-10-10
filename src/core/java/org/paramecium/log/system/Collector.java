package org.paramecium.log.system;

import java.util.Collection;

/**
 * 功 能 描 述:<br>
 * 系统日志信息收集器
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-9-15上午10:24:17
 * <br>项 目 信 息:paramecium:org.paramecium.log.system.Collector.java
 */
public interface Collector<LOG extends Object> {
	
	/**
	 * 放入日志收集器队列
	 * @param log
	 */
	public void put(LOG log);
	
	/**
	 * 全部取出，取出后的日志会被清空
	 * @return
	 */
	public Collection<String> getAll();
	
}
