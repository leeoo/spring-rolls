package org.cy.core.security;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 功 能 描 述:<br>
 * 在线用户缓存
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-22下午03:06:15
 * <br>项 目 信 息:paramecium:org.cy.core.security.OnlineUserCache.java
 */
public class OnlineUserCache {
	
	private final static ConcurrentMap<String, Boolean> onlineUsers = new ConcurrentHashMap<String, Boolean>();
	
	/**
	 * 用户登录
	 * @param username
	 */
	public static void login(String username){
		onlineUsers.put(username, true);
	}
	
	/**
	 * 用户退出
	 * @param username
	 */
	public static void logout(String username){
		onlineUsers.remove(username);
	}
	
	/**
	 * 获得所有在线用户列表
	 * @return
	 */
	public static Collection<String> getAllOnlineUsers(){
		return onlineUsers.keySet();
	}
	
	/**
	 * 用户是否在线
	 * @param username
	 * @return
	 */
	public static boolean isOnline(String username){
		return onlineUsers.get(username)!=null;
	}
	
}
