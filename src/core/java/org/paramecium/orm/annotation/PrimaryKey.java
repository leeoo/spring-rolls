package org.paramecium.orm.annotation;

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
 * <br>项 目 信 息:paramecium:org.paramecium.orm.annotation.PrimaryKey.java
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)    
@Target(ElementType.FIELD)
public @interface PrimaryKey {
	
	public static enum AUTO_GENERATE_MODE{
		/**
		 * 数据库级别自增
		 */
		NATIVE,
		/**
		 * 数据库级别序列自增,如Oracle、DB2等带有序列功能。
		 */
		NATIVE_SEQUENCE,
		/**
		 * 手动赋值,如果@PrimaryKey被两个以上属性声明,所有使用@PrimaryKey的属性都被视为AUTO_GENERATE_MODE.NATIVE
		 */
		MANUAL;
	}
	
	/**
	 * 
	 * @return
	 */
	AUTO_GENERATE_MODE autoGenerateMode() default AUTO_GENERATE_MODE.NATIVE ;
	
	/**
	 * 序列名称（oracle、db2带有序列功能），可以自定义序列生成器，该属性也可以用于区分标志位。
	 * @return
	 */
	String sequenceName() default "" ;
	
}
