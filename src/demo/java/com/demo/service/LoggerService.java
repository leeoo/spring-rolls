package com.demo.service;

import java.sql.SQLException;

import org.paramecium.ioc.annotation.Service;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.orm.GenericOrmDao;
import org.paramecium.security.annotation.Security;
import org.paramecium.transaction.annotation.Transactional;

import com.demo.entity.Logger;
import com.demo.entity.LoggerWhere;

@Service
@Transactional
@Security
public class LoggerService {
	
	private GenericOrmDao<Logger, Integer> ormDao = new GenericOrmDao<Logger, Integer>("ds1", Logger.class);
	
	public int save(Logger logger) throws SQLException{
		return ormDao.insert(logger).intValue();
	}
	
	public void update(Logger logger) throws SQLException{
		ormDao.update(logger);
	}
	
	public void delete(int id) throws SQLException{
		ormDao.delete(id);
	}

	public void delete(String[] ids) throws SQLException{
		for(String id : ids){
			ormDao.delete(Integer.parseInt(id));
		}
	}
	
	@Transactional(readOnly=true)
	public Logger get(int id){
		return ormDao.select(id);
	}
	
	@Transactional(readOnly=true)
	public Page getAll(Page page,LoggerWhere loggerWhere){
		return ormDao.select(page,loggerWhere);
	}
	
	@Transactional(readOnly=true)
	public Page getAll(Page page){
		return ormDao.select(page);
	}
	
}
