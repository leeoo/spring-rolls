package com.wisdom.core.task.domain;

import java.io.Serializable;
import java.util.Date;

import com.wisdom.core.task.TaskRunnable;

/**
 * 功能描述：任务执行历史记录 <br>
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-11-2</b>
 * <br>创建时间：<b>上午11:21:39</b>
 * <br>文件结构：<b>spring:com.wisdom.core.task.domain/RunHistory.java</b>
 */
public class RunHistory implements Serializable {
	private static final long serialVersionUID = -4913488329214911325L;
	private Long id;
	private Long taskId;
	private Date runTime;
	private Date endTime;
	private int runState = TaskRunnable.RUNSTATE_SUCCESS;
	private int runType = TaskRunnable.RUNTYPE_AUTO;
	private String errorInfo;
	private String runTypeName = getRunTypeName();
	private String runStateName = getRunStateName();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Date getRunTime() {
		return runTime;
	}

	public void setRunTime(Date runTime) {
		this.runTime = runTime;
	}

	public int getRunState() {
		return runState;
	}

	public void setRunState(int runState) {
		this.runState = runState;
	}

	public int getRunType() {
		return runType;
	}

	public void setRunType(int runType) {
		this.runType = runType;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getRunTypeName() {
		if(this.runType==TaskRunnable.RUNTYPE_AUTO){
			this.runTypeName="自动";
		}else{
			this.runTypeName="手动";
		}
		return this.runTypeName;
	}

	public void setRunTypeName(String runTypeName) {
		this.runTypeName = runTypeName;
	}

	public String getRunStateName() {
		if(this.runState==TaskRunnable.RUNSTATE_SUCCESS){
			this.runStateName="成功";
		}else{
			this.runStateName="失败";
		}
		return this.runStateName;
	}

	public void setRunStateName(String runStateName) {
		this.runStateName = runStateName;
	}

}
