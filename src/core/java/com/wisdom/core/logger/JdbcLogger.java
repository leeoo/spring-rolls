package com.wisdom.core.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wisdom.core.logger.domain.LoggerSql;
import com.wisdom.core.utils.ScheduledThreadUtils;
/**
 * 功 能 描 述:<br>
 * 用户截获jdbc产生的sql语句
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2010-11-17上午10:25:50
 * <br>项 目 信 息:wisdom.3.0:com.wisdom.core.logger.JdbcLogger.java
 */
public class JdbcLogger {
	
	private static Logger logger = LoggerFactory.getLogger(JdbcLogger.class);
	
	private static String sqlType;
	
	public static boolean enabled;
	
	private static JdbcThreadService jdbcThreadService;
	
	public static void putSql(String sql){
		logger.info("原生SQL语句：{}",sql);
		if(!enabled){
			return;
		}
		if(sql.indexOf("t_logger_sql")>-1){
			return;//如果是本表操作不做记录
		}
		String currentSqlType = sql.substring(0, 6).toUpperCase();
		if(sqlType.indexOf(currentSqlType)<0){
			return;//如果不是预先设定的SQL语句类型则不做记录
		}
		LoggerSql loggerSql = new LoggerSql();
		loggerSql.setSqlType(currentSqlType);
		loggerSql.setSqlValue(sql);
		JdbcCache.putCache(loggerSql);
		if(LoggerCache.isFull()){
			ScheduledThreadUtils.start(jdbcThreadService);
		}
	}
	
	public String getSqlType() {
		return sqlType;
	}

	public void setSqlType(String sqlType) {
		JdbcLogger.sqlType = sqlType.toUpperCase();
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		JdbcLogger.enabled = enabled;
	}
	
	public void setJdbcThreadService(JdbcThreadService jdbcThreadService) {
		JdbcLogger.jdbcThreadService = jdbcThreadService;
	}
	
}
