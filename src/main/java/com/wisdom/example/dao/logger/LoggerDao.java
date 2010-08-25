package com.wisdom.example.dao.logger;
/**
 * <b>业务说明</b>:<br>
 * 日志信息数据访问接口
 * <br><b>作  者</b>: Cao.Yang<br>
 * <b>建立日期</b>: 2009-12-3<br>
 * <b>建立时间</b>: 上午11:45:57<br>
 * <b>项目名称</b>: spring<br>
 * <b>包及类名</b>: com.wisdom.example.dao.logger.daoLoggerDao.java<br>
 */
public interface LoggerDao {
	public final String SQL_INSERT_LOGGER = "INSERT INTO t_logger_info(log_info,log_date) values(:logInfo,:logDate)";
	public final String SQL_DELETE_LOGGER = "DELETE FROM t_logger_info WHERE id=?";
	public final String SQL_SELECT_ALL_LOGGER = "SELECT id,log_info AS logInfo,log_date AS logDate FROM t_logger_info ORDER BY logDate DESC";
}
