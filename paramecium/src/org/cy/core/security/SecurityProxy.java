package org.cy.core.security;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.cy.core.aop.cglib.Enhancer;
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
 * 安全代理
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-4-23下午08:52:57</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.cy.core.security.SecurityProxy.java</b>
 */
public class SecurityProxy {
	
	/**
	 * 获得需要代理的service实例
	 * @param serviceClass实现类类型
	 * @return
	 */
	public static Object getSecurityInstance(Object instance){
		if(!ClassScanner.iocSecurity||instance.getClass().getAnnotation(Security.class)==null){
			return instance;
		}
		try{
			Enhancer enhancer = new Enhancer();
	        enhancer.setSuperclass(instance.getClass());
	        enhancer.setCallback(new SecurityInterceptor());
	        instance = enhancer.create();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
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
