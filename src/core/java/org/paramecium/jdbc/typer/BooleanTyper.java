package org.paramecium.jdbc.typer;

public class BooleanTyper extends AbsTyper implements JdbcTyper{

	public BooleanTyper(Class<?> fieldClazz) {
		super(fieldClazz);
	}

	@Override
	public Object getValue(Object jdbcValue) {
		jdbcValue = Boolean.parseBoolean(jdbcValue.toString());
		if(int.class.equals(fieldClazz) || Integer.class.equals(fieldClazz)){//但实际实体类属性类型为int
			if(jdbcValue.equals(true)){
				jdbcValue = 1;
			}else{
				jdbcValue = 0;
			}
		}else if(short.class.equals(fieldClazz) || Short.class.equals(fieldClazz)){//但实际实体类属性类型为short
			if(jdbcValue.equals(true)){
				jdbcValue = Short.parseShort("1");
			}else{
				jdbcValue = Short.parseShort("0");
			}
		}else if(byte.class.equals(fieldClazz) || Byte.class.equals(fieldClazz)){//但实际实体类属性类型为byte
			if(jdbcValue.equals(true)){
				jdbcValue = Byte.parseByte("1");
			}else{
				jdbcValue = Byte.parseByte("0");
			}
		}else if(fieldClazz==String.class){
			if(jdbcValue.equals(true)){
				jdbcValue = Boolean.TRUE.toString();
			}else{
				jdbcValue = Boolean.FALSE.toString();
			}
		}
		return jdbcValue;
	}

}
