package org.paramecium.commons.typer;

import java.sql.SQLException;

public class RefTyper extends AbsTyper implements JdbcTyper{

	public RefTyper(Class<?> fieldClazz) {
		super(fieldClazz);
	}

	@Override
	public Object getValue(Object jdbcValue) {
		java.sql.Ref ref = (java.sql.Ref)jdbcValue;
		if(Object.class.equals(fieldClazz)){
			try {
				jdbcValue = ref.getObject();
			} catch (SQLException e) {
				logger.error(e);
			}
		}
		return jdbcValue;
	}

}
