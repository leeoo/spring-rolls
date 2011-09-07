package org.paramecium.search.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 功能描述(Description):<br><b>
 * 搜索引擎支持
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-8-30下午07:20:25</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.paramecium.search.annotation.Index.java</b>
 */
@Documented
@Inherited
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
