package com.wisdom.core.dao.h2;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.ArrayUtils;
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
	
	
	/**
	 * H2用分页方法,page对象中返回结果为bean集合
	 * @param sql语句，不用写分页函数、排序，只需要写获取数据的内容，如：select * from tb_table where id=? and user_name like :?
	 * @param arrayParameters参数集合
	 * @param page分页对象
	 * @return
	 */
	public Page findPageListBean(final String sql,Class clazz,Page page,Object... arrayParameters){
		Assert.notNull(page,"分页信息不能为空");
		Assert.hasText(sql,"sql语句不正确!");
		String querySql=sql;
		long count=0;
		if (page.isAutoCount()) {
			count=findLongByArray(CountSqlBuilder.getCountSql(querySql), arrayParameters);
			page.setTotalCount((int)count);
		}
		List list=null;
		if (page.isFirstSetted()&&page.isPageSizeSetted()) {
			querySql=querySql.concat(" LIMIT ?,?");
			if(arrayParameters!=null){
				Object[] obs=new Object[2];
				obs[0]=page.getFirst();
				obs[1]=page.getPageSize();
				Object[] newprmts=ArrayUtils.addAll(arrayParameters, obs);
				list= findListBeanByArray(querySql, clazz, newprmts);
			}else{
				list= findListBeanByArray(querySql, clazz, page.getFirst(),page.getPageSize());
			}
		}else{
			list= findListBeanByArray(querySql, clazz, arrayParameters);
		}
		page.setResult(list);
		return page;
	}

	/**
	 * H2用分页方法,page对象中返回结果为map集合
	 * @param sql语句，不用写分页函数、排序，只需要写获取数据的内容，如：select * from tb_table where id=? and user_name like :?
	 * @param arrayParameters参数集合
	 * @param page分页对象
	 * @return
	 */
	public Page findPageListMap(final String sql,Page page,Object... arrayParameters){
		Assert.notNull(page,"分页信息不能为空");
		Assert.hasText(sql,"sql语句不正确!");
		String querySql=sql;
		long count=0;
		if (page.isAutoCount()) {
			count=findLongByArray(CountSqlBuilder.getCountSql(querySql), arrayParameters);
			page.setTotalCount((int)count);
		}
		List list=null;
		if (page.isFirstSetted()&&page.isPageSizeSetted()) {
			querySql=querySql.concat(" LIMIT ?,?");
			if(arrayParameters!=null){
				Object[] obs=new Object[2];
				obs[0]=page.getFirst();
				obs[1]=page.getPageSize();
				Object[] newprmts=ArrayUtils.addAll(arrayParameters, obs);
				list= findListMapByArray(querySql,newprmts);
			}else{
				list= findListMapByArray(querySql,page.getFirst(),page.getPageSize());
			}
		}else{
			list= findListMapByArray(querySql, arrayParameters);
		}
		page.setResult(list);
		return page;
	}
	
	
}
