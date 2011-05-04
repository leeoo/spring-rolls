package com.demo.entity;

import java.util.Date;

import org.paramecium.orm.annotation.Column;
import org.paramecium.orm.annotation.Entity;
import org.paramecium.orm.annotation.PrimaryKey;
import org.paramecium.orm.annotation.VirtualColumn;

@Entity(tableName="t_logger_info",orderBy="id DESC")
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
