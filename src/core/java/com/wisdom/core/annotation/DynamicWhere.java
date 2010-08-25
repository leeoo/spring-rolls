package com.wisdom.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * <b>业务说明</b>:条件动态查询，此字段部位NULL时动态装载。<br>
 * 该字段必须为Object子类,用于simpleEntity的字段属性<br>
 * <b>作  者</b>: Cao.Yang<br>
 * <b>建立日期</b>: 2010-3-4<br>
 * <b>建立时间</b>: 下午02:54:54<br>
 * <b>项目名称</b>: wisdom.3.0RC2<br>
 * <b>包及类名</b>: com.wisdom.core.annotation.DynamicWhere.java<br>
 */
@Documented   
@Retention(RetentionPolicy.RUNTIME)    
@Target(ElementType.FIELD)
public @interface DynamicWhere {
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
