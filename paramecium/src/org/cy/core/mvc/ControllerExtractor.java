package org.cy.core.mvc;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cy.core.ioc.ApplicationContext;
import org.cy.core.ioc.ControllerClassInfo;
import org.cy.core.ioc.IocContextIndex;
import org.cy.core.log.Log;
import org.cy.core.log.LoggerFactory;
import org.cy.core.mvc.annotation.MappingMethod;
import org.cy.core.security.SecurityConfig;
import org.cy.core.security.exception.AnonymousException;
import org.cy.core.security.exception.AuthorizationException;
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
				logger.warn("非法请求地址:"+servletPath);
				return;
			}
			try{
				ControllerClassInfo classInfo = IocContextIndex.getController(URIStrs[0]);
				if(classInfo==null){
					logger.warn("IocContextIndex未曾建立过的索引:"+URIStrs[0]);
					return;
				}
				Object controller = ApplicationContext.getBean(classInfo.getNamespace());
				if(controller==null){
					logger.warn("ApplicationContext无法构建该Controller:"+classInfo.getNamespace());
					return;
				}
				Method[] methods = classInfo.getClazz().getMethods();//只返回public，如果需要private可用getDeclaredMethods
				if(methods==null){
					logger.warn(classInfo.getClazz().getName()+"没有相符合的处理方法!");
					return;
				}
				for(Method method : methods){
					MappingMethod mappingMethod = method.getAnnotation(MappingMethod.class);
					if(mappingMethod==null){
						continue;
					}
					if(!mappingMethod.url().isEmpty()){
						if(mappingMethod.url().equals(URIStrs[1])){
							method.invoke(controller, new ModelAndView(request, response));
							return;
						}
					}else if((ControllerExtractor.lineStr+method.getName()).equals(URIStrs[1])){
						method.invoke(controller, new ModelAndView(request, response));
						return;
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
				if(e.getCause() instanceof AnonymousException||e instanceof AnonymousException){
					try {
						response.sendRedirect(SecurityConfig.anonymousExceptionPage);
					} catch (IOException e1) {
					}
				}else if(e.getCause() instanceof AuthorizationException||e instanceof AuthorizationException){
					try {
						response.sendRedirect(SecurityConfig.authorizationExceptionPage);
					} catch (IOException e1) {
					}
				}/*继续扩展，可在配置文件中加入更多异常*/
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
