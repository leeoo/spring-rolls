package org.paramecium.security;

import java.rmi.RemoteException;
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
public class OnlineUserCache {
	
	private final static Cache onlineUsers = CacheManager.getCacheByType("ONLINE_USERS",1000);
	private final static Cache sessionIdIndex = CacheManager.getCacheByType("SESSION_ID", 1000);
	private final static Log logger = LoggerFactory.getLogger();
	
	/**
	 * 踢出所有用户
	 */
	public static void killAll(){
		for(Object sessionId : onlineUsers.getKeys()){
			kill((String)sessionId);
		}
		try {
			onlineUsers.clear();
		} catch (RemoteException e) {
			logger.error(e);
		}
		try {
			sessionIdIndex.clear();
		} catch (RemoteException e) {
			logger.error(e);
		}
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
		try {
			onlineUsers.put(details.getSessionId(), details);
		} catch (RemoteException e) {
			logger.error(e);
		}
		try {
			sessionIdIndex.put(details.getUsername(), details.getSessionId());
		} catch (RemoteException e) {
			logger.error(e);
		}
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
		try {
			sessionIdIndex.remove(userDetails.getUsername());
		} catch (RemoteException e) {
			logger.error(e);
		}
		try {
			onlineUsers.remove(sessionId);
		} catch (RemoteException e) {
			logger.error(e);
		}
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
		String sessionId = (String) sessionIdIndex.get(username);
		if(sessionId==null){
			return null;
		}
		return (UserDetails<?>) onlineUsers.get(sessionId);
	}
	
}
