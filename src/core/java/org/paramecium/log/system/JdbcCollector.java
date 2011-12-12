package org.paramecium.log.system;

import org.paramecium.commons.DateUtils;
import org.paramecium.log.Log;
import org.paramecium.log.LogConfig;
import org.paramecium.log.LogInfoUtils;
import org.paramecium.log.LoggerFactory;
import org.paramecium.security.SecurityThread;
import org.paramecium.security.UserDetails;

/**
 * 功 能 描 述:<br>
 * Jdbc的SQL语句调用过程日志
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-9-15上午11:02:26
 * <br>项 目 信 息:paramecium:org.paramecium.log.system.JdbcCollector.java
 */
public class JdbcCollector implements Collector{

	private final static Log logger$ = LoggerFactory.getLogger();
	public static String[] notLogTableNames;
	
	public synchronized void put(String sql) {
		if(LogConfig.jdbcLogCollector&&sql!=null){
			if(notLogTableNames!=null&&notLogTableNames.length>0){
				for(String table : notLogTableNames){
					if(sql.toLowerCase().indexOf(table)>0){
						return;
					}
				}
			}
			if(CollectorFactory.logCollector!=null){
				StringBuffer logger = new StringBuffer();
				logger.append(DateUtils.getCurrentDateTimeStr()).append('|');
				UserDetails<?> user = SecurityThread.getUserNotException();
				String username = ANONYMOUS;
				if(user!=null){
					username = user.getUsername();
				}
				logger.append(username).append('|');
				logger.append(sql);
				CollectorFactory.logCollector.putJdbcLog(LogInfoUtils.cut(logger.toString()));
				logger$.debug(logger.toString());
			}
		}
	}

}
