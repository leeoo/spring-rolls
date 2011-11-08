package org.paramecium.security;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.paramecium.commons.DateUtils;
/**
 * 功 能 描 述:<br>
 * 用户安全信息
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-22下午01:46:36
 * <br>项 目 信 息:paramecium:org.paramecium.security.UserDetails.java
 */
public class UserDetails<T extends Object> implements Serializable{

	private static final long serialVersionUID = 943883230212509386L;
	
	private String username;
	
	private String name;
	
	private Date loginDate;
	
	private String sessionId;
	
	private String address;
	
	private boolean enable;
	
	private T otherInfo;
	
	private Collection<Resource> resources;
	
	public UserDetails(){
		this.loginDate = DateUtils.getCurrentDateTime();
	}
	
	public UserDetails(String username,String sessionId,String address,boolean enable,String name,Collection<Resource> resources){
		this.username = username;
		this.sessionId = sessionId;
		this.address = address;
		this.enable = enable;
		this.resources = resources;
		this.name = name;
		this.loginDate = DateUtils.getCurrentDateTime();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public Collection<Resource> getResources() {
		return resources;
	}

	public void setResources(Collection<Resource> resources) {
		this.resources = resources;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public T getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(T otherInfo) {
		this.otherInfo = otherInfo;
	}

}
