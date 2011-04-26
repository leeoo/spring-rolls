package org.paramecium.aop.cglib;

public interface GeneratorStrategy {
	
    byte[] generate(ClassGenerator cg) throws Exception;

    boolean equals(Object o);
    
}
