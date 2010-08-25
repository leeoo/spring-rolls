package com.wisdom.example.service.task;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wisdom.core.task.domain.RunHistory;
import com.wisdom.core.task.domain.Task;
import com.wisdom.core.task.service.TaskScheduledCache;
import com.wisdom.core.task.service.TaskService;
import com.wisdom.core.utils.Page;
import com.wisdom.example.dao.JdbcGenericSupportDao;
import com.wisdom.example.dao.task.RunHistoryDao;
import com.wisdom.example.dao.task.TaskDao;
/**
 * 功能描述(Description):<br><b>
 * 管理任务,同时展示一个servcie应用层实例如果管理多个dao数据访问层实例
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2010-5-18下午01:25:52</b>
 * <br>项目名称(Project Name): <b>wisdom.3.0RC2</b>
 * <br>包及类名(Package Class): <b>com.wisdom.example.service.task.TaskServiceImpl.java</b>
 */
@Service
@Transactional
public class TaskServiceImpl extends JdbcGenericSupportDao implements TaskService,TaskDao,RunHistoryDao{
	
	/**
	 * 功能描述：保存任务
	 * <br>@param task任务
	 * <br>@throws Exception
	 */
	public void saveTask(Task task)throws Exception{
		Long id=jdbcDao.insertBeanGetGeneratedKey("t_task_info", "id", task);
		task.setId(id);
		TaskScheduledCache.putCache(task);
	}
	
	/**
	 * 功能描述：修改任务
	 * <br>@param task任务
	 * <br>@throws Exception
	 */
	public void updateTask(Task task)throws Exception{
		jdbcDao.executeBean(SQL_UPDATE_TASK, task);
		TaskScheduledCache.removeCache(task.getId());
		TaskScheduledCache.putCache(task);
	}
	
	/**
	 * 功能描述：修改任务信息的下次执行时间
	 * <br>@param task任务
	 * <br>@throws Exception
	 */
	public void updateTaskNextRunTime(Task task)throws Exception{
		jdbcDao.executeBean(SQL_UPDATE_TASK_RUNTIME, task);
		Task cacheTask=TaskScheduledCache.getCache(task.getId());
		cacheTask.setNextRunTime(task.getNextRunTime());
		TaskScheduledCache.removeCache(cacheTask.getId());
		TaskScheduledCache.putCache(cacheTask);
	}
	
	/**
	 * 功能描述：修改任务信息的状态
	 * @see TaskRunnable.STATE_ENABLED=1为任务可用,STATE_DISABLED=0为任务已停用
	 * <br>@param task任务
	 * <br>@throws Exception
	 */
	public void updateTaskEnabled(Task task)throws Exception{
		jdbcDao.executeBean(SQL_UPDATE_TASK_STATE, task);
		Task cacheTask=TaskScheduledCache.getCache(task.getId());
		cacheTask.setEnabled(task.isEnabled());
		TaskScheduledCache.removeCache(cacheTask.getId());
		TaskScheduledCache.putCache(cacheTask);
	}
	
	/**
	 * 功能描述：删除任务信息,关联删除任务的执行历史.
	 * <br>@param id主键
	 * <br>@throws Exception
	 */
	public void deleteTask(Long id)throws Exception{
		jdbcDao.executeArray(SQL_DELETE_TASK, id);
		jdbcDao.executeArray(SQL_DELETE_HISTORY_BYTASK, id);
		TaskScheduledCache.removeCache(id);
	}
	
	/**
	 * 功能描述：根据主键得到任务信息
	 * <br>@param id主键
	 * <br>@return 任务信息
	 */
	@Transactional(readOnly=true)
	public Task getTaskById(Long id){
		return (Task)jdbcDao.findUniqueBeanByArray(SQL_SELECT_TASK_BYID, Task.class, id);
	}
	
	/**
	 * 功能描述：返回所有任务列表
	 * <br>@return 任务列表
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<Task> getAllTask(){
		return jdbcDao.findListBean(SQL_SELECT_ALL_TASK, Task.class);
	}
	
	/**
	 * 功能描述：系统自动保存执行历史
	 * <br>@param runHistory执行历史信息
	 * <br>@throws Exception
	 */
	public void saveRunHistory(RunHistory runHistory)throws Exception{
		jdbcDao.executeBean(SQL_INSERT_HISTORY,runHistory);
	}
	
	/**
	 * 功能描述：删除执行历史
	 * <br>@param id主键
	 * <br>@throws Exception
	 */
	public void deleteRunHistory(Long id)throws Exception{
		jdbcDao.executeArray(SQL_DELETE_HISTORY, id);
	}
	
	/**
	 * 功能描述：根据任务主键获得所有执行历史分页信息
	 * <br>@param page分页参数
	 * <br>@param taskId任务主键
	 * <br>@return 执行历史分页信息
	 */
	@Transactional(readOnly=true)
	public Page getAllRunHistoryByTaskId(Page page,Long taskId){
		return jdbcDao.findPageListBean(SQL_SELECT_HISTORY_BYTASK, RunHistory.class, page,taskId);
	}

	/**
	 * 功能描述：根据任务主键获得所有执行历史信息
	 * <br>@param taskId任务主键
	 * <br>@return 执行历史信息
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<RunHistory> getAllRunHistoryByTaskId(Long taskId){
		return jdbcDao.findListBeanByArray(SQL_SELECT_HISTORY_BYTASK, RunHistory.class,taskId);
	}
	
}
