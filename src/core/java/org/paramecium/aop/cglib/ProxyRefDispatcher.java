package org.paramecium.aop.cglib;

public interface ProxyRefDispatcher extends Callback {
	Object loadObject(Object proxy) throws Exception;
}
