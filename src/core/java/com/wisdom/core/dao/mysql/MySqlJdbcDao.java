package com.wisdom.core.dao.mysql;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.util.Assert;

import com.wisdom.core.dao.BaseJdbcTemplate;
import com.wisdom.core.dao.CountSqlBuilder;
import com.wisdom.core.utils.Page;
/**
 * 
 * <b>业务说明</b>:<br>
 * 基于MYSQL数据库的jdbc数据访问层
 *<br><b>作  者</b>: Cao.Yang<br>
 * <b>建立日期</b>: 2009-3-13<br>
 * <b>建立时间</b>: 上午02:24:10<br>
 * <b>项目名称</b>: easyapp<br>
 * <b>包及类名</b>: com.wisdom.core.dao.jdbc.mysql.MySqlJdbcDao.java<br>
 */
@SuppressWarnings("unchecked")
public final class MySqlJdbcDao extends BaseJdbcTemplate{

	public MySqlJdbcDao(DataSource dataSource) {
		super(dataSource);
	}
	
	public MySqlJdbcDao(DataSource dataSource,int mappingStrategy) {
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
