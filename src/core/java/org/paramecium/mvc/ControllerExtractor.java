package org.paramecium.mvc;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
import org.paramecium.security.exception.UserKillException;
/**
 * 功 能 描 述:<br>
 * 通过Servlet提取Controller所需的信息
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-18下午02:02:53
 * <br>项 目 信 息:paramecium:org.paramecium.mvc.ControllerExtractor.java
 */
public class ControllerExtractor implements ServletConstant{
	
	private final static Log logger = LoggerFactory.getLogger();
	
	
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
				SecurityThread.startThread(request);
				CollectorFactory.getWebCollector().put(request.getRemoteAddr().concat("|").concat(request.getRequestURI()));//放入日志缓存
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
				return invoke(classInfo.getClazz(), request, response, controller, URIStrs);
			} catch (Throwable e) {
				return security(e, request,response);
			}
		}
	}
	
	/**
	 * 调用Controller对应的方法
	 * @param clazz
	 * @param request
	 * @param response
	 * @param controller
	 * @param URIStrs
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private static boolean invoke(Class<?> clazz,final HttpServletRequest request,
			final HttpServletResponse response,Object controller,String[] URIStrs) 
	throws IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
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
					if(mappingMethod.value().equals(URIStrs[1])){//判断映射方法注解中是否有/，否则下面的判断加上/
						method.invoke(controller, mv);
						return security(mv);
					}else if(lineStr.concat(mappingMethod.value()).equals(URIStrs[1])){//同上
						method.invoke(controller, mv);
						return security(mv);
					}
				}else if(lineStr.concat(method.getName()).equals(URIStrs[1])){
					method.invoke(controller, mv);
					return security(mv);
				}
			}
		}
		logger.warn("该资源没有与之对应的处理方法!");
		return return404(response);
	}
	
	private static boolean return404(final HttpServletResponse response){
		try {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);//404
		} catch (IOException e) {
			logger.error(e);
		}
		return true;
	}

	private static boolean return500(final HttpServletRequest request,final HttpServletResponse response,Throwable e){
		try {
			logger.error(e);
			//将异常放入request,Jsp中的exception对象实际为request中定义的一个特定值KEY对应的Exception对象.
			request.setAttribute(PAGE_CONTEXT_EXCEPTION, e.getCause());
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);//500
		} catch (IOException ioe) {
			logger.error(ioe);
		}
		return false;
	}
	
	/*private static boolean return403(final HttpServletResponse response){
		try {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);//403
		} catch (IOException e) {
		}
		return false;
	}*/
	
	/**
	 * 正常情况下通过线程传递安全异常机制
	 */
	private static boolean security(ModelAndView modelAndView){
		try {
			if(SecurityThread.getSecurity() != SecurityThread.Security.Null){
				if(SecurityThread.getSecurity() == SecurityThread.Security.AnonymousException){
					modelAndView.getResponse().sendRedirect(PathUtils.getNewPath(SecurityConfig.anonymousExceptionPage));
				}else if(SecurityThread.getSecurity() == SecurityThread.Security.AuthorizationException){
					//return return403(modelAndView.getResponse());属于403跳转，如果需要可以替换
					modelAndView.getResponse().sendRedirect(PathUtils.getNewPath(SecurityConfig.authorizationExceptionPage));
				}else if(SecurityThread.getSecurity() == SecurityThread.Security.UserKickException){
					modelAndView.getResponse().sendRedirect(PathUtils.getNewPath(SecurityConfig.userKickExceptionPage));
				}else if(SecurityThread.getSecurity() == SecurityThread.Security.UserDisabledException){
					modelAndView.getResponse().sendRedirect(PathUtils.getNewPath(SecurityConfig.userDisabledExceptionPage));
				}else if(SecurityThread.getSecurity() == SecurityThread.Security.SessionExpiredException){
					modelAndView.getResponse().sendRedirect(PathUtils.getNewPath(SecurityConfig.sessionExpiredExceptionPage));
				}else if(SecurityThread.getSecurity() == SecurityThread.Security.IpAddressException){
					modelAndView.getResponse().sendRedirect(PathUtils.getNewPath(SecurityConfig.ipAddressExceptionPage));
				}else if(SecurityThread.getSecurity() == SecurityThread.Security.UserKillException){
					modelAndView.getResponse().sendRedirect(PathUtils.getNewPath(SecurityConfig.userKillExceptionPage));
				}/*继续扩展，可在配置文件中加入更多安全问题*/
				return false;
			}
		} catch (IOException ioe) {//一般来说不会有错误
			return return500(modelAndView.getRequest(),modelAndView.getResponse(),ioe);//500
		}
		return !modelAndView.isRedirect();
	}
	
	/**
	 * 通过权限安全产生的异常判断
	 * @param e
	 * @param response
	 * @return
	 */
	private static boolean security(Throwable e,final HttpServletRequest request,final HttpServletResponse response){
		try {
			if(e.getCause() instanceof AnonymousException||e instanceof AnonymousException){
				response.sendRedirect(PathUtils.getNewPath(SecurityConfig.anonymousExceptionPage));
			}else if(e.getCause() instanceof AuthorizationException||e instanceof AuthorizationException){
				//return return403(response);属于403跳转，如果需要可以替换
				response.sendRedirect(PathUtils.getNewPath(SecurityConfig.authorizationExceptionPage));
			}else if(e.getCause() instanceof UserKickException||e instanceof UserKickException){
				response.sendRedirect(PathUtils.getNewPath(SecurityConfig.userKickExceptionPage));
			}else if(e.getCause() instanceof UserDisabledException||e instanceof UserDisabledException){
				response.sendRedirect(PathUtils.getNewPath(SecurityConfig.userDisabledExceptionPage));
			}else if(e.getCause() instanceof SessionExpiredException||e instanceof SessionExpiredException){
				response.sendRedirect(PathUtils.getNewPath(SecurityConfig.sessionExpiredExceptionPage));
			}else if(e.getCause() instanceof IpAddressException||e instanceof IpAddressException){
				response.sendRedirect(PathUtils.getNewPath(SecurityConfig.ipAddressExceptionPage));
			}else if(e.getCause() instanceof UserKillException||e instanceof UserKillException){
				response.sendRedirect(PathUtils.getNewPath(SecurityConfig.userKillExceptionPage));
			}/*继续扩展，可在配置文件中加入更多异常*/
			else if(!(e.getCause() instanceof SecurityException)||!(e instanceof SecurityException)){//如果发生一个不是安全的异常，抛出500错误
				return return500(request,response,e);//500
			}
		} catch (IOException ioe) {//一般来说不会有错误
			return return500(request,response,ioe);//500
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
