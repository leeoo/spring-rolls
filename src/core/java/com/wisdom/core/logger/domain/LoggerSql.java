package com.wisdom.core.logger.domain;

import java.util.Date;

import com.wisdom.core.annotation.SimpleEntity;
import com.wisdom.core.utils.DateUtils;

@SimpleEntity(tableName = "t_logger_somewhere", orderBy = "log_date DESC")
public class LoggerSql {
	private Long id;
	private String sqlType;
	private String sqlValue;
	private Date logDate = DateUtils.getCurrentDateTime();
	private Long loggerId;

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

	public void setSqlValue(String sqlValue) {
		this.sqlValue = sqlValue;
	}

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public Long getLoggerId() {
		return loggerId;
	}

	public void setLoggerId(Long loggerId) {
		this.loggerId = loggerId;
	}
}
