package com.wisdom.core.logger;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.wisdom.core.logger.domain.AbstractMatch;
/**
 * 功 能 描 述:<br>
 * 地址匹配
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2010-11-15下午05:06:03
 * <br>项 目 信 息:wisdom.3.0:com.wisdom.core.logger.MatchCache.java
 */
public class MatchCache {
	
	public static ConcurrentMap<String,AbstractMatch> urlMatch = new ConcurrentHashMap<String, AbstractMatch>();
	
	public static void put(AbstractMatch match){
		urlMatch.put(match.getKeyword(), match);
	}
	
	public static AbstractMatch get(String keyword){
		return urlMatch.get(keyword);
	}
	
	public static void remove(String keyword){
		urlMatch.remove(keyword);
	}
	
	public static void removeAll(){
		urlMatch.clear();
	}
	
	public static Collection<AbstractMatch> getAll(){
		return urlMatch.values();
	}
	
}
