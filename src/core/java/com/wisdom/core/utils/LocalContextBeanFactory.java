package com.wisdom.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * <B>功能描述:</B><br>
 * 一次初始化本地容器，保障各模块得到本地容器唯一，节省JVM。<br>
 * 适合使用模块:JUNIT测试，以及一些UTIL,TOOLS,COMMONS,HELPER等工具类<br>
 * <br><B>作   者:</B> 曹阳<br>
 * <B>创建时间:</B> Jan 12, 2008<br>
 * <B>创建日期:</B> 9:53:32 AM<br>
 *
 */
public abstract class LocalContextBeanFactory {
	
	private final static ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:/applicationContext.xml");
	
	private final static Logger logger = LoggerFactory.getLogger(LocalContextBeanFactory.class);

	/**
	 * 
	 * @param beanName在容器中的BEANID<br>
	 * @return bean实例<br>
	 */
	public static Object getBean(String beanName) {
		try{
			Object bean=applicationContext.getBean(beanName);
			logger.info(""+bean.hashCode());
			return bean;
		}catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
}
