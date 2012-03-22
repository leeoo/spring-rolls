package com.demo.service.system;

import org.paramecium.ioc.annotation.Service;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.orm.GenericOrmDao;
import org.paramecium.transaction.annotation.Transactional;

import com.demo.entity.system.Log;
/**
 * 功 能 描 述:<br>
 * 系统日志持久化
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-12-9上午09:03:48
 * <br>项 目 信 息:paramecium:com.demo.service.system.LogService.java
 */
@Service
@Transactional
public class LogService {
	
	private GenericOrmDao<Log, Integer> ormDao = new GenericOrmDao<Log, Integer>(Log.class);
	
	public void save(Log log) throws Exception{
		ormDao.insert(log);
	}
	
	public void delete(String[] ids)throws Exception{
		for(String id : ids){
			ormDao.delete(Integer.parseInt(id));
		}
	}
	
	public Log get(Integer id){
		return ormDao.select(id);
	}
	
	public Page getAll(Page page,Log log){
		return ormDao.select(page, log);
	}

}
