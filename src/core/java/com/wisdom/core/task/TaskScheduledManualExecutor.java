package com.wisdom.core.task;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import com.wisdom.core.task.domain.RunHistory;
import com.wisdom.core.task.domain.Task;
import com.wisdom.core.task.service.TaskService;
import com.wisdom.core.utils.DateUtils;
/**
 * 功能描述：手动运行器
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-11-3</b>
 * <br>创建时间：<b>上午10:48:44</b>
 * <br>文件结构：<b>spring:com.wisdom.core.task/TaskScheduledManualExecutor.java</b>
 */
public class TaskScheduledManualExecutor implements Runnable,BeanFactoryAware{
	private static final Logger logger= LoggerFactory.getLogger(TaskScheduledManualExecutor.class);
	private Task task;
	private TaskService taskService;
	private BeanFactory beanFactory;
	
	public void run() {
		Date runTime=DateUtils.getCurrentDateTime();
		try{
			TaskRunnable runnable =(TaskRunnable)beanFactory.getBean(task.getInstanceName());
			logger.info("任务{}正在执行中...",task.getTaskName());
			runnable.execute();
			Date endTime=DateUtils.getCurrentDateTime();
			createSuccessHistory(task,runTime,endTime);
		}catch (Exception e) {
			logger.info("任务{}执行时发生错误",task.getTaskName());
			Date endTime=DateUtils.getCurrentDateTime();
			createErrorHistory(task,e.getMessage(),runTime,endTime);
		}
	}
	
	/**
	 * 功能描述：创建任务执行错误历史记录
	 * <br>@param id任务id
	 * <br>@param errorInfo错误信息
	 */
	private void createErrorHistory(final Task task,String errorInfo,Date runTime,Date endTime){
		RunHistory history=new RunHistory();
		history.setTaskId(task.getId());
		history.setRunState(TaskRunnable.RUNSTATE_FAILURE);
		history.setRunType(TaskRunnable.RUNTYPE_MANUAL);
		history.setRunTime(runTime);
		history.setEndTime(endTime);
		history.setErrorInfo(errorInfo);
		task.setEnabled(TaskRunnable.STATE_DISABLED);
		try {
			taskService.updateTaskEnabled(task);
			taskService.saveRunHistory(history);
		}catch (Exception e) {
			logger.error("{}任务的失败日志历史保存失败",task.getTaskName());
		}
	}
	/**
	 * 功能描述：创建任务执行成功历史记录
	 * <br>@param task任务
	 * <br>@param errorInfo错误信息
	 */
	private void createSuccessHistory(final Task task,Date runTime,Date endTime){
		RunHistory history=new RunHistory();
		history.setTaskId(task.getId());
		history.setRunState(TaskRunnable.RUNSTATE_SUCCESS);
		history.setRunType(TaskRunnable.RUNTYPE_MANUAL);
		history.setRunTime(runTime);
		history.setEndTime(endTime);
		history.setErrorInfo("执行成功");
		try {
			if(task.getNextRunTime().before(DateUtils.getCurrentDateTime())){
				task.setNextRunTime(TaskScheduledAutoExecutor.getNextDate(task.getNextRunTime(),task.getRunUnit()));
			}
			taskService.updateTaskNextRunTime(task);
			taskService.saveRunHistory(history);
		}catch (Exception e) {
			logger.error("{}任务的失败日志历史保存失败",task.getTaskName());
		}
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

}
