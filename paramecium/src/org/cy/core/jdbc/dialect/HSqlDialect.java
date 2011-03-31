package org.cy.core.jdbc.dialect;

import java.sql.Connection;
/**
 * 功 能 描 述:<br>
 * HSQL数据库方言实现
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-3-30下午03:15:14
 * <br>项 目 信 息:paramecium:org.cy.core.jdbc.dialect.HSqlDialect.java
 */
public final class HSqlDialect extends BaseDialect implements Dialect {

	public HSqlDialect(Connection connection) {
		super(connection);
	}

	public String getSql(final String sql,Page page){
		if (page.isFirstSetted()&&page.isPageSizeSetted()) {
			return "SELECT LIMIT "+page.getFirst()+" "+page.getPageSize()+" "+sql.substring(7,sql.length());
		}else{
			return sql;
		}
	}
	
}
