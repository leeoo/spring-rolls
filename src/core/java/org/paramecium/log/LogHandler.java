package org.paramecium.log;
/**
 * 功 能 描 述:<br>
 * 日志存在形式接口
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-2下午04:09:24
 * <br>项 目 信 息:paramecium:org.paramecium.log.LogHandler.java
 */
public interface LogHandler {
	public void log(String message,boolean isError);
}
