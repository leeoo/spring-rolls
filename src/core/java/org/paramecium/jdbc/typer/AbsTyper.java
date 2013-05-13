package org.paramecium.jdbc.typer;
/**
 * 定义实现类需要用带参数的构造方法进行实例化
 * @author caoyang
 */
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
