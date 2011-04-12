package org.cy.core.aop.cglib;

import org.cy.core.aop.asm.Type;

public interface Customizer {
    void customize(CodeEmitter e, Type type);
}
