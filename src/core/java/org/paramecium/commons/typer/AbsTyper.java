package org.paramecium.commons.typer;

public abstract class AbsTyper {
	
	public Class<?> fieldClazz;
	
	@SuppressWarnings("unused")
	private AbsTyper(){
		throw new RuntimeException("不能使用默认构造方法!");
	}
	
	public AbsTyper(Class<?> fieldClazz){
		this.fieldClazz = fieldClazz;
	}
	
	
}
