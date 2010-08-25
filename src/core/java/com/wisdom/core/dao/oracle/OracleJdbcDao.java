package com.wisdom.core.dao.oracle;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.util.Assert;

import com.wisdom.core.dao.BaseJdbcTemplate;
import com.wisdom.core.utils.Page;
/**
 * 功能描述：
 * 基于oracle的
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-10-29</b>
 * <br>创建时间：<b>下午04:08:42</b>
 * <br>文件结构：<b>spring:com.wisdom.core.dao.oracle/OracleJdbcDao.java</b>
 */
@SuppressWarnings("unchecked")
public final class OracleJdbcDao extends BaseJdbcTemplate{

	public OracleJdbcDao(DataSource dataSource) {
		super(dataSource);
	}
	
	public OracleJdbcDao(DataSource dataSource,int mappingStrategy) {
		super(dataSource,mappingStrategy);
	}
	
	
	/**
	 * oracle用分页方法,page对象中返回结果为bean集合
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
			count=findLongByArray(getCountSql(sql), arrayParameters);
			page.setTotalCount((int)count);
		}
		List list=null;
		if (page.isFirstSetted()&&page.isPageSizeSetted()) {
			String querySqlFirst="SELECT * FROM ( SELECT tempt.*,ROWNUM AS rn FROM ( ";
			String querySqlLast=" ) AS tempt WHERE ROWNUM<=? ) WHERE rn>=?";
			String lastSql=querySqlFirst.concat(sql.concat(querySqlLast));
			if(arrayParameters!=null){
				Object[] obs=new Object[2];
				obs[0]=page.getFirst()+page.getPageSize();
				obs[1]=page.getFirst();
				Object[] newprmts=ArrayUtils.addAll(arrayParameters, obs);
				list= findListBeanByArray(lastSql, clazz, newprmts);
			}else{
				list= findListBeanByArray(lastSql, clazz, page.getFirst()+page.getPageSize(),page.getFirst());
			}
		}else{
			list= findListBeanByArray(sql, clazz, arrayParameters);
		}
		page.setResult(list);
		return page;
	}

	/**
	 * oracle用分页方法,page对象中返回结果为map集合
	 * @param sql语句，不用写分页函数、排序，只需要写获取数据的内容，如：select * from tb_table where id=? and user_name like :?
	 * @param arrayParameters参数集合
	 * @param page分页对象
	 * @return
	 */
	public Page findPageListMap(final String sql,Page page,Object... arrayParameters){
		Assert.notNull(page,"分页信息不能为空");
		Assert.hasText(sql,"sql语句不正确!");
		getCountSql(sql);
		long count=0;
		if (page.isAutoCount()) {
			count=findLongByArray(getCountSql(sql), arrayParameters);
			page.setTotalCount((int)count);
		}
		List list=null;
		if (page.isFirstSetted()&&page.isPageSizeSetted()) {
			String querySqlFirst="SELECT * FROM ( SELECT tempt.*,ROWNUM AS rn FROM ( ";
			String querySqlLast=" ) AS tempt WHERE ROWNUM<=? ) WHERE rn>=?";
			String lastSql=querySqlFirst.concat(sql.concat(querySqlLast));
			if(arrayParameters!=null){
				Object[] obs=new Object[2];
				obs[0]=page.getFirst()+page.getPageSize();
				obs[1]=page.getFirst();
				Object[] newprmts=ArrayUtils.addAll(arrayParameters, obs);
				list= findListMapByArray(lastSql, newprmts);
			}else{
				list= findListMapByArray(lastSql,page.getFirst()+page.getPageSize(),page.getFirst());
			}
		}else{
			list= findListMapByArray(sql, arrayParameters);
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
