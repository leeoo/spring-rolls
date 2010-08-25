package com.wisdom.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * <b>业务说明</b>:对@Index所搜引擎索引建立查询字段标注,加入该标注的实体属性搜索引擎会建立文本索引<br>
 * <b>作  者</b>: Cao.Yang<br>
 * <b>建立日期</b>: 2010-1-19<br>
 * <b>建立时间</b>: 下午02:54:54<br>
 * <b>项目名称</b>: wisdom.3.0RC2<br>
 * <b>包及类名</b>: com.wisdom.core.annotation.TextWord.java<br>
 */
@Documented   
@Retention(RetentionPolicy.RUNTIME)    
@Target(ElementType.FIELD)
public @interface TextWord {
	
	/**
	 * 是否过滤HTML标签
	 * @return
	 */
	public boolean isFilterHtmlTags() default false;
	
}
