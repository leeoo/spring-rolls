package com.demo.service.system;

import org.paramecium.commons.DateUtils;
import org.paramecium.ioc.ApplicationContext;
import org.paramecium.log.system.LogCollector;

import com.demo.entity.system.Log;
/**
 * 功 能 描 述:<br>
 * 系统日志收集
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-12-9上午08:33:38
 * <br>项 目 信 息:paramecium:com.demo.service.system.LogCollectorImpl.java
 */
public class LogCollectorImpl implements LogCollector {
	
	public void putBeanLog(String log) {
		try {
			LogService logService = (LogService)ApplicationContext.getNotSecurityBean("logService");
			logService.save(LogCollectorImpl.getLog(log, 1));
		} catch (Exception e) {
		}
		ApplicationContext.destroy();
	}
	
	public void putJdbcLog(String log) {
		try {
			LogService logService = (LogService)ApplicationContext.getNotSecurityBean("logService");
			logService.save(LogCollectorImpl.getLog(log, 3));
		} catch (Exception e) {
		}
		ApplicationContext.destroy();
	}

	public void putWebLog(String log) {
		try {
			LogService logService = (LogService)ApplicationContext.getNotSecurityBean("logService");
			logService.save(LogCollectorImpl.getLog(log, 7));
		} catch (Exception e) {
		}
		ApplicationContext.destroy();
	}
	
	public static Log getLog(String log,Integer type){
		Log logger = new Log();
		logger.setLog(log);
		logger.setType(type);
		logger.setDate(DateUtils.getCurrentDateTime());
		return logger;
	}
	

}
