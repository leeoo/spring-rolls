package org.paramecium.commons.typer;

import java.io.InputStream;
import java.sql.SQLException;

public class BlobTyper extends AbsTyper implements JdbcTyper{

	public BlobTyper(Class<?> fieldClazz) {
		super(fieldClazz);
	}

	@Override
	public Object getValue(Object jdbcValue) {
		java.sql.Blob blob = (java.sql.Blob)jdbcValue;
		if(byte[].class.equals(fieldClazz)){
			try {
				jdbcValue = blob.getBytes(1l,(int)blob.length());
			} catch (SQLException e) {
				logger.error(e);
			}
		}else if(InputStream.class.equals(fieldClazz)){
			try {
				jdbcValue = blob.getBinaryStream(1l,(int)blob.length());
			} catch (SQLException e) {
				logger.error(e);
			}
		}
		return jdbcValue;
	}

}
