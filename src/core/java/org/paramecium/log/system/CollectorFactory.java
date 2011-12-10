package org.paramecium.log.system;

import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;

/**
 * 功 能 描 述:<br>
 * 收集器工厂
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-9-15下午01:21:55
 * <br>项 目 信 息:paramecium:org.paramecium.log.system.CollectorFactory.java
 */
public class CollectorFactory {
	
	private final static Log logger = LoggerFactory.getLogger();
	static LogCollector logCollector = null;//日志收集器一旦被实例化，各个收集器中的缓存将不会被使用，而是直接调用应用层持久化或在应用侧自行缓存。
	private final static WebCollector webCollector = new WebCollector();
	private final static BeanCollector beanCollector = new BeanCollector();
	private final static JdbcCollector jdbcCollector = new JdbcCollector();
	
	public static void setLogCollector(String logCollectorInterface){
		if(logCollectorInterface==null||logCollectorInterface.isEmpty()||logCollector!=null){
			return;
		}
		try{
			Class<?> clazz = Class.forName(logCollectorInterface);
			logCollector = (LogCollector) clazz.newInstance();
		}catch (Exception e) {
			logger.error(e);
		}
	}
	
	public static WebCollector getWebCollector(){
		return webCollector;
	}

	public static BeanCollector getBeanCollector(){
		return beanCollector;
	}

	public static JdbcCollector getJdbcCollector(){
		return jdbcCollector;
	}
	
}
