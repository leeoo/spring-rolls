package org.cy.core.security;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.cy.core.aop.cglib.MethodInterceptor;
import org.cy.core.aop.cglib.MethodProxy;
import org.cy.core.ioc.ClassScanner;
import org.cy.core.ioc.annotation.Service;
import org.cy.core.mvc.annotation.Controller;
import org.cy.core.mvc.annotation.MappingMethod;
import org.cy.core.security.annotation.Security;
import org.cy.core.security.exception.AnonymousException;
import org.cy.core.security.exception.AuthorizationException;
/**
 * 功能描述(Description):<br><b>
 * 安全拦截器基本功能
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-4-23下午08:52:57</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.cy.core.security.BaseSecurityInterceptor.java</b>
 */
public abstract class BaseSecurityInterceptor implements MethodInterceptor {
	
	private final static ConcurrentMap<String, Resource> mappingResources = new ConcurrentHashMap<String, Resource>();
	
	public BaseSecurityInterceptor() {
	}
	
	public Object intercept(Object service, Method method, Object[] parameters, MethodProxy proxy) throws Throwable{
		if(!ClassScanner.iocSecurity){
			return nextIntercept(service, method, parameters, proxy);
		}
		Security classSecurity = service.getClass().getAnnotation(Security.class);
		Security methodSecurity = method.getClass().getAnnotation(Security.class);
		if(classSecurity!=null&&classSecurity.protecting()){
			if(methodSecurity!=null&&!methodSecurity.protecting()){
				return nextIntercept(service, method, parameters, proxy);
			}
			UserDetails user = SecurityThread.get();
			if(user==null){
				return proxy.invokeSuper(service, parameters);
				//throw new AnonymousException("匿名用户没有登录！");
			}
			Resource resource = buildResource(service.getClass(), method);
			for(Resource userResource:user.getResources()){
				if(userResource.equals(resource)){
					return nextIntercept(service, method, parameters, proxy);
				}
			}
			return proxy.invokeSuper(service, parameters);
			//throw new AuthorizationException("该资源没有对此用户授权！");
		}
		return nextIntercept(service, method, parameters, proxy);
	}
	
	/**
	 * 其他拦截器需要实现自己的拦截实现
	 * @param service
	 * @param method
	 * @param parameters
	 * @param proxy
	 * @return
	 * @throws Throwable
	 */
	public abstract Object nextIntercept(Object service, Method method, Object[] parameters, MethodProxy proxy) throws Throwable;
	
	/**
	 * 根据拦截到的实例和方法构建授权资源
	 * @param clazz
	 * @param method
	 * @return
	 */
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
				Class<?> superClass = clazz.getSuperclass();
				String uniqueName = superClass.getSimpleName().substring(0, 1).toLowerCase()+superClass.getSimpleName().substring(1, superClass.getSimpleName().length());
				resource.setFirstResource(uniqueName);
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
