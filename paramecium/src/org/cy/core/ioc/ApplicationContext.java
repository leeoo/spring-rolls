package org.cy.core.ioc;

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
			}
		}else if(classInfo instanceof ControllerClassInfo){
			ControllerClassInfo controllerClassInfo = (ControllerClassInfo)classInfo;
			
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
