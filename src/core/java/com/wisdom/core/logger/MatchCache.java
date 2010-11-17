package com.wisdom.core.logger;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.dao.DataRetrievalFailureException;

import com.wisdom.core.logger.domain.AbstractMatch;
/**
 * 功 能 描 述:<br>
 * 地址匹配
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2010-11-15下午05:06:03
 * <br>项 目 信 息:wisdom.3.0:com.wisdom.core.logger.MatchCache.java
 */
public class MatchCache {
	
	private final static String SECURITY_CACHE_NAME = "match_cache";
	private static Cache cache = CacheManager.getInstance().getCache(SECURITY_CACHE_NAME);
	
	public static void put(AbstractMatch match){
		Element element=new Element(match.getKeyword(),match);
		cache.put(element);
	}
	
	public static AbstractMatch get(String keyword){
		Element element = null;
		try {
			element = cache.get(keyword);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("MatchCache failure: " + cacheException.getMessage(), cacheException);
		}
		if (element == null) {
			return null;
		} else {
			return (AbstractMatch) element.getValue();
		}
	}
	
	public static void remove(String keyword){
		cache.remove(keyword);
	}
	
	public static void removeAll(){
		cache.removeAll();
		cache.clearStatistics();
		cache.flush();
	}
	
	@SuppressWarnings("unchecked")
	public static Collection<AbstractMatch> getAll(){
		Collection<String> keywords;
		Collection<AbstractMatch> matchs = new ArrayList<AbstractMatch>();
		try {
			keywords = cache.getKeys();
		} catch (IllegalStateException e) {
			throw new IllegalStateException(e.getMessage(), e);
		} catch (CacheException e) {
			throw new UnsupportedOperationException(e.getMessage(), e);
		}
		for (String keyword:keywords) {
			AbstractMatch match = get(keyword);
			matchs.add(match);
		}
		return matchs;
	}
	
}
