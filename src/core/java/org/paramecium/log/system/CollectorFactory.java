package org.paramecium.log.system;

import javax.servlet.http.HttpServletRequest;

/**
 * 功 能 描 述:<br>
 * 收集器工厂
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-9-15下午01:21:55
 * <br>项 目 信 息:paramecium:org.paramecium.log.system.CollectorFactory.java
 */
public class CollectorFactory {
	
	static LogCollector logCollector = null;
	private static WebCollector<HttpServletRequest> webCollector = new WebCollector<HttpServletRequest>();
	private static BeanCollector<String> beanCollector = new BeanCollector<String>();
	private static JdbcCollector<String> jdbcCollector = new JdbcCollector<String>();
	
	public static void setLogCollector(String logCollectorInterface){
		if(logCollectorInterface==null||logCollectorInterface.isEmpty()){
			return;
		}
		try{
			Class<?> clazz = Class.forName(logCollectorInterface);
			logCollector = (LogCollector) clazz.newInstance();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static WebCollector<HttpServletRequest> getWebCollector(){
		return webCollector;
	}

	public static BeanCollector<String> getBeanCollector(){
		return beanCollector;
	}

	public static JdbcCollector<String> getJdbcCollector(){
		return jdbcCollector;
	}
	
}
