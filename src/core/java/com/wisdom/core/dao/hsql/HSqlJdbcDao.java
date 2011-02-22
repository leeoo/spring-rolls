package com.wisdom.core.dao.hsql;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.util.Assert;

import com.wisdom.core.dao.BaseJdbcTemplate;
import com.wisdom.core.dao.CountSqlBuilder;
import com.wisdom.core.utils.Page;
/**
 * 功能描述：
 * 基于hsql的DAO
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-10-6</b>
 * <br>创建时间：<b>下午02:45:19</b>
 * <br>文件结构：<b>spring:com.wisdom.core.dao.hsql/HSqlJdbcDao.java</b>
 */
@SuppressWarnings("unchecked")
public final class HSqlJdbcDao extends BaseJdbcTemplate{

	public HSqlJdbcDao(DataSource dataSource) {
		super(dataSource);
	}
	
	public HSqlJdbcDao(DataSource dataSource,int mappingStrategy) {
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
		List list=findListBeanByArray(getSql(sql, page), clazz, arrayParameters);
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
		List list = findListMapByArray(getSql(sql, page), arrayParameters);
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
		List list = findListBeanByBean(getSql(sql, page),clazz, beanParameters);
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
		List list = findListBeanByMap(getSql(sql, page),clazz, mapParameters);
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
		List list = findListMapByMap(getSql(sql, page), mapParameters);
		page.setResult(list);
		return page;
	}
	
	public static String getSql(final String sql,Page page){
		if (page.isFirstSetted()&&page.isPageSizeSetted()) {
			return "SELECT LIMIT "+page.getFirst()+" "+page.getPageSize()+" "+sql.substring(7,sql.length());
		}else{
			return sql;
		}
	}
	
}
