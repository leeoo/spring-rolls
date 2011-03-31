package org.cy.core.jdbc.dialect;

import java.sql.Connection;
/**
 * 功 能 描 述:<br>
 * SQLSERVER2000版本数据库方言实现
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-3-30下午03:26:35
 * <br>项 目 信 息:paramecium:org.cy.core.jdbc.dialect.SqlServerDialect.java
 */
public final class SqlServerDialect extends BaseDialect implements Dialect {

	public SqlServerDialect(Connection connection) {
		super(connection);
	}

	public String getSql(final String sql,Page page){
		String finalSql=sql;
		if (page.isFirstSetted()&&page.isPageSizeSetted()) {
			long i = page.getPageSize();
			long j = page.getFirst()+page.getPageSize();
			if(j>=page.getTotalCount()){
				i = i-(j-page.getTotalCount());
			}
			String querySqlFirst = "SELECT * FROM ( SELECT TOP "+i+" * FROM ( SELECT TOP "+j+" ";
			String querySqlLastTemp = " ORDER BY id ASC ) tempt1 ORDER BY id DESC ) as tempt2 ORDER BY id ASC";
			String querySqlLast = "";
			if(finalSql.toUpperCase().indexOf("ORDER BY")<0){
				querySqlLast = querySqlLastTemp;
			}else{
				querySqlLast = " ORDER BY ";
				int orderby=finalSql.toUpperCase().indexOf("ORDER BY");
				int groupby=finalSql.toUpperCase().indexOf("GROUP BY");
				if(orderby>groupby){
					groupby=finalSql.length();
				}
				String temp1 = finalSql.substring(finalSql.toUpperCase().indexOf("ORDER BY"), groupby);
				String temp2 = finalSql.substring(finalSql.toUpperCase().indexOf("ORDER BY")+8, groupby);
				finalSql = finalSql.replaceAll(temp1, " ");
				String[] orderBys=temp2.split(",");
				String orderByASC="";
				String orderByASC2="";
				String orderByDESC="";
				for(int m=0;m<orderBys.length;m++){
					String tempOrderBy=orderBys[m];
					String dot=",";
					if(m==orderBys.length-1){
						dot="";
					}
					String tempOrderByASC = tempOrderBy;
					String tempOrderByDESC = tempOrderBy;
					if(tempOrderBy.indexOf(".")>0){
						tempOrderByASC = "tempt2"+tempOrderByASC.substring(tempOrderBy.indexOf("."), tempOrderBy.length());
						tempOrderByDESC = "tempt1"+tempOrderByDESC.substring(tempOrderBy.indexOf("."), tempOrderBy.length());
					}
					if(tempOrderBy.toUpperCase().indexOf("DESC")>=0){
						orderByASC2 = orderByASC2.concat(tempOrderBy+dot);
						orderByASC = orderByASC.concat(tempOrderByASC+dot);
						int start = tempOrderByDESC.toUpperCase().indexOf("DESC");
						tempOrderByDESC = tempOrderByDESC.substring(0, start)+"ASC";
						orderByDESC = orderByDESC.concat(tempOrderByDESC+dot);
					}else if(tempOrderBy.toUpperCase().indexOf("ASC")>=0){
						orderByASC2 = orderByASC2.concat(tempOrderBy+dot);
						orderByASC = orderByASC.concat(tempOrderByASC+dot);
						int start = tempOrderByDESC.toUpperCase().indexOf("ASC");
						tempOrderByDESC = tempOrderByDESC.substring(0, start)+"DESC";
						orderByDESC = orderByDESC.concat(tempOrderByDESC+dot);
					}else{
						orderByASC2 = orderByASC2.concat(tempOrderBy+dot);
						orderByASC = orderByASC.concat(tempOrderByASC+" ASC"+dot);
						orderByDESC = orderByDESC.concat(tempOrderByDESC+" DESC"+dot);
					}
				}
				querySqlLast = querySqlLast.concat(orderByASC2+" ) tempt1 ORDER BY ".concat(orderByDESC)+" ) tempt2 ORDER BY ".concat(orderByASC));
			}
			String lastSql=querySqlFirst.concat(finalSql.trim().substring(6, finalSql.trim().length()).concat(querySqlLast));
			return lastSql;
		}else{
			return finalSql;
		}
	}
	
}
