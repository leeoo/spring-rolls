package org.paramecium.aop.cglib;

import java.lang.reflect.Method;

public interface InvocationHandler extends Callback {
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable;

}
