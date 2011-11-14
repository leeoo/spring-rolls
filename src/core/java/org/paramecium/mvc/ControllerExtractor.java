package org.paramecium.mvc;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.paramecium.commons.PathUtils;
import org.paramecium.ioc.ApplicationContext;
import org.paramecium.ioc.ControllerClassInfo;
import org.paramecium.ioc.IocContextIndex;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;
import org.paramecium.log.system.CollectorFactory;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.SecurityConfig;
import org.paramecium.security.SecurityThread;
import org.paramecium.security.exception.AnonymousException;
import org.paramecium.security.exception.AuthorizationException;
import org.paramecium.security.exception.IpAddressException;
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
	public static boolean extract(final HttpServletRequest request,final HttpServletResponse response){
		synchronized (request) {
			String servletPath = request.getServletPath();
			String[] URIStrs = getURIStrs(servletPath);
			if(URIStrs==null){
				logger.warn("非法请求地址:"+servletPath);
				return return404(response);
			}
			try{
				CollectorFactory.getWebCollector().put(request);//放入日志缓存
				ControllerClassInfo classInfo = IocContextIndex.getController(URIStrs[0]);
				if(classInfo==null){
					logger.warn("IocContextIndex未曾建立过的索引:"+URIStrs[0]);
					return return404(response);
				}
				Object controller = ApplicationContext.getBean(classInfo.getNamespace());
				if(controller==null){
					logger.warn("ApplicationContext无法构建该Controller:"+classInfo.getNamespace());
					return return404(response);
				}
				for (Class<?> clazz = classInfo.getClazz(); clazz != Object.class; clazz = clazz.getSuperclass()) {
					Method[] methods = clazz.getMethods();//只返回public，如果需要private可用getDeclaredMethods
					if(methods==null||methods.length<1){
						continue;
					}
					ModelAndView mv = new ModelAndView(request, response);
					for(Method method : methods){
						MappingMethod mappingMethod = method.getAnnotation(MappingMethod.class);
						if(mappingMethod==null){
							continue;
						}
						if(!mappingMethod.value().isEmpty()){
							if(mappingMethod.value().equals(URIStrs[1])){
								method.invoke(controller, mv);
								return security(mv);
							}
						}else if((ControllerExtractor.lineStr+method.getName()).equals(URIStrs[1])){
							method.invoke(controller, mv);
							return security(mv);
						}
					}
				}
			}catch (Throwable e) {
				return security(e,response);
			}
			logger.warn("该资源没有与之对应的处理方法!");
			return return404(response);
		}
	}
	
	private static boolean return404(final HttpServletResponse response){
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);//404
		return true;
	}
	
	private static boolean security(ModelAndView modelAndView){
		try{
			if(SecurityThread.getSecurity() != SecurityThread.Security.Null){
				if(SecurityThread.getSecurity() == SecurityThread.Security.AnonymousException){
					modelAndView.getResponse().sendRedirect(PathUtils.getNewPath(SecurityConfig.anonymousExceptionPage));
				}else if(SecurityThread.getSecurity() == SecurityThread.Security.AuthorizationException){
					modelAndView.getResponse().setStatus(HttpServletResponse.SC_FORBIDDEN);//403
					modelAndView.getResponse().sendRedirect(PathUtils.getNewPath(SecurityConfig.authorizationExceptionPage));
				}else if(SecurityThread.getSecurity() == SecurityThread.Security.UserKickException){
					modelAndView.getResponse().sendRedirect(PathUtils.getNewPath(SecurityConfig.userKickExceptionPage));
				}else if(SecurityThread.getSecurity() == SecurityThread.Security.UserDisabledException){
					modelAndView.getResponse().sendRedirect(PathUtils.getNewPath(SecurityConfig.userDisabledExceptionPage));
				}else if(SecurityThread.getSecurity() == SecurityThread.Security.SessionExpiredException){
					modelAndView.getResponse().sendRedirect(PathUtils.getNewPath(SecurityConfig.sessionExpiredExceptionPage));
				}else if(SecurityThread.getSecurity() == SecurityThread.Security.IpAddressException){
					modelAndView.getResponse().sendRedirect(PathUtils.getNewPath(SecurityConfig.ipAddressExceptionPage));
				}/*继续扩展，可在配置文件中加入更多异常*/
				return false;
			}
		}catch (Throwable e) {
			modelAndView.getResponse().setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);//500
		}
		return !modelAndView.isRedirect();
	}
	
	private static boolean security(Throwable e,final HttpServletResponse response){
		try {
			if(e.getCause() instanceof AnonymousException||e instanceof AnonymousException){
				response.sendRedirect(PathUtils.getNewPath(SecurityConfig.anonymousExceptionPage));
			}else if(e.getCause() instanceof AuthorizationException||e instanceof AuthorizationException){
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);//403
				response.sendRedirect(PathUtils.getNewPath(SecurityConfig.authorizationExceptionPage));
			}else if(e.getCause() instanceof UserKickException||e instanceof UserKickException){
				response.sendRedirect(PathUtils.getNewPath(SecurityConfig.userKickExceptionPage));
			}else if(e.getCause() instanceof UserDisabledException||e instanceof UserDisabledException){
				response.sendRedirect(PathUtils.getNewPath(SecurityConfig.userDisabledExceptionPage));
			}else if(e.getCause() instanceof SessionExpiredException||e instanceof SessionExpiredException){
				response.sendRedirect(PathUtils.getNewPath(SecurityConfig.sessionExpiredExceptionPage));
			}else if(e.getCause() instanceof IpAddressException||e instanceof IpAddressException){
				response.sendRedirect(PathUtils.getNewPath(SecurityConfig.ipAddressExceptionPage));
			}/*继续扩展，可在配置文件中加入更多异常*/
			else if(!(e.getCause() instanceof SecurityException)||!(e instanceof SecurityException)){//如果发生一个不是安全的异常，抛出500错误
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);//500
			}
		} catch (Throwable e2) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);//500
		}
		return false;
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
