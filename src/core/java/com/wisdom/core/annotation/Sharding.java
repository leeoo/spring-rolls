package com.wisdom.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 功 能 描 述:<br>
 * 数据表拆分(水平):
 * <br>（1）HASH:为指定分表value数量，数据随机分布各个表。
 * 			<b>分表实例“当value为3时，创建table_1、table_2、table_3三张表”。</b>
 * <br>（2）AUTO:当数据量达到value指定的最大值, 则新建表。
 * 			<b>分表实例“当value为100W,而真实数据量已经350W:table_1;table_2;table_3;table_4;前3张表各100W，后1张表50W”。
 * 			（注：value值指定的数量等同于主键值间隔数量如ID值为1~1000000,1000000~2000000，而并不是真实的数据量）</b>
 * <br>（3）ENUM:则是根据enumPropertyName指定的属性（字段）名称的值，该业务字段的值应为某些固定或有规律的值。
 * 			<b>分表实例“当enumPropertyName指定address时，address字段中存有‘沈阳’、‘福州’、'济南'、‘南京’等值，系统会将address值作为区分各表的标志”。
 * 			（注：如果值为汉字却需要英文，或属性为日期却需要年份，推荐为该实体及对应表添加新属性及字段，用以存储处理后的分表标志，入‘沈阳’对应‘SY’；2001-12-01对应2001；‘China Mobile’对应‘cm’）</b>
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-1-25上午11:14:51
 * <br>项 目 信 息:wisdom.3.0:com.wisdom.core.annotation.Sharding.java
 */
@Documented   
@Retention(RetentionPolicy.RUNTIME)    
@Target(ElementType.TYPE)
public @interface Sharding {
	/**
	 * 功 能 描 述:<br>
	 * 数据拆分类别
	 * <br>代 码 作 者:曹阳(CaoYang)
	 * <br>开 发 日 期:2011-1-25上午11:41:41
	 * <br>项 目 信 息:wisdom.3.0:com.wisdom.core.annotation.Sharding.java
	 */
	public enum shardingType{
		HASH,AUTO,ENUM;
	}
	
	/**
	 * 该值为HASH和AUTO所用
	 * @return
	 */
	public int value() default 0;
	
	
	/**
	 * 枚举处理的属性名称
	 * 该值为ENUM所用
	 * @return
	 */
	public String enumPropertyName() default "";
	
	
	
}
