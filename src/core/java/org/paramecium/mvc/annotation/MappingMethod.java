package org.paramecium.mvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 功 能 描 述:<br>
 * 映射到具体方法
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-18下午04:40:12
 * <br>项 目 信 息:paramecium:org.paramecium.mvc.annotation.MappingMethod.java
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MappingMethod {
	
	String value() default "";
	
}
