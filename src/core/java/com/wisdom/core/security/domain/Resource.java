package com.wisdom.core.security.domain;

/**
 * 功能描述：
 * 资源实体
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-10-8</b>
 * <br>创建时间：<b>下午05:52:20</b>
 * <br>文件结构：<b>spring:com.wisdom.core.security.domain/Resource.java</b>
 */
public class Resource implements java.io.Serializable{

	private static final long serialVersionUID = -5396060046559398409L;

	private Long id;
	
	private String name;

	private String path;

	private String cnname;
	
	public String toString() {
		return this.getCnname();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCnname() {
		return cnname;
	}

	public void setCnname(String cnname) {
		this.cnname = cnname;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}