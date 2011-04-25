package org.cy.core.security;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener{
	
	
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		SecurityThread.sessionThreadLocal.set(httpSessionEvent.getSession().getId());
	}

	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		SecurityThread.remove();
	}

}
