package org.paramecium.mvc;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.paramecium.security.SecurityThread;
/**
 * 功 能 描 述:<br>
 * 特殊功能的过滤器,包含字符集过滤，安全用户线程放入与销毁
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-18下午02:39:07
 * <br>项 目 信 息:paramecium:org.paramecium.mvc.SpecialFilter.java
 */
public class SpecialFilter implements Filter{
	
	private static String encoding = null;

	public void init(FilterConfig config) throws ServletException {
		if(SpecialFilter.encoding == null){
			SpecialFilter.encoding = config.getInitParameter("encoding");
		}
	}
	
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);
		start((HttpServletRequest)request);
		chain.doFilter(request, response);
		end();
	}

	private static void end(){
		SecurityThread.endThread();
	}

	private static void start(HttpServletRequest request){
		if(request.getSession(false)==null){
			request.getSession();
		}
		SecurityThread.startThread(request);
	}
	
	public void destroy() {
	}
	
}