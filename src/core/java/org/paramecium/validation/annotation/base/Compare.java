package org.paramecium.validation.annotation.base;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 功能描述(Description):<br><b>
 * 验证框架定义和某字段比较大小
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-8-22下午06:13:50</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.paramecium.validation.annotation.base.Compare.java</b>
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)    
@Target(ElementType.FIELD)
public @interface Compare{

	/**
	 * 错误消息
	 * @return
	 */
	String message() default "{ShowLabel}必须{comparison}{compareField.ShowLabel}!";
	
	String compareField();
	
	String comparison() default COMPARISON.THAN;
	
	/**
	 * 比较常量
	 */
	public static class COMPARISON{
		
		/**
		 * 等于
		 */
		public static final String EQUAL = " = ";
		/**
		 * 大于
		 */
		public static final String THAN = " > ";
		/**
		 * 大于或等于
		 */
		public static final String THAN_EQUAL = " >= ";
		/**
		 * 小于
		 */
		public static final String LESS = " < ";
		/**
		 * 小于或等于
		 */
		public static final String LESS_EQUAL = " <= ";
		
	}

}
