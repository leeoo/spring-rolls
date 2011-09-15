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
	
	private static WebCollector<HttpServletRequest> webCollector = new WebCollector<HttpServletRequest>();
	private static BeanCollector<String> beanCollector = new BeanCollector<String>();
	private static JdbcCollector<String> jdbcCollector = new JdbcCollector<String>();
	
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
