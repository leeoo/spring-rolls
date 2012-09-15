package org.paramecium.ioc;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.paramecium.commons.PathUtils;

/**
 * 功 能 描 述:<br>
 * web环境中第一次启动时加载
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-18下午02:29:25
 * <br>项 目 信 息:paramecium:org.paramecium.ioc.ApplicationContextListener.java
 */
public class ApplicationContextListener implements ServletContextListener {
	
	private static boolean isInit = true;
	private static String path;

	public void contextInitialized(ServletContextEvent event) {
		if(isInit){
			ServletContext servletContext = event.getServletContext();
			try{
				path = servletContext.getContextPath();
				if(path!=null && path.equals("/")){
					path = "";
				}
			}catch (Throwable e) {
				e.printStackTrace();
				System.err.println("请使用Servlet-api.jar2.5版本;2.4及之前版本缺少相关方法!系统为您停止启动服务，请查明原因再试。");
				System.exit(0);
			}
			PathUtils.webClassRootPath = servletContext.getRealPath("/WEB-INF")+"/classes";
			ApplicationContext.init();
			isInit = false;
		}
	}
	
	public static String getPath(){
		return path;
	}
	
	public void contextDestroyed(ServletContextEvent event) {
		
	}

}
