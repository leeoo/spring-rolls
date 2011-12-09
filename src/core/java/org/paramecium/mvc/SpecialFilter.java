package org.paramecium.mvc;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
/**
 * 功 能 描 述:<br>
 * 特殊功能的过滤器,包含字符集过滤(可以将安全用户线程放入与销毁，未经测试，无用请求是否开销太大)
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-18下午02:39:07
 * <br>项 目 信 息:paramecium:org.paramecium.mvc.SpecialFilter.java
 */
public class SpecialFilter implements Filter{
	
	private static String encoding = null;
	private final static String cn = "UTF-8";

	public void init(FilterConfig config) throws ServletException {
		if(SpecialFilter.encoding == null){
			SpecialFilter.encoding = config.getInitParameter("encoding");
		}
	}
	
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);
		chain.doFilter(request, response);
	}
	
	/**
	 * 获得当前系统的字符集
	 * @return
	 */
	public static String getEncoding(){
		if(encoding==null){
			return cn;
		}
		return encoding;
	}

	public void destroy() {
	}
	
}