package org.paramecium.jdbc.typer;

import java.sql.SQLException;

public class ArrayTyper extends AbsTyper implements JdbcTyper{
	
	public ArrayTyper(Class<?> fieldClazz) {
		super(fieldClazz);
	}

	@Override
	public Object getValue(Object jdbcValue) {
		if(java.sql.Array.class.equals(fieldClazz)){
			java.sql.Array array = (java.sql.Array)jdbcValue;
			try {
				jdbcValue = array.getArray();
			} catch (SQLException e) {
				logger.error(e);
			}
		}
		return jdbcValue;
	}

}
