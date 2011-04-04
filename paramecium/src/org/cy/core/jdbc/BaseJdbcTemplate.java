package org.cy.core.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.cy.core.commons.BeanUitls;
import org.cy.core.jdbc.datasource.DataSourceBuilder;
import org.cy.core.jdbc.formatter.DDLFormatter;
import org.cy.core.jdbc.formatter.DMLFormatter;
import org.cy.core.log.Log;
import org.cy.core.log.LoggerFactory;

/**
 * 功能描述(Description):<br><b>
 * jdbc基础模板
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-4-3下午09:33:42</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.cy.core.jdbc.BaseJdbcTemplate.java</b>
 */
public abstract class BaseJdbcTemplate implements JdbcTemplate{
	
	private static Log logger = LoggerFactory.getLogger();

	private Connection connection;

	public BaseJdbcTemplate(final Connection connection) {
		this.connection = connection;
	}

	/**
	 * 执行无参数selectSQL语句,数据以Map装载
	 * @param sql
	 * @return 结果集列表
	 * @throws SQLException
	 */
	public Collection<Map<String, Object>> query(final String sql) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		Collection<Map<String, Object>> collection = getCollection(resultSet);
		logger.debug(getNativeDMLSql(sql));
		close(statement, resultSet);
		return collection;
	}
	
	/**
	 * 执行无参数selectSQL语句，数据以bean装载
	 * @param sql
	 * @param clazz装载数据bean类型
	 * @return 结果集列表
	 * @throws SQLException
	 */
	public Collection<?> query(final String sql,Class<?> clazz) throws SQLException {
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		Collection<?> collection = getCollection(resultSet,clazz);
		logger.debug(getNativeDMLSql(sql));
		close(statement, resultSet);
		return collection;
	}
	
	/**
	 * 带参数selectSQL语句,参数顺序应与?对应,例如：select * from table where id>? and id<?
	 * @param sql
	 * @param arrayParams参数数组
	 * @return
	 * @throws SQLException
	 */
	public Collection<Map<String, Object>> queryByArray(final String sql,Object... arrayParams) throws SQLException {
		if(arrayParams==null||arrayParams.length==0){
			return query(sql);
		}
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		int i = 1;
		for(Object param:arrayParams){
			preparedStatement.setObject((i++), param);
		}
		ResultSet resultSet = preparedStatement.executeQuery();
		Collection<Map<String, Object>> collection = getCollection(resultSet);
		logger.debug(getNativeDMLSql(preparedStatement.toString()));
		close(preparedStatement, resultSet);
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
	public Collection<?> queryByArray(final String sql,Class<?> clazz,Object... arrayParams) throws SQLException {
		if(arrayParams==null||arrayParams.length==0){
			return query(sql,clazz);
		}
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		int i = 1;
		for(Object param:arrayParams){
			preparedStatement.setObject((i++), param);
		}
		ResultSet resultSet = preparedStatement.executeQuery();
		Collection<?> collection = getCollection(resultSet,clazz);
		logger.debug(getNativeDMLSql(preparedStatement.toString()));
		close(preparedStatement, resultSet);
		return collection;
	}
	
	/**
	 * 带参数selectSQL语句，:冒号后变量对应map中的key名称，区分大小写,例如：select * from table where id>:id and name=:name
	 * @param sql
	 * @param mapParams
	 * @return
	 * @throws SQLException
	 */
	public Collection<Map<String, Object>> queryByMap(final String sql,Map<String, Object> mapParams) throws SQLException {
		if(mapParams==null||mapParams.size()==0){
			return query(sql);
		}
		Map<Integer, Object> value = getPreparedStatementSql(sql,mapParams);
		String preparedSql = (String) value.get(-19820206);
		value.remove(-19820206);
		PreparedStatement preparedStatement = connection.prepareStatement(preparedSql);
		for(Entry<Integer, Object> entry : value.entrySet()){
			preparedStatement.setObject(entry.getKey(), entry.getValue());
		}
		ResultSet resultSet = preparedStatement.executeQuery();
		Collection<Map<String, Object>> collection = getCollection(resultSet);
		logger.debug(getNativeDMLSql(preparedStatement.toString()));
		close(preparedStatement, resultSet);
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
	public Collection<?> queryByMap(final String sql,Class<?> clazz,Map<String, Object> mapParams) throws SQLException {
		if(mapParams==null||mapParams.size()==0){
			return query(sql,clazz);
		}
		Map<Integer, Object> value = getPreparedStatementSql(sql,mapParams);
		String preparedSql = (String) value.get(-19820206);
		value.remove(-19820206);
		PreparedStatement preparedStatement = connection.prepareStatement(preparedSql);
		for(Entry<Integer, Object> entry : value.entrySet()){
			preparedStatement.setObject(entry.getKey(), entry.getValue());
		}
		ResultSet resultSet = preparedStatement.executeQuery();
		Collection<?> collection = getCollection(resultSet,clazz);
		logger.debug(getNativeDMLSql(preparedStatement.toString()));
		close(preparedStatement, resultSet);
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
	public Collection<?> queryByBean(final String sql,Class<?> clazz,Object beanParams) throws SQLException {
		if(beanParams==null){
			return query(sql,clazz);
		}
		Map<String, Object> mapParams = BeanUitls.bean2Map(beanParams);
		Map<Integer, Object> value = getPreparedStatementSql(sql,mapParams);
		String preparedSql = (String) value.get(-19820206);
		value.remove(-19820206);
		PreparedStatement preparedStatement = connection.prepareStatement(preparedSql);
		for(Entry<Integer, Object> entry : value.entrySet()){
			preparedStatement.setObject(entry.getKey(), entry.getValue());
		}
		ResultSet resultSet = preparedStatement.executeQuery();
		Collection<?> collection = getCollection(resultSet,clazz);
		logger.debug(getNativeDMLSql(preparedStatement.toString()));
		close(preparedStatement, resultSet);
		return collection;
	}
	
	/**
	 * 执行无参数selectSQL语句，返回唯一结果
	 * @param sql
	 * @return 唯一结果集
	 * @throws SQLException
	 */
	public Map<String, Object> queryUnique(final String sql) throws SQLException {
		return query(sql).iterator().next();
	}

	/**
	 * 执行无参数selectSQL语句，返回唯一结果
	 * @param sql
	 * @return 唯一结果集
	 * @throws SQLException
	 */
	public Object queryUnique(final String sql,Class<?> clazz) throws SQLException {
		return query(sql, clazz).iterator().next();
	}
	
	/**
	 * 执行带参数参数selectSQL语句，返回唯一结果,参数顺序应与?对应,例如：select * from table where id>? and id<?
	 * @param sql
	 * @return 唯一结果集
	 * @throws SQLException
	 */
	public Map<String, Object> queryUniqueByArray(final String sql,Object... arrayParams) throws SQLException {
		return queryByArray(sql, arrayParams).iterator().next();
	}
	
	/**
	 * 执行带参数参数selectSQL语句，返回唯一结果,参数顺序应与?对应,例如：select * from table where id>? and id<?
	 * @param sql
	 * @param clazz
	 * @param arrayParams
	 * @return
	 * @throws SQLException
	 */
	public Object queryUniqueByArray(final String sql,Class<?> clazz,Object... arrayParams) throws SQLException {
		return queryByArray(sql,clazz ,arrayParams).iterator().next();
	}
	
	/**
	 * 执行带参数参数selectSQL语句，返回唯一结果,参数顺序应与?对应,例如：select * from table where id>? and id<?
	 * @param sql
	 * @param clazz
	 * @param arrayParams
	 * @return
	 * @throws SQLException
	 */
	public Object queryUniqueByBean(final String sql,Class<?> clazz,Object beanParams) throws SQLException {
		return queryByBean(sql,clazz ,beanParams).iterator().next();
	}
	
	/**
	 * 执行带参数参数selectSQL语句，返回唯一结果.:冒号后变量对应map中的key名称，区分大小写,例如：select * from table where id>:id and name=:name
	 * @param sql
	 * @return 唯一结果集
	 * @throws SQLException
	 */
	public Map<String, Object> queryUniqueByMap(final String sql,Map<String, Object> mapParams) throws SQLException {
		return queryByMap(sql,mapParams).iterator().next();
	}
	
	/**
	 * 获得唯一值，如select count(1) from table;
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public Object queryUniqueColumnValue(final String sql) throws SQLException {
		Map<String,Object> values = queryUnique(sql);
		return values.entrySet().iterator().next().getValue();
	}

	/**
	 * 获得唯一值，如select count(1) from table where date>? and date<?;
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public Object queryUniqueColumnValueByArray(final String sql,Object... arrayParams) throws SQLException {
		Map<String,Object> values = queryUniqueByArray(sql, arrayParams);
		return values.entrySet().iterator().next().getValue();
	}

	/**
	 * 获得唯一值，如select count(1) from table where date>:startDate and date<:entDate;
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public Object queryUniqueColumnValueByMap(final String sql,Map<String, Object> mapParams) throws SQLException {
		Map<String,Object> values = queryUniqueByMap(sql, mapParams);
		return values.entrySet().iterator().next().getValue();
	}

	/**
	 * 获得唯一值，如select count(1) from table where date>:startDate and date<:entDate;
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public Object queryUniqueColumnValueByBean(final String sql,Object beanParams) throws SQLException {
		Map<String,Object> values = queryUniqueByMap(sql, BeanUitls.bean2Map(beanParams));
		return values.entrySet().iterator().next().getValue();
	}
	
	
	/**
	 * 执行无参数DDL的SQL语句，如：CREATE,DROP,ALTER,GRANT等语句
	 * @param sql
	 * @throws SQLException
	 */
	public void executeDDL(final String sql) throws SQLException {
		Statement statement = connection.createStatement();
		statement.execute(sql);
		logger.debug(getNativeDDLSql(sql));
		close(statement);
	}
	
	/**
	 * 执行除select以外的DML语句,如：INSERT,UPDATE,DELETE语句
	 * @param sql
	 * @return 受影响的条目数
	 * @throws SQLException
	 */
	public int executeDML(final String sql) throws SQLException {
		Statement statement = connection.createStatement();
		int result = statement.executeUpdate(sql);
		logger.debug(getNativeDMLSql(sql));
		close(statement);
		return result;
	}

	/**
	 * 对带有自增列的表插入INSERT操作，并获得自增值
	 * @param sql
	 * @return 受影响的条目数
	 * @throws SQLException
	 */
	public Number insertGetGeneratedKey(final String sql) throws SQLException {
		Statement statement = connection.createStatement();
		statement.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
		ResultSet resultSet = statement.getGeneratedKeys();
		resultSet.next();
		Number value = (Number) resultSet.getObject(1);
		logger.debug(getNativeDMLSql(sql));
		close(statement,resultSet);
		return value;
	}
	
	/**
	 * 带参数执行除select以外的DML语句,如：INSERT,UPDATE,DELETE语句,例如:INSERT INTO table(id,name) VALUES(?,?)
	 * @param sql
	 * @return 受影响的条目数
	 * @throws SQLException
	 */
	public int executeDMLByArray(final String sql,Object... arrayParams) throws SQLException {
		if(arrayParams==null||arrayParams.length==0){
			return executeDML(sql);
		}
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		int i = 1;
		for(Object param:arrayParams){
			preparedStatement.setObject((i++), param);
		}
		int result = preparedStatement.executeUpdate();
		logger.debug(getNativeDMLSql(preparedStatement.toString()));
		close(preparedStatement);
		return result;
	}
	
	/**
	 * 对带有自增列的表插入INSERT操作，并获得自增值
	 * @param sql
	 * @return 受影响的条目数
	 * @throws SQLException
	 */
	public Number insertGetGeneratedKeyByArray(final String sql,Object... arrayParams) throws SQLException {
		if(arrayParams==null||arrayParams.length==0){
			return insertGetGeneratedKey(sql);
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
		logger.debug(getNativeDMLSql(preparedStatement.toString()));
		close(preparedStatement,resultSet);
		return value;
	}

	/**
	 * 带参数执行除select以外的DML语句,如：INSERT,UPDATE,DELETE语句,参数形式为:冒号,例如:INSERT INTO table(id,name) VALUES(:id,:name)
	 * @param sql
	 * @return 受影响的条目数
	 * @throws SQLException
	 */
	public int executeDMLByMap(final String sql,Map<String, Object> mapParams) throws SQLException {
		if(mapParams==null||mapParams.size()==0){
			return executeDML(sql);
		}
		Map<Integer, Object> value = getPreparedStatementSql(sql,mapParams);
		String preparedSql = (String) value.get(-19820206);
		value.remove(-19820206);
		PreparedStatement preparedStatement = connection.prepareStatement(preparedSql);
		for(Entry<Integer, Object> entry : value.entrySet()){
			preparedStatement.setObject(entry.getKey(), entry.getValue());
		}
		int result = preparedStatement.executeUpdate();
		logger.debug(getNativeDMLSql(preparedStatement.toString()));
		close(preparedStatement);
		return result;
	}
	
	/**
	 * 对带有自增列的表插入INSERT操作，并获得自增值
	 * @param sql
	 * @return 受影响的条目数
	 * @throws SQLException
	 */
	public Number insertGetGeneratedKeyByMap(final String sql,Map<String, Object> mapParams) throws SQLException {
		if(mapParams==null||mapParams.size()==0){
			return insertGetGeneratedKey(sql);
		}
		Map<Integer, Object> value = getPreparedStatementSql(sql,mapParams);
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
		logger.debug(getNativeDMLSql(preparedStatement.toString()));
		close(preparedStatement,resultSet);
		return value2;
	}
	
	/**
	 * 带参数执行除select以外的DML语句,如：INSERT,UPDATE,DELETE语句,参数形式为:冒号,例如:INSERT INTO table(id,name) VALUES(:id,:name)
	 * @param sql
	 * @return 受影响的条目数
	 * @throws SQLException
	 */
	public int executeDMLByBean(final String sql,Object bean) throws SQLException {
		if(bean==null){
			return executeDML(sql);
		}
		Map<String, Object> mapParams = BeanUitls.bean2Map(bean);
		Map<Integer, Object> value = getPreparedStatementSql(sql,mapParams);
		String preparedSql = (String) value.get(-19820206);
		value.remove(-19820206);
		PreparedStatement preparedStatement = connection.prepareStatement(preparedSql);
		for(Entry<Integer, Object> entry : value.entrySet()){
			preparedStatement.setObject(entry.getKey(), entry.getValue());
		}
		int result = preparedStatement.executeUpdate();
		logger.debug(getNativeDMLSql(preparedStatement.toString()));
		close(preparedStatement);
		return result;
	}
	
	/**
	 * 对带有自增列的表插入INSERT操作，并获得自增值
	 * @param sql
	 * @return 受影响的条目数
	 * @throws SQLException
	 */
	public Number insertGetGeneratedKeyByBean(final String sql,Object bean) throws SQLException {
		if(bean==null){
			return insertGetGeneratedKey(sql);
		}
		Map<String, Object> mapParams = BeanUitls.bean2Map(bean);
		Map<Integer, Object> value = getPreparedStatementSql(sql,mapParams);
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
		logger.debug(getNativeDMLSql(preparedStatement.toString()));
		close(preparedStatement,resultSet);
		return value2;
	}
	
	/**
	 * 批量执行除select以外的DML语句,如：INSERT,UPDATE,DELETE语句
	 * @param sqls数组
	 * @return 受影响的条目数组
	 * @throws SQLException
	 */
	public int[] executeBatchDML(final String... sqls) throws SQLException {
		Statement statement = connection.createStatement();
		for(String sql:sqls){
			statement.addBatch(sql);
			logger.debug(getNativeDMLSql(sql));
		}
		int[] result = statement.executeBatch();
		close(statement);
		return result;
	}
	
	/**
	 * 批量执行除select以外的DML语句,如：INSERT,UPDATE,DELETE语句
	 * @param sqls集合
	 * @return 受影响的条目数组
	 * @throws SQLException
	 */
	public int[] executeBatchDML(final Collection<String> sqls) throws SQLException {
		Statement statement = connection.createStatement();
		for(String sql:sqls){
			statement.addBatch(sql);
			logger.debug(getNativeDMLSql(sql));
		}
		int[] result = statement.executeBatch();
		close(statement);
		return result;
	}
	
	/**
	 * 批量执行带参数除select以外的DML语句,如：INSERT,UPDATE,DELETE语句
	 * @param sql
	 * @param mapParamsList
	 * @return
	 * @throws SQLException
	 */
	public int[] executeBatchDMLByMaps(final String sql,Collection<Map<String, Object>> mapParamsList) throws SQLException {
		Collection<Map<Integer, Object>> values = getPreparedStatementSql(sql, mapParamsList);
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
		logger.debug(getNativeDMLSql(preparedStatement.toString()));
		close(preparedStatement);
		return result;
	}
	
	/**
	 * 批量执行带参数除select以外的DML语句,如：INSERT,UPDATE,DELETE语句
	 * @param sql
	 * @param beanParamsList
	 * @return
	 * @throws SQLException
	 */
	public int[] executeBatchDMLByBeans(final String sql,Collection<?> beanParamsList) throws SQLException {
		Collection<Map<String, Object>> mapParamsList = new ArrayList<Map<String,Object>>();
		for(Object bean : beanParamsList){
			mapParamsList.add(BeanUitls.bean2Map(bean));
		}
		Collection<Map<Integer, Object>> values = getPreparedStatementSql(sql, mapParamsList);
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
		logger.debug(getNativeDMLSql(preparedStatement.toString()));
		close(preparedStatement);
		return result;
	}
	
	/**
	 * 调用存储过程;例如：" {call 过程名}"
	 * @param sql
	 * @throws SQLException
	 */
	public void call(final String sql) throws SQLException {
		CallableStatement callableStatement = connection.prepareCall(sql);
		callableStatement.execute();
		logger.debug(getNativeDMLSql(callableStatement.toString()));
		close(callableStatement);
	}

	
	/**
	 * 调用存储过程;例如：" {call 过程名(?,?)}"
	 * @param sql
	 * @param inParams IN参数集合=?问号的顺序
	 * @throws SQLException
	 */
	public void call(final String sql,Object... inParams) throws SQLException {
		CallableStatement callableStatement = connection.prepareCall(sql);
		int i = 1;
		for(Object inParam:inParams){
			callableStatement.setObject((i++),inParam);
		}
		callableStatement.execute();
		logger.debug(getNativeDMLSql(callableStatement.toString()));
		close(callableStatement);
	}
	
	/**
	 * 调用存储过程;例如：" {? = call 过程名(?,?)}"
	 * @param sql
	 * @param outParams OUT参数集合=?问号的顺序,详见 java.sql.Types;
	 * @throws SQLException
	 */
	public Collection<?> call(final String sql,int... outParams) throws SQLException {
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
		logger.debug(getNativeDMLSql(callableStatement.toString()));
		close(callableStatement);
		return collection;
	}

	/**
	 * 调用存储过程;例如：" {? = call 过程名(?,?)}"
	 * @param sql
	 * @param outParams OUT参数集合=?问号的顺序,详见 java.sql.Types;
	 * @param inParams IN参数集合=?问号的顺序
	 * @throws SQLException
	 */
	public Map<String,Object> call(final String sql,Map<String,Object> inParams,Map<String,Integer> outParams) throws SQLException {
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
		logger.debug(getNativeDMLSql(callableStatement.toString()));
		close(callableStatement);
		return returnValues;
	}
	
	private static final char[] END = new char[] { ' ', ',', ')', '(', '!', '|', '=', '+', '-', '*', '%', '/', '^', '\\', '<', '>', '"', '\'', ':', '&', ';','	' };

	private static boolean isEND(char c) {
		if (Character.isWhitespace(c)) {
			return true;
		}
		for (char separator : END) {
			if (c == separator) {
				return true;
			}
		}
		return false;
	}
	
	private static String getNativeDMLSql(String sql){
		int splite = sql.indexOf(": ");
		if(splite>-1){
			sql = sql.substring(splite+1,sql.length());
		}
		return DataSourceBuilder.sqlIsFormat ? DMLFormatter.format(sql) : sql;
	}

	private static String getNativeDDLSql(String sql){
		return DataSourceBuilder.sqlIsFormat ? DDLFormatter.format(sql) : sql;
	}

	/**
	 * 获得带有:冒号参数的PreparedStatement能够执行的?问号参数语句
	 * @param sql
	 * @param mapParams
	 * @return
	 * @throws SQLException
	 */
	private static Map<Integer, Object> getPreparedStatementSql(final String sql,Map<String, Object> mapParams) throws SQLException {
		String jdbcSql = sql;
		Map<Integer, Object> value = new HashMap<Integer, Object>();
		StringBuffer tempFildeName = new StringBuffer("");
		boolean isReading = false;
		char[] cs = jdbcSql.toCharArray();
		int pcount = 1;
		for (int i = 0; i < cs.length; i++) {
			char c = cs[i];
			if (c == ':') {
				if (isReading) {
					throw new SQLException("请检查SQL语句是否正确!");
				} else {
					isReading = true;
				}
			} else if (isReading) {
				if (isEND(c)) {
					isReading = false;
					jdbcSql = jdbcSql.replaceFirst(":" + tempFildeName, "?");
					value.put(pcount++, mapParams.get(tempFildeName.toString()));
					tempFildeName = new StringBuffer("");;
				} else {
					tempFildeName.append(c);
					if (i + 1 == cs.length) {
						isReading = false;
						jdbcSql = jdbcSql.replaceFirst(":" + tempFildeName, "?");
						value.put(pcount++, mapParams.get(tempFildeName.toString()));
						tempFildeName = new StringBuffer("");;
					}
				}
			}
		}
		value.put(-19820206, jdbcSql);
		return value;
	}
	
	private static Collection<Map<Integer, Object>> getPreparedStatementSql(final String sql,Collection<Map<String, Object>> mapParamsList) throws SQLException {
		Collection<Map<Integer, Object>> values = new ArrayList<Map<Integer,Object>>();
		for(Map<String, Object> mapParams : mapParamsList){
			values.add(getPreparedStatementSql(sql, mapParams));
		}
		return values;
	}
	
	private static Collection<Map<String,Object>> getCollection(ResultSet resultSet) throws SQLException {
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
		int columnCount = resultSetMetaData.getColumnCount();
		Collection<Map<String, Object>> collection = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (; resultSet.next();) {
			map = new HashMap<String, Object>();
			for (int column = 1; column <= columnCount; column++) {
				String columnLabel = resultSetMetaData.getTableName(column);
				String columnLabel2 = resultSetMetaData.getColumnLabel(column);
				Object columnValue = resultSet.getObject(columnLabel+"."+columnLabel2);
				map.put(columnLabel+"."+columnLabel2, columnValue);
			}
			collection.add(map);
		}
		return collection;
	}

	private static Collection<?> getCollection(ResultSet resultSet,Class<?> clazz) throws SQLException {
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
		int columnCount = resultSetMetaData.getColumnCount();
		Collection<Object> collection = new ArrayList<Object>();
		Map<String, Object> map = null;
		Object bean = null;
		for (; resultSet.next();) {
			map = new HashMap<String, Object>();
			for (int column = 1; column <= columnCount; column++) {
				String columnLabel = resultSetMetaData.getColumnLabel(column);
				Object columnValue = resultSet.getObject(columnLabel);
				map.put(columnLabel, columnValue);
			}
			bean = BeanUitls.map2Bean(clazz, map, true);
			collection.add(bean);
		}
		return collection;
	}
	
	void close(CallableStatement callableStatement) throws SQLException {
		close(callableStatement, null, null, null);
	}

	void close(CallableStatement callableStatement, ResultSet resultSet)
			throws SQLException {
		close(callableStatement, null, null, resultSet);
	}

	void close(PreparedStatement preparedStatement) throws SQLException {
		close(null, preparedStatement, null, null);
	}

	void close(PreparedStatement preparedStatement, ResultSet resultSet)
			throws SQLException {
		close(null, preparedStatement, null, resultSet);
	}

	void close(Statement statement) throws SQLException {
		close(null, null, statement, null);
	}

	void close(Statement statement, ResultSet resultSet) throws SQLException {
		close(null, null, statement, resultSet);
	}

	void close(CallableStatement callableStatement,
			PreparedStatement preparedStatement, Statement statement,
			ResultSet resultSet) throws SQLException {
		if (resultSet != null) {
			resultSet.close();
		}
		if (callableStatement != null) {
			callableStatement.close();
		}
		if (preparedStatement != null) {
			preparedStatement.close();
		}
		if (statement != null) {
			statement.close();
		}
	}

}
