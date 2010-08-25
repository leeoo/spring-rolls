package com.wisdom.core.logger;

import java.util.Collection;

import com.wisdom.core.utils.Page;
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
	 * 批量保存日志信息
	 * @param loggers
	 * @throws Exception
	 */
	public void saveLoggers(Collection<String> loggers) throws Exception;
	
	/**
	 * 删除日志信息
	 * @param id
	 * @throws Exception
	 */
	public void deleteLogger(Long id) throws Exception;
	
	/**
	 * 获得所有分页的日志信息
	 * @param page
	 * @return
	 */
	public Page getAllLoggers(Page page);
	
}
