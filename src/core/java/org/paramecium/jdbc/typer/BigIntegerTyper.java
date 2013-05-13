package org.paramecium.jdbc.typer;

import java.math.BigInteger;

public class BigIntegerTyper extends AbsTyper implements JdbcTyper{

	public BigIntegerTyper(Class<?> fieldClazz) {
		super(fieldClazz);
	}

	@Override
	public Object getValue(Object jdbcValue) {
		if(Long.class.equals(fieldClazz) || long.class.equals(fieldClazz)){
			jdbcValue = Long.parseLong(jdbcValue.toString());
		}else if(Double.class.equals(fieldClazz) || double.class.equals(fieldClazz)){
			jdbcValue = Double.parseDouble(jdbcValue.toString());
		}else if(Float.class.equals(fieldClazz) || float.class.equals(fieldClazz)){
			jdbcValue = Float.parseFloat(jdbcValue.toString());
		}else if(java.math.BigDecimal.class.equals(fieldClazz)){
			jdbcValue = new java.math.BigDecimal(jdbcValue.toString());
		}else if(java.math.BigInteger.class.equals(fieldClazz)){
			jdbcValue = new BigInteger(jdbcValue.toString());
		}
		return jdbcValue;
	}

}
