package org.cy.core.orm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 功 能 描 述:<br>
 * 主键声明
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-3-31下午04:59:36
 * <br>项 目 信 息:paramecium:org.cy.core.orm.annotation.PrimaryKey.java
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)    
@Target(ElementType.FIELD)
public @interface PrimaryKey {
	
	public enum AUTO_GENERATE_MODE{
		/**
		 * 数据库级别自增
		 */
		NATIVE,
		/**
		 * 数据库级别序列自增
		 */
		NATIVE_SEQUENCE,
		/**
		 * 自定义自增策略,视isAutoGenerate为false
		 */
		SPECIAL,
		/**
		 * UUID值,视isAutoGenerate为false
		 */
		UUID,
		/**
		 * 联合主键策略,视isAutoGenerate为false
		 */
		JOINT
	}
	
	/**
	 * 是否自增
	 * @return
	 */
	boolean isAutoGenerate() default true ;
	
	/**
	 * 序列名称（oracle、db2带有序列功能），可以自定义序列生成器，该属性也可以用于区分标志位。
	 * @return
	 */
	String sequenceName() default "" ;
	
}
