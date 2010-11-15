package com.wisdom.core.security;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;

import com.wisdom.core.security.domain.User;
/**
 * 功 能 描 述:<br>
 * 在线用户缓存
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2010-11-1下午02:56:41
 * <br>项 目 信 息:wisdom.3.0:com.wisdom.core.security.OnlineUserCache.java
 */
public class OnlineUserCache {
	
	private final static String SECURITY_CACHE_NAME="online_user_cache";
	private static Cache cache=CacheManager.getInstance().getCache(SECURITY_CACHE_NAME);
	private static Logger logger = LoggerFactory.getLogger(OnlineUserCache.class);
	
	public static void put(User user){
		if(user!=null&&user.getUsername()!=null){
			Element element=new Element(user.getUsername(), user);
			cache.put(element);
			logger.info("{}已经登录!",user.getUsername());
		}
	}

	public static User get(String username){
		Element element = null;
		try {
			element = cache.get(username);
		} catch (CacheException cacheException) {
			throw new DataRetrievalFailureException("UserCache failure: " + cacheException.getMessage(), cacheException);
		}
		if (element == null) {
			return null;
		} else {
			return (User) element.getValue();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static Collection<User> getOnlineUsers(){
		Collection<String> usernames;
		Collection<User> userlist = new ArrayList<User>();
		try {
			usernames = cache.getKeys();
		} catch (IllegalStateException e) {
			throw new IllegalStateException(e.getMessage(), e);
		} catch (CacheException e) {
			throw new UnsupportedOperationException(e.getMessage(), e);
		}
		for (String username:usernames) {
			User user = get(username);
			userlist.add(user);
		}
		return userlist;
	}
	
	public static void remove(String username){
		if(username!=null){
			cache.remove(username);
			logger.info("{}退出登录!",username);
		}
	}

}
