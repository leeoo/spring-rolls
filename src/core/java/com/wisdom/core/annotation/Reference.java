package com.wisdom.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 功能描述(Description):<br><b>
 * 引用查询即标量子查询，通过外键或获得与之相连的表的某个字段信息。用于simpleEntity的字段属性。
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2010-3-5下午09:13:57</b>
 * <br>项目名称(Project Name): <b>wisdom.3.0RC2</b>
 * <br>包及类名(Package Class): <b>com.wisdom.core.annotation.Reference.java</b>
 */
@Documented   
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Reference {
	/**
	 * 引用的表名
	 * @return
	 */
	public String refTableName();
	
	/**
	 * 所引用表的主键字段名
	 * @return
	 */
	public String refPKFieldName() default "id";

	/**
	 * 所引用表的要显示的字段名
	 * @return
	 */
	public String refViewFieldName();
	
	/**
	 * 与引用关联的本表外键字段名
	 * @return
	 */
	public String FKFieldName() default "id";
	

}
