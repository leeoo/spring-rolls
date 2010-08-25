package com.wisdom.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * <b>业务说明</b>:搜索引擎支持<br>
 * <b>作  者</b>: Cao.Yang<br>
 * <b>建立日期</b>: 2010-1-19<br>
 * <b>建立时间</b>: 下午02:54:54<br>
 * <b>项目名称</b>: wisdom.3.0RC2<br>
 * <b>包及类名</b>: com.wisdom.core.annotationIndex.java<br>
 */
@Documented   
@Retention(RetentionPolicy.RUNTIME)    
@Target(ElementType.TYPE)
public @interface Index {
	/**
	 * @return 索引名称
	 */
	public String indexName();
	
	/**
	 * @return 索引唯一标示关键字，对应数据库的主键名
	 */
	public String keywordPropertyName() default "id";
}
