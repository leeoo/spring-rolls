package org.paramecium.commons.typer;

public class DateTyper extends AbsTyper implements JdbcTyper{

	public DateTyper(Class<?> fieldClazz) {
		super(fieldClazz);
	}

	@Override
	public Object getValue(Object jdbcValue) {
		if(java.util.Date.class.equals(fieldClazz)){
			jdbcValue = (java.util.Date)jdbcValue;
		}else if(java.sql.Date.class.equals(fieldClazz)){
			jdbcValue = (java.util.Date)jdbcValue;
		}else if((long.class.equals(fieldClazz)||Long.class.equals(fieldClazz))){
			jdbcValue = ((java.util.Date)jdbcValue).getTime();
		}
		return jdbcValue;
	}

}
