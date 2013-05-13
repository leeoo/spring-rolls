package org.paramecium.commons.typer;

import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;

public interface JdbcTyper {
	
	public final static Log logger = LoggerFactory.getLogger();
	
	public Object getValue(Object jdbcValue);
	
}
