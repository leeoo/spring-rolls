package org.paramecium.transaction;

import java.lang.reflect.Method;

import org.paramecium.aop.cglib.MethodProxy;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;
import org.paramecium.security.BaseSecurityInterceptor;
import org.paramecium.transaction.annotation.Transactional;
/**
 * 功 能 描 述:<br>
 * 事务拦截器实现
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-7下午03:58:54
 * <br>项 目 信 息:paramecium:org.paramecium.transaction.TransactionInterceptor.java
 */
public class TransactionInterceptor extends BaseSecurityInterceptor {
	
	private final static Log logger = LoggerFactory.getLogger();
	
	public Object intercept(Object service, Method method, Object[] parameters,MethodProxy proxy) throws Throwable {
		return super.intercept(service, method, parameters, proxy);
	}
	
	public Object nextIntercept(Object service, Method method, Object[] parameters, MethodProxy proxy) throws Throwable{
		Transactional serviceTransaction = service.getClass().getAnnotation(Transactional.class);
		if(serviceTransaction==null||TransactionManager.isThisThread()){
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
		}catch (Throwable e) {
			logger.error(e);
			TransactionManager.globalException();
			TransactionManager.end();
			throw new TransactionException(e.getMessage());
		}
		TransactionManager.end();
		return result;
	}

}
