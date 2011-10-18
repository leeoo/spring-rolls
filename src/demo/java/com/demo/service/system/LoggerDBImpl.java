package com.demo.service.system;

import org.paramecium.log.handler.LoggerDB;
/**
 * 功 能 描 述:<br>
 * 
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-10-18下午02:54:59
 * <br>项 目 信 息:paramecium:com.demo.service.system.LoggerDBImpl.java
 */
public class LoggerDBImpl implements LoggerDB{
	
	public void saveErrorLogger(String log) {
		System.err.println("已经持久化："+log);
	}

	public void saveLogger(String log) {
		System.err.println("已经持久化："+log);
	}

}
