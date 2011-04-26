package org.paramecium.aop.cglib;

import org.paramecium.aop.asm.ClassWriter;

public class DefaultGeneratorStrategy implements GeneratorStrategy {
    public static final DefaultGeneratorStrategy INSTANCE = new DefaultGeneratorStrategy();
    
    public byte[] generate(ClassGenerator cg) throws Exception {
        ClassWriter cw = getClassWriter();
        transform(cg).generateClass(cw);
        return transform(cw.toByteArray());
    }

    protected ClassWriter getClassWriter() throws Exception {
      return new DebuggingClassWriter(ClassWriter.COMPUTE_MAXS);
    }

    protected byte[] transform(byte[] b) throws Exception {
        return b;
    }

    protected ClassGenerator transform(ClassGenerator cg) throws Exception {
        return cg;
    }
}
