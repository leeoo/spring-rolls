package com.wisdom.example.dao;

import javax.sql.DataSource;

import com.wisdom.core.dao.GenericDaoFactory;
import com.wisdom.core.dao.JdbcTemplate;
/**
 * 功能描述：
 * 基础通用dao集成类，service可以集成本类进行业务层开发。<br>
 * 将数据层部分功能在业务层实现，可省略开发步骤.<br>
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-10-8</b>
 * <br>创建时间：<b>下午05:36:59</b>
 * <br>文件结构：<b>spring:com.wisdom.example.dao/JdbcGenericSupportDao.java</b>
 */
public abstract class JdbcGenericSupportDao {

	public JdbcTemplate jdbcDao;
	
	@javax.annotation.Resource
	public void setDataSource(DataSource dataSource) {
		jdbcDao = GenericDaoFactory.getJdbcTemplate(dataSource);
	}
	
}
