package org.paramecium.aop.cglib;

public interface LazyLoader extends Callback {

	Object loadObject() throws Exception;

}
