package org.paramecium.mvc;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.paramecium.ioc.ApplicationContext;
import org.paramecium.ioc.ControllerClassInfo;
import org.paramecium.ioc.IocContextIndex;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.SecurityConfig;
import org.paramecium.security.SecurityThread;
import org.paramecium.security.exception.AnonymousException;
import org.paramecium.security.exception.AuthorizationException;
import org.paramecium.security.exception.SessionExpiredException;
import org.paramecium.security.exception.UserDisabledException;
import org.paramecium.security.exception.UserKickException;
/**
 * 功 能 描 述:<br>
 * 通过Servlet提取Controller所需的信息
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-18下午02:02:53
 * <br>项 目 信 息:paramecium:org.paramecium.mvc.ControllerExtractor.java
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
			if(request.getSession(false)==null){
				request.getSession();
			}
			String servletPath = request.getServletPath();
			String[] URIStrs = getURIStrs(servletPath);
			if(URIStrs==null){
				logger.warn("非法请求地址:"+servletPath);
				end();
				return;
			}
			try{
				ControllerClassInfo classInfo = IocContextIndex.getController(URIStrs[0]);
				if(classInfo==null){
					logger.warn("IocContextIndex未曾建立过的索引:"+URIStrs[0]);
					end();
					return;
				}
				Object controller = ApplicationContext.getBean(classInfo.getNamespace(),request);
				if(controller==null){
					logger.warn("ApplicationContext无法构建该Controller:"+classInfo.getNamespace());
					end();
					return;
				}
				Method[] methods = classInfo.getClazz().getMethods();//只返回public，如果需要private可用getDeclaredMethods
				if(methods==null){
					logger.warn(classInfo.getClazz().getName()+"没有相符合的处理方法!");
					end();
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
							end();
							return;
						}
					}else if((ControllerExtractor.lineStr+method.getName()).equals(URIStrs[1])){
						method.invoke(controller, new ModelAndView(request, response));
						end();
						return;
					}
				}
			}catch (Exception e) {
				try {
					if(e.getCause() instanceof AnonymousException||e instanceof AnonymousException){
						response.sendRedirect(SecurityConfig.anonymousExceptionPage);
					}else if(e.getCause() instanceof AuthorizationException||e instanceof AuthorizationException){
						response.sendRedirect(SecurityConfig.authorizationExceptionPage);
					}else if(e.getCause() instanceof UserKickException||e instanceof UserKickException){
						response.sendRedirect(SecurityConfig.userKickExceptionPage);
					}else if(e.getCause() instanceof UserDisabledException||e instanceof UserDisabledException){
						response.sendRedirect(SecurityConfig.userDisabledExceptionPage);
					}else if(e.getCause() instanceof SessionExpiredException||e instanceof SessionExpiredException){
						response.sendRedirect(SecurityConfig.sessionExpiredExceptionPage);
					}/*继续扩展，可在配置文件中加入更多异常*/
				} catch (IOException e1) {
				}
				end();
				return;
			}
		}
	}
	
	private static void end(){
		SecurityThread.sessionThreadLocal.remove();
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
