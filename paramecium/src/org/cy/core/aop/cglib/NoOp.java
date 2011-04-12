package org.cy.core.aop.cglib;

public interface NoOp extends Callback {
	public static final NoOp INSTANCE = new NoOp() {
	};
}
