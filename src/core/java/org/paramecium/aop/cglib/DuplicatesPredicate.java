package org.paramecium.aop.cglib;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unchecked")
public class DuplicatesPredicate implements Predicate {
	private Set unique = new HashSet();

	public boolean evaluate(Object arg) {
		return unique.add(MethodWrapper.create((Method) arg));
	}
}
