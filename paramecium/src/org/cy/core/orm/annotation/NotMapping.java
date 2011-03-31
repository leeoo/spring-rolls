package org.cy.core.orm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 功 能 描 述:<br>
 * 不映射声明
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-3-31下午04:56:14
 * <br>项 目 信 息:paramecium:org.cy.core.orm.annotation.NotMapping.java
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)    
@Target(ElementType.FIELD)
public @interface NotMapping {

}
