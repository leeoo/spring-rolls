package com.wisdom.core.dao;

import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.util.Assert;

import com.wisdom.core.utils.BeanUtils;
import com.wisdom.core.utils.Page;

/**
 * 
 * <b>业务说明</b>:<br>
 * 基础jdbc模板实现层
 *<br><b>作  者</b>: Cao.Yang<br>
 * <b>建立日期</b>: 2009-3-13<br>
 * <b>建立时间</b>: 上午02:18:43<br>
 * <b>项目名称</b>: easyapp<br>
 * <b>包及类名</b>: com.wisdom.core.dao.jdbc.BaseJdbcTemplate.java<br>
 */
@SuppressWarnings("unchecked")
public abstract class BaseJdbcTemplate implements JdbcTemplate{
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected org.springframework.jdbc.core.simple.SimpleJdbcTemplate jdbcTemplate;
	protected org.springframework.jdbc.core.simple.SimpleJdbcInsert jdbcInsert;
	protected org.springframework.jdbc.core.simple.SimpleJdbcCall jdbcCall;
	protected DataSource dataSource;
	protected int mappingStrategy=0;
	
	public BaseJdbcTemplate(DataSource dataSource){
		this.dataSource=dataSource;
		jdbcTemplate=new org.springframework.jdbc.core.simple.SimpleJdbcTemplate(dataSource);
	}

	public BaseJdbcTemplate(DataSource dataSource,int mappingStrategy){
		this.dataSource=dataSource;
		jdbcTemplate=new org.springframework.jdbc.core.simple.SimpleJdbcTemplate(dataSource);
		this.mappingStrategy=mappingStrategy;
	}
	
	abstract public Page findPageListBean(final String sql,Class clazz,Page page,Object... arrayParameters);
	
	abstract public Page findPageListMap(final String sql,Page page,Object... arrayParameters);
	
	public void callProcedure(String procedureName){
		jdbcCall=new SimpleJdbcCall(dataSource);
		jdbcCall=jdbcCall.withProcedureName(procedureName);
		jdbcCall.execute();
	}

	public void callProcedure(String procedureName,Map<String,Object> inParameters){
		jdbcCall=new SimpleJdbcCall(dataSource);
		jdbcCall=jdbcCall.withProcedureName(procedureName);
		jdbcCall.execute(inParameters);
	}

	public Map<String,Object> callProcedureQueryOut(String procedureName,Map<String,Object> inParameters){
		jdbcCall=new SimpleJdbcCall(dataSource);
		jdbcCall=jdbcCall.withProcedureName(procedureName);
		return jdbcCall.execute(inParameters);
	}
	
	public List callProcedureQueryListBeans(String procedureName,Map<String,Object> inParameters,Class outBeansType){
		jdbcCall=new SimpleJdbcCall(dataSource);
		jdbcCall=jdbcCall.withProcedureName(procedureName);
		if(outBeansType!=null){
			jdbcCall=jdbcCall.returningResultSet("list_beans", resultBeanMapper(outBeansType));
		}
		return (List)jdbcCall.execute(inParameters).get("list_beans");
	}

	public Connection getConnection() throws Exception{
		return dataSource.getConnection();
	}
	
	public List findListBeanByMap(final String sql,Class clazz,Map parameters){
		try{
			Assert.hasText(sql,"sql语句不正确!");
			Assert.notNull(clazz,"集合中对象类型不能为空!");
			logger.info("SQL:"+sql);
			if(parameters!=null){
				return jdbcTemplate.query(sql, resultBeanMapper(clazz),parameters);
			}else{
				return jdbcTemplate.query(sql, resultBeanMapper(clazz));
			}
		}catch (Exception e) {
			logger.error("not result!");
			return null;
		}
	}
	
	public List findListBean(final String sql,Class clazz){
		try{
			Assert.hasText(sql,"sql语句不正确!");
			Assert.notNull(clazz,"集合中对象类型不能为空!");
			logger.info("SQL:"+sql);
			return jdbcTemplate.query(sql, resultBeanMapper(clazz));
		}catch (Exception e) {
			logger.error("not result!");
			return null;
		}
	}
	
	public List findListBeanByBean(final String sql,Class clazz,Object beanParameters){
		try{
			Assert.hasText(sql,"sql语句不正确!");
			Assert.notNull(clazz,"集合中对象类型不能为空!");
			logger.info("SQL:"+sql);
			if(beanParameters!=null){
				return jdbcTemplate.query(sql, resultBeanMapper(clazz),paramBeanMapper(beanParameters));
			}else{
				return jdbcTemplate.query(sql, resultBeanMapper(clazz));
			}
		}catch (Exception e) {
			logger.error("not result!");
			return null;
		}
	}
	
