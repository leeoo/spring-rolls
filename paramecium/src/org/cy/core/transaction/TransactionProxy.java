package org.cy.core.transaction;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
/**
 * 功 能 描 述:<br>
 * 事务代理
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-7下午03:58:54
 * <br>项 目 信 息:paramecium:org.cy.core.transaction.TransactionProxy.java
 */
public class TransactionProxy {
	
	/**
	 * 获得需要代理的service实例
	 * @param serviceClass实现类类型
	 * @return
	 */
	public static Object getServiceInstance(Class<?> serviceClass){
		Object service = null;
		try{
			service = serviceClass.newInstance();
			service = Proxy.newProxyInstance(serviceClass.getClassLoader(), serviceClass.getInterfaces(), new TransactionHandler(service));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return service;
	}
	
	private static class TransactionHandler implements InvocationHandler{

		private Object service;
		
		public TransactionHandler(Object service){
			this.service = service;
		}
		
		public Object invoke(Object proxy, Method method, Object[] parms) throws Exception {
			TransactionManager.before();
			Object result = null;
			try{
				result = method.invoke(service, parms);
			}catch (Exception e) {
				e.printStackTrace();
				TransactionManager.globalException();
			}
			TransactionManager.end();
			return result;
		}

	}
	
}
