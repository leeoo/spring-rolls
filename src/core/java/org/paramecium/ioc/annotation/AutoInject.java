package org.paramecium.ioc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 功 能 描 述:<br>
 * 需要自动注入声明
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-13下午05:49:41
 * <br>项 目 信 息:paramecium:org.paramecium.ioc.annotation.AutoInject.java
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AutoInject {
	
	/**
	 * 防止同名Service注入冲突,为注入到容器中的service起一个唯一的名字，默认为service实例化名称
	 * @return
	 */
	String value() default "";
	
}
