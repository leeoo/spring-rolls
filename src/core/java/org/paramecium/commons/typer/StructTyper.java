package org.paramecium.commons.typer;

import java.sql.SQLException;

public class StructTyper extends AbsTyper implements JdbcTyper{

	public StructTyper(Class<?> fieldClazz) {
		super(fieldClazz);
	}

	@Override
	public Object getValue(Object jdbcValue) {
		java.sql.Struct struct = (java.sql.Struct)jdbcValue;
		if(Object[].class.equals(fieldClazz)){
			try {
				jdbcValue = struct.getAttributes();
			} catch (SQLException e) {
				logger.error(e);
			}
		}
		return jdbcValue;
	}

}
