package org.cy.core.transaction;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.cy.core.aop.cglib.Callback;
import org.cy.core.aop.cglib.Enhancer;
import org.cy.core.aop.cglib.MethodInterceptor;
import org.cy.core.aop.cglib.MethodProxy;
import org.cy.core.ioc.annotation.Service;
import org.cy.core.mvc.annotation.Controller;
import org.cy.core.mvc.annotation.MappingMethod;
import org.cy.core.security.Resource;
import org.cy.core.security.SecurityThread;
import org.cy.core.security.UserDetails;
import org.cy.core.security.annotation.Security;
import org.cy.core.security.exception.AnonymousException;
import org.cy.core.security.exception.AuthorizationException;
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
	        Callback[] callbacks = {new TransactionInterceptor(),new SecurityInterceptor()};
	        enhancer.setCallbacks(callbacks);/
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
	
	private static class SecurityInterceptor implements MethodInterceptor{
		
		private final static ConcurrentMap<String, Resource> mappingResources = new ConcurrentHashMap<String, Resource>();
		
		public SecurityInterceptor() {
			
		}
		
		public Object intercept(Object service, Method method, Object[] parameters, MethodProxy proxy) throws Throwable{
			Security classSecurity = service.getClass().getAnnotation(Security.class);
			Security methodSecurity = method.getClass().getAnnotation(Security.class);
			if(classSecurity!=null&&classSecurity.protecting()){
				if(methodSecurity!=null&&!methodSecurity.protecting()){
					return proxy.invokeSuper(service, parameters);
				}
				UserDetails user = SecurityThread.get();
				if(user==null){
					throw new AnonymousException("匿名用户没有登录！");
				}
				Resource resource = buildResource(service.getClass(), method);
				for(Resource userResource:user.getResources()){
					if(userResource.equals(resource)){
						return proxy.invokeSuper(service, parameters);
					}
				}
				throw new AuthorizationException("该资源没有对该用户授权！");
			}
			return proxy.invokeSuper(service, parameters);
		}
		
		private static Resource buildResource(Class<?> clazz,Method method){
			Resource resource = mappingResources.get(clazz.getName()+method.getName());
			if(resource!=null){
				return resource;
			}
			resource = new Resource();
			Service service = clazz.getAnnotation(Service.class);
			Controller controller = clazz.getAnnotation(Controller.class);
			if(service!=null){
				if(!service.uniqueName().isEmpty()){
					resource.setFirstResource(service.uniqueName());
				}else{
					resource.setFirstResource(clazz.getSimpleName().substring(0, 1).toLowerCase()+clazz.getSimpleName().substring(1, clazz.getSimpleName().length()));
				}
			}else if(controller!=null){
				resource.setFirstResource(controller.namespace());
			}
			MappingMethod mappingMethod = method.getAnnotation(MappingMethod.class);
			if(mappingMethod!=null){
				resource.setLastResource(mappingMethod.url());
			}else{
				resource.setLastResource(method.getName());
			}
			mappingResources.put(clazz.getName()+method.getName(), resource);
			return resource;
		}

	}
	
}
