package org.paramecium.jdbc.dialect;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import org.paramecium.jdbc.BaseJdbcTemplate;

/**
 * 功 能 描 述:<br>
 * 数据库方言初步实现
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-3-30下午03:08:25
 * <br>项 目 信 息:paramecium:org.paramecium.jdbc.dialect.BaseDialect.java
 */
public abstract class BaseDialect extends BaseJdbcTemplate{
	
	/**
	 * 获得分页语句
	 * @param sql
	 * @param page
	 * @return
	 */
	public abstract String getSql(final String sql, Page page);
	
	public Page queryPageBeansByArray(final Connection connection,final String sql, Class<?> clazz, Page page, Object... arrayParameters) throws SQLException {
		long count = 0;
		if (page.isAutoCount()) {
			count = (Long) queryUniqueColumnValueByArray(connection,getCountSql(sql), arrayParameters);
			page.setTotalCount((int) count);
		}
		Collection<?> list = queryByArray(connection,getSql(sql, page), clazz, arrayParameters);
		page.setResult(list);
		return page;
	}

	public Page queryPageMapsByArray(final Connection connection,final String sql, Page page,Object... arrayParameters) throws SQLException {
		long count = 0;
		if (page.isAutoCount()) {
			count = (Long) queryUniqueColumnValueByArray(connection,getCountSql(sql), arrayParameters);
			page.setTotalCount((int) count);
		}
		Collection<?> list = queryByArray(connection,getSql(sql, page), arrayParameters);
		page.setResult(list);
		return page;
	}

	public Page queryPageBeansByBean(final Connection connection,final String sql, Class<?> clazz, Page page, Object beanParameters) throws SQLException {
		long count = 0;
		if (page.isAutoCount()) {
			count = (Long) queryUniqueColumnValueByBean(connection,getCountSql(sql), beanParameters);
			page.setTotalCount((int) count);
		}
		Collection<?> list = queryByBean(connection,getSql(sql, page), clazz, beanParameters);
		page.setResult(list);
		return page;
	}

	public Page queryPageBeansByMap(final Connection connection,final String sql, Class<?> clazz, Page page, Map<String, Object> mapParameters) throws SQLException {
		long count = 0;
		if (page.isAutoCount()) {
			count = (Long) queryUniqueColumnValueByMap(connection,getCountSql(sql), mapParameters);
			page.setTotalCount((int) count);
		}
		Collection<?> list = queryByMap(connection,getSql(sql, page), clazz, mapParameters);
		page.setResult(list);
		return page;
	}

	public Page queryPageMapsByMap(final Connection connection,final String sql, Page page, Map<String, Object> mapParameters) throws SQLException {
		long count = 0;
		if (page.isAutoCount()) {
			count = (Long) queryUniqueColumnValueByMap(connection,getCountSql(sql), mapParameters);
			page.setTotalCount((int) count);
		}
		Collection<?> list = queryByMap(connection,getSql(sql, page), mapParameters);
		page.setResult(list);
		return page;
	}
	
	public static String getCountSql(final String nativeSQL){
		return getCountSql(nativeSQL,"COUNT(0)");
	}

	public static String getCountSql(final String nativeSQL,String countSQL){
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
