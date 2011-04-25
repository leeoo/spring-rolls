package org.cy.core.ioc;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import org.cy.core.commons.PropertiesUitls;
import org.cy.core.ioc.annotation.Service;
import org.cy.core.ioc.annotation.ShowLabel;
import org.cy.core.log.Log;
import org.cy.core.log.LoggerFactory;
import org.cy.core.mvc.annotation.Controller;
import org.cy.core.mvc.annotation.MappingMethod;
import org.cy.core.security.AuthorizationMenu;
import org.cy.core.security.Resource;
import org.cy.core.security.SecurityConfig;
import org.cy.core.security.annotation.Security;
import org.cy.core.transaction.annotation.Transactional;
/**
 * 功 能 描 述:<br>
 * 类扫描器
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-14上午10:43:25
 * <br>项 目 信 息:paramecium:org.cy.core.ioc.ClassScanner.java
 */
public class ClassScanner {
	
	private final static Log logger = LoggerFactory.getLogger();
	public static String iocScanBasePackage;
	private static Collection<String> classes = new ArrayList<String>();
	
	static{
		Map<String,String> properties = PropertiesUitls.get("/context.properties");
		iocScanBasePackage = properties.get("iocScanBasePackage");
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
			}
		}
		classes.clear();//物理类文件信息可以清空了
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
			IocContextIndex.setService(classInfo);
			logger.debug(clazz.getName()+" 被载入");
		}else if(controller!=null){
			ControllerClassInfo classInfo = new ControllerClassInfo();
			classInfo.setClazz(clazz);
			classInfo.setNamespace(iocUniqueName);
			IocContextIndex.setController(classInfo);
			logger.debug(clazz.getName()+" 被载入");
		}
		if(security!=null){
			Resource resource = new Resource();
			resource.setFirstResource(iocUniqueName);
			ShowLabel showLabel = clazz.getAnnotation(ShowLabel.class);
			if(showLabel!=null){
				resource.setShowLabel(showLabel.name());
			}
			Collection<Resource> resources = new HashSet<Resource>();
			for(Method method : clazz.getMethods()){
				if(method.getName().equals("wait")||method.getName().equals("equals")
						||method.getName().equals("getClass")||method.getName().equals("toString")
						||method.getName().equals("hashCode")||method.getName().equals("notify")
						||method.getName().equals("notifyAll")){
					continue;
				}
				Security methodMecurity = method.getAnnotation(Security.class);
				if(methodMecurity!=null&&!methodMecurity.protecting()){
					continue;
				}
				Resource subResource = new Resource();
				subResource.setFirstResource(iocUniqueName);
				showLabel = method.getAnnotation(ShowLabel.class);
				if(showLabel!=null){
					subResource.setShowLabel(showLabel.name());
				}
				MappingMethod mappingMethod = method.getAnnotation(MappingMethod.class);
				if(mappingMethod!=null){
					subResource.setLastResource(mappingMethod.url());
				}else{
					subResource.setLastResource(method.getName());
				}
				resources.add(subResource);
			}
			AuthorizationMenu.put(resource, resources);
		}
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
			if(!service.uniqueName().isEmpty()){
				return service.uniqueName();
			}else{
				String uniqueName = clazz.getSimpleName().substring(0, 1).toLowerCase()+clazz.getSimpleName().substring(1, clazz.getSimpleName().length());
				return uniqueName;
			}
		}else if(controller!=null){
			return controller.namespace();
		}
		return null;
	}
	
	/**
	 * 读取类装载路径下根目录类文件
	 */
	private static void readRootClassFile(){
		for(String baesPackage:iocScanBasePackage.split(",")){
			if(baesPackage!=null&&!baesPackage.trim().isEmpty()){
				String baesPath = baesPackage.replaceAll("\\.", "//").trim();
				String filePath = System.class.getResource("/").getFile()+baesPath;
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
