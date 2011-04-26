package org.paramecium.aop.cglib;

import java.lang.reflect.Method;

public interface CallbackFilter {
	int accept(Method method);

	boolean equals(Object o);
}
