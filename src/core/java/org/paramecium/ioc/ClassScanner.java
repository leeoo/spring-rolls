package org.paramecium.ioc;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.paramecium.aop.ClassProxy;
import org.paramecium.commons.PathUtils;
import org.paramecium.commons.PropertiesUitls;
import org.paramecium.ioc.annotation.Service;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.AuthorizationMenu;
import org.paramecium.security.Resource;
import org.paramecium.security.SecurityConfig;
import org.paramecium.security.SecurityInterceptor;
import org.paramecium.security.annotation.Security;
import org.paramecium.transaction.TransactionInterceptor;
import org.paramecium.transaction.annotation.Transactional;
/**
 * 功 能 描 述:<br>
 * 类扫描器
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-14上午10:43:25
 * <br>项 目 信 息:paramecium:org.paramecium.ioc.ClassScanner.java
 */
public class ClassScanner {
	
	private final static Log logger = LoggerFactory.getLogger();
	private final static ConcurrentMap<String, Object> instanceContext = new ConcurrentHashMap<String, Object>();
	public static String iocScanBasePackage;
	private static Collection<String> classes = new ArrayList<String>();
	
	static{
		logger.debug("正在初始化Application容器...");
		Map<String,String> properties = PropertiesUitls.get("/context.properties");
		iocScanBasePackage = properties.get("iocScanBasePackage");
		ApplicationContext.priorityStart = properties.get("priorityStart");
		properties = PropertiesUitls.get("/security.properties");
		String iocSecurityStr = properties.get("iocSecurity");
		String sessionControlStr = properties.get("sessionControl");
		SecurityConfig.iocSecurity = iocSecurityStr!=null?Boolean.parseBoolean(iocSecurityStr):false;
		SecurityConfig.sessionControl = sessionControlStr!=null?Boolean.parseBoolean(sessionControlStr):false;
		SecurityConfig.loginExceptionPage = properties.get("loginExceptionPage");
		SecurityConfig.anonymousExceptionPage = properties.get("anonymousExceptionPage");
		SecurityConfig.authorizationExceptionPage = properties.get("authorizationExceptionPage");
		SecurityConfig.userKickExceptionPage = properties.get("userKickExceptionPage");
		SecurityConfig.userDisabledExceptionPage = properties.get("userDisabledExceptionPage");
		SecurityConfig.sessionExpiredExceptionPage = properties.get("sessionExpiredExceptionPage");
		SecurityConfig.ipAddressExceptionPage = properties.get("ipAddressExceptionPage");
		SecurityConfig.userKillExceptionPage = properties.get("userKillExceptionPage");
		init();
	}
	
	/**
	 * 初始化装载路径下的类，并将符合IOC注入的类装入IOC容器内
	 */
	public static void init(){
		readRootClassFile();
		for(String cp:classes){
			Class<?> clazz = null;
			try {
				clazz = Class.forName(cp);
				putIocContext(clazz);
			} catch (ClassNotFoundException e) {
				logger.warn(e.getMessage());
			}
		}
		ApplicationContext.fillApplicationContext();
		classes.clear();//物理类文件信息可以清空了
		instanceContext.clear();
	}
	
