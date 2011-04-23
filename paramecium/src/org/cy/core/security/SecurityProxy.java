package org.cy.core.security;

import java.lang.reflect.Method;
import java.security.GeneralSecurityException;

import org.cy.core.aop.cglib.Enhancer;
import org.cy.core.aop.cglib.MethodInterceptor;
import org.cy.core.aop.cglib.MethodProxy;
import org.cy.core.ioc.annotation.Service;
import org.cy.core.mvc.annotation.Controller;
import org.cy.core.mvc.annotation.MappingMethod;
import org.cy.core.security.annotation.Security;
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
	public static Object getServiceInstance(Class<?> serviceClass){
		Object service = null;
		try{
			Enhancer enhancer = new Enhancer();
	        enhancer.setSuperclass(serviceClass);
	        enhancer.setCallback(new SecurityInterceptor());
	        service = enhancer.create();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return service;
	}
	
	private static class SecurityInterceptor implements MethodInterceptor{
		
		public SecurityInterceptor() {
			
		}
		
		public Object intercept(Object service, Method method, Object[] parameters, MethodProxy proxy) throws Throwable{
			if(!method.isAccessible()){
				return proxy.invokeSuper(service, parameters);
			}
			Security security = service.getClass().getAnnotation(Security.class);
			if(security!=null&&security.protecting()){
				Resource resource = buildResource(service.getClass(), method);
				//此处需要日后验证是否安全
				throw new GeneralSecurityException();
			}
			return proxy.invokeSuper(service, parameters);
		}
		
		private static Resource buildResource(Class<?> clazz,Method method){
			Resource resource = new Resource();
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
			return resource;
		}

	}
	
}
