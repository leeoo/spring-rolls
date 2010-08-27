package com.wisdom.core.security.domain;

import java.util.Collection;

/**
 * 功能描述：
 * 角色实体
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-10-8</b>
 * <br>创建时间：<b>下午05:51:14</b>
 * <br>文件结构：<b>spring:com.wisdom.core.security.domain/Role.java</b>
 */
public class Role implements java.io.Serializable {

	private static final long serialVersionUID = 2792629731265339761L;

	private Long id;
	
	private String name;
	
	private String cnname;

	private Collection<Resource> resources;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<Resource> getResources() {
		return resources;
	}

	public void setResources(Collection<Resource> resources) {
		this.resources = resources;
	}

	public String toString() {
		return this.getCnname();
	}

	public String getCnname() {
		return cnname;
	}

	public void setCnname(String cnname) {
		this.cnname = cnname;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
