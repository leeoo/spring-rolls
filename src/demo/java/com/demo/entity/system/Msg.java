package com.demo.entity.system;

import java.io.Serializable;
import java.util.Date;
/**
 * 站内消息
 * @author caoyang
 */
public class Msg implements Serializable{

	private static final long serialVersionUID = -7701262766308987948L;
	
	private String file;
	
	private Date publishDate;
	
	private String content;
	
	private String auth;

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
	
}
