package org.cy.core.aop.cglib;

import org.cy.core.aop.asm.Label;

public interface ObjectSwitchCallback {
	void processCase(Object key, Label end) throws Exception;

	void processDefault() throws Exception;
}
