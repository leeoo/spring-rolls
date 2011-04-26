package org.paramecium.aop.cglib;

import org.paramecium.aop.asm.Label;

public interface ObjectSwitchCallback {
	void processCase(Object key, Label end) throws Exception;

	void processDefault() throws Exception;
}
