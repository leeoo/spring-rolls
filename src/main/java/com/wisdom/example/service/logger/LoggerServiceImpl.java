package com.wisdom.example.service.logger;

import java.util.Collection;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wisdom.core.logger.LoggerService;
import com.wisdom.core.logger.domain.Logger;
import com.wisdom.core.logger.domain.LoggerSql;
import com.wisdom.core.orm.SimpleOrmGenericDao;
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
@Service("loggerService")
@Transactional
public class LoggerServiceImpl implements LoggerService{
	
	private SimpleOrmGenericDao<Logger, Long> ormDao;
	private SimpleOrmGenericDao<LoggerSql, Long> jdbcLoggerDao;
	
	@javax.annotation.Resource
	public void setDataSource(DataSource dataSource) {
		ormDao = new SimpleOrmGenericDao<Logger, Long>(dataSource,Logger.class);
		jdbcLoggerDao = new SimpleOrmGenericDao<LoggerSql, Long>(dataSource,LoggerSql.class);
	}
	
	/**
	 * 批量保存日志信息
	 * @param loggers
	 * @throws Exception
	 */
	public void saveLoggers(Collection<Logger> loggers) throws Exception{
		Logger[] loggerArray=loggers.toArray(new Logger[loggers.size()]);
		ormDao.saveAll(loggerArray);
	}
	
	/**
	 * 删除日志信息
	 * @param id
	 * @throws Exception
	 */
	public void deleteLogger(Long id)throws Exception{
		ormDao.delete(id);
	}
	
	/**
	 * 获得所有分页的日志信息
	 * @param page
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page getAllLoggers(Page page){
		return ormDao.getAll(page);
	}

	/**
	 * 存储sql
	 */
	public void saveLoggerSQLs(Collection<LoggerSql> loggers) throws Exception {
		LoggerSql[] loggerArray=loggers.toArray(new LoggerSql[loggers.size()]);
		jdbcLoggerDao.saveAll(loggerArray);
	}
	
	
}
