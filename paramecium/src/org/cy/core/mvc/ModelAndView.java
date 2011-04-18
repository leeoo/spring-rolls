package org.cy.core.mvc;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.cy.core.commons.BeanUitls;
import org.cy.core.log.Log;
import org.cy.core.log.LoggerFactory;
/**
 * 功能描述(Description):<br><b>
 * 模型视图
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-4-18下午07:53:16</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.cy.core.mvc.ModelAndView.java</b>
 */
public class ModelAndView {
	
	private final static Log logger = LoggerFactory.getLogger();
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public ModelAndView(final HttpServletRequest request,final HttpServletResponse response){
		this.request = request;
		this.response = response;
	}
	
	@SuppressWarnings("unchecked")
	public Object getValue(Class<?> clazz){
		return BeanUitls.map2Bean(clazz, request.getParameterMap(), false);
	}
	
	/**
	 * 获得值
	 * @param name
	 * @return
	 */
	public Object getValue(String name){
		return request.getAttribute(name);
	}
	
	/**
	 * 添加值
	 * @param name
	 * @param value
	 */
	public void addValue(String name,Object value){
		request.setAttribute(name, value);
	}
	
	/**
	 * forward跳转
	 * @param forwardUrl
	 */
	public void forward(String forwardUrl){
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(forwardUrl);
		try {
			requestDispatcher.forward(request, response);
		} catch (ServletException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
	}

	/**
	 * redirect重定向
	 * @param redirectUrl
	 */
	public void redirect(String redirectUrl){
		try {
			response.sendRedirect(redirectUrl);
		} catch (IOException e) {
			logger.error(e);
		}
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}
	
	public HttpSession getSession() {
		return request.getSession();
	}
	
}
