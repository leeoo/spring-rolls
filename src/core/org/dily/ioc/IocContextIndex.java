package org.dily.ioc;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
/**
 * 功能描述(Description):<br><b>
 * 依赖注入上下文容器索引
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-4-13下午08:56:36</b>
 * <br>项目名称(Project Name): <b>dily</b>
 * <br>包及类名(Package Class): <b>org.dily.ioc.IocContextIndex.java</b>
 */
public class IocContextIndex {
	
	private final static ConcurrentMap<String, ControllerClassInfo> controllerClassInfos = new ConcurrentHashMap<String, ControllerClassInfo>();
	
	public static Collection<ControllerClassInfo> getControllers(){
		return controllerClassInfos.values();
	}
	
	public static void setController(ControllerClassInfo classInfo){
		controllerClassInfos.put(classInfo.getNamespace(), classInfo);
	}
	
	public static ControllerClassInfo getController(String url){
		return controllerClassInfos.get(url);
	}
	
}
