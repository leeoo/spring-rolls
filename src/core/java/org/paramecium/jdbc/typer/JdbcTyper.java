package org.paramecium.jdbc.typer;

import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;
/**
 * jdbc类型，根据字段类型获得jdbc所给出的类型，然后通过getValue方法进行转型
 * @author caoyang
 */
public interface JdbcTyper {
	
	public final static Log logger = LoggerFactory.getLogger();
	/**
	 * 根据AbsTyper构建的fieldClass，转换jdbc传递的value。
	 * @param jdbcValue
	 * @return
	 */
	public Object getValue(Object jdbcValue);
	
}
