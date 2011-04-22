package org.cy.core.transaction;

import java.lang.reflect.Method;
import java.sql.Connection;

import org.cy.core.aop.cglib.Enhancer;
import org.cy.core.aop.cglib.MethodInterceptor;
import org.cy.core.aop.cglib.MethodProxy;
import org.cy.core.transaction.annotation.Transactional;
/**
 * 功 能 描 述:<br>
 * 事务自动代理
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-7下午03:58:54
 * <br>项 目 信 息:paramecium:org.cy.core.transaction.TransactionAutoProxy.java
 */
public class TransactionAutoProxy {
	
	/**
	 * 获得需要代理的service实例
	 * @param serviceClass实现类类型
	 * @return
	 */
	public static Object getServiceInstance(Class<?> serviceClass){
		Object service = null;
		try{
			Enhancer enhancer = new Enhancer();
	        enhancer.setSuperclass(serviceClass);
	        enhancer.setCallback(new TransactionInterceptor());
	        service = enhancer.create();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return service;
	}
	
	private static class TransactionInterceptor implements MethodInterceptor{
		
		public TransactionInterceptor() {
			
		}
		
		public Object intercept(Object service, Method method, Object[] parameters, MethodProxy proxy) throws Throwable{
			boolean readOnly = false;
			int level = Connection.TRANSACTION_READ_COMMITTED;
			Transactional serviceTransaction = service.getClass().getAnnotation(Transactional.class);
			if(serviceTransaction==null){
				return proxy.invokeSuper(service, parameters);
			}
			readOnly = serviceTransaction.readOnly();
			level = serviceTransaction.transactionLevel();
			
			TransactionManager.begin();
			Transactional methodTransaction = method.getAnnotation(Transactional.class);
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
	
}
