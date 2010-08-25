package com.wisdom.core.task.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.wisdom.core.task.domain.RunHistory;
import com.wisdom.core.task.domain.Task;
import com.wisdom.core.utils.Page;
/**
 * 功能描述：管理任务,同时展示一个servcie应用层实例如果管理多个dao数据访问层实例
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-11-2</b>
 * <br>创建时间：<b>上午11:22:20</b>
 * <br>文件结构：<b>spring:com.wisdom.core.task.service.TaskService.java</b>
 */
public interface TaskService {
	
	/**
	 * 功能描述：保存任务
	 * <br>@param task任务
	 * <br>@throws Exception
	 */
	public void saveTask(Task task)throws Exception;
	
	/**
	 * 功能描述：修改任务
	 * <br>@param task任务
	 * <br>@throws Exception
	 */
	public void updateTask(Task task)throws Exception;
	
	/**
	 * 功能描述：修改任务信息的下次执行时间
	 * <br>@param task任务
	 * <br>@throws Exception
	 */
	public void updateTaskNextRunTime(Task task)throws Exception;
	
	/**
	 * 功能描述：修改任务信息的状态
	 * @see TaskRunnable.STATE_ENABLED=1为任务可用,STATE_DISABLED=0为任务已停用
	 * <br>@param task任务
	 * <br>@throws Exception
	 */
	public void updateTaskEnabled(Task task)throws Exception;
	
	/**
	 * 功能描述：删除任务信息,关联删除任务的执行历史.
	 * <br>@param id主键
	 * <br>@throws Exception
	 */
	public void deleteTask(Long id)throws Exception;
	
	/**
	 * 功能描述：根据主键得到任务信息
	 * <br>@param id主键
	 * <br>@return 任务信息
	 */
	public Task getTaskById(Long id);
	
	/**
	 * 功能描述：返回所有任务列表
	 * <br>@return 任务列表
	 */
	public List<Task> getAllTask();
	
	/**
	 * 功能描述：系统自动保存执行历史
	 * <br>@param runHistory执行历史信息
	 * <br>@throws Exception
	 */
	public void saveRunHistory(RunHistory runHistory)throws Exception;
	
	/**
	 * 功能描述：删除执行历史
	 * <br>@param id主键
	 * <br>@throws Exception
	 */
	public void deleteRunHistory(Long id)throws Exception;
	
	/**
	 * 功能描述：根据任务主键获得所有执行历史分页信息
	 * <br>@param page分页参数
	 * <br>@param taskId任务主键
	 * <br>@return 执行历史分页信息
	 */
	@Transactional(readOnly=true)
	public Page getAllRunHistoryByTaskId(Page page,Long taskId);

	/**
	 * 功能描述：根据任务主键获得所有执行历史信息
	 * <br>@param taskId任务主键
	 * <br>@return 执行历史信息
	 */
	public List<RunHistory> getAllRunHistoryByTaskId(Long taskId);
	
}
