package org.paramecium.ioc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 功 能 描 述:<br>
 * 做UI显示标签
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-22下午04:56:22
 * <br>项 目 信 息:paramecium:org.paramecium.ioc.annotation.ShowLabel.java
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.TYPE,ElementType.METHOD})
public @interface ShowLabel {
	
	/**
	 * 显示此值
	 * @return
	 */
	String value();
	
}
