package org.paramecium.mvc;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.paramecium.commons.BeanUtils;
import org.paramecium.commons.PathUtils;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;
/**
 * 功能描述(Description):<br><b>
 * 模型视图
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-4-18下午07:53:16</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.paramecium.mvc.ModelAndView.java</b>
 */
public class ModelAndView implements Serializable,Cloneable{
	
	private static final long serialVersionUID = 7105417137624158939L;
	private final static Log logger = LoggerFactory.getLogger();
	private boolean redirect = false;
	private transient HttpServletRequest request;//无需序列化
	private transient HttpServletResponse response;//无需序列化
	
	protected ModelAndView clone() {
		try {
			return (ModelAndView) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
	
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
	@SuppressWarnings("unchecked")
	public <T> T getBean(String beanName,Class<T> clazz){
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
						BeanUtils.setFieldValue(field, value, bean);
					} catch (Exception e) {
					}
				}
			}
		} catch (Exception e) {
		}
		return (T)bean;
	}

	/**
	 * 构建没有beanName.的表单数据成实体
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getBean(Class<T> clazz){
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
						BeanUtils.setFieldValue(field, value, bean);
					} catch (Exception e) {
					}
				}
			}
		} catch (Exception e) {
		}
		return (T)bean;
	}
	
	/**
	 * 获得单值
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getValue(String name,Class<T> clazz){
		String value = request.getParameter(name);
		if(value==null){
			return null;
		}
		return (T)BeanUtils.getValueByClass(value, clazz);
	}

	/**
	 * 获得多值,类型为基本数据类型
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> Collection <T> getValues(String name,Class<T> clazz){
		String[] values = request.getParameterValues(name);
		if(values==null||values.length==0){
			return null;
		}
		Collection<T> list = new HashSet<T>();
		for(String value : values){
			list.add((T)BeanUtils.getValueByClass(value, clazz));
		}
		return list;
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
	public ModelAndView forward(String forwardUrl){
		if(response.isCommitted()){
			return this.clone();
		}
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(forwardUrl);
		try {
			requestDispatcher.forward(request, response);
		} catch (ServletException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
		return this.clone();
	}

	/**
	 * redirect重定向
	 * @param redirectUrl
	 */
	public ModelAndView redirect(String redirectUrl){
		try {
			response.sendRedirect(PathUtils.getNewPath(redirectUrl));
			redirect = true;
		} catch (IOException e) {
			logger.error(e);
		}
		return this.clone();
	}
	
	/**
	 * 提供下载功能
	 * @param file
	 */
	public ModelAndView download(byte[] file){
		return download(file, null);
	}
	
	/**
	 * 下载功能
	 * @param file
	 * @param fileName
	 */
	public ModelAndView download(byte[] file,String fileName){
		return download(file, fileName, null);
	}
	
	/**
	 * 下载功能
	 * @param file
	 * @param fileName
	 * @param encoding
	 */
	public ModelAndView download(byte[] file,String fileName,String encoding){
		encoding = encoding!=null?encoding:"utf-8";
		fileName = fileName!=null?fileName:"file";
		response.setContentType("application/octet-stream;charset="+encoding);
		response.setHeader( "Content-Disposition", "attachment;filename="+fileName);
		try{
			OutputStream outStream = response.getOutputStream();
			outStream.write(file);
			outStream.flush();
			outStream.close();
		}catch (Exception e) {
			logger.error(e);
		}
		return this.clone();
	}
	
	/**
	 * 输出xml
	 * @param xml
	 */
	public ModelAndView printXML(String xml){
		return printXML(xml,null);
	}
	
	/**
	 * 输出xml
	 * @param xml
	 * @param encoding
	 */
	public ModelAndView printXML(String xml,String encoding){
		encoding = encoding!=null?encoding:"utf-8";
		response.setContentType("text/xml;charset="+encoding);
		try{
			PrintWriter writer = response.getWriter();
			writer.write(xml);
			writer.flush();
			writer.close();
		}catch (Exception e) {
			logger.error(e);
		}
		return this.clone();
	}
	
	/**
	 * 输出json
	 * @param json
	 */
	public ModelAndView printJSON(String json){
		return printJSON(json,null);
	}
	
	/**
	 * 输出json
	 * @param json
	 * @param encoding
	 */
	public ModelAndView printJSON(String json,String encoding){
		encoding = encoding!=null?encoding:"utf-8";
		response.setContentType("text/html;charset="+encoding);
		try{
			PrintWriter writer = response.getWriter();
			writer.write(json);
			writer.flush();
			writer.close();
		}catch (Exception e) {
			logger.error(e);
		}
		return this.clone();
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}
	
	public HttpSession getSession() {
		return request.getSession(false);
	}

	public boolean isRedirect() {
		return redirect;
	}

	public void setRedirect(boolean redirect) {
		this.redirect = redirect;
	}
	
}
