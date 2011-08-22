package org.paramecium.validation.annotation.base;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 功能描述(Description):<br><b>
 * 验证框架定义带小数数值范围
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-8-22下午06:12:20</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.paramecium.validation.annotation.base.DecimalSize.java</b>
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)    
@Target(ElementType.FIELD)
public @interface DecimalSize{

	/**
	 * 错误消息
	 * @return
	 */
	String message() default "{ShowLabel}的范围应在{min}到{max}之间!";
	
	/**
	 * 最小值
	 * @return
	 */
	double min() default 0.0D;
	
	/**
	 * 最大值
	 * @return
	 */
	double max();

}
