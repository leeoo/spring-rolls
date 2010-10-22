package com.wisdom.core.dao.derby;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.util.Assert;

import com.wisdom.core.dao.BaseJdbcTemplate;
import com.wisdom.core.dao.CountSqlBuilder;
import com.wisdom.core.utils.Page;
/**
 * 功 能 描 述:<br>
 * 基于Derby的jdbc实现
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2010-8-24上午11:10:52
 * <br>项 目 信 息:wisdom.3.0:com.wisdom.core.dao.derby.DerbyJdbcDao.java
 */
@SuppressWarnings("unchecked")
public final class DerbyJdbcDao extends BaseJdbcTemplate{

	public DerbyJdbcDao(DataSource dataSource) {
		super(dataSource);
	}
	
	public DerbyJdbcDao(DataSource dataSource,int mappingStrategy) {
		super(dataSource,mappingStrategy);
	}
	
	
	/**
	 * Derby用分页方法,page对象中返回结果为bean集合
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
			String queryLastSql=") temp_results) final_results WHERE row_number > "+page.getFirst()+" AND row_number <= "+page.getFirst()+page.getPageSize();
			int groupby=sql.toUpperCase().indexOf("GROUP BY");
			int orderby=sql.toUpperCase().indexOf("ORDER BY");
			if(orderby>groupby){
				groupby=sql.length();
			}
			String temp1 = "";
			if(orderby>0){
				temp1 = sql.substring(orderby, groupby);
			}
			String queryFristSql="SELECT * FROM (SELECT ROW_NUMBER() OVER ("+temp1+")  row_number,temp_results.* FROM(";
			String lastSql=queryFristSql.concat(sql.concat(queryLastSql));
			list= findListBeanByArray(lastSql, clazz, arrayParameters);
		}else{
			list= findListBeanByArray(sql, clazz, arrayParameters);
		}
		page.setResult(list);
		return page;
	}

	/**
	 * Derby用分页方法,page对象中返回结果为map集合
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
			String queryLastSql=") temp_results) final_results WHERE row_number > "+page.getFirst()+" AND row_number <= "+page.getFirst()+page.getPageSize();
			int groupby=sql.toUpperCase().indexOf("GROUP BY");
			int orderby=sql.toUpperCase().indexOf("ORDER BY");
			if(orderby>groupby){
				groupby=sql.length();
			}
			String temp1 = "";
			if(orderby>0){
				temp1 = sql.substring(orderby, groupby);
			}
			String queryFristSql="SELECT * FROM (SELECT ROW_NUMBER() OVER ("+temp1+") row_number,temp_results.* FROM(";
			String lastSql=queryFristSql.concat(sql.concat(queryLastSql));
			list= findListMapByArray(lastSql, arrayParameters);
		}else{
			list= findListMapByArray(sql,arrayParameters);
		}
		page.setResult(list);
		return page;
	}
	
	
}
