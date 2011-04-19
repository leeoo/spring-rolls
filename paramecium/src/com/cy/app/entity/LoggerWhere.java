package com.cy.app.entity;

import java.util.Date;

import org.cy.core.orm.annotation.Entity;
import org.cy.core.orm.annotation.VirtualColumn;

@Entity(tableName="t_logger_info")
public class LoggerWhere extends Logger{
	
	@VirtualColumn(comparison=VirtualColumn.DYNAMIC_WHERE_COMPARISON.LESS_EQUAL,comparisonColumn="log_date")
	private Date startDate;
	
	@VirtualColumn(comparison=VirtualColumn.DYNAMIC_WHERE_COMPARISON.THAN_EQUAL,comparisonColumn="log_date")
	private Date endDate;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}
