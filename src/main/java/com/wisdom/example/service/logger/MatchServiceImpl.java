package com.wisdom.example.service.logger;

import java.util.Collection;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wisdom.core.logger.LoggerMatch;
import com.wisdom.core.logger.MatchService;
import com.wisdom.core.logger.domain.LoggerSomething;
import com.wisdom.core.logger.domain.LoggerSomewhere;
import com.wisdom.core.orm.SimpleOrmGenericDao;
/**
 * 功 能 描 述:<br>
 * url地址关键字匹配
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2010-11-16上午09:42:04
 * <br>项 目 信 息:wisdom.3.0:com.wisdom.example.service.logger.MatchServiceImpl.java
 */
@Service
@Transactional
public class MatchServiceImpl implements MatchService{

	private SimpleOrmGenericDao<LoggerSomething, String> somethingDao;
	private SimpleOrmGenericDao<LoggerSomewhere, String> somewhereDao;
	
	@javax.annotation.Resource
	public void setDataSource(DataSource dataSource) {
		somethingDao = new SimpleOrmGenericDao<LoggerSomething, String>(dataSource,LoggerSomething.class);
		somewhereDao = new SimpleOrmGenericDao<LoggerSomewhere, String>(dataSource,LoggerSomewhere.class);
	}
	
	public void saveLoggerSomething(LoggerSomething something)throws Exception{
		somethingDao.save(something);
		LoggerMatch.put(something);
	}
	
	public void updateLoggerSomething(LoggerSomething something)throws Exception{
		somethingDao.update(something);
		LoggerMatch.remove(something.getKeyword());
		LoggerMatch.put(something);
	}
	
	public void deleteLoggerSomething(String keyword)throws Exception{
		somethingDao.delete(keyword);
		LoggerMatch.remove(keyword);
	}
	
	@Transactional(readOnly=true)
	public LoggerSomething getLoggerSomething(String keyword){
		return (LoggerSomething) LoggerMatch.get(keyword);
	}
	
	@Transactional(readOnly=true)
	public Collection<LoggerSomething> getAllLoggerSomething(){
		return somethingDao.getAll();
	}
	
	public void saveLoggerSomewhere(LoggerSomewhere somewhere)throws Exception{
		somewhereDao.save(somewhere);
	}
	
	public void updateLoggerSomewhere(LoggerSomewhere somewhere)throws Exception{
		somewhereDao.update(somewhere);
		LoggerMatch.remove(somewhere.getKeyword());
		LoggerMatch.put(somewhere);
	}
	
	public void deleteLoggerSomewhere(String keyword)throws Exception{
		somewhereDao.delete(keyword);
		LoggerMatch.remove(keyword);
	}

	@Transactional(readOnly=true)
	public LoggerSomewhere getLoggerSomewhere(String keyword){
		return (LoggerSomewhere) LoggerMatch.get(keyword);
	}
	
	@Transactional(readOnly=true)
	public Collection<LoggerSomewhere> getAllLoggerSomewhere(){
		return somewhereDao.getAll();
	}
	
	
}
