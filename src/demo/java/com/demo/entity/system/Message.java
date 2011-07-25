package com.demo.entity.system;

import java.util.Date;

import org.paramecium.orm.annotation.Column;
import org.paramecium.orm.annotation.Entity;
import org.paramecium.orm.annotation.PrimaryKey;

@Entity(tableName="t_message",orderBy="publish_date DESC")
public class Message {

	@PrimaryKey
	@Column
	private Long id;
	
	@Column
	private String title;
	
	@Column
	private Date publishDate;
	
	@Column
	private String content;
	
	@Column
	private String auth;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

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

	
}
