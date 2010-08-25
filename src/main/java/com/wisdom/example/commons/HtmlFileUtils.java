package com.wisdom.example.commons;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
/**
 * 功能描述：
 * 生成静态页工具
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-10-16</b>
 * <br>创建时间：<b>上午11:22:43</b>
 * <br>文件结构：<b>spring:com.wisdom.example.commons/HtmlFileUtils.java</b>
 */
public abstract class HtmlFileUtils {
	protected static final Logger logger = LoggerFactory.getLogger(HtmlFileUtils.class);

	/**
	 * 功能描述：
	 * 通过freemarker模板建立静态页
	 * <br>@param templateFilePath模板文件路径和文件名（默认flt扩展名）
	 * <br>@param dataBean数据载体
	 * <br>@param htmlFileNames要生成的html静态页
	 * <br>@param locale本地化语言设置
	 * <br>@param encoding字符集
	 */
	public static void createHtml(String templateFilePath,Object dataBean,String htmlFileName,Locale locale,String encoding ) {
		Configuration cfg = new Configuration();
		if(locale!=null){
			cfg.setEncoding(locale, encoding);
		}
		try {
			File templateFile=new File(getFilePath(templateFilePath));
			cfg.setDefaultEncoding("UTF-8");
			cfg.setDirectoryForTemplateLoading(templateFile);
		} catch (IOException e) {
			logger.error(e.getMessage());
			System.exit(0);
		}
		Template temp=null;
		try {
			String templateFileName=getFileName(templateFilePath);
			temp = cfg.getTemplate(templateFileName.trim());
		} catch (IOException e) {
			logger.error(e.getMessage());
			System.exit(0);
		}
		Map<String,Object> root=new HashMap<String,Object>();
		root.put("bean", dataBean);
		Writer out=null;
		FileOutputStream fos=null;
		try {
			try {
				FileUtils.mkDirectory(getFilePath(htmlFileName));
			} catch (Exception e) {
				logger.error("文件目录创建错误:"+e.getMessage());
			}
			File htmlFile=new File(htmlFileName);
			fos=new FileOutputStream(htmlFile);
			out = new OutputStreamWriter(fos, "UTF-8");
			temp.process(root, out);
		} catch (FileNotFoundException e) {
			logger.error("文件未找到:"+e.getMessage());
		} catch (IOException e) {
			logger.error("文件操作错误:"+e.getMessage());
		} catch (TemplateException e) {
			logger.error("文件模板操作错误:"+e.getMessage());
		}
		try{fos.flush();}catch (Exception e) {}
		try{out.flush();}catch (Exception e) {}
		try{fos.close();}catch (Exception e) {}
		try{out.close();}catch (Exception e) {}
		logger.info("文件创建成功:"+htmlFileName);
	}
	
	/**
	 * 功能描述：
	 * 通过freemarker模板建立静态页
	 * <br>@param templateFilePath模板文件路径和文件名（默认flt扩展名）
	 * <br>@param dataBean数据载体
	 * <br>@param htmlFileNames要生成的html静态页
	 */
	public static void createHtml(String templateFilePath,Object dataBean,String htmlFileName){
		createHtml(templateFilePath, dataBean, htmlFileName, null, null);
	}
	
	/**
	 * 功能描述：获得文件完整路径的文件名
	 * <br>@param path完整路径
	 * <br>@return文件名
	 */
	public static String getFileName(final String fullPathFileName) {
		String name=fullPathFileName.replaceAll("\\\\", "/");
		String[] str=name.split("/");
		String lastName=null;
		if(str!=null){
			lastName=str[str.length-1];
		}
		return lastName;
	}
	
	/**
	 * 功能描述：获得完整路径的路径
	 * <br>@param path完整路径
	 * <br>@return文件名
	 */
	public static String getFilePath(final String fullPathFileName) {
		return fullPathFileName.replace(getFileName(fullPathFileName), "");
	}
	
}
