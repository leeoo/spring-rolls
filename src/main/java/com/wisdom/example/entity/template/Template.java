package com.wisdom.example.entity.template;

import java.io.Serializable;
import java.util.Date;
/**
 * 功能描述：模板实体
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-10-16</b>
 * <br>创建时间：<b>上午11:48:06</b>
 * <br>文件结构：<b>spring:com.wisdom.example.entity/Template.java</b>
 */
public class Template implements Serializable{

	private static final long serialVersionUID = -1382472148615935259L;
	
	private Long id;

	private String name;
	
	private String templatePath;
	
	private String htmlPath;
	
	private Date lastDate=new Date();
	
	private String description;
	
	private byte[] file;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public String getHtmlPath() {
		return htmlPath;
	}

	public void setHtmlPath(String htmlPath) {
		this.htmlPath = htmlPath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

}
