package org.cy.core.mvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 功 能 描 述:<br>
 * 声明MVC控制器
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-13下午05:59:12
 * <br>项 目 信 息:paramecium:org.cy.core.mvc.annotation.Controller.java
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Controller {
	
	/**
	 * 进入该Controller的url命名空间
	 * @return
	 */
	String namespace();
	
	/**
	 * 是否是单例模式
	 * @return
	 */
	boolean singleton() default true;
	
}
