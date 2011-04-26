package org.paramecium.transaction.annotation;

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
 * <br>项 目 信 息:paramecium:org.paramecium.transaction.annotation.Transactional.java
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface Transactional {
	
	/**
	 * 只读设置，默认为可写
	 * @return
	 */
	boolean readOnly() default false;
	
	/**
	 * 设置书事务级别,如果数据库为Mysql并开启BinLog，需要把默认值改为TRANSACTION_REPEATABLE_READ
	 * Connection.TRANSACTION_READ_COMMITTED:默认,可以防止脏读<br>
	 * Connection.TRANSACTION_READ_UNCOMMITTED:只保证不会读到非法数据，有可能出现脏读取、重复读取、虚读<br>
	 * Connection.TRANSACTION_REPEATABLE_READ:可以防止脏读和不可重复读取<br>
	 * Connection.TRANSACTION_READ_SERIALIZABLE防止出现脏读取、重复读取、虚读。<br>
	 * Connection.TRANSACTION_NONE<br>
	 * @return
	 */
	int transactionLevel() default Connection.TRANSACTION_READ_COMMITTED;
	
}
