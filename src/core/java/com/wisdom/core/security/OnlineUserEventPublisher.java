package com.wisdom.core.security;

import javax.servlet.http.HttpSessionEvent;

import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.wisdom.core.security.domain.User;
import com.wisdom.core.security.resource.SecurityUtils;
/**
 * 功 能 描 述:<br>
 * 通过继承HttpSessionEventPublisher重写相关方法获取在线用户信息
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2010-11-1下午03:00:00
 * <br>项 目 信 息:wisdom.3.0:com.wisdom.core.security.OnlineUserEventPublisher.java
 */
public class OnlineUserEventPublisher extends HttpSessionEventPublisher{
	
	public void sessionCreated(HttpSessionEvent event) {
		User user=SecurityUtils.getCurrentUser();
		if(user!=null){
			OnlineUserCache.put(user);
		}
		super.sessionCreated(event);
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		User user=SecurityUtils.getCurrentUser();
		if(user!=null){
			OnlineUserCache.remove(user);
		}
		super.sessionDestroyed(event);
	}
	
}

