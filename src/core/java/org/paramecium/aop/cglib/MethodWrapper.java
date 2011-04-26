package org.paramecium.aop.cglib;

import java.lang.reflect.Method;
import java.util.*;

@SuppressWarnings("unchecked")
public class MethodWrapper {
	private static final MethodWrapperKey KEY_FACTORY = (MethodWrapperKey) KeyFactory
			.create(MethodWrapperKey.class);

	public interface MethodWrapperKey {
		public Object newInstance(String name, String[] parameterTypes,
				String returnType);
	}

	private MethodWrapper() {
	}

	public static Object create(Method method) {
		return KEY_FACTORY.newInstance(method.getName(), ReflectUtils
				.getNames(method.getParameterTypes()), method.getReturnType()
				.getName());
	}

	public static Set createSet(Collection methods) {
		Set set = new HashSet();
		for (Iterator it = methods.iterator(); it.hasNext();) {
			set.add(create((Method) it.next()));
		}
		return set;
	}
}
