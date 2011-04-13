package org.cy.core.mvc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
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
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Controller {
	
	/**
	 * 防止同名Service注入冲突,为注入到容器中的controller起一个唯一的名字，默认为controller实例化名称
	 * @return
	 */
	String uniqueName() default "";
	
	/**
	 * 是否采用单例模式生成实例,默认为即时创建
	 * @return
	 */
	boolean isSingle() default false;
	
	/**
	 * 进入该Controller的url地址
	 * @return
	 */
	String url();
	
}
