package org.cy.core.test;

import java.util.Date;

import org.cy.core.orm.annotation.Column;
import org.cy.core.orm.annotation.Entity;
import org.cy.core.orm.annotation.PrimaryKey;

@Entity(tableName="t_logger_info")
public class Logger {

	@PrimaryKey
	@Column
	private Integer id;
	
	@Column(fieldName="log_info")
	private String info;
	
	@Column
	private Date logDate;

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

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}
	
}
