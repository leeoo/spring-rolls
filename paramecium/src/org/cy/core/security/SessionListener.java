package org.cy.core.security;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.cy.core.log.Log;
import org.cy.core.log.LoggerFactory;

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
