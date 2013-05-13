package org.paramecium.commons.typer;

import java.math.BigInteger;

public class FloatTyper extends AbsTyper implements JdbcTyper{

	public FloatTyper(Class<?> fieldClazz) {
		super(fieldClazz);
	}

	@Override
	public Object getValue(Object jdbcValue) {
		if(Integer.class.equals(fieldClazz) || int.class.equals(fieldClazz)){
			jdbcValue = Integer.parseInt(jdbcValue.toString());
		}else if(Byte.class.equals(fieldClazz) || byte.class.equals(fieldClazz)){
			jdbcValue = Byte.parseByte(jdbcValue.toString());
		}else if(Short.class.equals(fieldClazz) || short.class.equals(fieldClazz)){
			jdbcValue = Short.parseShort(jdbcValue.toString());
		}else if(Long.class.equals(fieldClazz) || long.class.equals(fieldClazz)){
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
