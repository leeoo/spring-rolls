package org.paramecium.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 功能描述(Description):<br><b>
 * 验证框架定义IPV4地址
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-8-22下午04:25:58</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.paramecium.validation.annotation.IpV4.java</b>
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)    
@Target(ElementType.FIELD)
public @interface IpV4{

	/**
	 * 错误消息
	 * @return
	 */
	String message() default "IP(V4)地址格式的错误!例如:'192.168.1.1'";

}
