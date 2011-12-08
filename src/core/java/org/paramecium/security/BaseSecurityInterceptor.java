package org.paramecium.security;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.paramecium.aop.cglib.MethodInterceptor;
import org.paramecium.aop.cglib.MethodProxy;
import org.paramecium.ioc.ApplicationContext;
import org.paramecium.ioc.ClassScanner;
import org.paramecium.log.system.BeanCollector;
import org.paramecium.log.system.CollectorFactory;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.annotation.Security;
import org.paramecium.security.exception.AnonymousException;
import org.paramecium.security.exception.AuthorizationException;
/**
 * 功能描述(Description):<br><b>
 * 安全拦截器基本功能
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-4-23下午08:52:57</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.paramecium.security.BaseSecurityInterceptor.java</b>
 */
public abstract class BaseSecurityInterceptor implements MethodInterceptor {
	
	private final static ConcurrentMap<String, Resource> mappingResources = new ConcurrentHashMap<String, Resource>();
	
	public BaseSecurityInterceptor() {
	}
	
	
	
	/**
	 * 首先要拦截的安全验证,其他需要继承此抽象类后，实现nextIntercept方法
	 */
	public Object intercept(Object service, Method method, Object[] parameters, MethodProxy proxy) throws Throwable{
		String log = BeanCollector.getLog(service.getClass().getSuperclass(), method);
		CollectorFactory.getBeanCollector().put(log);
		if(!SecurityConfig.iocSecurity||ApplicationContext.isSecurityThreadLocal()){
			return nextIntercept(service, method, parameters, proxy);
		}
		Security classSecurity = service.getClass().getAnnotation(Security.class);
		Security methodSecurity = method.getAnnotation(Security.class);
		if(classSecurity!=null&&classSecurity.value()){
			if(methodSecurity!=null&&!methodSecurity.value()){
				return nextIntercept(service, method, parameters, proxy);
			}
			UserDetails<?> user = SecurityThread.get();
			if(user==null){
				SecurityThread.putSecurity(SecurityThread.Security.AnonymousException);
				throw new AnonymousException("匿名用户没有登录！");
			}
			Resource resource = buildResource(service.getClass(), method);
			if(user.getResources()!=null){
				for(Resource userResource:user.getResources()){
					if(userResource.equals(resource)){
						return nextIntercept(service, method, parameters, proxy);
					}
				}
			}
			SecurityThread.putSecurity(SecurityThread.Security.AuthorizationException);
			throw new AuthorizationException("该资源没有对此用户授权！");
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
		resource.setFirstResource(ClassScanner.getIocUniqueName(clazz.getSuperclass()));
		MappingMethod mappingMethod = method.getAnnotation(MappingMethod.class);
		if(mappingMethod!=null && !mappingMethod.value().isEmpty()){
			resource.setLastResource(mappingMethod.value());
		}else{
			resource.setLastResource(method.getName());
		}
		mappingResources.put(clazz.getName()+method.getName(), resource);
		return resource;
	}

}
