package org.paramecium.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 功能描述(Description):<br><b>
 * 验证框架定义必须为数值类型
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-8-22下午04:25:58</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.paramecium.validation.annotation.Numeric.java</b>
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)    
@Target(ElementType.FIELD)
public @interface Numeric{

	/**
	 * 错误消息
	 * @return
	 */
	String message() default "{ShowLabel}必须是数值型!";
	
	/**
	 * 数值类型
	 * @return
	 */
	NUMBER_TYPE type() default NUMBER_TYPE.NUMERIC;
	
	/**
	 * 功能描述(Description):<br><b>
	 * 数值类型
	 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
	 * <br>建立日期(Create Date): <b>2011-8-22下午05:21:09</b>
	 * <br>项目名称(Project Name): <b>paramecium</b>
	 * <br>包及类名(Package Class): <b>org.paramecium.validation.annotation.Number.java</b>
	 */
	public enum NUMBER_TYPE{
		/**
		 * 所有数值,包括整数，小数等
		 */
		NUMERIC,
		/**
		 * 整数:包括负数
		 */
		INTEGER,
		/**
		 * 自然数:包括0的正整数
		 */
		NATURAL 
	}

}
