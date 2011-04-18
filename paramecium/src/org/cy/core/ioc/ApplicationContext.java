package org.cy.core.ioc;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.cy.core.ioc.annotation.AutoInject;
import org.cy.core.mvc.annotation.Controller;
import org.cy.core.transaction.TransactionAutoProxy;

/**
 * 功能描述(Description):<br><b>
 * 应用上下文容器
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-4-14下午08:39:22</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.cy.core.ioc.ApplicationContext.java</b>
 */
public class ApplicationContext {
	
	private volatile static ConcurrentMap<String, Object> controllerContext = new ConcurrentHashMap<String, Object>();
	
	static{
		try {
			Class.forName("org.cy.core.ioc.ClassScanner");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据索引构建所需应用实例<br>
	 * 如果是首层事务的service，其中包含的service属性声明无需自动注入，可以通过new方式得到。<br>
	 * 如果已经加入@AutoInject自动注入声明，其其下所有service属性实例将不会被声明为Transaction,统统交由最高层service事务代理<br>
	 * @param classInfo索引
	 * @return
	 */
	private static Object buildInstance(final BaseClassInfo classInfo){
		Object instance = null;
		if(classInfo instanceof ServiceClassInfo){
			ServiceClassInfo serviceClassInfo = (ServiceClassInfo)classInfo;
			if(serviceClassInfo.isTransactional()){
				instance = TransactionAutoProxy.getServiceInstance(serviceClassInfo.getClazz());
				loopInject(instance);
			}
		}else if(classInfo instanceof ControllerClassInfo){
			ControllerClassInfo controllerClassInfo = (ControllerClassInfo)classInfo;
			try {
				instance = controllerClassInfo.getClazz().newInstance();
			} catch (Exception e) {
			}
			Field[] fields = controllerClassInfo.getClazz().getDeclaredFields();
			for(Field field : fields){
				field.setAccessible(true);
				AutoInject autoInject = field.getAnnotation(AutoInject.class);
				if(autoInject!=null){
					String fieldName = field.getName();
					ServiceClassInfo serviceClassInfo = IocContextIndex.getService(fieldName);
					if(serviceClassInfo==null){
						throw new InjectException(fieldName+"注入时,请用@Service声明!");
					}
					try {
						Object fieldInstance = TransactionAutoProxy.getServiceInstance(serviceClassInfo.getClazz());
						loopInject(fieldInstance);
						field.set(instance, fieldInstance);
					} catch (Exception e) {
					}
				}
			}
			controllerContext.put(controllerClassInfo.getNamespace(), instance);
		}
		return instance;
	}
	
	private static void loopInject(Object instance){
		Field[] fields = instance.getClass().getDeclaredFields();
		for(Field field : fields){
			field.setAccessible(true);
			AutoInject autoInject = field.getAnnotation(AutoInject.class);
			if(autoInject!=null){
				String fieldName = field.getName();
				ServiceClassInfo serviceClassInfo = IocContextIndex.getService(fieldName);
				if(serviceClassInfo==null){
					throw new InjectException(fieldName+"注入时,请用@Service声明!");
				}
				try {
					Object fieldInstance = serviceClassInfo.getClazz().newInstance();
					loopInject(fieldInstance);
					field.set(instance, fieldInstance);
				} catch (Exception e) {
				}
			}
		}
	}
	
	static class InjectException extends IllegalArgumentException {

		private static final long serialVersionUID = 2632354973442186300L;

		public InjectException(String message) {
	    	 super(message);
	    }

	}
	
	/**
	 * 根据唯一标示获得实例名称
	 * @param name
	 * @return
	 */
	public static Object getBean(String name){
		Object instance = null;
		BaseClassInfo index = IocContextIndex.getController(name);
		if(index!=null){
			Controller controller = index.getClazz().getAnnotation(Controller.class);
			if(controller!=null&&controller.singleton()){
				instance = controllerContext.get(((ControllerClassInfo)index).getNamespace());
				if(instance!=null){
					synchronized (instance) {
						return instance;
					}
				}
			}
		}
		if(index == null){
			index = IocContextIndex.getService(name);
		}
		if(index!=null){
			instance = buildInstance(index);
		}
		return instance;
	}
	
}
