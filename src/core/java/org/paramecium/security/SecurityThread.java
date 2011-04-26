package org.paramecium.security;

import javax.servlet.http.HttpSession;

import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;
import org.paramecium.security.exception.SessionExpiredException;
import org.paramecium.security.exception.UserDisabledException;
import org.paramecium.security.exception.UserKickException;

/**
 * 功 能 描 述:<br>
 * 登录用户线程容器
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-22下午01:47:50
 * <br>项 目 信 息:paramecium:org.paramecium.security.SecurityThread.java
 */
public class SecurityThread {
	
	private final static Log logger = LoggerFactory.getLogger();
	public final static ThreadLocal<String> sessionThreadLocal = new ThreadLocal<String>();
	
	/**
	 * 将登录正确的用户放入系统安全验证
	 * @param userDetails
	 * @param session
	 */
	public static void put(UserDetails userDetails,HttpSession session){
		initSessionId(session);
		put(userDetails);
	}
	
	/**
	 * 将登录正确的用户放入系统安全验证
	 * @param userDetails
	 */
	public static void put(UserDetails userDetails){
		userDetails.setSessionId(sessionThreadLocal.get());
		if(SecurityConfig.sessionControl){//判断是否是同一session登录
			for(UserDetails onlineUser : OnlineUserCache.getAllOnlineUsers()){
				if(onlineUser.getUsername().equals(userDetails.getUsername())&&!onlineUser.getSessionId().equals(userDetails.getSessionId())){//用户信息相同,Session不同,说明用户出现重复登录或盗用,前者的在线用户信息删除
					OnlineUserCache.logout(onlineUser.getSessionId());
					break;
				}else if(onlineUser.getSessionId().equals(userDetails.getSessionId())&&!onlineUser.getUsername().equals(userDetails.getUsername())){//Session相同,用户信息不同，说明用户用相同浏览器重新登录,Session保存，之前在线用户信息删除
					OnlineUserCache.logout(onlineUser.getSessionId());
					break;
				}
			}
		}
		OnlineUserCache.login(userDetails);
		logger.debug(userDetails.getUsername()+"登录成功!");
	}
	
	/**
	 * 获得当前登录用户信息
	 * @return
	 */
	public static UserDetails get() {
		String sessionId = sessionThreadLocal.get();
		if(sessionId!=null){
			UserDetails userDetails = OnlineUserCache.getOnlineUser(sessionId);
			if(userDetails==null){//如果在线用户列表不存在该用户，而该用户线程仍有信息，被视为强制被踢出(一般管理员可用使用该权限)
				throw new UserKickException("用户已经被强制退出!");
			}else if(!userDetails.isEnable()){//如果账户是被冻结的，抛出异常
				throw new UserDisabledException("用户已经被冻结!");
			}
			return userDetails;
		}
		throw new SessionExpiredException("当前 Session已经过期!");
	}
	
	/**
	 * 移除用户登录信息
	 */
	public static void remove(){
		if(sessionThreadLocal.get()!=null){
			remove(sessionThreadLocal.get());
		}
	}

	/**
	 * 移除用户登录信息
	 * @param sessionId
	 */
	public static void remove(String sessionId){
		sessionThreadLocal.remove();
		UserDetails userDetails = OnlineUserCache.getOnlineUser(sessionId);
		if(userDetails==null){
			return;
		}
		OnlineUserCache.logout(sessionId);
		logger.debug(userDetails.getUsername()+"退出成功!");
	}

	/**
	 * 移除用户登录信息
	 * @param session
	 */
	public static void remove(HttpSession session){
		sessionThreadLocal.remove();
		UserDetails userDetails = OnlineUserCache.getOnlineUser(session.getId());
		if(userDetails==null){
			return;
		}
		OnlineUserCache.logout(session.getId());
		session.invalidate();
		logger.debug(userDetails.getUsername()+"退出成功!");
	}
	
	/**
	 * 初始化sessionID
	 * @param session
	 */
	private static void initSessionId(HttpSession session){
		if(sessionThreadLocal.get()==null){
			session.invalidate();
		}
		return;
	}
	
}
