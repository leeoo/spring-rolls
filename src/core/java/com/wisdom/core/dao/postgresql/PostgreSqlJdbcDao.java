package com.wisdom.core.dao.postgresql;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.util.Assert;

import com.wisdom.core.dao.BaseJdbcTemplate;
import com.wisdom.core.utils.Page;
/**
 * 功 能 描 述:<br>
 * 基于PostgreSql数据库的jdbc数据访问层
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2010-8-24上午10:42:44
 * <br>项 目 信 息:wisdom.3.0:com.wisdom.core.dao.postgresql.PostgreSqlJdbcDao.java
 */
@SuppressWarnings("unchecked")
public final class PostgreSqlJdbcDao extends BaseJdbcTemplate{

	public PostgreSqlJdbcDao(DataSource dataSource) {
		super(dataSource);
	}
	
	public PostgreSqlJdbcDao(DataSource dataSource,int mappingStrategy) {
		super(dataSource,mappingStrategy);
	}
	
	
	/**
	 * PostgreSql用分页方法,page对象中返回结果为bean集合
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
			count=findLongByArray(getCountSql(querySql), arrayParameters);
			page.setTotalCount((int)count);
		}
		List list=null;
		if (page.isFirstSetted()&&page.isPageSizeSetted()) {
			querySql=querySql.concat(" LIMIT ? OFFSET ?");
			if(arrayParameters!=null){
				Object[] obs=new Object[2];
				obs[0]=page.getFirst();
				obs[1]=page.getPageSize();
				Object[] newprmts=ArrayUtils.addAll(arrayParameters, obs);
				list= findListBeanByArray(querySql, clazz, newprmts);
			}else{
				list= findListBeanByArray(querySql, clazz, page.getPageSize(),page.getFirst());
			}
		}else{
			list= findListBeanByArray(querySql, clazz, arrayParameters);
		}
		page.setResult(list);
		return page;
	}

	/**
	 * PostgreSql用分页方法,page对象中返回结果为map集合
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
			count=findLongByArray(getCountSql(querySql), arrayParameters);
			page.setTotalCount((int)count);
		}
		List list=null;
		if (page.isFirstSetted()&&page.isPageSizeSetted()) {
			querySql=querySql.concat(" LIMIT ? OFFSET ?");
			if(arrayParameters!=null){
				Object[] obs=new Object[2];
				obs[0]=page.getFirst();
				obs[1]=page.getPageSize();
				Object[] newprmts=ArrayUtils.addAll(arrayParameters, obs);
				list= findListMapByArray(querySql,newprmts);
			}else{
				list= findListMapByArray(querySql,page.getPageSize(),page.getFirst());
			}
		}else{
			list= findListMapByArray(querySql, arrayParameters);
		}
		page.setResult(list);
		return page;
	}
	
	private static String getCountSql(String sql){
		Assert.hasText(sql,"sql语句不正确!");
		sql=sql.toUpperCase();
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
		tempSql=" FROM "+sql.substring(tempSql.length(),sql.length());
		String withOutOrderBy=tempSql;
		if(tempSql.indexOf(" ORDER BY ")>=0){
			withOutOrderBy=tempSql.substring(0,tempSql.indexOf(" ORDER BY"));
		}
		return "SELECT COUNT(*) ".concat(withOutOrderBy);
	}
	
}
