package org.paramecium.log.system;

import java.util.Collection;

import org.paramecium.cache.Cache;
import org.paramecium.cache.CacheManager;
import org.paramecium.commons.DateUtils;
import org.paramecium.log.Log;
import org.paramecium.log.LogConfig;
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
public class JdbcCollector<STR extends Object> implements Collector<STR>{

	private final static Log logger$ = LoggerFactory.getLogger();
	public static String[] notLogTableNames;
	
	@SuppressWarnings("unchecked")
	private final static Cache<String,String> jdbcLogCache = CacheManager.getDefaultCache("CACHE_JDBC_LOG");

	public synchronized Collection<String> getAll() {
		if(!LogConfig.jdbcLogCollector){
			return null;
		}
		Collection<String> logs = jdbcLogCache.getKeys();
		jdbcLogCache.clear();
		logger$.debug("JDBC log cache is claer!");
		return logs;
	}

	public synchronized void put(STR log) {
		if(LogConfig.jdbcLogCollector&&log!=null){
			if(notLogTableNames!=null&&notLogTableNames.length>0){
				for(String table : notLogTableNames){
					if(log.toString().toLowerCase().indexOf(table)>0){
						return;
					}
				}
			}
			StringBuffer logger = new StringBuffer();
			logger.append(DateUtils.getCurrentDateTimeStr()).append("|");
			UserDetails<?> user = SecurityThread.getUserNotException();
			String username = "匿名用户";
			if(user!=null){
				username = user.getUsername();
			}
			logger.append(username).append("|");
			logger.append(log);
			if(CollectorFactory.logCollector!=null){
				CollectorFactory.logCollector.putWebLog(logger.toString());
			}else{
				jdbcLogCache.put(logger.toString(), null);
			}
			logger$.debug(logger.toString());
		}
	}

}
