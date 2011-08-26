package org.paramecium.orm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 功 能 描 述:<br>
 * 虚拟字段声明，该字段只作为动态查询使用，该字段之负责带值并比较，不负责入库
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-3-31下午04:49:08
 * <br>项 目 信 息:paramecium:org.paramecium.orm.annotation.VirtualColumn.java
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)    
@Target(ElementType.FIELD)
public @interface VirtualColumn {
	
	/**
	 * 动态查询逻辑枚举
	 */
	public static class DYNAMIC_WHERE_LOGIC{
		public static final String AND = " AND ";
		public static final String OR = " OR ";
	}
	
	/**
	 * 动态查询比较常量
	 */
	public static class DYNAMIC_WHERE_COMPARISON{
		
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
		
		/**
		 * Like相似
		 */
		public static final String LIKE = " LIKE ";
		
	}
	
	/**
	 * 逻辑运算符
	 * @return
	 */
	String logical() default DYNAMIC_WHERE_LOGIC.AND;
	
	/**
	 * 比较运算符
	 * @return
	 */
	String comparison() default DYNAMIC_WHERE_COMPARISON.EQUAL;
	
	/**
	 * 被比较的数据库字段名
	 * @return
	 */
	String comparisonColumn();
	
}
