package com.wisdom.core.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/**
 * 功能描述：
 * 系统内存监控过滤器
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-10-30</b>
 * <br>创建时间：<b>下午03:55:04</b>
 * <br>文件结构：<b>spring:com.wisdom.core.web/SystemMemoryInterceptor.java</b>
 */
public class SystemMemoryInterceptor extends HandlerInterceptorAdapter{
	protected static Logger logger = LoggerFactory.getLogger(SystemMemoryInterceptor.class);
	public static boolean enabled = false;

	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		if(enabled){
			int a=1024*1024;
			String maxMem=Runtime.getRuntime().maxMemory()/a+"MB";
			String currentMem=Runtime.getRuntime().totalMemory()/a+"MB";
			String  residualMem=Runtime.getRuntime().freeMemory()/a+"MB";
			request.setAttribute("maxMem", maxMem);
			request.setAttribute("currentMem", currentMem);
			request.setAttribute("residualMem", residualMem);
			float b=(float)(a);
			logger.info("JVM内存分配: 额定"+(float)((float)Runtime.getRuntime().maxMemory()/b)+"MB 实际"+(float)(Runtime.getRuntime().totalMemory()/b)+"MB 剩余"+(float)Runtime.getRuntime().freeMemory()/b+"MB");
		}
		return super.preHandle(request, response, handler);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled2) {
		enabled = enabled2;
	}

	

}
