package com.wisdom.core.security.domain;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 功能描述：
 * 用户实体
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-10-8</b>
 * <br>创建时间：<b>下午05:50:46</b>
 * <br>文件结构：<b>spring:com.wisdom.core.security.domain/User.java</b>
 */
public class User implements java.io.Serializable,UserDetails{

	private static final long serialVersionUID = 533999380252969758L;

	private Long id;
	
	private String username;
  
	private String password;

	private String cnname;
	
	private String mobile;

	private String email;
	
	private boolean enabled = true;

	private boolean accountExpired = true;

	private boolean accountLocked = true;

	private boolean credentialsExpired = true;

	private List<Role> roles;

	private List<GrantedAuthority> authorities;

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

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public boolean isAccountNonExpired() {
		return isAccountExpired();
	}

	public boolean isAccountNonLocked() {
		return isAccountLocked();
	}

	public boolean isCredentialsNonExpired() {
		return isCredentialsExpired();
	}

	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
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

}