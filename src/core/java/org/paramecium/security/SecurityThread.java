package org.paramecium.security;

import javax.servlet.http.HttpServletRequest;

import org.paramecium.cache.Cache;
import org.paramecium.cache.CacheManager;
import org.paramecium.security.exception.AnonymousException;
import org.paramecium.security.exception.IpAddressException;
import org.paramecium.security.exception.SessionExpiredException;
import org.paramecium.security.exception.UserDisabledException;
import org.paramecium.security.exception.UserKickException;
import org.paramecium.security.exception.UserKillException;

/**
 * 功 能 描 述:<br>
 * 登录用户线程容器
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-22下午01:47:50
 * <br>项 目 信 息:paramecium:org.paramecium.security.SecurityThread.java
 */
@SuppressWarnings("unchecked")
public class SecurityThread {
	
	private final static ThreadLocal<String> sessionThreadLocal = new ThreadLocal<String>();//session本地线程
	private final static ThreadLocal<Security> securityThreadLocal = new ThreadLocal<Security>();//安全限制级别本地线程
	private final static Cache<String,Boolean> kickUserCache = (Cache<String, Boolean>) CacheManager.getCacheByType("KICK_USER", 20);//判断重复登录用缓存
	private final static Cache<String,Boolean> killUserCache = (Cache<String, Boolean>) CacheManager.getCacheByType("KILL_USER", 20);//判断被强制踢出缓存
	
	public static void putKillUserCache(String sessionId){
		killUserCache.put(sessionId,true);
	}

	/**
	 * 功能描述(Description):<br><b>
	 * 安全隐患
	 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
	 * <br>建立日期(Create Date): <b>2011-9-15下午09:51:10</b>
	 * <br>项目名称(Project Name): <b>paramecium</b>
	 * <br>包及类名(Package Class): <b>org.paramecium.security.SecurityThread.java</b>
	 */
	public static enum Security{
		Null,
		SessionExpiredException,
		UserKickException,
		UserDisabledException,
		AnonymousException,
		AuthorizationException,
		IpAddressException,
		UserKillException
	}
	
	/**
	 * 放入安全隐患类型
	 * @param security
	 */
	public static void putSecurity(Security security){
		securityThreadLocal.set(security);
	}

	/**
	 * 获取安全隐患类型
	 * @return
	 */
	public static Security getSecurity(){
		return securityThreadLocal.get();
	}
	
	/**
	 * 开启本次线程
	 * @param request
	 */
	public static void startThread(HttpServletRequest request){
		if(request.getSession(false)!=null){
			sessionThreadLocal.set(request.getSession(false).getId());
			securityThreadLocal.set(Security.Null);
			return;
		}
		putSecurity(SecurityThread.Security.SessionExpiredException);
		throw new SessionExpiredException("当前 Session已经过期!");
	}
	
	/**
	 * 结束本次线程
	 */
	public static void endThread(){
		sessionThreadLocal.remove();
		securityThreadLocal.remove();
	}
	
	/**
	 * 将登录正确的用户放入系统安全验证
	 * @param userDetails
	 * @param request
	 */
	public static void put(UserDetails<?> userDetails,HttpServletRequest request){
		if(userDetails==null){
			putSecurity(SecurityThread.Security.AnonymousException);
			throw new AnonymousException("用户信息为空,登录失败!");
		}
		if(request.getSession(false)!=null){
			userDetails.setSessionId(request.getSession(false).getId());
			userDetails.setAddress(request.getRemoteAddr());
			if(!IpAddressVoter.voteIPV4(userDetails.getAddress())){
				putSecurity(SecurityThread.Security.IpAddressException);
				throw new IpAddressException("登录用户"+userDetails.getUsername()+",登录IP为"+userDetails.getAddress()+"没有被授权IP!");
			}
			put(userDetails);
			return;
		}
		request.getSession(true);//获得新session，一般来说到不了这里就已经创建新的session（startThread方法）
		putSecurity(SecurityThread.Security.SessionExpiredException);
		throw new SessionExpiredException("当前 Session已经过期!");
	}
	
	/**
	 * 将登录正确的用户放入系统安全验证
	 * @param userDetails
	 */
	private static void put(UserDetails<?> userDetails){
		if(SecurityConfig.sessionControl){//判断是否是同一session登录
			UserDetails<?> onlineUser = OnlineUserCache.getOnlineUserByUsername(userDetails.getUsername());
			if(onlineUser!=null){//用户信息相同,Session不同,说明用户出现重复登录或盗用,前者的在线用户信息删除
				OnlineUserCache.logout(onlineUser.getSessionId());
				kickUserCache.put(onlineUser.getSessionId(), true);//先记录下该用户被踢掉的状态
			}else{//Session相同,用户信息不同，说明用户用相同浏览器重新登录,Session保存，之前在线用户信息删除
				onlineUser = OnlineUserCache.getOnlineUserBySessionId(userDetails.getSessionId());
				if(onlineUser!=null){
					OnlineUserCache.logout(onlineUser.getSessionId());
				}
			}
		}
		OnlineUserCache.login(userDetails);
	}
	
	/**
	 * 获得当前登录用户信息
	 * @return
	 */
	public static UserDetails<?> get() {
		String sessionId = sessionThreadLocal.get();
		if(sessionId!=null){
			UserDetails<?> userDetails = OnlineUserCache.getOnlineUserBySessionId(sessionId);
			if(userDetails==null){//如果在线用户列表不存在该用户，而该用户线程仍有信息，被视为强制被踢出(一般管理员可用使用该权限)
				if(kickUserCache.get(sessionId)!=null){
					putSecurity(SecurityThread.Security.UserKickException);
					kickUserCache.remove(sessionId);
					throw new UserKickException("该账号重复登录被踢掉!");
				}else if(killUserCache.get(sessionId)!=null){
					putSecurity(SecurityThread.Security.UserKillException);
					killUserCache.remove(sessionId);
					throw new UserKillException("该账号被管理员强制退出!");
				}else{
					putSecurity(SecurityThread.Security.AnonymousException);
					throw new AnonymousException("匿名用户没有登录!");
				}
			}else if(!userDetails.isEnable()){//如果账户是被冻结的，抛出异常
				putSecurity(SecurityThread.Security.UserDisabledException);
				throw new UserDisabledException("用户已经被冻结!");
			}
			return userDetails;
		}
		putSecurity(SecurityThread.Security.SessionExpiredException);
		throw new SessionExpiredException("当前 Session已经过期!");
	}
	
	/**
	 * 安全的获得当前用户，不会产生异常.
	 * @return
	 */
	public static UserDetails<?> getUserNotException(){
		String sessionId = sessionThreadLocal.get();
		if(sessionId!=null){
			UserDetails<?> userDetails = OnlineUserCache.getOnlineUserBySessionId(sessionId);
			if(userDetails==null){
				return null;
			}
			return userDetails;
		}
		return null;
		
	}
	

	/**
	 * 移除用户登录信息
	 * @param sessionId
	 */
	public static void remove(String sessionId){
		OnlineUserCache.logout(sessionId);
		kickUserCache.remove(sessionId);
		killUserCache.remove(sessionId);
	}

}
