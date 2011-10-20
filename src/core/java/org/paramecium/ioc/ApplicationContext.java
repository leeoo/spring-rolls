package org.paramecium.ioc;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.paramecium.ioc.annotation.AutoInject;

/**
 * 功能描述(Description):<br><b>
 * 应用上下文容器
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-4-14下午08:39:22</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.paramecium.ioc.ApplicationContext.java</b>
 */
public class ApplicationContext {
	
	private final static ConcurrentMap<String, Object> applicationContext = new ConcurrentHashMap<String, Object>();
	public final static ThreadLocal<Boolean> isSecurityThreadLocal = new ThreadLocal<Boolean>();
	public static String priorityStartClass;
	
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
			priority();
		}catch (Exception e) {
			System.err.println("请使用JDK1.6及以上版本;JDK1.5及之前版本缺少相关方法!系统为您停止启动服务，请查明原因再试。");
			System.exit(0);
		}
	}
	
	/**
	 * 优先 启动的业务类,随着服务一起启动。
	 */
	private static void priority(){
		if(ApplicationContext.priorityStartClass!=null&&!ApplicationContext.priorityStartClass.isEmpty()){
			try {
				String className = priorityStartClass.substring(0,priorityStartClass.indexOf('#'));
				String methodName = priorityStartClass.substring(priorityStartClass.indexOf('#')+1,priorityStartClass.length());
				Class<?> clazz = Class.forName(className);
				Object object = clazz.newInstance();
				Method method = clazz.getMethod(methodName);
				method.invoke(object);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 根据索引构建所需应用实例<br>
	 * 如果是首层事务的service，其中包含的service属性声明无需自动注入，可以通过new方式得到。<br>
	 * 如果已经加入@AutoInject自动注入声明，其其下所有service属性实例将不会被声明为Transaction,统统交由最高层service事务代理<br>
	 * @param classInfo索引
	 * @return
	 */
	public static void buildApplicationContext(){
		for(ServiceClassInfo info : IocContextIndex.getServices()){
			String name = info.getUniqueName();
			Object instance = ClassScanner.getIocInstance(name);
			setFieldInstance(instance,info.getClazz());
			applicationContext.put(name, instance);
		}
		for(ControllerClassInfo info : IocContextIndex.getControllers()){
			String name = info.getNamespace();
			Object instance = ClassScanner.getIocInstance(name);
			setFieldInstance(instance,info.getClazz());
			applicationContext.put(name, instance);
		}
	}
	
	private static void setFieldInstance(Object instance,Class<?> clazz){
		Field[] fields = clazz.getDeclaredFields();
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
					Object fieldInstance = ClassScanner.getIocInstance(fieldName);
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
		return applicationContext.get(name);
	}
	
}