	public List findListBeanByArray(final String sql,Class clazz,Object... parameters){
		try{
			Assert.hasText(sql,"sql语句不正确!");
			Assert.notNull(clazz,"集合中对象类型不能为空!");
			logger.info("SQL:"+sql);
			if(parameters!=null){
				return jdbcTemplate.query(sql, resultBeanMapper(clazz),parameters);
			}else{
				return jdbcTemplate.query(sql, resultBeanMapper(clazz));
			}
		}catch (Exception e) {
			logger.error("not result!");
			return null;
		}
	}
	
	public Object findUniqueBeanByMap(final String sql,Class clazz,Map parameters){
		try{
			Assert.hasText(sql,"sql语句不正确!");
			Assert.notNull(clazz,"集合中对象类型不能为空!");
			logger.info("SQL:"+sql);
			if(parameters!=null){
				return jdbcTemplate.queryForObject(sql, resultBeanMapper(clazz), parameters);
			}else{
				return jdbcTemplate.queryForLong(sql, resultBeanMapper(clazz));
			}
		}catch (Exception e) {
			logger.error("not result!");
			return null;
		}
	}
	
	public Object findUniqueBeanByArray(final String sql,Class clazz,Object... parameters){
		try{
			Assert.hasText(sql,"sql语句不正确!");
			Assert.notNull(clazz,"集合中对象类型不能为空!");
			logger.info("SQL:"+sql);
			if(parameters!=null){
				return jdbcTemplate.queryForObject(sql, resultBeanMapper(clazz), parameters);
			}else{
				return jdbcTemplate.queryForLong(sql, resultBeanMapper(clazz));
			}
		}catch (Exception e) {
			logger.error("not result!");
			return null;
		}
	}
	
	public long findLongByBean(final String sql,Object beanParameters){
		try{
			Assert.hasText(sql,"sql语句不正确!");
			logger.info("SQL:"+sql);
			if(beanParameters!=null){
				return jdbcTemplate.queryForLong(sql, paramBeanMapper(beanParameters));
			}else{
				return jdbcTemplate.queryForLong(sql);
			}
		}catch (Exception e) {
			logger.error("not result!");
			return 0;
		}
	}

	public long findLongByMap(final String sql,Map parameters){
		try{
			Assert.hasText(sql,"sql语句不正确!");
			logger.info("SQL:"+sql);
			if(parameters!=null){
				return jdbcTemplate.queryForLong(sql, parameters);
			}else{
				return jdbcTemplate.queryForLong(sql);
			}
		}catch (Exception e) {
			logger.error("not result!");
			return 0;
		}
	}
	

	public long findLongByArray(final String sql,Object... parameters){
		try{
			Assert.hasText(sql,"sql语句不正确!");
			logger.info("SQL:"+sql);
			if(parameters!=null){
				return jdbcTemplate.queryForLong(sql, parameters);
			}else{
				return jdbcTemplate.queryForLong(sql);
			}
		}catch (Exception e) {
			logger.error("not result!");
			return 0;
		}
	}
	
	public Map findUniqueMapByMap(final String sql,Map parameters){
		try{
			Assert.hasText(sql,"sql语句不正确!");
			logger.info("SQL:"+sql);
			if(parameters!=null){
				return jdbcTemplate.queryForMap(sql, parameters);
			}else{
				return jdbcTemplate.queryForMap(sql);
			}
		}catch (Exception e) {
			logger.error("not result!");
			return null;
		}
	}
	
	public Map findUniqueMapByArray(final String sql,Object... parameters){
		try{
			Assert.hasText(sql,"sql语句不正确!");
			logger.info("SQL:"+sql);
			if(parameters!=null){
				return jdbcTemplate.queryForMap(sql, parameters);
			}else{
				return jdbcTemplate.queryForMap(sql);
			}
		}catch (Exception e) {
			logger.error("not result!");
			return null;
		}
	}
	
	public List<Map<String,Object>> findListMapByMap(final String sql,Map parameters){
		try{
			Assert.hasText(sql,"sql语句不正确!");
			logger.info("SQL:"+sql);
			if(parameters!=null){
				return jdbcTemplate.queryForList(sql, parameters);
			}else{
				return jdbcTemplate.queryForList(sql);
			}
		}catch (Exception e) {
			logger.error("not result!");
			return null;
		}
	}

	public List<Map<String,Object>> findListMapByArray(final String sql,Object... parameters){
		try{
			Assert.hasText(sql,"sql语句不正确!");
			logger.info("SQL:"+sql);
			if(parameters!=null){
				return jdbcTemplate.queryForList(sql, parameters);
			}else{
				return jdbcTemplate.queryForList(sql);
			}
		}catch (Exception e) {
			logger.error("not result!");
			return null;
		}
	}
	
