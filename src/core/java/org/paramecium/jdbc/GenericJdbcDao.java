package org.paramecium.jdbc;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import org.paramecium.jdbc.datasource.MultiDataSourceFactory;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;
import org.paramecium.transaction.TransactionManager;

/**
 * 功能描述(Description):<br><b>
 * JDBC数据访问通用工具
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-4-1下午08:24:01</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.paramecium.jdbc.GenericJdbcDao.java</b>
 */
public class GenericJdbcDao {
	
	private final static Log logger = LoggerFactory.getLogger();
	
	private JdbcTemplate jdbcTemplate;
	
	private String dataSourceName;
	
	/**
	 * 默认数据源，构造方法会自动加载事务线程
	 * @param connection
	 */
	public GenericJdbcDao(){
		this(MultiDataSourceFactory.defaultDataSourceName);
	}
	
	/**
	 * 默认构造方法会自动加载事务线程
	 * @param connection
	 */
	public GenericJdbcDao(final String dataSourceName){
		try {
			this.dataSourceName = dataSourceName;
			this.jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate(dataSourceName);
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	/**
	 * 设置多表查询，MAP结果集将变成[表名/别名].[字段名/别名],默认FALSE
	 * @param isManyTable
	 */
	public void setManyTable(Boolean isManyTable){
		this.jdbcTemplate.setManyTable(isManyTable);
	}
	
	/**
	 * 执行无参数selectSQL语句,数据以Map装载
	 * @param sql
	 * @return 结果集列表
	 * @throws SQLException
	 */
	public Collection<Map<String, Object>> query(final String sql){
		try{
			logger.debug(sql);
			return this.jdbcTemplate.query(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql);
		}catch (Exception e) {
			logger.error(e);
		}
		return null;
	}
	
	/**
	 * 执行无参数selectSQL语句，数据以bean装载
	 * @param sql
	 * @param clazz装载数据bean类型
	 * @return 结果集列表
	 * @throws SQLException
	 */
	public Collection<?> query(final String sql,Class<?> clazz){
		try{
			logger.debug(sql);
			return this.jdbcTemplate.query(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql, clazz);
		}catch (Exception e) {
			logger.error(e);
		}
		return null;
	}
	
	/**
	 * 带参数selectSQL语句,参数顺序应与?对应,例如：select * from table where id>? and id<?
	 * @param sql
	 * @param arrayParams参数数组
	 * @return
	 * @throws SQLException
	 */
	public Collection<Map<String, Object>> queryByArray(final String sql,Object... arrayParams){
		try{
			logger.debug(sql);
			return this.jdbcTemplate.queryByArray(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql, arrayParams);
		}catch (Exception e) {
			logger.error(e);
		}
		return null;
	}
	
	/**
	 * 带参数selectSQL语句,数据以bean装载,参数顺序应与?对应,例如：select * from table where id>? and id<?
	 * @param sql
	 * @param clazz装载数据bean类型
	 * @param arrayParams参数数组
	 * @return
	 * @throws SQLException
	 */
	public Collection<?> queryByArray(final String sql,Class<?> clazz,Object... arrayParams){
		try{
			logger.debug(sql);
			return this.jdbcTemplate.queryByArray(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql, clazz, arrayParams);
		}catch (Exception e) {
			logger.error(e);
		}
		return null;
	}
	
	/**
	 * 带参数selectSQL语句，:冒号后变量对应map中的key名称，区分大小写,例如：select * from table where id>:id and name=:name
	 * @param sql
	 * @param mapParams
	 * @return
	 * @throws SQLException
	 */
	public Collection<Map<String, Object>> queryByMap(final String sql,Map<String, Object> mapParams){
		try{
			logger.debug(sql);
			return this.jdbcTemplate.queryByMap(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql, mapParams);
		}catch (Exception e) {
			logger.error(e);
		}
		return null;
	}
	
	/**
	 *  带参数selectSQL语句，:冒号后变量对应map中的key名称，区分大小写,例如：select * from table where id>:id and name=:name
	 * @param sql
	 * @param clazz
	 * @param mapParams
	 * @return
	 * @throws SQLException
	 */
	public Collection<?> queryByMap(final String sql,Class<?> clazz,Map<String, Object> mapParams) {
		try{
			logger.debug(sql);
			return this.jdbcTemplate.queryByMap(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql, clazz, mapParams);
		}catch (Exception e) {
			logger.error(e);
		}
		return null;
	}
	
	/**
	 * 带参数selectSQL语句，:冒号后变量对应bean中的属性名称，区分大小写,例如：select * from table where id>:id and name=:name
	 * @param sql
	 * @param clazz
	 * @param beanParams
	 * @return
	 * @throws SQLException
	 */
	public Collection<?> queryByBean(final String sql,Class<?> clazz,Object beanParams) {
		try{
			logger.debug(sql);
			return this.jdbcTemplate.queryByBean(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql, clazz, beanParams);
		}catch (Exception e) {
			logger.error(e);
		}
		return null;
	}
	
	/**
	 * 执行无参数selectSQL语句，返回唯一结果
	 * @param sql
	 * @return 唯一结果集
	 * @throws SQLException
	 */
	public Map<String, Object> queryUnique(final String sql) {
		try{
			logger.debug(sql);
			return this.jdbcTemplate.queryUnique(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql);
		}catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	/**
	 * 执行无参数selectSQL语句，返回唯一结果
	 * @param sql
	 * @return 唯一结果集
	 * @throws SQLException
	 */
	public Object queryUnique(final String sql,Class<?> clazz) {
		try{
			logger.debug(sql);
			return this.jdbcTemplate.queryUnique(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql, clazz);
		}catch (Exception e) {
			logger.error(e);
		}
		return null;
	}
	
	/**
	 * 执行带参数参数selectSQL语句，返回唯一结果,参数顺序应与?对应,例如：select * from table where id>? and id<?
	 * @param sql
	 * @return 唯一结果集
	 * @throws SQLException
	 */
	public Map<String, Object> queryUniqueByArray(final String sql,Object... arrayParams) {
		try{
			logger.debug(sql);
			return this.jdbcTemplate.queryUniqueByArray(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql, arrayParams);
		}catch (Exception e) {
			logger.error(e);
		}
		return null;
	}
	
	/**
	 * 执行带参数参数selectSQL语句，返回唯一结果,参数顺序应与?对应,例如：select * from table where id>? and id<?
	 * @param sql
	 * @param clazz
	 * @param arrayParams
	 * @return
	 * @throws SQLException
	 */
	public Object queryUniqueByArray(final String sql,Class<?> clazz,Object... arrayParams) {
		try{
			logger.debug(sql);
			return this.jdbcTemplate.queryUniqueByArray(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql, clazz, arrayParams);
		}catch (Exception e) {
			logger.error(e);
		}
		return null;
	}
	
	/**
	 * 执行带参数参数selectSQL语句，返回唯一结果,参数顺序应与?对应,例如：select * from table where id>? and id<?
	 * @param sql
	 * @param clazz
	 * @param arrayParams
	 * @return
	 * @throws SQLException
	 */
	public Object queryUniqueByBean(final String sql,Class<?> clazz,Object beanParams) {
		try{
			logger.debug(sql);
			return this.jdbcTemplate.queryUniqueByBean(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql, clazz, beanParams);
		}catch (Exception e) {
			logger.error(e);
		}
		return null;
	}
	
	/**
	 * 执行带参数参数selectSQL语句，返回唯一结果.:冒号后变量对应map中的key名称，区分大小写,例如：select * from table where id>:id and name=:name
	 * @param sql
	 * @return 唯一结果集
	 * @throws SQLException
	 */
	public Map<String, Object> queryUniqueByMap(final String sql,Map<String, Object> mapParams) {
		try{
			logger.debug(sql);
			return this.jdbcTemplate.queryUniqueByMap(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql, mapParams);
		}catch (Exception e) {
			logger.error(e);
		}
		return null;
	}
	
	/**
	 * 获得唯一值，如select count(1) from table;
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public Object queryUniqueColumnValue(final String sql) {
		try{
			logger.debug(sql);
			return this.jdbcTemplate.queryUniqueColumnValue(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql);
		}catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	/**
	 * 获得唯一值，如select count(1) from table where date>? and date<?;
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public Object queryUniqueColumnValueByArray(final String sql,Object... arrayParams) {
		try{
			logger.debug(sql);
			return this.jdbcTemplate.queryUniqueColumnValueByArray(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql, arrayParams);
		}catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	/**
	 * 获得唯一值，如select count(1) from table where date>:startDate and date<:entDate;
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public Object queryUniqueColumnValueByMap(final String sql,Map<String, Object> mapParams){
		try{
			logger.debug(sql);
			return this.jdbcTemplate.queryUniqueColumnValueByMap(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql, mapParams);
		}catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	/**
	 * 获得唯一值，如select count(1) from table where date>:startDate and date<:entDate;
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public Object queryUniqueColumnValueByBean(final String sql,Object beanParams) {
		try{
			logger.debug(sql);
			return this.jdbcTemplate.queryUniqueColumnValueByBean(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql, beanParams);
		}catch (Exception e) {
			logger.error(e);
		}
		return null;
	}
	
	
	/**
	 * 执行无参数DDL的SQL语句，如：CREATE,DROP,ALTER,GRANT等语句
	 * @param sql
	 * @throws SQLException
	 */
	public boolean executeDDL(final String sql) throws SQLException {
		try{
			logger.debug(sql);
			return this.jdbcTemplate.executeDDL(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql);
		}catch (Exception e) {
			logger.error(e);
			throw new SQLException(e);
		}
	}
	
	/**
	 * 执行除select以外的DML语句,如：INSERT,UPDATE,DELETE语句
	 * @param sql
	 * @return 受影响的条目数
	 * @throws SQLException
	 */
	public int executeDML(final String sql) throws SQLException {
		try{
			logger.debug(sql);
			return this.jdbcTemplate.executeDML(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql);
		}catch (Exception e) {
			logger.error(e);
			throw new SQLException(e);
		}
	}
	
	/**
	 * 对带有自增列的表插入INSERT操作，并获得自增值
	 * @param sql
	 * @return 受影响的条目数
	 * @throws SQLException
	 */
	public Number insertGetGeneratedKey(final String sql) throws SQLException {
		try{
			logger.debug(sql);
			return this.jdbcTemplate.insertGetGeneratedKey(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql);
		}catch (Exception e) {
			logger.error(e);
			throw new SQLException(e);
		}
	}
	
	/**
	 * 带参数执行除select以外的DML语句,如：INSERT,UPDATE,DELETE语句,例如:INSERT INTO table(id,name) VALUES(?,?)
	 * @param sql
	 * @return 受影响的条目数
	 * @throws SQLException
	 */
	public int executeDMLByArray(final String sql,Object... arrayParams) throws SQLException {
		try{
			logger.debug(sql);
			return this.jdbcTemplate.executeDMLByArray(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql, arrayParams);
		}catch (Exception e) {
			logger.error(e);
			throw new SQLException(e);
		}
	}
	
	/**
	 * 对带有自增列的表插入INSERT操作，并获得自增值
	 * @param sql
	 * @return 受影响的条目数
	 * @throws SQLException
	 */
	public Number insertGetGeneratedKeyByArray(final String sql,Object... arrayParams) throws SQLException{
		try{
			logger.debug(sql);
			return this.jdbcTemplate.insertGetGeneratedKeyByArray(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql, arrayParams);
		}catch (Exception e) {
			logger.error(e);
			throw new SQLException(e);
		}
	}
	
	/**
	 * 带参数执行除select以外的DML语句,如：INSERT,UPDATE,DELETE语句,参数形式为:冒号,例如:INSERT INTO table(id,name) VALUES(:id,:name)
	 * @param sql
	 * @return 受影响的条目数
	 * @throws SQLException
	 */
	public int executeDMLByMap(final String sql,Map<String, Object> mapParams) throws SQLException {
		try{
			logger.debug(sql);
			return this.jdbcTemplate.executeDMLByMap(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql, mapParams);
		}catch (Exception e) {
			logger.error(e);
			throw new SQLException(e);
		}
	}
	
	/**
	 * 对带有自增列的表插入INSERT操作，并获得自增值
	 * @param sql
	 * @return 受影响的条目数
	 * @throws SQLException
	 */
	public Number insertGetGeneratedKeyByMap(final String sql,Map<String, Object> mapParams) throws SQLException {
		try{
			logger.debug(sql);
			return this.jdbcTemplate.insertGetGeneratedKeyByMap(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql, mapParams);
		}catch (Exception e) {
			logger.error(e);
			throw new SQLException(e);
		}
	}
	
	/**
	 * 带参数执行除select以外的DML语句,如：INSERT,UPDATE,DELETE语句,参数形式为:冒号,例如:INSERT INTO table(id,name) VALUES(:id,:name)
	 * @param sql
	 * @return 受影响的条目数
	 * @throws SQLException
	 */
	public int executeDMLByBean(final String sql,Object bean) throws SQLException {
		try{
			logger.debug(sql);
			return this.jdbcTemplate.executeDMLByBean(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql, bean);
		}catch (Exception e) {
			logger.error(e);
			throw new SQLException(e);
		}
	}
	
	/**
	 * 对带有自增列的表插入INSERT操作，并获得自增值
	 * @param sql
	 * @return 受影响的条目数
	 * @throws SQLException
	 */
	public Number insertGetGeneratedKeyByBean(final String sql,Object bean) throws SQLException {
		try{
			logger.debug(sql);
			return this.jdbcTemplate.insertGetGeneratedKeyByBean(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql, bean);
		}catch (Exception e) {
			logger.error(e);
			throw new SQLException(e);
		}
	}
	
	/**
	 * 批量执行除select以外的DML语句,如：INSERT,UPDATE,DELETE语句
	 * @param sqls数组
	 * @return 受影响的条目数组
	 * @throws SQLException
	 */
	public int[] executeBatchDML(final String... sqls) throws SQLException {
		try{
			logger.debug(sqls[0]);
			return this.jdbcTemplate.executeBatchDML(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sqls);
		}catch (Exception e) {
			logger.error(e);
			throw new SQLException(e);
		}
	}
	
	/**
	 * 批量执行除select以外的DML语句,如：INSERT,UPDATE,DELETE语句
	 * @param sqls集合
	 * @return 受影响的条目数组
	 * @throws SQLException
	 */
	public int[] executeBatchDML(final Collection<String> sqls) throws SQLException {
		try{
			logger.debug(sqls.iterator().next());
			return this.jdbcTemplate.executeBatchDML(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sqls);
		}catch (Exception e) {
			logger.error(e);
			throw new SQLException(e);
		}
	}
	
	/**
	 * 批量执行带参数除select以外的DML语句,如：INSERT,UPDATE,DELETE语句
	 * @param sql
	 * @param mapParamsList
	 * @return
	 * @throws SQLException
	 */
	public int[] executeBatchDMLByMaps(final String sql,Collection<Map<String, Object>> mapParamsList) throws SQLException {
		try{
			logger.debug(sql);
			return this.jdbcTemplate.executeBatchDMLByMaps(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql, mapParamsList);
		}catch (Exception e) {
			logger.error(e);
			throw new SQLException(e);
		}
	}
	
	/**
	 * 批量执行带参数除select以外的DML语句,如：INSERT,UPDATE,DELETE语句
	 * @param sql
	 * @param beanParamsList
	 * @return
	 * @throws SQLException
	 */
	public int[] executeBatchDMLByBeans(final String sql,Collection<?> beanParamsList) throws SQLException {
		try{
			logger.debug(sql);
			return this.jdbcTemplate.executeBatchDMLByBeans(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql, beanParamsList);
		}catch (Exception e) {
			logger.error(e);
			throw new SQLException(e);
		}
	}
	
	/**
	 * 调用存储过程;例如：" {call 过程名}"
	 * @param sql
	 * @throws SQLException
	 */
	public void call(final String sql) throws SQLException {
		try{
			logger.debug(sql);
			this.jdbcTemplate.call(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql);
		}catch (Exception e) {
			logger.error(e);
			throw new SQLException(e);
		}
	}

	
	/**
	 * 调用存储过程;例如：" {call 过程名(?,?)}"
	 * @param sql
	 * @param inParams IN参数集合=?问号的顺序
	 * @throws SQLException
	 */
	public void call(final String sql,Object... inParams) throws SQLException {
		try{
			logger.debug(sql);
			this.jdbcTemplate.call(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql, inParams);
		}catch (Exception e) {
			logger.error(e);
			throw new SQLException(e);
		}
	}
	
	/**
	 * 调用存储过程;例如：" {? = call 过程名(?,?)}"
	 * @param sql
	 * @param outParams OUT参数集合=?问号的顺序,详见 java.sql.Types;
	 * @throws SQLException
	 */
	public Collection<?> call(final String sql,int... outParams) throws SQLException {
		try{
			logger.debug(sql);
			return this.jdbcTemplate.call(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql, outParams);
		}catch (Exception e) {
			logger.error(e);
			throw new SQLException(e);
		}
	}

	/**
	 * 调用存储过程;例如：" {? = call 过程名(?,?)}"
	 * @param sql
	 * @param outParams OUT参数集合=?问号的顺序,详见 java.sql.Types;
	 * @param inParams IN参数集合=?问号的顺序
	 * @throws SQLException
	 */
	public Map<String,Object> call(final String sql,Map<String,Object> inParams,Map<String,Integer> outParams) throws SQLException {
		try{
			logger.debug(sql);
			return this.jdbcTemplate.call(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql, inParams, outParams);
		}catch (Exception e) {
			logger.error(e);
			throw new SQLException(e);
		}
	}
	
	/**
	 * 获得刚刚产生的自增主键,用于insert产生自增值的获取
	 * @return
	 * @throws SQLException
	 */
	public Number getAutoGeneratedKey() {
		try{
			return this.jdbcTemplate.getAutoGeneratedKey(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection());
		}catch (SQLException e) {
			logger.error(e);
		}
		return null;
	}
	
	/**
	 * 通过sql获得刚刚产生的自增主键,用于insert产生自增值的获取，对oracle等不支持自增列的数据库提供支持,无用可空实现
	 * @param sql语句
	 * @return
	 * @throws SQLException
	 */
	public Number getAutoGeneratedKey(final String sql) throws SQLException {
		try{
			logger.debug(sql);
			return this.jdbcTemplate.getAutoGeneratedKey(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql);
		}catch (SQLException e) {
			logger.error(e);
		}
		return null;
	}
	
	/**
	 * 通用用分页方法,page对象中返回结果为bean集合
	 * @param sql语句，不用写分页函数、排序，只需要写获取数据的内容，如：select * from tb_table where id=? and user_name like ?
	 * @param clazz返回对象类型
	 * @param arrayParameters参数集合
	 * @param page分页对象
	 * @return
	 */
	public Page queryPageBeansByArray(final String sql,Class<?> clazz,Page page,Object... arrayParameters) {
		try{
			logger.debug(sql);
			return this.jdbcTemplate.queryPageBeansByArray(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql, clazz, page, arrayParameters);
		}catch (SQLException e) {
			logger.error(e);
		}
		return null;
	}

	/**
	 * 通用用分页方法,page对象中返回结果为bean集合
	 * @param sql语句，不用写分页函数、排序，只需要写获取数据的内容，如：select * from tb_table where id=:userId and user_name =:userName
	 * @param clazz返回对象类型
	 * @param page分页对象
	 * @param beanParameters参数bean
	 * @return
	 */
	public Page queryPageBeansByBean(final String sql,Class<?> clazz,Page page,Object beanParameters) {
		try{
			logger.debug(sql);
			return this.jdbcTemplate.queryPageBeansByBean(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql, clazz, page, beanParameters);
		}catch (SQLException e) {
			logger.error(e);
		}
		return null;
	}
	
	/**
	 * 通用用分页方法,page对象中返回结果为bean集合
	 * @param sql语句，不用写分页函数、排序，只需要写获取数据的内容，如：select * from tb_table where id=:user_id and user_name =:user_name
	 * @param clazz返回对象类型
	 * @param page分页对象
	 * @param mapParameters参数map
	 * @return
	 */
	public Page queryPageBeansByMap(final String sql,Class<?> clazz,Page page,Map<String,Object> mapParameters) {
		try{
			logger.debug(sql);
			return this.jdbcTemplate.queryPageBeansByMap(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql, clazz, page, mapParameters);
		}catch (SQLException e) {
			logger.error(e);
		}
		return null;
	}

	/**
	 * 通用用分页方法,page对象中返回结果为map集合
	 * @param sql语句，不用写分页函数、排序，只需要写获取数据的内容，如：select * from tb_table where id=? and user_name like ?
	 * @param arrayParameters参数集合
	 * @param page分页对象
	 * @return
	 */
	public Page queryPageMapsByArray(final String sql,Page page,Object... arrayParameters) {
		try{
			logger.debug(sql);
			return this.jdbcTemplate.queryPageMapsByArray(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql, page, arrayParameters);
		}catch (SQLException e) {
			logger.error(e);
		}
		return null;
	}
	
	/**
	 * 通用用分页方法,page对象中返回结果为map集合
	 * @param sql语句，不用写分页函数、排序，只需要写获取数据的内容，如：select * from tb_table where id=:user_id and user_name like :user_name
	 * @param page分页对象
	 * @param mapParameters参数map
	 * @return
	 */
	public Page queryPageMapsByMap(final String sql,Page page,Map<String,Object> mapParameters) {
		try{
			logger.debug(sql);
			return this.jdbcTemplate.queryPageMapsByMap(TransactionManager.getCurrentTransaction(this.dataSourceName).getCurrentConnection(),sql, page, mapParameters);
		}catch (SQLException e) {
			logger.error(e);
		}
		return null;
	}
	
}
