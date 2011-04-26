package org.paramecium.aop.cglib;

public interface MethodInterceptor extends Callback {
	public Object intercept(Object obj, java.lang.reflect.Method method,
			Object[] args, MethodProxy proxy) throws Throwable;

}
