package org.paramecium.aop.cglib;

public interface Dispatcher extends Callback {
    Object loadObject() throws Exception;
}
