package org.paramecium.security;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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
	
	private final static ConcurrentMap<String, UserDetails> onlineUsers = new ConcurrentHashMap<String, UserDetails>();
	private final static ConcurrentMap<String, String> sessionIdIndex = new ConcurrentHashMap<String, String>();
	private final static Log logger = LoggerFactory.getLogger();
	
	/**
	 * 用户登录
	 * @param details
	 */
	public static void login(UserDetails details){
		onlineUsers.put(details.getSessionId(), details);
		sessionIdIndex.put(details.getUsername(), details.getSessionId());
		logger.debug(details.getUsername()+"登录成功!sessionId:"+details.getSessionId());
	}
	
	/**
	 * 用户退出
	 * @param username
	 */
	public static void logout(String sessionId){
		UserDetails userDetails = getOnlineUserBySessionId(sessionId);
		if(userDetails==null){
			logger.debug("sessionId:"+sessionId+"对应的用户之前被同账号挤掉，系统自动销毁Session！");
			onlineUsers.remove(sessionId);
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
	public static Collection<UserDetails> getAllOnlineUsers(){
		return onlineUsers.values();
	}
	
	/**
	 * 获得某用户信息
	 * @param username
	 * @return
	 */
	public static UserDetails getOnlineUserBySessionId(String sessionId){
		if(sessionId==null){
			logger.error("SessionID为空,不能正常获取用户信息!");
			return null;
		}
		return onlineUsers.get(sessionId);
	}
	
	/**
	 * 获得某用户信息
	 * @param username
	 * @return
	 */
	public static UserDetails getOnlineUserByUsername(String username){
		if(username==null){
			logger.error("username为空,不能正常获取用户信息!");
			return null;
		}
		String sessionId = sessionIdIndex.get(username);
		if(sessionId==null){
			return null;
		}
		return onlineUsers.get(sessionId);
	}
	
}