	public int executeBean(final String sql,Object bean)throws Exception{
		Assert.hasText(sql,"sql语句不正确!");
		logger.info("SQL:"+sql);
		if(bean!=null){
			return jdbcTemplate.update(sql, paramBeanMapper(bean));
		}else{
			return jdbcTemplate.update(sql);
		}
	}

	public int executeMap(final String sql,Map parameters)throws Exception{
		Assert.hasText(sql,"sql语句不正确!");
		logger.info("SQL:"+sql);
		if(parameters!=null){
			return jdbcTemplate.update(sql, parameters);
		}else{
			return jdbcTemplate.update(sql);
		}
	}
	
	public int executeArray(final String sql,Object... parameters)throws Exception{
		Assert.hasText(sql,"sql语句不正确!");
		logger.info("SQL:"+sql);
		if(parameters!=null){
			return jdbcTemplate.update(sql, parameters);
		}else{
			return jdbcTemplate.update(sql);
		}
	}
	
	public int[] executeBatchByBeans(final String sql,Collection<Object> beans)throws Exception{
		Assert.hasText(sql,"sql语句不正确!");
		logger.info("SQL:"+sql);
		if(beans!=null&&!beans.isEmpty()){
			SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(beans.toArray());
			return jdbcTemplate.batchUpdate(sql, batch);
		}
		return null;
	}
	
	public int[] executeBatchByBeans(final String sql,Object... beans)throws Exception{
		Assert.hasText(sql,"sql语句不正确!");
		logger.info("SQL:"+sql);
		if(beans!=null&&beans.length>0){
			SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(beans);
			return jdbcTemplate.batchUpdate(sql, batch);
		}
		return null;
	}

	public int[] executeBatchByMaps(final String sql,Collection<Map> maps)throws Exception{
		Assert.hasText(sql,"sql语句不正确!");
		logger.info("SQL:"+sql);
		if(maps!=null&&!maps.isEmpty()){
			Map[] mapParameters=(Map[]) maps.toArray(new Map[maps.size()]);
			SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(mapParameters);
			return jdbcTemplate.batchUpdate(sql, batch);
		}
		return null;
	}
	
	public int[] executeBatchByMaps(final String sql,Map... maps)throws Exception{
		Assert.hasText(sql,"sql语句不正确!");
		logger.info("SQL:"+sql);
		if(maps!=null&&maps.length>0){
			SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(maps);
			return jdbcTemplate.batchUpdate(sql, batch);
		}
		return null;
	}
	
	public long insertBeanGetGeneratedKey(String tableName,String keyName,Object bean) throws Exception{
		Assert.notNull(tableName,"表名不能为空!");
		Assert.notNull(keyName,"自增字段名称不能为空!");
		Assert.notNull(bean,"对象bean不能为空!");
		jdbcInsert=new org.springframework.jdbc.core.simple.SimpleJdbcInsert(dataSource).withTableName(tableName).usingGeneratedKeyColumns(keyName);
		Number number=jdbcInsert.executeAndReturnKey(paramBeanMapper(bean));
		logger.info("generated-key:"+number);
		return number.longValue();
	}

	public long insertMapGetGeneratedKey(String tableName,String keyName,Map map) throws Exception{
		Assert.notNull(tableName,"表名不能为空!");
		Assert.notNull(keyName,"自增字段名称不能为空!");
		Assert.notNull(map,"对象map不能为空!");
		jdbcInsert=new org.springframework.jdbc.core.simple.SimpleJdbcInsert(dataSource).withTableName(tableName).usingGeneratedKeyColumns(keyName);
		Number number=jdbcInsert.executeAndReturnKey(map);
		logger.info("generated-key:"+number);
		return number.longValue();
	}
	

	protected RowMapper resultBeanMapper(Class clazz) {
		return ParameterizedBeanPropertyRowMapper.newInstance(clazz);
	}
	
	protected Map paramBeanMapper_(Object bean)throws Exception{
		if(mappingStrategy==Bean2MapStrategy.PROPERTY_TO_LOWERCASE){
			return BeanUtils.describeByDbField(bean, true, false);
		}else if(mappingStrategy==Bean2MapStrategy.PROPERTY_TO_LOWERCASE_Underline){
			return BeanUtils.describeByDbField(bean, true, true);
		}else if(mappingStrategy==Bean2MapStrategy.PROPERTY_TO_UPPERCASE){
			return BeanUtils.describeByDbField(bean, false, false);
		}else if(mappingStrategy==Bean2MapStrategy.PROPERTY_TO_UPPERCASE_Underline){
			return BeanUtils.describeByDbField(bean, false, true);
		}else if(mappingStrategy==Bean2MapStrategy.PROPERTY_Unchanged){
			return BeanUtils.describe(bean);
		}
		return new HashMap();
	}
	
	protected BeanPropertySqlParameterSource paramBeanMapper(Object object) {
		return new BeanPropertySqlParameterSource(object);
	}
}
