package com.wisdom.core.logger;

import java.util.Collection;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * <b>业务说明</b>:
 * 日志缓存<br>
 * <b>作  者</b>: Cao.Yang<br>
 * <b>建立日期</b>: 2009-12-3<br>
 * <b>建立时间</b>: 下午12:33:23<br>
 * <b>项目名称</b>: spring<br>
 * <b>包及类名</b>: com.wisdom.core.logger.LoggerCache.java<br>
 */
public class LoggerCache {
	private final static String SECURITY_CACHE_NAME="logger_cache";
	private static Cache cache=CacheManager.getInstance().getCache(SECURITY_CACHE_NAME);
	public static String currentLoggerLever="!!";
	
	/**
	 * 设置缓存级别
	 * @param levers
	 */
	public static void setLoggerLever(String[] levers){
		if(levers==null||levers.length==0){
			return;
		}
		currentLoggerLever="";
		for(String lever:levers){
			currentLoggerLever=currentLoggerLever.concat(lever);
		}
		if(currentLoggerLever.indexOf("!!")!=-1){
			currentLoggerLever="!!";
			return;
		}else if(currentLoggerLever.indexOf("**")!=-1){
			currentLoggerLever="**";
			return;
		}
	}
	
	/**
	 * 将日志放入缓存
	 * @param logger
	 */
	public synchronized static void putCache(String logger){
		Element element=new Element(logger, null);
		cache.put(element);
	}
	
	/**
	 * 从缓存中移除日志
	 * @param logger
	 */
	public synchronized static void removeCache(String logger){
		cache.remove(logger);
	}

	/**
	 * 是否已经饱和
	 * @return
	 */
	public synchronized static boolean isFull(){
		if(cache.getKeys().size()>LoggerInterceptor.maxCacheValue){
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
	public synchronized static Collection<String> getAllCache(){
		return cache.getKeys();
	}
	
}
