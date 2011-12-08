package org.paramecium.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.paramecium.commons.BeanUtils;
import org.paramecium.commons.JdbcUtils;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;
import org.paramecium.log.system.CollectorFactory;

/**
 * 功能描述(Description):<br><b>
 * jdbc基础模板
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-4-3下午09:33:42</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.paramecium.jdbc.BaseJdbcTemplate.java</b>
 */
public abstract class BaseJdbcTemplate implements JdbcTemplate{
	
	private final static Log logger = LoggerFactory.getLogger();

	private Boolean isManyTable = new Boolean(false);

	/**
	 * 执行无参数selectSQL语句,数据以Map装载
	 * @param sql
	 * @return 结果集列表
	 * @throws SQLException
	 */
	public Collection<Map<String, Object>> query(final Connection connection,final String sql) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		Collection<Map<String, Object>> collection = JdbcUtils.getCollection(resultSet,this.isManyTable);
		logger.debug(JdbcUtils.getNativeDMLSql(sql));
		CollectorFactory.getJdbcCollector().put(sql);//放入日志缓存
		JdbcUtils.close(statement, resultSet);
		return collection;
	}
	
	/**
	 * 执行无参数selectSQL语句，数据以bean装载
	 * @param sql
	 * @param clazz装载数据bean类型
	 * @return 结果集列表
	 * @throws SQLException
	 */
	public Collection<?> query(final Connection connection,final String sql,Class<?> clazz) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		Collection<?> collection = JdbcUtils.getCollection(resultSet,clazz);
		logger.debug(JdbcUtils.getNativeDMLSql(sql));
		CollectorFactory.getJdbcCollector().put(sql);//放入日志缓存
		JdbcUtils.close(statement, resultSet);
		return collection;
	}
	
	/**
	 * 带参数selectSQL语句,参数顺序应与?对应,例如：select * from table where id>? and id<?
	 * @param sql
	 * @param arrayParams参数数组
	 * @return
	 * @throws SQLException
	 */
	public Collection<Map<String, Object>> queryByArray(final Connection connection,final String sql,Object... arrayParams) throws SQLException {
		if(arrayParams==null||arrayParams.length==0){
			return query(connection,sql);
		}
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		int i = 1;
		for(Object param:arrayParams){
			preparedStatement.setObject((i++), param);
		}
		ResultSet resultSet = preparedStatement.executeQuery();
		Collection<Map<String, Object>> collection = JdbcUtils.getCollection(resultSet,this.isManyTable);
		logger.debug(JdbcUtils.getNativeDMLSql(preparedStatement.toString()));
		CollectorFactory.getJdbcCollector().put(preparedStatement.toString());//放入日志缓存
		JdbcUtils.close(preparedStatement, resultSet);
		return collection;
	}
	
	/**
	 * 带参数selectSQL语句,数据以bean装载,参数顺序应与?对应,例如：select * from table where id>? and id<?
	 * @param sql
	 * @param clazz装载数据bean类型
	 * @param arrayParams参数数组
	 * @return
	 * @throws SQLException
	 */
	public Collection<?> queryByArray(final Connection connection,final String sql,Class<?> clazz,Object... arrayParams) throws SQLException {
		if(arrayParams==null||arrayParams.length==0){
			return query(connection,sql,clazz);
		}
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		int i = 1;
		for(Object param:arrayParams){
			preparedStatement.setObject((i++), param);
		}
		ResultSet resultSet = preparedStatement.executeQuery();
		Collection<?> collection = JdbcUtils.getCollection(resultSet,clazz);
		logger.debug(JdbcUtils.getNativeDMLSql(preparedStatement.toString()));
		CollectorFactory.getJdbcCollector().put(preparedStatement.toString());//放入日志缓存
		JdbcUtils.close(preparedStatement, resultSet);
		return collection;
	}
	
	/**
	 * 带参数selectSQL语句，:冒号后变量对应map中的key名称，区分大小写,例如：select * from table where id>:id and name=:name
	 * @param sql
	 * @param mapParams
	 * @return
	 * @throws SQLException
	 */
	public Collection<Map<String, Object>> queryByMap(final Connection connection,final String sql,Map<String, Object> mapParams) throws SQLException {
		if(mapParams==null||mapParams.size()==0){
			return query(connection,sql);
		}
		Map<Integer, Object> value = JdbcUtils.getPreparedStatementSql(sql,mapParams);
		String preparedSql = (String) value.get(-19820206);
		value.remove(-19820206);
		PreparedStatement preparedStatement = connection.prepareStatement(preparedSql);
		for(Entry<Integer, Object> entry : value.entrySet()){
			preparedStatement.setObject(entry.getKey(), entry.getValue());
		}
		ResultSet resultSet = preparedStatement.executeQuery();
		Collection<Map<String, Object>> collection = JdbcUtils.getCollection(resultSet,this.isManyTable);
		logger.debug(JdbcUtils.getNativeDMLSql(preparedStatement.toString()));
		CollectorFactory.getJdbcCollector().put(preparedStatement.toString());//放入日志缓存
		JdbcUtils.close(preparedStatement, resultSet);
		return collection;
	}
	
	/**
	 *  带参数selectSQL语句，:冒号后变量对应map中的key名称，区分大小写,例如：select * from table where id>:id and name=:name
	 * @param sql
	 * @param clazz
	 * @param mapParams
	 * @return
	 * @throws SQLException
	 */
	public Collection<?> queryByMap(final Connection connection,final String sql,Class<?> clazz,Map<String, Object> mapParams) throws SQLException {
		if(mapParams==null||mapParams.size()==0){
			return query(connection,sql,clazz);
		}
		Map<Integer, Object> value = JdbcUtils.getPreparedStatementSql(sql,mapParams);
		String preparedSql = (String) value.get(-19820206);
		value.remove(-19820206);
		PreparedStatement preparedStatement = connection.prepareStatement(preparedSql);
		for(Entry<Integer, Object> entry : value.entrySet()){
			preparedStatement.setObject(entry.getKey(), entry.getValue());
		}
		ResultSet resultSet = preparedStatement.executeQuery();
		Collection<?> collection = JdbcUtils.getCollection(resultSet,clazz);
		logger.debug(JdbcUtils.getNativeDMLSql(preparedStatement.toString()));
		CollectorFactory.getJdbcCollector().put(preparedStatement.toString());//放入日志缓存
		JdbcUtils.close(preparedStatement, resultSet);
		return collection;
	}
	
	/**
	 * 带参数selectSQL语句，:冒号后变量对应bean中的属性名称，区分大小写,例如：select * from table where id>:id and name=:name
	 * @param sql
	 * @param clazz
	 * @param beanParams
	 * @return
	 * @throws SQLException
	 */
	public Collection<?> queryByBean(final Connection connection,final String sql,Class<?> clazz,Object beanParams) throws SQLException {
		if(beanParams==null){
			return query(connection,sql,clazz);
		}
		Map<String, Object> mapParams = BeanUtils.bean2Map(beanParams);
		Map<Integer, Object> value = JdbcUtils.getPreparedStatementSql(sql,mapParams);
		String preparedSql = (String) value.get(-19820206);
		value.remove(-19820206);
		PreparedStatement preparedStatement = connection.prepareStatement(preparedSql);
		for(Entry<Integer, Object> entry : value.entrySet()){
			preparedStatement.setObject(entry.getKey(), entry.getValue());
		}
		ResultSet resultSet = preparedStatement.executeQuery();
		Collection<?> collection = JdbcUtils.getCollection(resultSet,clazz);
		logger.debug(JdbcUtils.getNativeDMLSql(preparedStatement.toString()));
		CollectorFactory.getJdbcCollector().put(preparedStatement.toString());//放入日志缓存
		JdbcUtils.close(preparedStatement, resultSet);
		return collection;
	}
	
	/**
	 * 执行无参数selectSQL语句，返回唯一结果
	 * @param sql
	 * @return 唯一结果集
	 * @throws SQLException
	 */
	public Map<String, Object> queryUnique(final Connection connection,final String sql) throws SQLException {
		return query(connection,sql).iterator().next();
	}

	/**
	 * 执行无参数selectSQL语句，返回唯一结果
	 * @param sql
	 * @return 唯一结果集
	 * @throws SQLException
	 */
	public Object queryUnique(final Connection connection,final String sql,Class<?> clazz) throws SQLException {
		return query(connection,sql, clazz).iterator().next();
	}
	
	/**
	 * 执行带参数参数selectSQL语句，返回唯一结果,参数顺序应与?对应,例如：select * from table where id>? and id<?
	 * @param sql
	 * @return 唯一结果集
	 * @throws SQLException
	 */
	public Map<String, Object> queryUniqueByArray(final Connection connection,final String sql,Object... arrayParams) throws SQLException {
		return queryByArray(connection,sql, arrayParams).iterator().next();
	}
	
	/**
	 * 执行带参数参数selectSQL语句，返回唯一结果,参数顺序应与?对应,例如：select * from table where id>? and id<?
	 * @param sql
	 * @param clazz
	 * @param arrayParams
	 * @return
	 * @throws SQLException
	 */
	public Object queryUniqueByArray(final Connection connection,final String sql,Class<?> clazz,Object... arrayParams) throws SQLException {
		return queryByArray(connection,sql,clazz ,arrayParams).iterator().next();
	}
	
	/**
	 * 执行带参数参数selectSQL语句，返回唯一结果,参数顺序应与?对应,例如：select * from table where id>? and id<?
	 * @param sql
	 * @param clazz
	 * @param arrayParams
	 * @return
	 * @throws SQLException
	 */
	public Object queryUniqueByBean(final Connection connection,final String sql,Class<?> clazz,Object beanParams) throws SQLException {
		return queryByBean(connection,sql,clazz ,beanParams).iterator().next();
	}
	
	/**
	 * 执行带参数参数selectSQL语句，返回唯一结果.:冒号后变量对应map中的key名称，区分大小写,例如：select * from table where id>:id and name=:name
	 * @param sql
	 * @return 唯一结果集
	 * @throws SQLException
	 */
	public Map<String, Object> queryUniqueByMap(final Connection connection,final String sql,Map<String, Object> mapParams) throws SQLException {
		return queryByMap(connection,sql,mapParams).iterator().next();
	}
	
	/**
	 * 获得唯一值，如select count(1) from table;
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public Object queryUniqueColumnValue(final Connection connection,final String sql) throws SQLException {
		Map<String,Object> values = queryUnique(connection,sql);
		return values.entrySet().iterator().next().getValue();
	}

	/**
	 * 获得唯一值，如select count(1) from table where date>? and date<?;
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public Object queryUniqueColumnValueByArray(final Connection connection,final String sql,Object... arrayParams) throws SQLException {
		Map<String,Object> values = queryUniqueByArray(connection,sql, arrayParams);
		return values.entrySet().iterator().next().getValue();
	}

	/**
	 * 获得唯一值，如select count(1) from table where date>:startDate and date<:entDate;
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public Object queryUniqueColumnValueByMap(final Connection connection,final String sql,Map<String, Object> mapParams) throws SQLException {
		Map<String,Object> values = queryUniqueByMap(connection,sql, mapParams);
		return values.entrySet().iterator().next().getValue();
	}

	/**
	 * 获得唯一值，如select count(1) from table where date>:startDate and date<:entDate;
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public Object queryUniqueColumnValueByBean(final Connection connection,final String sql,Object beanParams) throws SQLException {
		Map<String,Object> values = queryUniqueByMap(connection,sql, BeanUtils.bean2Map(beanParams));
		return values.entrySet().iterator().next().getValue();
	}
	
	
	/**
	 * 执行无参数DDL的SQL语句，如：CREATE,DROP,ALTER,GRANT等语句
	 * @param sql
	 * @return 是否成功
	 * @throws SQLException
	 */
	public boolean executeDDL(final Connection connection,final String sql) throws SQLException {
		Statement statement = connection.createStatement();
		boolean r = statement.execute(sql);
		logger.debug(JdbcUtils.getNativeDDLSql(sql));
		CollectorFactory.getJdbcCollector().put(sql);//放入日志缓存
		JdbcUtils.close(statement);
		return r;
	}
	
	/**
	 * 执行除select以外的DML语句,如：INSERT,UPDATE,DELETE语句
	 * @param sql
	 * @return 受影响的条目数
	 * @throws SQLException
	 */
	public int executeDML(final Connection connection,final String sql) throws SQLException {
		Statement statement = connection.createStatement();
		int result = statement.executeUpdate(sql);
		logger.debug(JdbcUtils.getNativeDMLSql(sql));
		CollectorFactory.getJdbcCollector().put(sql);//放入日志缓存
		JdbcUtils.close(statement);
		return result;
	}

	/**
	 * 对带有自增列的表插入INSERT操作，并获得自增值
	 * @param sql
	 * @return 受影响的条目数
	 * @throws SQLException
	 */
	public Number insertGetGeneratedKey(final Connection connection,final String sql) throws SQLException {
		Statement statement = connection.createStatement();
		statement.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
		ResultSet resultSet = statement.getGeneratedKeys();
		resultSet.next();
		Number value = (Number) resultSet.getObject(1);
		logger.debug(JdbcUtils.getNativeDMLSql(sql));
		CollectorFactory.getJdbcCollector().put(sql);//放入日志缓存
		JdbcUtils.close(statement,resultSet);
		return value;
	}
	
	/**
	 * 带参数执行除select以外的DML语句,如：INSERT,UPDATE,DELETE语句,例如:INSERT INTO table(id,name) VALUES(?,?)
	 * @param sql
	 * @return 受影响的条目数
	 * @throws SQLException
	 */
	public int executeDMLByArray(final Connection connection,final String sql,Object... arrayParams) throws SQLException {
		if(arrayParams==null||arrayParams.length==0){
			return executeDML(connection,sql);
		}
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		int i = 1;
		for(Object param:arrayParams){
			preparedStatement.setObject((i++), param);
		}
		int result = preparedStatement.executeUpdate();
		logger.debug(JdbcUtils.getNativeDMLSql(preparedStatement.toString()));
		CollectorFactory.getJdbcCollector().put(preparedStatement.toString());//放入日志缓存
		JdbcUtils.close(preparedStatement);
		return result;
	}
	
	/**
	 * 对带有自增列的表插入INSERT操作，并获得自增值
	 * @param sql
	 * @return 受影响的条目数
	 * @throws SQLException
	 */
	public Number insertGetGeneratedKeyByArray(final Connection connection,final String sql,Object... arrayParams) throws SQLException {
		if(arrayParams==null||arrayParams.length==0){
			return insertGetGeneratedKey(connection,sql);
		}
		PreparedStatement preparedStatement = connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
		int i = 1;
		for(Object param:arrayParams){
			preparedStatement.setObject((i++), param);
		}
		preparedStatement.executeUpdate();
		ResultSet resultSet = preparedStatement.getGeneratedKeys();
		resultSet.next();
		Number value = (Number) resultSet.getObject(1);
		logger.debug(JdbcUtils.getNativeDMLSql(preparedStatement.toString()));
		CollectorFactory.getJdbcCollector().put(preparedStatement.toString());//放入日志缓存
		JdbcUtils.close(preparedStatement,resultSet);
		return value;
	}

	/**
	 * 带参数执行除select以外的DML语句,如：INSERT,UPDATE,DELETE语句,参数形式为:冒号,例如:INSERT INTO table(id,name) VALUES(:id,:name)
	 * @param sql
	 * @return 受影响的条目数
	 * @throws SQLException
	 */
	public int executeDMLByMap(final Connection connection,final String sql,Map<String, Object> mapParams) throws SQLException {
		if(mapParams==null||mapParams.size()==0){
			return executeDML(connection,sql);
		}
		Map<Integer, Object> value = JdbcUtils.getPreparedStatementSql(sql,mapParams);
		String preparedSql = (String) value.get(-19820206);
		value.remove(-19820206);
		PreparedStatement preparedStatement = connection.prepareStatement(preparedSql);
		for(Entry<Integer, Object> entry : value.entrySet()){
			preparedStatement.setObject(entry.getKey(), entry.getValue());
		}
		int result = preparedStatement.executeUpdate();
		logger.debug(JdbcUtils.getNativeDMLSql(preparedStatement.toString()));
		CollectorFactory.getJdbcCollector().put(preparedStatement.toString());//放入日志缓存
		JdbcUtils.close(preparedStatement);
		return result;
	}
	
	/**
	 * 对带有自增列的表插入INSERT操作，并获得自增值
	 * @param sql
	 * @return 受影响的条目数
	 * @throws SQLException
	 */
	public Number insertGetGeneratedKeyByMap(final Connection connection,final String sql,Map<String, Object> mapParams) throws SQLException {
		if(mapParams==null||mapParams.size()==0){
			return insertGetGeneratedKey(connection,sql);
		}
		Map<Integer, Object> value = JdbcUtils.getPreparedStatementSql(sql,mapParams);
		String preparedSql = (String) value.get(-19820206);
		value.remove(-19820206);
		PreparedStatement preparedStatement = connection.prepareStatement(preparedSql,PreparedStatement.RETURN_GENERATED_KEYS);
		for(Entry<Integer, Object> entry : value.entrySet()){
			preparedStatement.setObject(entry.getKey(), entry.getValue());
		}
		preparedStatement.executeUpdate();
		ResultSet resultSet = preparedStatement.getGeneratedKeys();
		resultSet.next();
		Number value2 = (Number) resultSet.getObject(1);
		logger.debug(JdbcUtils.getNativeDMLSql(preparedStatement.toString()));
		CollectorFactory.getJdbcCollector().put(preparedStatement.toString());//放入日志缓存
		JdbcUtils.close(preparedStatement,resultSet);
		return value2;
	}
	
	/**
	 * 带参数执行除select以外的DML语句,如：INSERT,UPDATE,DELETE语句,参数形式为:冒号,例如:INSERT INTO table(id,name) VALUES(:id,:name)
	 * @param sql
	 * @return 受影响的条目数
	 * @throws SQLException
	 */
	public int executeDMLByBean(final Connection connection,final String sql,Object bean) throws SQLException {
		if(bean==null){
			return executeDML(connection,sql);
		}
		Map<String, Object> mapParams = BeanUtils.bean2Map(bean);
		Map<Integer, Object> value = JdbcUtils.getPreparedStatementSql(sql,mapParams);
		String preparedSql = (String) value.get(-19820206);
		value.remove(-19820206);
		PreparedStatement preparedStatement = connection.prepareStatement(preparedSql);
		for(Entry<Integer, Object> entry : value.entrySet()){
			preparedStatement.setObject(entry.getKey(), entry.getValue());
		}
		int result = preparedStatement.executeUpdate();
		logger.debug(JdbcUtils.getNativeDMLSql(preparedStatement.toString()));
		CollectorFactory.getJdbcCollector().put(preparedStatement.toString());//放入日志缓存
		JdbcUtils.close(preparedStatement);
		return result;
	}
	
	/**
	 * 对带有自增列的表插入INSERT操作，并获得自增值
	 * @param sql
	 * @return 受影响的条目数
	 * @throws SQLException
	 */
	public Number insertGetGeneratedKeyByBean(final Connection connection,final String sql,Object bean) throws SQLException {
		if(bean==null){
			return insertGetGeneratedKey(connection,sql);
		}
		Map<String, Object> mapParams = BeanUtils.bean2Map(bean);
		Map<Integer, Object> value = JdbcUtils.getPreparedStatementSql(sql,mapParams);
		String preparedSql = (String) value.get(-19820206);
		value.remove(-19820206);
		PreparedStatement preparedStatement = connection.prepareStatement(preparedSql,PreparedStatement.RETURN_GENERATED_KEYS);
		for(Entry<Integer, Object> entry : value.entrySet()){
			preparedStatement.setObject(entry.getKey(), entry.getValue());
		}
		preparedStatement.executeUpdate();
		ResultSet resultSet = preparedStatement.getGeneratedKeys();
		resultSet.next();
		Number value2 = (Number) resultSet.getObject(1);
		logger.debug(JdbcUtils.getNativeDMLSql(preparedStatement.toString()));
		CollectorFactory.getJdbcCollector().put(preparedStatement.toString());//放入日志缓存
		JdbcUtils.close(preparedStatement,resultSet);
		return value2;
	}
	
	/**
	 * 批量执行除select以外的DML语句,如：INSERT,UPDATE,DELETE语句
	 * @param sqls数组
	 * @return 受影响的条目数组
	 * @throws SQLException
	 */
	public int[] executeBatchDML(final Connection connection,final String... sqls) throws SQLException {
		Statement statement = connection.createStatement();
		for(String sql:sqls){
			statement.addBatch(sql);
			logger.debug(JdbcUtils.getNativeDMLSql(sql));
		}
		int[] result = statement.executeBatch();
		CollectorFactory.getJdbcCollector().put(sqls.toString());//放入日志缓存
		JdbcUtils.close(statement);
		return result;
	}
	
	/**
	 * 批量执行除select以外的DML语句,如：INSERT,UPDATE,DELETE语句
	 * @param sqls集合
	 * @return 受影响的条目数组
	 * @throws SQLException
	 */
	public int[] executeBatchDML(final Connection connection,final Collection<String> sqls) throws SQLException {
		Statement statement = connection.createStatement();
		for(String sql:sqls){
			statement.addBatch(sql);
			logger.debug(JdbcUtils.getNativeDMLSql(sql));
		}
		int[] result = statement.executeBatch();
		CollectorFactory.getJdbcCollector().put(sqls.toString());//放入日志缓存
		JdbcUtils.close(statement);
		return result;
	}
	
	/**
	 * 批量执行带参数除select以外的DML语句,如：INSERT,UPDATE,DELETE语句
	 * @param sql
	 * @param mapParamsList
	 * @return
	 * @throws SQLException
	 */
	public int[] executeBatchDMLByMaps(final Connection connection,final String sql,Collection<Map<String, Object>> mapParamsList) throws SQLException {
		Collection<Map<Integer, Object>> values = JdbcUtils.getPreparedStatementSql(sql, mapParamsList);
		String preparedSql = (String) (values.iterator().next().get(-19820206));
		PreparedStatement preparedStatement = connection.prepareStatement(preparedSql);
		for(Map<Integer, Object> value : values){
			value.remove(-19820206);
			for(Entry<Integer, Object> entry : value.entrySet()){
				preparedStatement.setObject(entry.getKey(), entry.getValue());
			}
			preparedStatement.addBatch();
		}
		int[] result = preparedStatement.executeBatch();
		logger.debug(JdbcUtils.getNativeDMLSql(preparedStatement.toString()));
		CollectorFactory.getJdbcCollector().put(preparedStatement.toString());//放入日志缓存
		JdbcUtils.close(preparedStatement);
		return result;
	}
	
	/**
	 * 批量执行带参数除select以外的DML语句,如：INSERT,UPDATE,DELETE语句
	 * @param sql
	 * @param beanParamsList
	 * @return
	 * @throws SQLException
	 */
	public int[] executeBatchDMLByBeans(final Connection connection,final String sql,Collection<?> beanParamsList) throws SQLException {
		Collection<Map<String, Object>> mapParamsList = new ArrayList<Map<String,Object>>();
		for(Object bean : beanParamsList){
			mapParamsList.add(BeanUtils.bean2Map(bean));
		}
		Collection<Map<Integer, Object>> values = JdbcUtils.getPreparedStatementSql(sql, mapParamsList);
		String preparedSql = (String) (values.iterator().next().get(-19820206));
		PreparedStatement preparedStatement = connection.prepareStatement(preparedSql);
		for(Map<Integer, Object> value : values){
			value.remove(-19820206);
			for(Entry<Integer, Object> entry : value.entrySet()){
				preparedStatement.setObject(entry.getKey(), entry.getValue());
			}
			preparedStatement.addBatch();
		}
		int[] result = preparedStatement.executeBatch();
		logger.debug(JdbcUtils.getNativeDMLSql(preparedStatement.toString()));
		CollectorFactory.getJdbcCollector().put(preparedStatement.toString());//放入日志缓存
		JdbcUtils.close(preparedStatement);
		return result;
	}
	
	/**
	 * 调用存储过程;例如：" {call 过程名}"
	 * @param sql
	 * @throws SQLException
	 */
	public void call(final Connection connection,final String sql) throws SQLException {
		CallableStatement callableStatement = connection.prepareCall(sql);
		callableStatement.execute();
		logger.debug(JdbcUtils.getNativeDMLSql(callableStatement.toString()));
		CollectorFactory.getJdbcCollector().put(callableStatement.toString());//放入日志缓存
		JdbcUtils.close(callableStatement);
	}

	
	/**
	 * 调用存储过程;例如：" {call 过程名(?,?)}"
	 * @param sql
	 * @param inParams IN参数集合=?问号的顺序
	 * @throws SQLException
	 */
	public void call(final Connection connection,final String sql,Object... inParams) throws SQLException {
		CallableStatement callableStatement = connection.prepareCall(sql);
		int i = 1;
		for(Object inParam:inParams){
			callableStatement.setObject((i++),inParam);
		}
		callableStatement.execute();
		logger.debug(JdbcUtils.getNativeDMLSql(callableStatement.toString()));
		CollectorFactory.getJdbcCollector().put(callableStatement.toString());//放入日志缓存
		JdbcUtils.close(callableStatement);
	}
	
	/**
	 * 调用存储过程;例如：" {? = call 过程名(?,?)}"
	 * @param sql
	 * @param outParams OUT参数集合=?问号的顺序,详见 java.sql.Types;
	 * @throws SQLException
	 */
	public Collection<?> call(final Connection connection,final String sql,int... outParams) throws SQLException {
		CallableStatement callableStatement = connection.prepareCall(sql);
		int i = 1;
		for(int outParam:outParams){
			callableStatement.registerOutParameter((i++), outParam);
		}
		callableStatement.execute();
		Collection<Object> collection = new ArrayList<Object>();
		for(int j=1;j<=outParams.length;j++){
			collection.add(callableStatement.getObject((j++)));
		}
		logger.debug(JdbcUtils.getNativeDMLSql(callableStatement.toString()));
		CollectorFactory.getJdbcCollector().put(callableStatement.toString());//放入日志缓存
		JdbcUtils.close(callableStatement);
		return collection;
	}

	/**
	 * 调用存储过程;例如：" {? = call 过程名(?,?)}"
	 * @param sql
	 * @param outParams OUT参数集合=?问号的顺序,详见 java.sql.Types;
	 * @param inParams IN参数集合=?问号的顺序
	 * @throws SQLException
	 */
	public Map<String,Object> call(final Connection connection,final String sql,Map<String,Object> inParams,Map<String,Integer> outParams) throws SQLException {
		CallableStatement callableStatement = connection.prepareCall(sql);
		for(String outParam:outParams.keySet()){
			callableStatement.registerOutParameter(outParam, outParams.get(outParam));
		}
		for(String inParam:inParams.keySet()){
			callableStatement.setObject(inParam,inParams.get(inParam));
		}
		callableStatement.execute();
		for(String outParam:outParams.keySet()){
			callableStatement.registerOutParameter(outParam, outParams.get(outParam));
		}
		Map<String,Object> returnValues = new HashMap<String, Object>();
		for(String outParam:outParams.keySet()){
			returnValues.put(outParam, callableStatement.getObject(outParam));
		}
		logger.debug(JdbcUtils.getNativeDMLSql(callableStatement.toString()));
		CollectorFactory.getJdbcCollector().put(callableStatement.toString());//放入日志缓存
		JdbcUtils.close(callableStatement);
		return returnValues;
	}
	
	/**
	 * 设置多表查询，MAP结果集将变成[表名/别名].[字段名/别名],默认FALSE
	 * @param isManyTable
	 */
	public void setManyTable(Boolean isManyTable) {
		this.isManyTable = isManyTable;
	}

}
