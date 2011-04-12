package org.cy.core.test;

import org.cy.core.orm.GenericOrmDao;
import org.cy.core.transaction.annotation.Transactional;

@Transactional
public class LoggerService{
	static int i = 0;
	private GenericOrmDao<Logger, Integer> ormDao1 = new GenericOrmDao<Logger, Integer>("ds1",Logger.class);
	private GenericOrmDao<Logger, Integer> ormDao2 = new GenericOrmDao<Logger, Integer>("ds2",Logger.class);
	
	public int save(Logger logger) throws Exception{
		int j = ormDao1.insert(logger).intValue();
		int i = ormDao2.insert(logger).intValue();
		return i+j;
	}

	public void delete(Integer pk) throws Exception{
		ormDao1.delete(pk);
	}

}
