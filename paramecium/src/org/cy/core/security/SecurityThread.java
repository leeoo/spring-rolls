package org.cy.core.security;

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
	
	private final static ThreadLocal<String> transactionThreadLocal = new ThreadLocal<String>();
	
	
	public static void put(UserDetails details){
		transactionThreadLocal.set(details.getUsername());
		OnlineUserCache.login(details);
	}
	
	public static UserDetails get() throws UserKickException,UserDisabledException{
		String username = transactionThreadLocal.get();
		if(username!=null){
			UserDetails userDetails = OnlineUserCache.getOnlineUser(username);
			if(userDetails==null){//如果在线用户列表不存在该用户，而该用户线程仍有信息，被视为强制被踢出(一般管理员可用使用该权限)
				transactionThreadLocal.remove();
				throw new UserKickException("用户已经被强制踢出!");
			}else if(!userDetails.isEnable()){//如果账户是被冻结的，抛出异常
				transactionThreadLocal.remove();
				throw new UserDisabledException("用户已经被冻结!");
			}
			return userDetails;
		}
		return null;
	}
	
	public static void remove(){
		OnlineUserCache.logout(transactionThreadLocal.get());
		transactionThreadLocal.remove();
	}
	

	
}
