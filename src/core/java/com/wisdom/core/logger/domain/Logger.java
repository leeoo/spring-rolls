package com.wisdom.core.logger.domain;

import java.io.Serializable;
import java.util.Date;

import com.wisdom.core.annotation.SimpleEntity;
import com.wisdom.core.security.resource.SecurityUtils;
import com.wisdom.core.utils.DateUtils;
/**
 * 功 能 描 述:<br>
 * 日志详细信息
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2010-11-15下午02:47:01
 * <br>项 目 信 息:wisdom.3.0:com.wisdom.core.logger.domain.Logger.java
 */

@SimpleEntity(tableName="t_logger_info",orderBy="log_date DESC")
public class Logger implements Serializable{
	
	private static final long serialVersionUID = 5409618497838653983L;

	private Long id;
	
	private Date logDate = DateUtils.getCurrentDateTime();
	
	private String username = SecurityUtils.getCurrentUserName();
	
	private String cnname = SecurityUtils.getCurrentUser()!=null?SecurityUtils.getCurrentUser().getCnname():"";
	
	private String ip = SecurityUtils.getCurrentUserIp();
	
	private String organName = SecurityUtils.getCurrentUser()!=null?SecurityUtils.getCurrentUser().getOrganName():"";
	
	private String url;
	
	private String somewhere;
	
	private String something;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCnname() {
		return cnname;
	}

	public void setCnname(String cnname) {
		this.cnname = cnname;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSomewhere() {
		return somewhere;
	}

	public void setSomewhere(String somewhere) {
		this.somewhere = somewhere;
	}

	public String getSomething() {
		return something;
	}

	public void setSomething(String something) {
		this.something = something;
	}
	
}
