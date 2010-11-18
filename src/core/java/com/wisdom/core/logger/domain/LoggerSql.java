package com.wisdom.core.logger.domain;

import java.io.Serializable;
import java.util.Date;

import com.wisdom.core.annotation.SimpleEntity;
import com.wisdom.core.utils.DateUtils;

@SimpleEntity(tableName = "t_logger_sql", orderBy = "log_date DESC")
public class LoggerSql implements Serializable{
	private static final long serialVersionUID = 2017845500895539560L;
	
	private Long id;
	private String sqlType;
	private String sqlValue;
	private String callDetails;
	private Date logDate = DateUtils.getCurrentDateTime();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSqlType() {
		return sqlType;
	}

	public void setSqlType(String sqlType) {
		this.sqlType = sqlType;
	}

	public String getSqlValue() {
		return sqlValue;
	}

	public String getCallDetails() {
		return callDetails;
	}

	public void setCallDetails(String callDetails) {
		this.callDetails = callDetails;
	}

	public void setSqlValue(String sqlValue) {
		this.sqlValue = sqlValue;
	}

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

}
