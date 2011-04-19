package org.cy.core.mvc;

import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.cy.core.commons.BeanUitls;
import org.cy.core.commons.DateUtils;
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
	
	/**
	 * 根据提交的name构建bean,表单中beanName.propertyName格式将被构建。
	 * @param beanName
	 * @param clazz
	 * @return
	 */
	public Object getBean(String beanName,Class<?> clazz){
		String bn = beanName.concat(".");
		Object bean = null;
		try {
			bean = clazz.newInstance();
			for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
				Field[] fields = superClass.getDeclaredFields();
				for(Field field : fields){
					field.setAccessible(true);
					try {
						String name = bn.concat(field.getName());
						String value = request.getParameter(name);
						BeanUitls.setFieldValue(field, value, bean);
					} catch (Exception e) {
					}
				}
			}
		} catch (Exception e) {
		}
		return bean;
	}

	/**
	 * 构建没有beanName.的表单数据成实体
	 * @param clazz
	 * @return
	 */
	public Object getBean(Class<?> clazz){
		Object bean = null;
		try {
			bean = clazz.newInstance();
			for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
				Field[] fields = superClass.getDeclaredFields();
				for(Field field : fields){
					field.setAccessible(true);
					try {
						String name = field.getName();
						String value = request.getParameter(name);
						BeanUitls.setFieldValue(field, value, bean);
					} catch (Exception e) {
					}
				}
			}
		} catch (Exception e) {
		}
		return bean;
	}
	
	/**
	 * 获得单值
	 * @param name
	 * @return
	 */
	public Object getValue(String name,Class<?> clazz){
		String value = request.getParameter(name);
		if(value==null){
			return null;
		}
		if (String.class.equals(clazz)){
			return value;
		}else if (int.class.equals(clazz) || Integer.class.equals(clazz)) {
			return Integer.parseInt(value);
		}else if (java.util.Date.class.equals(clazz)) {
			return DateUtils.parse(value);
		}else if (long.class.equals(clazz) || Long.class.equals(clazz)) {
			return Long.parseLong(value);
		}else if (boolean.class.equals(clazz) || Boolean.class.equals(clazz)) {
			return Boolean.parseBoolean(value);
		}else if (byte.class.equals(clazz) || Byte.class.equals(clazz)) {
			return Byte.parseByte(value);
		}else if (short.class.equals(clazz) || Short.class.equals(clazz)) {
			return Short.parseShort(value);
		}else if (float.class.equals(clazz) || Float.class.equals(clazz)) {
			return Float.parseFloat(value);
		}else if (double.class.equals(clazz) || Double.class.equals(clazz) || Number.class.equals(clazz)) {
			return Double.parseDouble(value);
		}else if (byte[].class.equals(clazz)) {
			return value.getBytes();
		}else if (BigDecimal.class.equals(clazz)) {
			return new BigDecimal(value);
		}
		return null;
	}
	
	/**
	 * 添加数据
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
