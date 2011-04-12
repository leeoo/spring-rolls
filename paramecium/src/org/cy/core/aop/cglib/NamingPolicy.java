package org.cy.core.aop.cglib;

public interface NamingPolicy {
	String getClassName(String prefix, String source, Object key,
			Predicate names);

	boolean equals(Object o);
}
