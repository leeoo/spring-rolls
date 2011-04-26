package org.paramecium.aop.cglib;

import org.paramecium.aop.asm.ClassVisitor;

public interface ClassGenerator {
    void generateClass(ClassVisitor v) throws Exception;
}
