package com.wisdom.core.orm;

import java.io.Serializable;

import javax.sql.DataSource;

import org.springframework.util.Assert;

import com.wisdom.core.annotation.Sharding;
import com.wisdom.core.annotation.Sharding.MODE;
import com.wisdom.core.dao.JdbcTemplate;
/**
 * 功 能 描 述:<br>
 * 带有表拆分功能的通用DAO，由于多表操作，默认屏蔽掉影响性能的方法。
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-1-25下午03:34:21
 * <br>项 目 信 息:wisdom.3.0:com.wisdom.core.orm.ShardingOrmGenericDao.java
 */
public class ShardingOrmGenericDao <T, PK extends Serializable>{
	
	private SimpleOrmGenericDao<T,PK> genericDao;
	
	private Class<T> clazz;
	
	private MODE mode;
	
	private String originalTableName;//原始表名
	
	private String shardingTableName;//拆分后表名
	
	/**
	 * 初始化方法
	 */
	private void init(){
		originalTableName = genericDao.getTableName();
		validation();
		Sharding sharding = this.clazz.getAnnotation(Sharding.class);
		mode = sharding.shardingMode();
	}
	
	/**
	 * 分表dao构造方法
	 * @param dataSource
	 * @param clazz
	 */
	public ShardingOrmGenericDao(DataSource dataSource,Class<T> clazz){
		genericDao = new SimpleOrmGenericDao<T, PK>(dataSource, clazz);
		this.clazz = clazz;
		init();
	}
	
	/**
	 * 分表dao构造方法
	 * @param jdbcTemplate
	 * @param clazz
	 */
	public ShardingOrmGenericDao(JdbcTemplate jdbcTemplate,Class<T> clazz){
		genericDao = new SimpleOrmGenericDao<T, PK>(jdbcTemplate, clazz);
		this.clazz = clazz;
		init();
	}
	
	/**
	 * 验证该dao是否可以使用.
	 */
	private void validation(){
		Assert.notNull(originalTableName,"tableName表名不能为空!请使用@SimpleEntity注解加入实体");
	}
	
}
