package org.paramecium.validation.annotation.base;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 功能描述(Description):<br><b>
 * 验证框架定义非空
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-8-22下午06:12:48</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.paramecium.validation.annotation.base.NotNull.java</b>
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)    
@Target(ElementType.FIELD)
public @interface NotNull {

	/**
	 * 错误消息
	 * @return
	 */
	String message() default "{ShowLabel}不能为空!";
	
	/**
	 * 包含去除空格的空字符视为错误
	 * @return
	 */
	boolean empty() default false;
	
}
