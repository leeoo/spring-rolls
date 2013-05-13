package org.paramecium.commons.typer;

import java.sql.SQLException;

public class NClobTyper extends AbsTyper implements JdbcTyper{

	public NClobTyper(Class<?> fieldClazz) {
		super(fieldClazz);
	}

	@Override
	public Object getValue(Object jdbcValue) {
		java.sql.NClob clob = (java.sql.NClob)jdbcValue;
		if(java.sql.NClob.class.equals(fieldClazz)){
			try {
				jdbcValue = clob.getSubString(1l, (int)clob.length());
			} catch (SQLException e) {
				logger.error(e);
			}
		}else if(java.io.Reader.class.equals(fieldClazz)){
			try {
				jdbcValue = clob.getCharacterStream(1l, (int)clob.length());
			} catch (SQLException e) {
				logger.error(e);
			}
		}
		return jdbcValue;
	}

}
