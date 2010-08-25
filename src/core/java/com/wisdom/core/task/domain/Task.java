package com.wisdom.core.task.domain;

import java.io.Serializable;
import java.util.Date;

import com.wisdom.core.task.TaskRunnable;

/**
 * 功能描述：任务调度实体
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-11-2</b>
 * <br>创建时间：<b>上午11:21:29</b>
 * <br>文件结构：<b>spring:com.wisdom.core.task.domain/Task.java</b>
 */
public class Task implements Serializable{
	private static final long serialVersionUID = -9091534412267344466L;
	private Long id;
	
	private String taskName;
	
	private Date nextRunTime;

	private int runUnit = TaskRunnable.RUNUNIT_DAY;
	
	private boolean enabled = TaskRunnable.STATE_ENABLED;

	private String instanceName;
	
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Date getNextRunTime() {
		return nextRunTime;
	}

	public void setNextRunTime(Date nextRunTime) {
		this.nextRunTime = nextRunTime;
	}

	public int getRunUnit() {
		return runUnit;
	}

	public void setRunUnit(int runUnit) {
		this.runUnit = runUnit;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
