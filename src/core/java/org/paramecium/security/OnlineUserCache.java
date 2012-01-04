package org.paramecium.security;

import java.util.Collection;

import org.paramecium.cache.Cache;
import org.paramecium.cache.CacheManager;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;

/**
 * 功 能 描 述:<br>
 * 在线用户缓存
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-22下午03:06:15
 * <br>项 目 信 息:paramecium:org.paramecium.security.OnlineUserCache.java
 */
@SuppressWarnings("unchecked")
public class OnlineUserCache {
	
	private final static Cache<String,UserDetails<?>> onlineUsers = (Cache<String, UserDetails<?>>) CacheManager.getCacheByType("ONLINE_USERS",1000);
	private final static Cache<String,String> sessionIdIndex = (Cache<String, String>) CacheManager.getCacheByType("SESSION_ID", 1000);
	private final static Log logger = LoggerFactory.getLogger();
	
	/**
	 * 踢出所有用户
	 */
	public static void killAll(){
		for(String sessionId : onlineUsers.getKeys()){
			kill((String)sessionId);
		}
		onlineUsers.clear();
		sessionIdIndex.clear();
		logger.debug("系统已将所有用户强制退出登录！");
	}
	
	/**
	 * 踢出用户
	 * @param username
	 */
	public static void kill(String sessionId){
		SecurityThread.putKillUserCache(sessionId);
		logout(sessionId);
	}
	
	/**
	 * 用户登录
	 * @param details
	 */
	public static void login(UserDetails<?> details){
		onlineUsers.put(details.getSessionId(), details);
		sessionIdIndex.put(details.getUsername(), details.getSessionId());
		logger.debug(details.getUsername()+"登录成功!sessionId:"+details.getSessionId());
	}
	
	/**
	 * 用户退出
	 * @param username
	 */
	public static void logout(String sessionId){
		UserDetails<?> userDetails = getOnlineUserBySessionId(sessionId);
		if(userDetails==null){
			logger.debug("sessionId:"+sessionId+"对应的用户之前被同账号挤掉或未登录过，系统自动销毁Session！");
			return;
		}
		sessionIdIndex.remove(userDetails.getUsername());
		onlineUsers.remove(sessionId);
		logger.debug(userDetails.getUsername()+"退出成功!sessionId:"+sessionId);
	}
	
	/**
	 * 获得所有在线用户列表
	 * @return
	 */
	public static Collection<?> getAllOnlineUsers(){
		return onlineUsers.getValues();
	}
	
	/**
	 * 获得某用户信息
	 * @param username
	 * @return
	 */
	public static UserDetails<?> getOnlineUserBySessionId(String sessionId){
		if(sessionId==null){
			logger.error("SessionID为空,不能正常获取用户信息!");
			return null;
		}
		return (UserDetails<?>)onlineUsers.get(sessionId);
	}
	
	/**
	 * 获得某用户信息
	 * @param username
	 * @return
	 */
	public static UserDetails<?> getOnlineUserByUsername(String username){
		if(username==null){
			logger.error("username为空,不能正常获取用户信息!");
			return null;
		}
		String sessionId = sessionIdIndex.get(username);
		if(sessionId==null){
			return null;
		}
		return (UserDetails<?>) onlineUsers.get(sessionId);
	}
	
}
