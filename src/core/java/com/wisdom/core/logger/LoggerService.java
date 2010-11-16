package com.wisdom.core.logger;

import java.util.Collection;

import com.wisdom.core.logger.domain.Logger;
import com.wisdom.core.logger.domain.LoggerSql;
/**
 * <b>业务说明</b>:<br>
 * 日志操作业务类
 * <br><b>作  者</b>: Cao.Yang<br>
 * <b>建立日期</b>: 2009-12-3<br>
 * <b>建立时间</b>: 上午11:43:50<br>
 * <b>项目名称</b>: spring<br>
 * <b>包及类名</b>: com.wisdom.core.logger.serviceLoggerService.java<br>
 */
public interface LoggerService {
	
	/**
	 * 批量保存web请求日志信息
	 * @param loggers
	 * @throws Exception
	 */
	public void saveLoggers(Collection<Logger> loggers) throws Exception;

	/**
	 * 批量保存数据库日志信息
	 * @param loggers
	 * @throws Exception
	 */
	public void saveLoggerSQLs(Collection<LoggerSql> loggers) throws Exception;
	
}
