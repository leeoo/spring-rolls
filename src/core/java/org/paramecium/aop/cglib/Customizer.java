package org.paramecium.aop.cglib;

import org.paramecium.aop.asm.Type;

public interface Customizer {
    void customize(CodeEmitter e, Type type);
}
