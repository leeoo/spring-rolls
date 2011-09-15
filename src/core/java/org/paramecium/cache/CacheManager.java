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
	
	@SuppressWarnings("unchecked")
	public static synchronized Cache getDefaultCache(String name){
		return getDefaultCache(name, 500);
	}

	@SuppressWarnings("unchecked")
	public static synchronized Cache getDefaultCache(String name,int maxSize){
		if(map.get(name)==null){
			Cache<?,?> cache = new DefaultCache<Object, Object>(name, maxSize);
			map.put(name, cache);
		}
		return map.get(name);
	}
	
}
