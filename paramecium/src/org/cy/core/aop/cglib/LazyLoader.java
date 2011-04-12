package org.cy.core.aop.cglib;

public interface LazyLoader extends Callback {

	Object loadObject() throws Exception;

}
