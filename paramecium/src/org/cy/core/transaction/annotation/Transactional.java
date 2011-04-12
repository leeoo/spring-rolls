package org.cy.core.transaction.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.sql.Connection;

/**
 * 功 能 描 述:<br>
 * 事务声明
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-12下午02:15:05
 * <br>项 目 信 息:paramecium:org.cy.core.transaction.annotation.Transactional.java
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface Transactional {
	
	boolean readOnly() default false;
	
	int transactionLevel() default Connection.TRANSACTION_READ_COMMITTED;
	
}
