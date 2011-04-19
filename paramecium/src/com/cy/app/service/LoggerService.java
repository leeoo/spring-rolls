package com.cy.app.service;

import java.sql.SQLException;

import org.cy.core.ioc.annotation.Service;
import org.cy.core.jdbc.dialect.Page;
import org.cy.core.orm.GenericOrmDao;
import org.cy.core.transaction.annotation.Transactional;

import com.cy.app.entity.Logger;
import com.cy.app.entity.LoggerWhere;

@Service
@Transactional
public class LoggerService {
	
	private GenericOrmDao<Logger, Integer> ormDao = new GenericOrmDao<Logger, Integer>("ds1", Logger.class);
	private GenericOrmDao<Logger, Integer> ormDao2 = new GenericOrmDao<Logger, Integer>("ds2", Logger.class);
	
	public int save(Logger logger) throws SQLException{
		return ormDao.insert(logger).intValue();
	}
	
	public void update(Logger logger) throws SQLException{
		ormDao.update(logger);
	}
	
	public void delete(int id) throws SQLException{
		ormDao.delete(id);
	}
	
	@Transactional(readOnly=true)
	public Logger get(int id){
		return ormDao.select(id);
	}
	
	@Transactional(readOnly=true)
	public Page getAll(Page page,LoggerWhere loggerWhere){
		return ormDao2.select(page,loggerWhere);
	}
	
	@Transactional(readOnly=true)
	public Page getAll(Page page){
		return ormDao2.select(page);
	}
	
}
