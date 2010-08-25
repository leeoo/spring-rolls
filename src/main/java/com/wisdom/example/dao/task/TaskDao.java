package com.wisdom.example.dao.task;
/**
 * 功能描述(Description):<br><b>
 * 任务调度信息数据操作接口
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2010-5-18下午01:27:06</b>
 * <br>项目名称(Project Name): <b>wisdom.3.0RC2</b>
 * <br>包及类名(Package Class): <b>com.wisdom.example.dao.task.TaskDao.java</b>
 */
public interface TaskDao {
	public final String SQL_INSERT_TASK = "INSERT INTO t_task_info(task_name,next_run_time,run_unit,enabled,instance_name,description) "
			+ "values(:taskName,:nextRunTime,:runUnit,:enabled,:instanceName,:description)";
	public final String SQL_UPDATE_TASK = "UPDATE t_task_info SET task_name=:taskName,next_run_time=:nextRunTime,"
			+ "run_unit=:runUnit,instance_name=:instanceName,description=:description WHERE id=:id";
	public final String SQL_UPDATE_TASK_RUNTIME = "UPDATE t_task_info SET next_run_time=:nextRunTime WHERE id=:id";
	public final String SQL_UPDATE_TASK_STATE = "UPDATE t_task_info SET enabled=:enabled WHERE id=:id";
	public final String SQL_DELETE_TASK = "DELETE FROM t_task_info WHERE id=?";
	public final String SQL_DELETE_HISTORY_BYTASK = "DELETE FROM t_task_run_history WHERE task_id=?";
	public final String SQL_SELECT_ALL_TASK = "SELECT id,task_name,next_run_time,run_unit,enabled,instance_name,description FROM t_task_info";
	public final String SQL_SELECT_TASK_BYID = "SELECT id,task_name,next_run_time,run_unit,enabled,instance_name,description FROM t_task_info WHERE id=?";
}
