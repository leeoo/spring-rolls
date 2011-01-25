package com.wisdom.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * <b>业务说明</b>:暂无用，保留此注解。<br>
 * 主键标示,用于simpleEntity的字段属性<br>
 * <b>作  者</b>: Cao.Yang<br>
 * <b>建立日期</b>: 2010-1-19<br>
 * <b>建立时间</b>: 下午02:54:54<br>
 * <b>项目名称</b>: wisdom.3.0RC2<br>
 * <b>包及类名</b>: com.wisdom.core.annotation.Id.java<br>
 */
@Deprecated
@Documented   
@Retention(RetentionPolicy.RUNTIME)    
@Target(ElementType.FIELD)
public @interface $Id {
	/**
	 * 是否使用主键构建器
	 * @return
	 */
	public boolean isUseIDCreator() default false;
	
	/**
	 * 指定对应数据库表中主键字段名称
	 * @return
	 */
	public String fieldName() default "id";
}
