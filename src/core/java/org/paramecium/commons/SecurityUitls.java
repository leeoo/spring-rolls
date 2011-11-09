package org.paramecium.commons;

import javax.servlet.http.HttpServletRequest;

import org.paramecium.security.OnlineUserCache;
import org.paramecium.security.SecurityThread;
import org.paramecium.security.UserDetails;

/**
 * 功能描述(Description):<br><b>
 * 安全工具
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-10-17下午07:26:59</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.paramecium.commons.SecurityUitls.java</b>
 */
public abstract class SecurityUitls {
	
	/**
	 * 获得当前登录用户
	 * @return
	 */
	public static UserDetails<?> getLoginUser(){
		return SecurityThread.getUserNotException();
	}
	
	/**
	 * 根据session获得用户
	 * @return
	 */
	public static UserDetails<?> getLoginUser(String sessionId){
		return OnlineUserCache.getOnlineUserBySessionId(sessionId);
	}
	
	/**
	 * 用户登录
	 * @param userDetails
	 * @param request
	 */
	public static void login(UserDetails<?> userDetails,HttpServletRequest request){
		SecurityThread.put(userDetails, request);
	}
	
}
