package org.cy.core.aop.cglib;

import org.cy.core.aop.asm.Label;

public interface ProcessSwitchCallback {
	void processCase(int key, Label end) throws Exception;

	void processDefault() throws Exception;
}
