package org.dily.mvc;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dily.ioc.ApplicationContext;
import org.dily.ioc.ControllerClassInfo;
import org.dily.ioc.IocContextIndex;
import org.dily.log.Log;
import org.dily.log.LoggerFactory;
import org.dily.mvc.annotation.MappingMethod;
/**
 * 功 能 描 述:<br>
 * 通过Servlet提取Controller所需的信息
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-18下午02:02:53
 * <br>项 目 信 息:dily:org.dily.mvc.ControllerExtractor.java
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
				ControllerClassInfo classInfo = IocContextIndex.getController(URIStrs[0]);
				if(classInfo==null){
					logger.warn("IocContextIndex未曾建立过的索引:"+URIStrs[0]);
					return return404(response);
				}
				Object controller = null;
				if(classInfo.isSingle()){
					controller = ApplicationContext.getBean(classInfo.getNamespace());
				}else{
					controller = classInfo.getClazz().newInstance();
				}
				if(controller==null){
					logger.warn("ApplicationContext无法构建该Controller:"+classInfo.getNamespace());
					return return404(response);
				}
				return invoke(classInfo.getClazz(), request, response, controller, URIStrs);
			} catch (Throwable e) {
				return return500(request,response,e);
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
						return true;
					}else if(lineStr.concat(mappingMethod.value()).equals(URIStrs[1])){//同上
						method.invoke(controller, mv);
						return true;
					}
				}else if(lineStr.concat(method.getName()).equals(URIStrs[1])){
					method.invoke(controller, mv);
					return true;
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
