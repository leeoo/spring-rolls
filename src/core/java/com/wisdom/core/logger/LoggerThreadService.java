package com.wisdom.core.logger;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <b>业务说明</b>:
 * 日志异步存入数据库<br>
 * <b>作  者</b>: Cao.Yang<br>
 * <b>建立日期</b>: 2009-12-3<br>
 * <b>建立时间</b>: 下午12:55:14<br>
 * <b>项目名称</b>: spring<br>
 * <b>包及类名</b>: com.wisdom.core.logger.LoggerThreadService.java<br>
 */
@Service
public class LoggerThreadService implements Runnable{
	private final Logger logger=LoggerFactory.getLogger(getClass());
	
	private LoggerService loggerService;
	
	/**
	 * 异步执行任务入口
	 */
	public void run(){
		Collection<com.wisdom.core.logger.domain.Logger> loggers=LoggerCache.getAllCache();
		if(loggers==null||loggers.isEmpty()){
			return;
		}
		LoggerCache.removeAllCache();
		try{
			loggerService.saveLoggers(loggers);
			logger.info("日志信息批量保存成功!");
		}catch (Exception e) {
			logger.error("日志信息批量保存失败!{}",e.getMessage());
		}
	}

	public LoggerService getLoggerService() {
		return loggerService;
	}

	public void setLoggerService(LoggerService loggerService) {
		this.loggerService = loggerService;
	}
	
}
