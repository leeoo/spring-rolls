package org.cy.core.jdbc.dialect;

import java.sql.Connection;

/**
 * 功 能 描 述:<br>
 * DB2方言实现
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-3-30下午03:10:39
 * <br>项 目 信 息:paramecium:org.cy.core.jdbc.dialect.Db2Dialect.java
 */
public final class Db2Dialect extends BaseDialect implements Dialect {

	public Db2Dialect(Connection connection) {
		super(connection);
	}

	public String getSql(final String sql, Page page) {
		if (page.isFirstSetted() && page.isPageSizeSetted()) {
			String queryLastSql = ") rs1) rs2 WHERE rn > " + page.getFirst()+ " AND rn <= " + page.getFirst() + page.getPageSize();
			int groupby = sql.toUpperCase().indexOf("GROUP BY");
			int orderby = sql.toUpperCase().indexOf("ORDER BY");
			if (orderby > groupby) {
				groupby = sql.length();
			}
			String temp1 = "";
			if (orderby > 0) {
				temp1 = sql.substring(orderby, groupby);
			}
			String queryFristSql = "SELECT * FROM (SELECT rs1.*,ROWNUMBER() OVER("+ temp1 + ") rn FROM(";
			String lastSql = queryFristSql.concat(sql.concat(queryLastSql));
			return lastSql;
		} else {
			return sql;
		}
	}

}
