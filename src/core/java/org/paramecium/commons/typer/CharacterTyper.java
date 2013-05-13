package org.paramecium.commons.typer;

public class CharacterTyper extends AbsTyper implements JdbcTyper{

	public CharacterTyper(Class<?> fieldClazz) {
		super(fieldClazz);
	}

	@Override
	public Object getValue(Object jdbcValue) {
		if(char.class.equals(fieldClazz)||Character.class.equals(fieldClazz)){
			jdbcValue = jdbcValue.toString().charAt(0);
		}else if(String.class.equals(fieldClazz)){
			jdbcValue = jdbcValue.toString();
		}else if(boolean.class.equals(fieldClazz) || Boolean.class.equals(fieldClazz)){
			if(jdbcValue.equals(1)){
				jdbcValue = Boolean.TRUE;
			}else{
				jdbcValue = Boolean.FALSE;
			}
		}
		return jdbcValue;
	}

}
