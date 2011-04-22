package org.cy.core.security;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener{
	
	private static int i = 1;
	
	public void sessionCreated(HttpSessionEvent arg0) {
		String str = ""+(i++);
		System.out.println("新的session创建:"+str);
		Security.put(str);
	}

	public void sessionDestroyed(HttpSessionEvent arg0) {
		String str = Security.get();
		System.out.println("旧的session销毁:"+str);
		Security.remove();
	}

}
