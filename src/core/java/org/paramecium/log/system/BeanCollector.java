package org.paramecium.log.system;

import java.util.Collection;

import org.paramecium.cache.Cache;
import org.paramecium.cache.CacheManager;
import org.paramecium.commons.DateUtils;
import org.paramecium.security.SecurityThread;
import org.paramecium.security.UserDetails;

/**
 * 功 能 描 述:<br>
 * Bean的调用过程日志
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-9-15上午11:02:26
 * <br>项 目 信 息:paramecium:org.paramecium.log.system.BeanCollector.java
 */
@SuppressWarnings("unchecked")
public class BeanCollector<STR extends Object> implements Collector<STR>{

	private static boolean enabled = false;
	
	private static Cache<String,String> beanLogCache = CacheManager.getDefaultCache("CACHE_BEAN_LOG");

	public synchronized Collection<String> getAll() {
		if(!enabled){
			beanLogCache.clear();
			return null;
		}
		Collection<String> logs = beanLogCache.getKeys();
		beanLogCache.clear();
		return logs;
	}

	public synchronized void put(STR log) {
		if(enabled){
			StringBuffer logger = new StringBuffer();
			logger.append(DateUtils.getCurrentDateTimeStr()).append("|");
			UserDetails user = SecurityThread.getUserNotException();
			String username = "匿名用户";
			if(user!=null){
				username = user.getUsername();
			}
			logger.append(username).append("|");
			logger.append(log);
			beanLogCache.put(logger.toString(), null);
		}
	}

	public synchronized void enabled(boolean ebd) {
		enabled = ebd;
	}

}
