package org.paramecium.commons.typer;

import java.io.Reader;
import java.sql.SQLException;

public class ClobTyper extends AbsTyper implements JdbcTyper{

	public ClobTyper(Class<?> fieldClazz) {
		super(fieldClazz);
	}

	@Override
	public Object getValue(Object jdbcValue) {
		java.sql.Clob clob = (java.sql.Clob)jdbcValue;
		if(String.class.equals(fieldClazz)){
			try {
				jdbcValue = clob.getSubString(1l, (int)clob.length());
			} catch (SQLException e) {
				logger.error(e);
			}
		}else if(Reader.class.equals(fieldClazz)){
			try {
				jdbcValue = clob.getCharacterStream(1l, (int)clob.length());
			} catch (SQLException e) {
				logger.error(e);
			}
		}
		return jdbcValue;
	}

}
