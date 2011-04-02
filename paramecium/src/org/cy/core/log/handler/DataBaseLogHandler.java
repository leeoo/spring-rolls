package org.cy.core.log.handler;

import org.cy.core.log.LogHandler;
/**
 * 功 能 描 述:<br>
 * 日志存入数据库
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-2下午04:08:06
 * <br>项 目 信 息:paramecium:org.cy.core.log.handler.DataBaseLogHandler.java
 */
public class DataBaseLogHandler implements LogHandler{

	public void log(String message) {
		System.out.println(message);
	}

}
