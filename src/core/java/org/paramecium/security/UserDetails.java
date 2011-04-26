package org.paramecium.security;

import java.io.Serializable;
import java.util.Collection;
/**
 * 功 能 描 述:<br>
 * 用户安全信息
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-22下午01:46:36
 * <br>项 目 信 息:paramecium:org.paramecium.security.UserDetails.java
 */
public class UserDetails implements Serializable{

	private static final long serialVersionUID = 943883230212509386L;
	
	private String username;
	
	private String sessionId;
	
	private boolean enable;
	
	private Collection<Resource> resources;
	
	public UserDetails(){
		
	}
	
	public UserDetails(String username,String sessionId,boolean enable,Collection<Resource> resources){
		this.username = username;
		this.sessionId = sessionId;
		this.enable = enable;
		this.resources = resources;
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

}
