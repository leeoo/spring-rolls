package org.cy.core.security;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.cy.core.log.Log;
import org.cy.core.log.LoggerFactory;
/**
 * 功能描述(Description):<br><b>
 * Session监听器
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-4-25下午07:55:02</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.cy.core.security.SessionListener.java</b>
 */
public class SessionListener implements HttpSessionListener{
	
	private final static Log logger = LoggerFactory.getLogger();
	
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		logger.debug("创建一个新的SessionID:"+httpSessionEvent.getSession().getId());
		SecurityThread.sessionThreadLocal.set(httpSessionEvent.getSession().getId());
	}

	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		logger.debug("销毁当前的SessionID:"+httpSessionEvent.getSession().getId());
		SecurityThread.remove(httpSessionEvent.getSession().getId());
	}

}
