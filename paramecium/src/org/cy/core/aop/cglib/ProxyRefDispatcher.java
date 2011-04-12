package org.cy.core.aop.cglib;

public interface ProxyRefDispatcher extends Callback {
	Object loadObject(Object proxy) throws Exception;
}
