package org.paramecium.search.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.lucene.search.SortField;
/**
 * 功能描述(Description):<br><b>
 * Lucene排序用，只作存储，不做索引和分词
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-8-30下午07:23:30</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.paramecium.search.annotation.TextWord.java</b>
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SortWord {
	
	/**
	 * 排序方式，默认反向排序
	 * @return
	 */
	public boolean reverse() default true;
	
	/**
	 * 排序类型
	 * @return
	 */
	public int type() default SortField.INT;
	
}
