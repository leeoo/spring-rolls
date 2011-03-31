package org.cy.core.orm;

import java.sql.Connection;

import org.cy.core.jdbc.JdbcTemplate;
import org.cy.core.jdbc.JdbcTemplateFactory;

public class GenericOrmDao<T> {

	private JdbcTemplate jdbcTemplate;

	public GenericOrmDao(Connection connection) {
		jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate(connection);
	}
	
	public void save(T bean){
		
	}

}
