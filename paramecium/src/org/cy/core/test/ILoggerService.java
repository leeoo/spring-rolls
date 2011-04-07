package org.cy.core.test;

import java.sql.SQLException;

public interface ILoggerService {

	public void save(Logger logger) throws SQLException ;
	public void delete(Integer pk) throws SQLException ;
	
}
