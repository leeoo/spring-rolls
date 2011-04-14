package org.cy.core.ioc;

/**
 * 功能描述(Description):<br><b>
 * 应用实例容器
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-4-14下午08:39:22</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.cy.core.ioc.ApplicationContext.java</b>
 */
public class ApplicationContext {
	
	
	/**
	 * 根据索引构建所需应用实例
	 * @param classInfo
	 * @return
	 */
	private static Object buildInstance(BaseClassInfo classInfo){
		Object instance = null;
		try {
			instance = classInfo.getClazz().newInstance();
		} catch (Exception e) {
		}
		if(classInfo instanceof ServiceClassInfo){
			classInfo = (ServiceClassInfo)classInfo;
		}else if(classInfo instanceof ControllerClassInfo){
			classInfo = (ControllerClassInfo)classInfo;
		}
		return instance;
	}
	
	/**
	 * 根据唯一标示获得实例名称
	 * @param name
	 * @return
	 */
	public static Object getBean(String name){
		Object instance = null;
		BaseClassInfo index = IocContextIndex.getService(name);
		if(index == null){
			index = IocContextIndex.getController(name);
		}
		if(index!=null){
			instance = buildInstance(index);
		}
		return instance;
	}
	
}
