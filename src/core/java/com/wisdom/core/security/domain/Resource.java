package com.wisdom.core.security.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.wisdom.core.annotation.NotUpdate;
import com.wisdom.core.annotation.SimpleEntity;

/**
 * 功能描述：
 * 资源实体
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-10-8</b>
 * <br>创建时间：<b>下午05:52:20</b>
 * <br>文件结构：<b>spring:com.wisdom.core.security.domain/Resource.java</b>
 */
@SimpleEntity(tableName="t_system_resource_info",isUseIDCreator=true)
public class Resource implements java.io.Serializable{

	private static final long serialVersionUID = -5396060046559398409L;

	private Long id;
	
	@NotNull(message="资源标识不能为空！")
	@Size(min=2,max=20,message="资源标识最少输入{min}个字符最多不超过{max}个字符")
	@NotUpdate
	private String name;

	@NotNull(message="资源路径不能为空！")
	@Size(min=2,max=200,message="资源路径最少输入{min}个字符最多不超过{max}个字符")
	private String path;

	@NotNull(message="资源名称不能为空！")
	@Size(min=2,max=200,message="资源名称最少输入{min}个字符最多不超过{max}个字符")
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