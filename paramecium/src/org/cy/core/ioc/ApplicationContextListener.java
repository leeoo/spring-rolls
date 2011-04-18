package org.cy.core.ioc;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.cy.core.log.Log;
import org.cy.core.log.LoggerFactory;

/**
 * 功 能 描 述:<br>
 * web环境中第一次启动时加载
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-18下午02:29:25
 * <br>项 目 信 息:paramecium:org.cy.core.ioc.ApplicationContextListener.java
 */
public class ApplicationContextListener implements ServletContextListener {
	
	private static Log logger = LoggerFactory.getLogger();
	private static boolean isInit = true;

	public void contextInitialized(ServletContextEvent event) {
		if(isInit){
			ApplicationContext.init();
			isInit = false;
			logger.debug("ApplicationContext已经加载...");
		}
	}
	
	public void contextDestroyed(ServletContextEvent event) {
		
	}

}
