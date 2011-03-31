package org.cy.core.jdbc.dialect;

/**
 * 功 能 描 述:<br>
 * 获得count语句的构建工具
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-3-30上午09:12:52
 * <br>项 目 信 息:paramecium:org.cy.core.jdbc.dialect.CountSqlBuilder.java
 */
public abstract class CountSqlBuilder {
	
	public static String getCountSql(String nativeSQL){
		return getCountSql(nativeSQL,"COUNT(0)");
	}

	public static String getCountSql(String nativeSQL,String countSQL){
		String sql=nativeSQL.toUpperCase();
		if(sql.indexOf("DISTINCT(")>=0||sql.indexOf(" GROUP BY ")>=0){
			return "SELECT "+countSQL+" FROM ("+nativeSQL+") TEMP_COUNT_TABLE";
		}
		String[] froms=sql.split(" FROM ");
		String tempSql="";
		for(int i=0;i<froms.length;i++){
			if(i!=froms.length-1){
				tempSql=tempSql.concat(froms[i]+" FROM ");
			}else{
				tempSql=tempSql.concat(froms[i]);
			}
			int left=tempSql.split("\\(").length;
			int right=tempSql.split("\\)").length;
			if(left==right){
				break;
			}
		}
		tempSql=" FROM "+nativeSQL.substring(tempSql.length(),sql.length());
		int orderBy = tempSql.toUpperCase().indexOf(" ORDER BY ");
		if(orderBy>=0){
			tempSql=tempSql.substring(0,orderBy);
		}
		return "SELECT "+countSQL+" ".concat(tempSql);
	}
	
}
