package com.demo.service.system;

import org.paramecium.ioc.ApplicationContext;
import org.paramecium.log.handler.LoggerDB;
/**
 * 功 能 描 述:<br>
 * 错误日志以及打印日志持久化
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-10-18下午02:54:59
 * <br>项 目 信 息:paramecium:com.demo.service.system.LoggerDBImpl.java
 */
public class LoggerDBImpl implements LoggerDB{
	
	public LoggerDBImpl() {
		try {
			TableInitService.createTables();
		} catch (Exception e) {
		}
	}
	
	public void saveErrorLogger(String log) {
		try {
			LogService logService = (LogService)ApplicationContext.getNotSecurityBean("logService");
			logService.save(LogCollectorImpl.getLog(log, 0));
		} catch (Exception e) {
		}
		ApplicationContext.destroy();
	}

	public void saveLogger(String log) {
	}

}
