package org.cy.core.test;

import java.sql.SQLException;

import org.cy.core.orm.GenericOrmDao;
import org.cy.core.transaction.TransactionManager;

public class LoggerService {
	static int i = 0;
	private GenericOrmDao<Logger, Integer> ormDao1 = new GenericOrmDao<Logger, Integer>("ds1",Logger.class);
	private GenericOrmDao<Logger, Integer> ormDao2 = new GenericOrmDao<Logger, Integer>("ds2",Logger.class);
	
	public void save(Logger logger) throws SQLException{
		TransactionManager.before();
		try{
			ormDao1.insert(logger);
			logger.setInfo("第二个");
			ormDao2.insert(logger);
			if(i++>=1){
				i = 0/0;
			}
		}catch (Exception e) {
			TransactionManager.globalException();
		}
		TransactionManager.end();
	}

	public void delete(Integer pk) throws SQLException{
		TransactionManager.before();
		try{
			ormDao1.delete(pk);
		}catch (Exception e) {
			TransactionManager.globalException();
		}
		TransactionManager.end();
	}

}
