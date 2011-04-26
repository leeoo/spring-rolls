package org.paramecium.aop.cglib;

import org.paramecium.aop.asm.Label;

public interface ProcessSwitchCallback {
	void processCase(int key, Label end) throws Exception;

	void processDefault() throws Exception;
}
