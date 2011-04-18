package org.cy.core.mvc;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
/**
 * 功 能 描 述:<br>
 * 字符集过滤器
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-18下午02:39:07
 * <br>项 目 信 息:paramecium:org.cy.core.mvc.EncodingFilter.java
 */
public class EncodingFilter implements Filter{
	
	private String encoding = "UTF-8";

	public void init(FilterConfig config) throws ServletException {
		this.encoding = config.getInitParameter("encoding");
	}
	
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);
		chain.doFilter(request, response);
	}

	public void destroy() {
		
	}
	
}