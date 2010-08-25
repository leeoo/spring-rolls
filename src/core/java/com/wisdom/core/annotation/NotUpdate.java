package com.wisdom.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * <b>业务说明</b>:用过改注解的属性将不会被update修改。用于simpleEntity的字段属性<br>
 * <b>作  者</b>: Cao.Yang<br>
 * <b>建立日期</b>: 2010-3-4<br>
 * <b>建立时间</b>: 下午02:54:54<br>
 * <b>项目名称</b>: wisdom.3.0RC2<br>
 * <b>包及类名</b>: com.wisdom.core.annotation.NotUpdate.java<br>
 */
@Documented   
@Retention(RetentionPolicy.RUNTIME)    
@Target(ElementType.FIELD)
public @interface NotUpdate {
	
}
