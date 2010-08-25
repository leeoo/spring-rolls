package com.wisdom.example.dao.task;

/**
 * 功能描述(Description):<br><b>
 * 任务调度执行历史数据操作接口
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2010-5-18下午01:26:59</b>
 * <br>项目名称(Project Name): <b>wisdom.3.0RC2</b>
 * <br>包及类名(Package Class): <b>com.wisdom.example.dao.task.RunHistoryDao.java</b>
 */
public interface RunHistoryDao {
	public final String SQL_INSERT_HISTORY = "INSERT INTO t_task_run_history(task_id,run_time,end_time,run_state,run_type,error_info)"
			+ "values(:taskId,:runTime,:endTime,:runState,:runType,:errorInfo)";
	public final String SQL_DELETE_HISTORY = "DELETE FROM t_task_run_history WHERE id=?";
	public final String SQL_SELECT_HISTORY_BYTASK = "SELECT id,task_id,run_time,end_time,run_state,run_type,error_info FROM t_task_run_history WHERE task_id=? ORDER BY id DESC";

}
