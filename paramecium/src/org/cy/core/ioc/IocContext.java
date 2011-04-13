package org.cy.core.ioc;

import java.util.concurrent.ConcurrentHashMap;

public class IocContext {
	
	public static ConcurrentHashMap<String, BaseClassInfo> classInfoContext = new ConcurrentHashMap<String, BaseClassInfo>();
	
	
}
