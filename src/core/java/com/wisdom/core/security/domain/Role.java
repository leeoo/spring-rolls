package com.wisdom.core.security.domain;

import java.util.Collection;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.wisdom.core.annotation.NotMapping;
import com.wisdom.core.annotation.NotUpdate;
import com.wisdom.core.annotation.SimpleEntity;
import com.wisdom.core.orm.domain.BaseEntity;

/**
 * 功能描述：
 * 角色实体
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-10-8</b>
 * <br>创建时间：<b>下午05:51:14</b>
 * <br>文件结构：<b>spring:com.wisdom.core.security.domain/Role.java</b>
 */
@SimpleEntity(tableName="t_system_role_info",isUseIDCreator=true)
public class Role extends BaseEntity implements java.io.Serializable {

	private static final long serialVersionUID = 2792629731265339761L;

	private Long id;
	
	@NotNull(message="角色标识不能为空！")
	@Size(min=2,max=20,message="角色标识最少输入{min}个字符最多不超过{max}个字符")
	@NotUpdate
	private String name;
	
	@NotNull(message="角色名称不能为空！")
	@Size(min=2,max=20,message="角色名称最少输入{min}个字符最多不超过{max}个字符")
	private String cnname;

	@NotMapping
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
