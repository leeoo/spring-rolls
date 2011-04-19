package com.cy.app.entity;

import java.util.Date;

import org.cy.core.orm.annotation.Column;
import org.cy.core.orm.annotation.Entity;
import org.cy.core.orm.annotation.PrimaryKey;
import org.cy.core.orm.annotation.VirtualColumn;

@Entity(tableName="t_logger_info")
public class Logger {
	
	@PrimaryKey
	@Column
	private Integer id;
	
	@Column(fieldName="log_info",comparison=VirtualColumn.DYNAMIC_WHERE_COMPARISON.LIKE)
	private String info;
	
	@Column(fieldName="log_date")
	private Date date;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
