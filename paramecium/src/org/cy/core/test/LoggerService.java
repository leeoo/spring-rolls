package org.cy.core.test;

import java.sql.SQLException;

import org.cy.core.orm.GenericOrmDao;

public class LoggerService implements ILoggerService{
	static int i = 0;
	private GenericOrmDao<Logger, Integer> ormDao1 = new GenericOrmDao<Logger, Integer>("ds1",Logger.class);
	private GenericOrmDao<Logger, Integer> ormDao2 = new GenericOrmDao<Logger, Integer>("ds2",Logger.class);
	
	public void save(Logger logger) throws SQLException{
		ormDao1.insert(logger);
		logger.setInfo("第二个");
		ormDao2.insert(logger);
	}

	public void delete(Integer pk) throws SQLException{
		ormDao1.delete(pk);
	}

}
