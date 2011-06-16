package org.paramecium.security;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
/**
 * 功能描述(Description):<br><b>
 * Session监听器
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-4-25下午07:55:02</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.paramecium.security.SessionListener.java</b>
 */
public class SessionListener implements HttpSessionListener{
	
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
	}

	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		SecurityThread.remove(httpSessionEvent.getSession().getId());
	}

}
