package com.wisdom.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <b>业务说明</b>:简单实体注解定义, 用于定义simple操作的实体.<br>
 * <b>作  者</b>: Cao.Yang<br>
 * <b>建立日期</b>: 2010-1-7<br>
 * <b>建立时间</b>: 下午03:06:05<br>
 * <b>项目名称</b>: wisdom.3.0RC2<br>
 * <b>包及类名</b>: com.wisdom.core.annotationSimpleEntity.java<br>
 */
@Documented  
@Retention(RetentionPolicy.RUNTIME)    
@Target(ElementType.TYPE)
public @interface SimpleEntity {
	/**
	 * 简单实体对应的数据库表名称
	 * @return 表名称
	 */
	public String tableName();
	
	/**
	 * 简单实体中那个属性名称为对应表的主键,默认值为'id'
	 * @return 属性名称
	 */
	public String pkPropertyName() default "id";
	
	/**
	 * 是否使用主键构建器
	 * @return
	 */
	public boolean isUseIDCreator() default false;
	
	/**
	 * 主键构建器名称
	 * @return
	 */
	public String IDName() default "";
	
	/**
	 * 该实体全局的where条件，实例：“id > 0 AND name='bb'”
	 * @return 合成语句
	 */
	public String where() default "";
	
	/**
	 * 该实体的多结果集查询时(除去唯一结果的get方法)，加入该属性值，起到排序作用。实例：“id DESC , name ASC”
	 * @return 合成语句
	 */
	public String orderBy() default "";
}
