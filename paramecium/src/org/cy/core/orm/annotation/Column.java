package org.cy.core.orm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.cy.core.orm.annotation.VirtualColumn.DYNAMIC_WHERE_COMPARISON;
import org.cy.core.orm.annotation.VirtualColumn.DYNAMIC_WHERE_LOGIC;
/**
 * 功 能 描 述:<br>
 * 字段声明
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-3-31下午04:49:08
 * <br>项 目 信 息:paramecium:org.cy.core.orm.annotation.Column.java
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)    
@Target(ElementType.FIELD)
public @interface Column {

	/**
	 * 对应字段名称，默认将属性名变大写变小写并在前加下划线，如“userName <=> user_name”；如果字段名为u_name,则需要手动填写fieldName
	 * @return
	 */
	String fieldName() default "" ;
	
	/**
	 * 可用于扩展页面显示组件
	 * @return UI显示
	 */
	public String showLabel() default "";
	
	/**
	 * 是否参与动态查询
	 * @return
	 */
	boolean isDynamicWhere() default true;
	
	/**
	 * 逻辑运算符
	 * @return
	 */
	public String logical() default DYNAMIC_WHERE_LOGIC.AND;
	
	/**
	 * 比较运算符
	 * @return
	 */
	public String comparison() default DYNAMIC_WHERE_COMPARISON.EQUAL;

}
