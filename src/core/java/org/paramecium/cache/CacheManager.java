package org.paramecium.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * 功 能 描 述:<br>
 * 缓存管理器
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-7-1下午04:29:39
 * <br>项 目 信 息:paramecium:org.paramecium.cache.CacheManager.java
 */
public class CacheManager {
	
	private static Map<String,Cache<?,?>> map = new HashMap<String,Cache<?,?>>();
	
	/**
	 * 默认先进先出
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static synchronized Cache getDefaultCache(String name){
		return getDefaultCache(name, 500);
	}

	/**
	 * 默认先进先出
	 * @param name
	 * @param maxSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static synchronized Cache getDefaultCache(String name,int maxSize){
		if(map.get(name)==null){
			Cache<?,?> cache = new DefaultCache<Object, Object>(name, maxSize);
			map.put(name, cache);
		}
		return map.get(name);
	}

	/**
	 * 远程同步缓存,在分布式环境下使用
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static synchronized Cache getRemoteCache(String name){
		return getRemoteCache(name, 500);
	}
	
	/**
	 * 远程同步缓存,在分布式环境下使用
	 * @param name
	 * @param maxSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static synchronized Cache getRemoteCache(String name,int maxSize){
		if(map.get(name)==null){
			Cache<?,?> cache = new RemoteCache<Object, Object>(name, maxSize);
			map.put(name, cache);
		}
		return map.get(name);
	}
	
}
