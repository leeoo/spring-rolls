package org.cy.core.transaction;

import java.lang.reflect.Method;

import org.cy.core.aop.cglib.MethodInterceptor;
import org.cy.core.aop.cglib.MethodProxy;
import org.cy.core.transaction.annotation.Transactional;
/**
 * 功 能 描 述:<br>
 * 事务拦截器
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-7下午03:58:54
 * <br>项 目 信 息:paramecium:org.cy.core.transaction.MethodInterceptor.java
 */
public class TransactionInterceptor implements MethodInterceptor{
	
	public TransactionInterceptor() {
	}
	
	public Object intercept(Object service, Method method, Object[] parameters, MethodProxy proxy) throws Throwable{
		Transactional serviceTransaction = service.getClass().getAnnotation(Transactional.class);
		if(serviceTransaction==null){
			return proxy.invokeSuper(service, parameters);
		}
		TransactionManager.begin();
		Transactional methodTransaction = method.getAnnotation(Transactional.class);
		boolean readOnly = serviceTransaction.readOnly();
		int level = serviceTransaction.transactionLevel();
		if(methodTransaction!=null){
			readOnly = methodTransaction.readOnly();
			level = methodTransaction.transactionLevel();
		}
		TransactionManager.setLevel(level);
		if(readOnly){
			TransactionManager.readOnly();
		}
		Object result = null;
		try{
			result = proxy.invokeSuper(service, parameters);
		}catch (Exception e) {
			e.printStackTrace();
			TransactionManager.globalException();
		}
		TransactionManager.end();
		return result;
	}

}