	/**
	 * 装入IOC容器
	 * @param clazz
	 */
	public static void putIocContext(Class<?> clazz){
		Service service = clazz.getAnnotation(Service.class);
		Controller controller = clazz.getAnnotation(Controller.class);
		Security security = clazz.getAnnotation(Security.class);
		String iocUniqueName = getIocUniqueName(clazz);
		if(service!=null){
			ServiceClassInfo classInfo = new ServiceClassInfo();
			classInfo.setClazz(clazz);
			classInfo.setUniqueName(iocUniqueName);
			classInfo.setTransactional(false);
			Transactional transactional = clazz.getAnnotation(Transactional.class);
			if(transactional!=null){
				classInfo.setTransactional(true);
			}
			if(IocContextIndex.getService(iocUniqueName)!=null){
				logger.fatal(clazz.getName()+" # "+iocUniqueName+"容器内已经存在，重复注入将会产生隐患!系统为您停止启动服务，请查明原因再试。");
				System.exit(0);
			}
			IocContextIndex.setService(classInfo);
			try {
				ClassProxy proxy = new ClassProxy(new TransactionInterceptor(),clazz);
				instanceContext.put(iocUniqueName, proxy.getClassInstance());
			} catch (Throwable e) {
				logger.fatal(clazz.getName()+" # "+iocUniqueName+"无法实例化，请给出该类无参数构造方法！");
				System.exit(0);
			}
			logger.debug(clazz.getName()+" # "+iocUniqueName+" 被载入");
		}else if(controller!=null){
			ControllerClassInfo classInfo = new ControllerClassInfo();
			classInfo.setClazz(clazz);
			classInfo.setNamespace(iocUniqueName);
			if(IocContextIndex.getController(iocUniqueName)!=null){
				logger.fatal(clazz.getName()+" # "+iocUniqueName+"容器内已经存在，重复注入将会产生隐患!系统为您停止启动服务，请查明原因再试。");
				System.exit(0);
			}
			IocContextIndex.setController(classInfo);
			try {
				ClassProxy proxy = new ClassProxy(new SecurityInterceptor(),clazz);
				instanceContext.put(iocUniqueName, proxy.getClassInstance());
			} catch (Throwable e) {
				logger.fatal(clazz.getName()+" # "+iocUniqueName+"无法实例化，请给出该类无参数构造方法！");
				System.exit(0);
			}
			logger.debug(clazz.getName()+" # "+iocUniqueName+" 被载入");
		}
		if(security!=null){
			Resource resource = new Resource();
			resource.setFirstResource(iocUniqueName);
			ShowLabel showLabel = clazz.getAnnotation(ShowLabel.class);
			if(showLabel!=null){
				resource.setShowLabel(showLabel.value());
			}
			Collection<Resource> resources = new LinkedList<Resource>();
			for(Method method : clazz.getMethods()){
				if(Modifier.isStatic(method.getModifiers())){
					continue;//如果该方法为静态则过滤不计
				}
				if(method.getName().equals("wait")||method.getName().equals("equals")
						||method.getName().equals("getClass")||method.getName().equals("toString")
						||method.getName().equals("hashCode")||method.getName().equals("notify")
						||method.getName().equals("notifyAll")){
					continue;
				}
				Security methodMecurity = method.getAnnotation(Security.class);
				if(methodMecurity!=null&&!methodMecurity.value()){
					continue;
				}
				Resource subResource = new Resource();
				subResource.setFirstResource(iocUniqueName);
				showLabel = method.getAnnotation(ShowLabel.class);
				if(showLabel!=null){
					subResource.setShowLabel(showLabel.value());
				}
				MappingMethod mappingMethod = method.getAnnotation(MappingMethod.class);
				if(mappingMethod!=null && !mappingMethod.value().isEmpty()){
					subResource.setLastResource(mappingMethod.value());
				}else{
					subResource.setLastResource(method.getName());
				}
				resources.add(subResource);
			}
			AuthorizationMenu.put(resource, resources);
		}
	}
	
	public static Object getIocInstance(String name){
		return instanceContext.get(name);
	}
	
	
	/**
	 * 获得IOC容器中的类唯一标示
	 * @param clazz
	 * @return
	 */
	public static String getIocUniqueName(Class<?> clazz){
		Service service = clazz.getAnnotation(Service.class);
		Controller controller = clazz.getAnnotation(Controller.class);
		if(service!=null){
			if(!service.value().isEmpty()){
				return service.value();
			}else{
				return getInstanceName(clazz);
			}
		}else if(controller!=null){
			return controller.value();
		}
		return null;
	}
	
	/**
	 * 获得理论实例名
	 * @param clazz
	 * @return
	 */
	public static String getInstanceName(Class<?> clazz){
		return clazz.getSimpleName().substring(0, 1).toLowerCase()+clazz.getSimpleName().substring(1, clazz.getSimpleName().length());
	}
	
	/**
	 * 读取类装载路径下根目录类文件
	 */
	private static void readRootClassFile(){
		for(String baesPackage:iocScanBasePackage.split(",")){
			if(baesPackage!=null&&!baesPackage.trim().isEmpty()){
				String baesPath = baesPackage.replaceAll("\\.", "//").trim();
				String filePath = PathUtils.getClassRootPath()+"//"+baesPath;
				readClassFile(filePath,baesPackage.trim());
			}
		}
	}
	
	/**
	 * 递归读取子目录下类文件
	 * @param currentPath
	 * @param classPackage
	 */
	private static void readClassFile(String currentPath,String classPackage){
		File file = new File(currentPath);
		for(String str : file.list()){
			String currentFile = currentPath.concat("//"+str);
			String currentPackage = classPackage+"."+str;
			if(new File(currentFile).isDirectory()){
				readClassFile(currentFile,currentPackage);
			}else if(str.length()>6&&str.substring(str.length()-6, str.length()).equalsIgnoreCase(".class")){
				classes.add(currentPackage.substring(0, currentPackage.length()-6));
			}
		}
	}
	
}
