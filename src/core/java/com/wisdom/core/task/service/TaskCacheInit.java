package com.wisdom.core.task.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wisdom.core.task.domain.Task;

/**
 * 功能描述：启动初始化任务调度缓存
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-11-2</b>
 * <br>创建时间：<b>上午11:37:34</b>
 * <br>文件结构：<b>spring:com.wisdom.core.task.service/TaskCacheInit.java</b>
 */
public class TaskCacheInit {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private TaskService taskService;
	
	public void init(){
		logger.info("正在初始化任务调度缓存");
		List<Task> tasks=taskService.getAllTask();
		for(Task task:tasks){
			TaskScheduledCache.putCache(task);
		}
		logger.info("任务调度缓存初始化结束");
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}
	
}
