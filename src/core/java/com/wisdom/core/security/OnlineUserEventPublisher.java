package com.wisdom.core.security;

import javax.servlet.http.HttpSessionEvent;

import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.wisdom.core.security.domain.User;
import com.wisdom.core.security.resource.SecurityUtils;
import com.wisdom.core.security.service.UserDetailServiceImpl;
/**
 * 功 能 描 述:<br>
 * 通过继承HttpSessionEventPublisher重写相关方法获取在线用户信息
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2010-11-1下午03:00:00
 * <br>项 目 信 息:wisdom.3.0:com.wisdom.core.security.OnlineUserEventPublisher.java
 */
public class OnlineUserEventPublisher extends HttpSessionEventPublisher{
	public final static String LOGIN_USERNAME = "LOGIN_USERNAME";
	
	/**
	 * 此处只有选择rememberMe才会获得用户登录信息。
	 * 因为登录成功后，security会将session销毁，并重新创建session，用户线程断开无法获取。
	 * 目前用户登录UserDetailServiceImpl采用返回userDetail方式，所以支持并发session，但无法使用rememberMe，所以可以忽略此处。
	 * 担如果返回userDetail子类User详细信息时，并发无效，rememberMe则可用。
	 * @see UserDetailServiceImpl
	 */
	public void sessionCreated(HttpSessionEvent event) {
		User user=SecurityUtils.getCurrentUser();
		if(user!=null){
			OnlineUserCache.put(user);
			event.getSession().setAttribute(LOGIN_USERNAME,user.getUsername());
		}
		super.sessionCreated(event);
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		User user=SecurityUtils.getCurrentUser();
		if(user!=null){
			OnlineUserCache.remove(user.getUsername());
		}else{
			String username = (String)event.getSession().getAttribute(LOGIN_USERNAME);
			if(username!=null){
				OnlineUserCache.remove(username);
			}
		}
		super.sessionDestroyed(event);
	}
	
}

