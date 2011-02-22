package com.wisdom.core.dao.sqlserver;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.util.Assert;

import com.wisdom.core.dao.BaseJdbcTemplate;
import com.wisdom.core.dao.CountSqlBuilder;
import com.wisdom.core.utils.Page;
/**
 * 功能描述：
 * 基于sqlserver2000的jdbc实现
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-10-29</b>
 * <br>创建时间：<b>下午04:15:02</b>
 * <br>文件结构：<b>spring:com.wisdom.core.dao.sqlserver/SqlServer2kJdbcDao.java</b>
 */
@SuppressWarnings("unchecked")
public final class SqlServerJdbcDao extends BaseJdbcTemplate{

	public SqlServerJdbcDao(DataSource dataSource) {
		super(dataSource);
	}
	
	public SqlServerJdbcDao(DataSource dataSource,int mappingStrategy) {
		super(dataSource,mappingStrategy);
	}
	
	
	public Page findPageListBean(final String sql,Class clazz,Page page,Object... arrayParameters){
		Assert.notNull(page,"分页信息不能为空");
		Assert.hasText(sql,"sql语句不正确!");
		long count=0;
		if (page.isAutoCount()) {
			count=findLongByArray(CountSqlBuilder.getCountSql(sql), arrayParameters);
			page.setTotalCount((int)count);
		}
		List list=findListBeanByArray(getSql(sql, page,count), clazz, arrayParameters);
		page.setResult(list);
		return page;
	}

	public Page findPageListMap(final String sql,Page page,Object... arrayParameters){
		Assert.notNull(page,"分页信息不能为空");
		Assert.hasText(sql,"sql语句不正确!");
		long count=0;
		if (page.isAutoCount()) {
			count=findLongByArray(CountSqlBuilder.getCountSql(sql), arrayParameters);
			page.setTotalCount((int)count);
		}
		List list = findListMapByArray(getSql(sql, page,count), arrayParameters);
		page.setResult(list);
		return page;
	}
	

	public Page findPageListBeanByBean(String sql, Class clazz, Page page,Object beanParameters) {
		Assert.notNull(page,"分页信息不能为空");
		Assert.hasText(sql,"sql语句不正确!");
		long count=0;
		if (page.isAutoCount()) {
			count=findLongByBean(CountSqlBuilder.getCountSql(sql), beanParameters);
			page.setTotalCount((int)count);
		}
		List list = findListBeanByBean(getSql(sql, page,count),clazz, beanParameters);
		page.setResult(list);
		return page;
	}

	public Page findPageListBeanByMap(String sql, Class clazz, Page page,Map<String, Object> mapParameters) {
		Assert.notNull(page,"分页信息不能为空");
		Assert.hasText(sql,"sql语句不正确!");
		long count=0;
		if (page.isAutoCount()) {
			count=findLongByMap(CountSqlBuilder.getCountSql(sql), mapParameters);
			page.setTotalCount((int)count);
		}
		List list = findListBeanByMap(getSql(sql, page,count),clazz, mapParameters);
		page.setResult(list);
		return page;
	}

	public Page findPageListMapByMap(String sql, Page page,Map<String, Object> mapParameters) {
		Assert.notNull(page,"分页信息不能为空");
		Assert.hasText(sql,"sql语句不正确!");
		long count=0;
		if (page.isAutoCount()) {
			count=findLongByMap(CountSqlBuilder.getCountSql(sql), mapParameters);
			page.setTotalCount((int)count);
		}
		List list = findListMapByMap(getSql(sql, page,count), mapParameters);
		page.setResult(list);
		return page;
	}
	
	public static String getSql(final String sql,Page page,long count){
		String finalSql=sql;
		if (page.isFirstSetted()&&page.isPageSizeSetted()) {
			long i = page.getPageSize();
			long j = page.getFirst()+page.getPageSize();
			if(j>=count){
				i = i-(j-count);
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
