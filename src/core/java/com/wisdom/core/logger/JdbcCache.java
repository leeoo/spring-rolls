package com.wisdom.core.logger;

import java.util.Collection;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import com.wisdom.core.logger.domain.LoggerSql;
/**
 * 功 能 描 述:<br>
 * 用户存放jdbc产生的SQL语句
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2010-11-17上午10:22:24
 * <br>项 目 信 息:wisdom.3.0:com.wisdom.core.logger.JdbcCache.java
 */
public class JdbcCache {
	
	private final static String SECURITY_CACHE_NAME="jdbc_cache";
	private static Cache cache=CacheManager.getInstance().getCache(SECURITY_CACHE_NAME);
	public static int maxCacheValue=150;
	
	/**
	 * 将日志放入缓存
	 * @param logger
	 */
	public synchronized static void putCache(LoggerSql logger){
		Element element=new Element(logger,null);
		cache.put(element);
	}
	
	/**
	 * 从缓存中移除日志
	 * @param logger
	 */
	public synchronized static void removeCache(LoggerSql logger){
		cache.remove(logger);
	}

	/**
	 * 是否已经饱和
	 * @return
	 */
	public synchronized static boolean isFull(){
		if(cache.getKeys().size()>maxCacheValue){
			return true;
		}
		return false;
	}

	/**
	 * 移除所有日志缓存
	 */
	public synchronized static void removeAllCache(){
		cache.removeAll();
		cache.clearStatistics();
		cache.flush();
	}  

	/**
	 * 在缓存中获得所有日志信息
	 * @return 日志信息
	 */
	@SuppressWarnings("unchecked")
	public synchronized static Collection<LoggerSql> getAllCache(){
		return cache.getKeys();
	}
}
