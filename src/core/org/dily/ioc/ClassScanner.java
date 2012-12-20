package org.dily.ioc;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.dily.commons.PathUtils;
import org.dily.commons.PropertiesUitls;
import org.dily.log.Log;
import org.dily.log.LoggerFactory;
import org.dily.mvc.annotation.Controller;
/**
 * 功 能 描 述:<br>
 * 类扫描器
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-14上午10:43:25
 * <br>项 目 信 息:dily:org.dily.ioc.ClassScanner.java
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
		init();
		properties.clear();
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
		Controller controller = clazz.getAnnotation(Controller.class);
		if(controller!=null){
			String iocUniqueName = controller.value();
			boolean single = controller.single();
			ControllerClassInfo classInfo = new ControllerClassInfo();
			classInfo.setClazz(clazz);
			classInfo.setNamespace(iocUniqueName);
			classInfo.setSingle(single);
			if(IocContextIndex.getController(iocUniqueName)!=null){
				logger.fatal(clazz.getName()+" # "+iocUniqueName+"容器内已经存在，重复注入将会产生隐患!系统为您停止启动服务，请查明原因再试。");
				System.exit(0);
			}
			IocContextIndex.setController(classInfo);
			try {
				if(single){
					instanceContext.put(iocUniqueName, clazz.newInstance());
				}else{
					logger.debug(iocUniqueName+"实例不是单例模式，所以无需创建实例放入容器！");
				}
			} catch (Throwable e) {
				logger.fatal(clazz.getName()+" # "+iocUniqueName+"无法实例化，请给出该类无参数构造方法！");
				System.exit(0);
			}
			logger.debug(clazz.getName()+" # "+iocUniqueName+" 被载入");
		}
	}
	
	public static Object getIocInstance(String name){
		return instanceContext.get(name);
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
