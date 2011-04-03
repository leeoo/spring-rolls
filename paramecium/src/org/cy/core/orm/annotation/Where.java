package org.cy.core.orm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 功能描述(Description):<br><b>
 * 动态条件加入，如果值为NULL将被装载条件查询中
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-4-3下午07:56:26</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.cy.core.orm.annotation.Where.java</b>
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)    
@Target(ElementType.FIELD)
public @interface Where {
	/**
	 * 逻辑运算符
	 * @return
	 */
	public String logical() default "AND";
	
	/**
	 * 比较运算符
	 * @return
	 */
	public String comparison() default "=";

	/**
	 * 参与运算的字段名
	 * @return
	 */
	public String comparisonField() default "";
	

}
