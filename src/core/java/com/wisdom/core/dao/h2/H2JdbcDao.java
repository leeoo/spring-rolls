package com.wisdom.core.dao.h2;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.util.Assert;

import com.wisdom.core.dao.BaseJdbcTemplate;
import com.wisdom.core.dao.CountSqlBuilder;
import com.wisdom.core.utils.Page;
/**
 * 功 能 描 述:<br>
 * 基于H2数据库的jdbc数据访问层
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2010-8-24上午11:25:26
 * <br>项 目 信 息:wisdom.3.0:com.wisdom.core.dao.h2.H2JdbcDao.java
 */
@SuppressWarnings("unchecked")
public final class H2JdbcDao extends BaseJdbcTemplate{

	public H2JdbcDao(DataSource dataSource) {
		super(dataSource);
	}
	
	public H2JdbcDao(DataSource dataSource,int mappingStrategy) {
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
		String querySql=sql;
		if (page.isFirstSetted()&&page.isPageSizeSetted()) {
			querySql=querySql.concat(" LIMIT "+page.getFirst()+","+page.getPageSize());
		}
		return querySql;
	}
	
	
}
