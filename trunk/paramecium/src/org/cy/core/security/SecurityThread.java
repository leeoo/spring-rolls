package org.cy.core.security;

import org.cy.core.log.Log;
import org.cy.core.log.LoggerFactory;
import org.cy.core.security.exception.SessionExpiredException;
import org.cy.core.security.exception.UserDisabledException;
import org.cy.core.security.exception.UserKickException;

/**
 * 功 能 描 述:<br>
 * 登录用户线程容器
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-22下午01:47:50
 * <br>项 目 信 息:paramecium:org.cy.core.security.SecurityThread.java
 */
public class SecurityThread {
	
	private final static Log logger = LoggerFactory.getLogger();
	public final static ThreadLocal<String> sessionThreadLocal = new ThreadLocal<String>();
	
	public static void put(UserDetails userDetails){
		if(sessionThreadLocal.get()==null){
			throw new SessionExpiredException("当前 Session已经过期!");
		}
		userDetails.setSessionId(sessionThreadLocal.get());
		if(SecurityConfig.sessionControl){//判断是否是同一session登录
			for(UserDetails onlineUser : OnlineUserCache.getAllOnlineUsers()){
				if(onlineUser.getUsername().equals(userDetails.getUsername())&&!onlineUser.getSessionId().equals(userDetails.getSessionId())){
					remove(onlineUser.getSessionId());
					break;
				}else if(onlineUser.getSessionId().equals(userDetails.getSessionId())&&!onlineUser.getUsername().equals(userDetails.getUsername())){
					remove(onlineUser.getSessionId());
					break;
				}
			}
		}
		OnlineUserCache.login(userDetails);
		logger.debug(userDetails.getUsername()+"登录成功!");
	}
	
	public static UserDetails get() {
		String sessionId = sessionThreadLocal.get();
		if(sessionId!=null){
			UserDetails userDetails = OnlineUserCache.getOnlineUser(sessionId);
			if(userDetails==null){//如果在线用户列表不存在该用户，而该用户线程仍有信息，被视为强制被踢出(一般管理员可用使用该权限)
				remove();
				throw new UserKickException("用户已经被强制退出!");
			}else if(!userDetails.isEnable()){//如果账户是被冻结的，抛出异常
				remove();
				throw new UserDisabledException("用户已经被冻结!");
			}
			return userDetails;
		}
		throw new SessionExpiredException("当前 Session已经过期!");
	}
	
	public static void remove(){
		if(sessionThreadLocal.get()!=null){
			remove(sessionThreadLocal.get());
		}
	}

	public static void remove(String sessionId){
		sessionThreadLocal.remove();
		UserDetails userDetails = OnlineUserCache.getOnlineUser(sessionId);
		if(userDetails==null){
			return;
		}
		OnlineUserCache.logout(sessionId);
		logger.debug(userDetails.getUsername()+"退出成功!");
	}
	

	
}
