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
 * Bean的调用过程日志
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-9-15上午11:02:26
 * <br>项 目 信 息:paramecium:org.paramecium.log.system.BeanCollector.java
 */
public class BeanCollector<STR extends Object> implements Collector<STR>{
	
	private final static Log logger$ = LoggerFactory.getLogger();

	@SuppressWarnings("unchecked")
	private final static Cache<String,String> beanLogCache = CacheManager.getDefaultCache("CACHE_BEAN_LOG");

	public synchronized Collection<String> getAll() {
		if(!LogConfig.beanLogCollector){
			return null;
		}
		Collection<String> logs = beanLogCache.getKeys();
		beanLogCache.clear();
		logger$.debug("BEAN log cache is claer!");
		return logs;
	}

	public synchronized void put(STR log) {
		if(LogConfig.beanLogCollector){
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
				beanLogCache.put(logger.toString(), null);
			}
			logger$.debug(logger.toString());
		}
	}

}
