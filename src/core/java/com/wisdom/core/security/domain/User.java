package com.wisdom.core.security.domain;

import java.util.Collection;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;

import com.wisdom.core.annotation.NotMapping;
import com.wisdom.core.annotation.NotUpdate;
import com.wisdom.core.annotation.Reference;
import com.wisdom.core.annotation.SimpleEntity;
import com.wisdom.core.orm.domain.BaseEntity;

/**
 * 功能描述：
 * 用户实体
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-10-8</b>
 * <br>创建时间：<b>下午05:50:46</b>
 * <br>文件结构：<b>spring:com.wisdom.core.security.domain/User.java</b>
 */
@SimpleEntity(tableName="t_system_user_info",isUseIDCreator=true)
public class User extends BaseEntity implements java.io.Serializable{

	private static final long serialVersionUID = 533999380252969758L;

	private Long id;
	
	@NotNull(message="登录用户名不能为空！")
	@Size(min=2,max=20,message="登录用户名最少输入{min}个字符最多不超过{max}个字符")
	@NotUpdate
	private String username;
  
	@NotNull(message="登录密码不能为空！")
	@Size(min=2,max=40,message="登录密码最少输入{min}个字符最多不超过{max}个字符")
	@NotUpdate
	private String password;

	@NotNull(message="姓名不能为空！")
	@Size(min=2,max=20,message="姓名最少输入{min}个字符最多不超过{max}个字符")
	private String cnname;
	
	@Size(min=5,max=20,message="移动电话最少输入{min}个字符最多不超过{max}个字符")
	private String mobile;

	@Size(min=5,max=50,message="电子邮箱最少输入{min}个字符最多不超过{max}个字符")
	private String email;
	
	private String organCode;//所属企业编码，暂时无用
	
	private String pyname;

	@NotMapping
	@Reference(refTableName = "t_organ", FKFieldName = "organ_code", refViewFieldName = "organ_name", refPKFieldName = "organ_code")
	private String organName;//所属企业编码，暂时无用
	
	@NotUpdate
	private boolean enabled = true;

	@NotUpdate
	private boolean accountExpired = true;

	@NotUpdate
	private boolean accountLocked = true;

	@NotUpdate
	private boolean credentialsExpired = true;

	@NotMapping
	private Collection<Role> roles;

	@NotMapping
	private Collection<GrantedAuthority> authorities;
	
	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}
	
	public String getOrganCode() {
		return organCode;
	}

	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAccountExpired() {
		return accountExpired;
	}

	public void setAccountExpired(boolean accountExpired) {
		this.accountExpired = accountExpired;
	}

	public boolean isAccountLocked() {
		return accountLocked;
	}

	public void setAccountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	public boolean isCredentialsExpired() {
		return credentialsExpired;
	}

	public void setCredentialsExpired(boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCnname() {
		return cnname;
	}

	public void setCnname(String cnname) {
		this.cnname = cnname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public Collection<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPyname() {
		return pyname;
	}

	public void setPyname(String pyname) {
		this.pyname = pyname;
	}

}