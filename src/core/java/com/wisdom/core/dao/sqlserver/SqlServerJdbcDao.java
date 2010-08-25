package com.wisdom.core.dao.sqlserver;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.util.Assert;

import com.wisdom.core.dao.BaseJdbcTemplate;
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
	
	
	/**
	 * sqlserver用分页方法,page对象中返回结果为bean集合
	 * @param sql语句，不用写分页函数、排序，只需要写获取数据的内容，如：select * from tb_table where id=? and user_name like :?
	 * @param arrayParameters参数集合
	 * @param page分页对象
	 * @return
	 */
	public Page findPageListBean(final String sql,Class clazz,Page page,Object... arrayParameters){
		Assert.notNull(page,"分页信息不能为空");
		Assert.hasText(sql,"sql语句不正确!");
		String finalSql=sql;
		long count=0;
		if (page.isAutoCount()) {
			count=findLongByArray(getCountSql(finalSql), arrayParameters);
			page.setTotalCount((int)count);
		}
		List list=null;
		if (page.isFirstSetted()&&page.isPageSizeSetted()) {
			long i = page.getPageSize();
			long j = page.getFirst()+page.getPageSize();
			if(j>=count){
				i = i-(j-count);
			}
			String querySqlFirst = "SELECT * FROM ( SELECT TOP "+i+" * FROM ( SELECT TOP "+j+" ";
			String querySqlLastTemp = " ORDER BY id ASC ) AS tempt1 ORDER BY id DESC ) as tempt2 ORDER BY id ASC";
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
						orderByDESC = orderByDESC.concat(tempOrderByDESC.replaceAll("DESC", "ASC")+dot);
					}else if(tempOrderBy.toUpperCase().indexOf("ASC")>=0){
						orderByASC2 = orderByASC2.concat(tempOrderBy+dot);
						orderByASC = orderByASC.concat(tempOrderByASC+dot);
						orderByDESC = orderByDESC.concat(tempOrderByDESC.replaceAll("ASC", "DESC")+dot);
					}else{
						orderByASC2 = orderByASC2.concat(tempOrderBy+dot);
						orderByASC = orderByASC.concat(tempOrderByASC+" ASC"+dot);
						orderByDESC = orderByDESC.concat(tempOrderByDESC+" DESC"+dot);
					}
				}
				querySqlLast = querySqlLast.concat(orderByASC2+" ) AS tempt1 ORDER BY ".concat(orderByDESC)+" ) AS tempt2 ORDER BY ".concat(orderByASC));
			}
			String lastSql=querySqlFirst.concat(finalSql.trim().substring(6, finalSql.trim().length()).concat(querySqlLast));
			list = findListBeanByArray(lastSql, clazz, arrayParameters);
		}else{
			list = findListBeanByArray(finalSql, clazz, arrayParameters);
		}
		page.setResult(list);
		return page;
	}

	/**
	 * sqlserver用分页方法,page对象中返回结果为map集合
	 * @param sql语句，不用写分页函数、排序，只需要写获取数据的内容，如：select * from tb_table where id=? and user_name like :?
	 * @param arrayParameters参数集合
	 * @param page分页对象
	 * @return
	 */
	public Page findPageListMap(final String sql,Page page,Object... arrayParameters){
		Assert.notNull(page,"分页信息不能为空");
		Assert.hasText(sql,"sql语句不正确!");
		String finalSql=sql;
		long count=0;
		if (page.isAutoCount()) {
			count=findLongByArray(getCountSql(finalSql), arrayParameters);
			page.setTotalCount((int)count);
		}
		List list=null;
		if (page.isFirstSetted()&&page.isPageSizeSetted()) {
			long i = page.getPageSize();
			long j = page.getFirst()+page.getPageSize();
			if(j>=count){
				i = i-(j-count);
			}
			String querySqlFirst = "SELECT * FROM ( SELECT TOP "+i+" * FROM ( SELECT TOP "+j+" ";
			String querySqlLastTemp = " ORDER BY id ASC ) AS tempt1 ORDER BY id DESC ) as tempt2 ORDER BY id ASC";
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
						orderByDESC = orderByDESC.concat(tempOrderByDESC.replaceAll("DESC", "ASC")+dot);
					}else if(tempOrderBy.toUpperCase().indexOf("ASC")>=0){
						orderByASC2 = orderByASC2.concat(tempOrderBy+dot);
						orderByASC = orderByASC.concat(tempOrderByASC+dot);
						orderByDESC = orderByDESC.concat(tempOrderByDESC.replaceAll("ASC", "DESC")+dot);
					}else{
						orderByASC2 = orderByASC2.concat(tempOrderBy+dot);
						orderByASC = orderByASC.concat(tempOrderByASC+" ASC"+dot);
						orderByDESC = orderByDESC.concat(tempOrderByDESC+" DESC"+dot);
					}
				}
				querySqlLast = querySqlLast.concat(orderByASC+" ) AS tempt1 ORDER BY ".concat(orderByDESC)+" ) AS tempt2 ORDER BY ".concat(orderByASC));
			}
			String lastSql=querySqlFirst.concat(finalSql.trim().substring(6, finalSql.trim().length()).concat(querySqlLast));
			list = findListMapByArray(lastSql,arrayParameters);
		}else{
			list= findListMapByArray(finalSql,arrayParameters);
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
