package org.cy.core.aop;

import org.cy.core.aop.cglib.Enhancer;
import org.cy.core.aop.cglib.MethodInterceptor;
/**
 * 功能描述(Description):<br><b>
 * 利用cglib和asm进行类代理
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-4-24下午03:09:43</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.cy.core.aop.ClassProxy.java</b>
 */
public class ClassProxy {
	
	private Enhancer enhancer;
	
	public ClassProxy(MethodInterceptor interceptor,Class<?> clazz){
		enhancer = new Enhancer();
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(interceptor);
	}
	
	/**
	 * 获得实例
	 * @return
	 * @throws Throwable 
	 */
	public Object getClassInstance() throws RuntimeException{
		return enhancer.create();
	}
	
}
