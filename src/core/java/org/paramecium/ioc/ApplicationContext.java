package org.paramecium.ioc;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.paramecium.aop.ClassProxy;
import org.paramecium.ioc.annotation.AutoInject;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.security.SecurityInterceptor;
import org.paramecium.transaction.TransactionInterceptor;

/**
 * 功能描述(Description):<br><b>
 * 应用上下文容器
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-4-14下午08:39:22</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.paramecium.ioc.ApplicationContext.java</b>
 */
public class ApplicationContext {
	
	private final static ConcurrentMap<String, Object> controllerContext = new ConcurrentHashMap<String, Object>();
	public final static ThreadLocal<Boolean> isSecurityThreadLocal = new ThreadLocal<Boolean>();
	
	static{
		try {
			Class.forName("org.paramecium.ioc.ClassScanner");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 第一次加载
	 */
	public static void init(){
		try{
			System.class.getName().isEmpty();
		}catch (Exception e) {
			System.err.println("请使用JDK1.6及以上版本;JDK1.5及之前版本缺少相关方法!系统为您停止启动服务，请查明原因再试。");
			System.exit(0);
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
				ClassProxy proxy = new ClassProxy(new TransactionInterceptor(),serviceClassInfo.getClazz());
				instance = proxy.getClassInstance();
				loopInjectNoSecurity(instance,false);
			}else{//没有事务可能为综合多个带事务的service，如任务调度
				try {
					instance = serviceClassInfo.getClazz().newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
				loopInjectNoSecurity(instance,true);
			}
		}else if(classInfo instanceof ControllerClassInfo){
			ControllerClassInfo controllerClassInfo = (ControllerClassInfo)classInfo;
			try {
				ClassProxy proxy = new ClassProxy(new SecurityInterceptor(),controllerClassInfo.getClazz());
				instance = proxy.getClassInstance();
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
						Object fieldInstance = null;
						if(serviceClassInfo.isTransactional()){
							ClassProxy proxy = new ClassProxy(new TransactionInterceptor(),serviceClassInfo.getClazz());
							fieldInstance = proxy.getClassInstance();
							loopInject(fieldInstance,false);
						}else{//没有事务可能为综合多个带事务的service，如任务调度
							ClassProxy proxy = new ClassProxy(new SecurityInterceptor(),serviceClassInfo.getClazz());
							fieldInstance = proxy.getClassInstance();
							loopInject(fieldInstance,true);
						}
						field.set(instance, fieldInstance);
					} catch (Exception e) {
					}
				}
			}
			controllerContext.put(controllerClassInfo.getNamespace(), instance);
		}
		return instance;
	}
	
	/**
	 * 递归注入
	 * @param instance
	 */
	private static void loopInject(Object instance,boolean isTran){
		Field[] fields = instance.getClass().getSuperclass().getDeclaredFields();
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
					Object fieldInstance = null;
					if(serviceClassInfo.isTransactional()&&isTran){
						ClassProxy proxy = new ClassProxy(new TransactionInterceptor(),serviceClassInfo.getClazz());
						fieldInstance = proxy.getClassInstance();
						loopInject(fieldInstance,false);
					}else{//没有事务可能为综合多个带事务的service，如任务调度
						ClassProxy proxy = new ClassProxy(new SecurityInterceptor(),serviceClassInfo.getClazz());
						fieldInstance = proxy.getClassInstance();
						loopInject(fieldInstance,isTran);
					}
					field.set(instance, fieldInstance);
				} catch (Exception e) {
				}
			}
		}
	}
	
	/**
	 * 递归注入
	 * 该方法为至引入service时使用，如果基类没有使用事务，loopInjectNoSecurity时子属性实例化需要查找第一次（第一层事务），找到后，下一层无需在启动事务
	 * @param instance
	 */
	private static void loopInjectNoSecurity(Object instance,boolean isTran){
		Field[] fields = instance.getClass().getSuperclass().getDeclaredFields();
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
					Object fieldInstance = null;
					if(serviceClassInfo.isTransactional()&&isTran){
						ClassProxy proxy = new ClassProxy(new TransactionInterceptor(),serviceClassInfo.getClazz());
						fieldInstance = proxy.getClassInstance();
						loopInjectNoSecurity(fieldInstance,false);
					}else{
						fieldInstance = serviceClassInfo.getClazz().newInstance();
						loopInjectNoSecurity(fieldInstance,isTran);
					}
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
	 * 获得不受安全拦截的实例
	 * @param name
	 * @return
	 */
	public static Object getNotSecurityBean(String name){
		isSecurityThreadLocal.set(false);
		return getBean(name);
	}
	
	/**
	 * 销毁不受安全拦截产生的线程引用
	 */
	public static void destroy(){
		isSecurityThreadLocal.remove();
	}
	
	/**
	 * 根据唯一标示获得实例
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
