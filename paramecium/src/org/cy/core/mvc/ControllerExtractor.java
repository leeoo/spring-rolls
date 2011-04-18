package org.cy.core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cy.core.ioc.ApplicationContext;
import org.cy.core.ioc.ControllerClassInfo;
import org.cy.core.ioc.IocContextIndex;
import org.cy.core.log.Log;
import org.cy.core.log.LoggerFactory;
/**
 * 功 能 描 述:<br>
 * 通过Servlet提取Controller所需的信息
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-18下午02:02:53
 * <br>项 目 信 息:paramecium:org.cy.core.mvc.ControllerExtractor.java
 */
public class ControllerExtractor {
	
	private final static Log logger = LoggerFactory.getLogger();
	private final static String dotStr = ".";
	private final static String lineStr = "/";
	
	/**
	 * 开始提取
	 * @param request
	 * @param response
	 */
	public static void extract(final HttpServletRequest request,final HttpServletResponse response){
		synchronized (request) {
			String servletPath = request.getServletPath();
			String[] URIStrs = getURIStrs(servletPath);
			if(URIStrs==null){
				return;
			}
			ControllerClassInfo classInfo = IocContextIndex.getController(URIStrs[0]);
			if(classInfo==null){
				return;
			}
			Object controller = ApplicationContext.getBean(classInfo.getNamespace());
			if(controller==null){
				return;
			}
			
		}
	}
	
	/**
	 * 根据请求拆分Controller所需的索引
	 * @param servletPath
	 * @return
	 */
	private static String[] getURIStrs(final String servletPath){
		String[] URIStrs = new String[2];
		int dot = servletPath.indexOf(ControllerExtractor.dotStr);
		String delDot = dot>0 ? servletPath.substring(0, dot) : servletPath;//去掉'.'后面的内容
		int firstLine = delDot.indexOf(ControllerExtractor.lineStr);//获得第一个'/'位置
		int lastLine = delDot.lastIndexOf(ControllerExtractor.lineStr);//获得最后一个'/'位置
		if(firstLine>-1&&firstLine==lastLine){//如果没有命名空间为被视为'/'
			URIStrs[0] = ControllerExtractor.lineStr;
			URIStrs[1] = delDot;
			return URIStrs;
		}
		URIStrs[0] = delDot.substring(0, lastLine);//最后一个'/'之前的内容皆为命名空间
		URIStrs[1] = delDot.substring(lastLine, delDot.length());//之后为请求方法
		return URIStrs;
	}
	
}
