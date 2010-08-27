package com.wisdom.core.task;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import com.wisdom.core.task.domain.RunHistory;
import com.wisdom.core.task.domain.Task;
import com.wisdom.core.task.service.TaskScheduledCache;
import com.wisdom.core.task.service.TaskService;
import com.wisdom.core.utils.DateUtils;

/**
 * 功能描述：任务调度自动运行入口
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-11-2</b>
 * <br>创建时间：<b>上午11:23:00</b>
 * <br>文件结构：<b>spring:com.wisdom.core.task/TaskScheduledAutoExecutor.java</b>
 */
public class TaskScheduledAutoExecutor implements Runnable,BeanFactoryAware{
	private static final Logger logger= LoggerFactory.getLogger(TaskScheduledAutoExecutor.class);
	private boolean run;
	private TaskService taskService;
	private BeanFactory beanFactory;
	
	/**
	 * 运行入口方法
	 */
	public void run() {
		if(run){
			logger.info("//****************任务调度开始检查任务列表****************//");
			Date currentDate=DateUtils.getCurrentDateTime();
			Collection<Task> tasks=createCurrentTaskList(currentDate);
			Map<Long,TaskRunnable> runnables=createTaskRunnableList(tasks);
			execute(runnables);
		}
		logger.info("//****************本时段任务调度结束****************//");
	}
	
	/**
	 * 功能描述：创建当前任务列表，符合运行时间要求的会被加入。
	 * <br>@param currentDate当前时间
	 * <br>@return 任务列表
	 */
	private Collection<Task> createCurrentTaskList(Date currentDate){
		Collection<Task> tasks=new LinkedList<Task>();
		for(Task task:TaskScheduledCache.getAllCache()){
			if(task.getNextRunTime().before(currentDate)){
				tasks.add(task);
				logger.info("----------====任务{}被加入当前任务列表====----------",task.getTaskName());
			}
		}
		return tasks;
	}
	
	/**
	 * 功能描述：创建可执行任务实例列表
	 * <br>@param tasks任务列表
	 * <br>@return 任务实例列表
	 */
	private Map<Long,TaskRunnable> createTaskRunnableList(Collection<Task> tasks){
		Map<Long,TaskRunnable> runnables= new LinkedHashMap<Long,TaskRunnable>();
		for(Task task:tasks){
			synchronized (task) {
				if(task.isEnabled()){
					try{
						TaskRunnable runnable=(TaskRunnable)beanFactory.getBean(task.getInstanceName());
						runnables.put(task.getId(),runnable);
					}catch(Exception ex){
						logger.error("{}任务运行失败",task.getTaskName());
						createErrorHistory(task,ex.getMessage(),DateUtils.getCurrentDateTime(),DateUtils.getCurrentDateTime());
					}
				}
			}
		}
		return runnables;
	}
	
	/**
	 * 功能描述：执行任务
	 * <br>@param runnables
	 */
	private void execute(Map<Long,TaskRunnable> runnables){
		if(runnables!=null&&!runnables.isEmpty()){
			TaskRunnable runnable=null;
			Task task=null;
			for(Long id:runnables.keySet()){
				task=TaskScheduledCache.getCache(id);
				runnable=runnables.get(id);
				Date runTime=DateUtils.getCurrentDateTime();
				try{
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
		history.setErrorInfo(errorInfo);
		history.setRunTime(runTime);
		history.setEndTime(endTime);
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
		history.setRunTime(runTime);
		history.setEndTime(endTime);
		history.setErrorInfo("执行成功");
		try {
			task.setNextRunTime(getNextDate(task.getNextRunTime(),task.getRunUnit()));
			taskService.updateTaskNextRunTime(task);
			taskService.saveRunHistory(history);
		}catch (Exception e) {
			logger.error("{}任务的失败日志历史保存失败",task.getTaskName());
		}
	}
	/**
	 * 功能描述：成功后改变
	 * <br>@param currentDate
	 * <br>@param unit
	 * <br>@return
	 */
	public static Date getNextDate(Date currentDate,int unit){
		if(unit==TaskRunnable.RUNUNIT_DAY){
			currentDate=DateUtils.addDays(currentDate, 1);
		}else if(unit==TaskRunnable.RUNUNIT_WEEK){
			currentDate=DateUtils.addWeeks(currentDate, 1);
		}else if(unit==TaskRunnable.RUNUNIT_MONTH){
			currentDate=DateUtils.addMonths(currentDate, 1);
		}else if(unit==TaskRunnable.RUNUNIT_YEAR){
			currentDate=DateUtils.addYears(currentDate, 1);
		}else if(unit==TaskRunnable.RUNUNIT_HOUR){
			currentDate=DateUtils.addHours(currentDate, 1);
		}
		return currentDate;
	}
	
	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public boolean isRun() {
		return run;
	}

	public void setRun(boolean run) {
		this.run = run;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
}
