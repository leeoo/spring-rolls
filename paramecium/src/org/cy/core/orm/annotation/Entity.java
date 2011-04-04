package org.cy.core.orm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 功 能 描 述:<br>
 * 实体声明
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-3-31下午04:51:46
 * <br>项 目 信 息:paramecium:org.cy.core.orm.annotation.Entity.java
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)    
@Target(ElementType.TYPE)
public @interface Entity {
	
	/**
	 * 实体对应的数据库表名称
	 * @return 表名称
	 */
	public String tableName();

	/**
	 * 可用于扩展页面显示组件
	 * @return UI显示
	 */
	public String showLabel() default "";
	
	/**
	 * 排序实例：“id DESC , name ASC”
	 * @return 合成语句
	 */
	public String orderBy() default "";
	
}
