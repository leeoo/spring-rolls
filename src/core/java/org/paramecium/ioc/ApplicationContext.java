package org.paramecium.ioc;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.paramecium.ioc.annotation.AutoInject;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;

/**
 * 功能描述(Description):<br><b>
 * 应用上下文容器
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-4-14下午08:39:22</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.paramecium.ioc.ApplicationContext.java</b>
 */
public class ApplicationContext {
	
	private final static Log logger = LoggerFactory.getLogger();
	private final static ConcurrentMap<String, Object> applicationContext = new ConcurrentHashMap<String, Object>();
	private final static ThreadLocal<Boolean> isSecurityThreadLocal = new ThreadLocal<Boolean>();
	static String priorityStart;
	
	static{
		try {
			Class.forName("org.paramecium.ioc.ClassScanner");
		} catch (ClassNotFoundException e) {
			logger.error(e);
		}
	}
	
	/**
	 * 第一次加载
	 */
	static void init(){
		try{
			System.class.getName().isEmpty();
			priority();
		}catch (Throwable e) {
			logger.error(e);
			logger.error("请使用JDK1.6及以上版本;JDK1.5及之前版本缺少相关方法!系统为您停止启动服务，请查明原因再试。");
			System.exit(0);
		}
	}
	
	/**
	 * 判断此线程是否传递不要需要安全保护的通知
	 * @return
	 */
	public static boolean isSecurityThreadLocal(){
		if(isSecurityThreadLocal.get()!=null&&!isSecurityThreadLocal.get()){
			return true;
		}
		return false;
	}
	
	/**
	 * 优先 启动的业务类,随着服务一起启动。
	 */
	private static void priority(){
		if(priorityStart!=null&&!priorityStart.isEmpty()){
			if(priorityStart.indexOf(',')>0){
				String[] prioritys = priorityStart.split(",");
				for(String priority : prioritys){
					invoke(priority);
				}
			}else{
				invoke(priorityStart);
			}
		}
	}
	
	/**
	 * 执行调用容器中的方法
	 * @param instanceName
	 * @param methodName
	 */
	private static void invoke(String priority){
		try {
			priority = priority.trim();
			int length = priority.length();
			if(length>3){
				int methodIndex = priority.indexOf('#');
				String instanceName = priority.substring(0,methodIndex);
				String methodName = priority.substring(methodIndex+1,length);
				Object object = getNotSecurityBean(instanceName);
				Method method = object.getClass().getMethod(methodName);
				method.invoke(object);
				logger.debug(priority.concat("优先执行成功!"));
				destroy();
			}
		} catch (Throwable e) {
			logger.error(e);
		}
	}
	
	/**
	 * 填充实例到ApplicationContext容器中，避免嵌套注入的隐患，同时提高了注入性能，占用少量的内存。
	 * 容器中的实例是单例存储，使用者应避免使用属性变量赋值方式，应多采用注入或方法内声明变量。
	 * @return
	 */
	static void fillApplicationContext(){
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
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fields = superClass.getDeclaredFields();
			for(Field field : fields){
				field.setAccessible(true);
				AutoInject autoInject = field.getAnnotation(AutoInject.class);
				if(autoInject!=null){
					String fieldName = field.getName();
					if(!autoInject.value().isEmpty()){
						fieldName = autoInject.value();
					}
					ServiceClassInfo serviceClassInfo = IocContextIndex.getService(fieldName);
					if(serviceClassInfo==null){
						throw new InjectException(fieldName+"注入时,请用@Service声明!");
					}
					try {
						Object fieldInstance = ClassScanner.getIocInstance(fieldName);
						field.set(instance, fieldInstance);
					} catch (Throwable e) {
						logger.error(e);
					}
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
