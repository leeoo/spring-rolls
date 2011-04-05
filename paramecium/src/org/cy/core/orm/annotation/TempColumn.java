package org.cy.core.orm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.cy.core.orm.annotation.Column.DYNAMIC_WHERE_COMPARISON;
import org.cy.core.orm.annotation.Column.DYNAMIC_WHERE_LOGIC;
/**
 * 功 能 描 述:<br>
 * 临时字段声明，该字段只作为动态查询使用，该字段之负责带值并比较，不负责入库
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-3-31下午04:49:08
 * <br>项 目 信 息:paramecium:org.cy.core.orm.annotation.TempColumn.java
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)    
@Target(ElementType.FIELD)
public @interface TempColumn {
	
	/**
	 * 逻辑运算符
	 * @return
	 */
	public DYNAMIC_WHERE_LOGIC logical() default DYNAMIC_WHERE_LOGIC.AND;
	
	/**
	 * 比较运算符
	 * @return
	 */
	public String comparison() default DYNAMIC_WHERE_COMPARISON.EQUAL;
	
	/**
	 * 被比较的数据库字段名
	 * @return
	 */
	public String comparisonColumn();

}
