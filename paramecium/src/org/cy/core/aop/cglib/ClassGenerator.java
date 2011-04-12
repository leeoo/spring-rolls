package org.cy.core.aop.cglib;

import org.cy.core.aop.asm.ClassVisitor;

public interface ClassGenerator {
    void generateClass(ClassVisitor v) throws Exception;
}
