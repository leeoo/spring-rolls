package com.wisdom.core.dao.hsql;

import java.util.List;

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
	
	
	/**
	 * hsql用分页方法,page对象中返回结果为bean集合
	 * @param sql语句，不用写分页函数、排序，只需要写获取数据的内容，如：select * from tb_table where id=? and user_name like :?
	 * @param arrayParameters参数集合
	 * @param page分页对象
	 * @return
	 */
	public Page findPageListBean(final String sql,Class clazz,Page page,Object... arrayParameters){
		Assert.notNull(page,"分页信息不能为空");
		Assert.hasText(sql,"sql语句不正确!");
		long count=0;
		if (page.isAutoCount()) {
			count=findLongByArray(CountSqlBuilder.getCountSql(sql), arrayParameters);
			page.setTotalCount((int)count);
		}
		List list=null;
		if (page.isFirstSetted()&&page.isPageSizeSetted()) {
			list= findListBeanByArray("SELECT LIMIT "+page.getFirst()+" "+page.getPageSize()+" "+sql.substring(7,sql.length()), clazz, arrayParameters);
		}else{
			list= findListBeanByArray(sql, clazz, arrayParameters);
		}
		page.setResult(list);
		return page;
	}

	/**
	 * hsql用分页方法,page对象中返回结果为map集合
	 * @param sql语句，不用写分页函数、排序，只需要写获取数据的内容，如：select * from tb_table where id=? and user_name like :?
	 * @param arrayParameters参数集合
	 * @param page分页对象
	 * @return
	 */
	public Page findPageListMap(final String sql,Page page,Object... arrayParameters){
		Assert.notNull(page,"分页信息不能为空");
		Assert.hasText(sql,"sql语句不正确!");
		long count=0;
		if (page.isAutoCount()) {
			count=findLongByArray(CountSqlBuilder.getCountSql(sql), arrayParameters);
			page.setTotalCount((int)count);
		}
		List list=null;
		if (page.isFirstSetted()&&page.isPageSizeSetted()) {
			list= findListMapByArray("SELECT LIMIT "+page.getFirst()+" "+page.getPageSize()+" "+sql.substring(7,sql.length()),arrayParameters);
		}else{
			list= findListMapByArray(sql, arrayParameters);
		}
		page.setResult(list);
		return page;
	}
	
}
