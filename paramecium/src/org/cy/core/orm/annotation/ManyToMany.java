package org.cy.core.orm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 功 能 描 述:<br>
 * 多对多声明，需要建立中间关联表，性能较差，不推荐大量使用
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-3-31下午04:54:21
 * <br>项 目 信 息:paramecium:org.cy.core.orm.annotation.ManyToMany.java
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)    
@Target(ElementType.FIELD)
public @interface ManyToMany {

}
