package org.paramecium.commons.typer;

public class ShortTyper extends AbsTyper implements JdbcTyper{

	public ShortTyper(Class<?> fieldClazz) {
		super(fieldClazz);
	}

	@Override
	public Object getValue(Object jdbcValue) {
		jdbcValue = Short.parseShort(jdbcValue.toString());
		if(boolean.class.equals(fieldClazz) || Boolean.class.equals(fieldClazz)){
			if(jdbcValue.equals(1)){
				jdbcValue = Boolean.TRUE;
			}else{
				jdbcValue = Boolean.FALSE;
			}
		}else if(Integer.class.equals(fieldClazz) || int.class.equals(fieldClazz)){
			jdbcValue = Integer.parseInt(jdbcValue.toString());
		}else if(Byte.class.equals(fieldClazz) || byte.class.equals(fieldClazz)){
			jdbcValue = Byte.parseByte(jdbcValue.toString());
		}else if(Long.class.equals(fieldClazz) || long.class.equals(fieldClazz)){
			jdbcValue = Long.parseLong(jdbcValue.toString());
		}
		return jdbcValue;
	}

}
