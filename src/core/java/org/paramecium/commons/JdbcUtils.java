package org.paramecium.commons;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.paramecium.jdbc.formatter.DDLFormatter;
import org.paramecium.jdbc.formatter.DMLFormatter;
import org.paramecium.log.LogConfig;

/**
 * 功能描述(Description):<br><b>
 * JDBC操作工具
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-4-4上午09:44:08</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.paramecium.commons.JdbcUtils.java</b>
 */
public abstract class JdbcUtils {
	
	private static final char[] END = new char[] { ' ', ',', ')', '(', '!', '|', '=', '+', '-', '*', '%', '/', '^', '\\', '<', '>', '"', '\'', ':', '&', ';','	' };

	private static final String C_MARK = ":";
	private static final String Q_MARK = "?";
	private static final String N_MARK = " ";
	
	
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
	
	public static String getNativeDMLSql(String sql){
		int splite = sql.indexOf(C_MARK.concat(N_MARK));
		if(splite>-1){
			sql = sql.substring(splite+1,sql.length());
		}
		return LogConfig.sqlIsFormat ? DMLFormatter.format(sql) : sql;
	}

	public static String getNativeDDLSql(String sql){
		return LogConfig.sqlIsFormat ? DDLFormatter.format(sql) : sql;
	}

	/**
	 * 获得带有:冒号参数的PreparedStatement能够执行的?问号参数语句
	 * @param sql
	 * @param mapParams
	 * @return
	 * @throws SQLException
	 */
	public static Map<Integer, Object> getPreparedStatementSql(final String sql,Map<String, Object> mapParams) throws SQLException {
		String jdbcSql = sql;
		Map<Integer, Object> value = new HashMap<Integer, Object>();
		StringBuffer tempFildeName = new StringBuffer();
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
					jdbcSql = jdbcSql.replaceFirst(C_MARK.concat(tempFildeName.toString()), Q_MARK);
					value.put(pcount++, mapParams.get(tempFildeName.toString()));
					tempFildeName = new StringBuffer();;
				} else {
					tempFildeName.append(c);
					if (i + 1 == cs.length) {
						isReading = false;
						jdbcSql = jdbcSql.replaceFirst(C_MARK.concat(tempFildeName.toString()), Q_MARK);
						value.put(pcount++, mapParams.get(tempFildeName.toString()));
						tempFildeName = new StringBuffer();;
					}
				}
			}
		}
		value.put(-19820206, jdbcSql);
		return value;
	}
	
	public static Collection<Map<Integer, Object>> getPreparedStatementSql(final String sql,Collection<Map<String, Object>> mapParamsList) throws SQLException {
		Collection<Map<Integer, Object>> values = new ArrayList<Map<Integer,Object>>();
		for(Map<String, Object> mapParams : mapParamsList){
			values.add(getPreparedStatementSql(sql, mapParams));
		}
		return values;
	}
	
	/**
	 * 获得MAP对象结果集
	 * @param resultSet
	 * @param isManyTable是否多表查询，如果是则key中带有[tableName/alias].[columnName/alias]
	 * @return
	 * @throws SQLException
	 */
	public static Collection<Map<String,Object>> getCollection(ResultSet resultSet ,Boolean isManyTable) throws SQLException {
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
		int columnCount = resultSetMetaData.getColumnCount();
		Collection<Map<String, Object>> collection = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (; resultSet.next();) {
			map = new HashMap<String, Object>();
			for (int column = 1; column <= columnCount; column++) {
				String columnLabel = resultSetMetaData.getColumnLabel(column);
				if(isManyTable.booleanValue()){
					columnLabel = resultSetMetaData.getTableName(column).concat(".").concat(columnLabel);
				}
				Object columnValue = resultSet.getObject(columnLabel);
				map.put(columnLabel.toLowerCase(), columnValue);
			}
			collection.add(map);
		}
		isManyTable = new Boolean(false);//还原
		return collection;
	}

	/**
	 * 获得Bean的对象结果集
	 * @param resultSet
	 * @param clazz
	 * @return
	 * @throws SQLException
	 */
	public static Collection<?> getCollection(ResultSet resultSet,Class<?> clazz) throws SQLException {
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
				map.put(columnLabel.toLowerCase(), columnValue);
			}
			bean = BeanUtils.map2Bean(clazz, map, true);
			collection.add(bean);
		}
		return collection;
	}
	
	public static void close(CallableStatement callableStatement) throws SQLException {
		close(callableStatement, null, null, null);
	}

	public static void close(CallableStatement callableStatement, ResultSet resultSet)
			throws SQLException {
		close(callableStatement, null, null, resultSet);
	}

	public static void close(PreparedStatement preparedStatement) throws SQLException {
		close(null, preparedStatement, null, null);
	}

	public static void close(PreparedStatement preparedStatement, ResultSet resultSet)
			throws SQLException {
		close(null, preparedStatement, null, resultSet);
	}

	public static void close(Statement statement) throws SQLException {
		close(null, null, statement, null);
	}

	public static void close(Statement statement, ResultSet resultSet) throws SQLException {
		close(null, null, statement, resultSet);
	}

	public static void close(CallableStatement callableStatement,
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
	
	/**
	 * 为JDBC提供原生的SQL日志
	 * @param jdbcSql
	 * @param params
	 * @return
	 */
	public static String getNativeSql2(String jdbcSql, Map<String, Object> params){
		return getNativeSql(jdbcSql, params.values());
	}
	
	/**
	 * 为JDBC提供原生的SQL日志
	 * @param jdbcSql
	 * @param params
	 * @return
	 */
	public static String getNativeSql(String jdbcSql, Map<Integer, Object> params){
		int count = params.size();
		Object[] objs = new Object[count];
		for(int i=1;i<=count;i++){
			objs[i-1] = params.get(i);
		}
		return getNativeSql(jdbcSql, objs);
	}
	
	/**
	 * 为JDBC提供原生的SQL日志
	 * @param jdbcSql
	 * @param params
	 * @return
	 */
	public static String getNativeSql(String jdbcSql, Object... params){
		StringBuffer finalSql = new StringBuffer();
		if(jdbcSql == null){
			return "";
		}
		char[] cs = jdbcSql.toCharArray();
		int i = 0;
		for(char c : cs){
			if(c == '?'){
				try{
					finalSql.append(getStringSqlValue(params[i++]));
				}catch (java.lang.ArrayIndexOutOfBoundsException e) {
					System.err.println("参数数量与SQL语句的?数量不匹配!");
					e.printStackTrace();
				}
				
			}else{
				finalSql.append(c);
			}
		}
		return finalSql.toString();
	}
	
	/**
	 * 根据类型取得sql语句中的？值
	 * @param value
	 * @return
	 */
	private static String getStringSqlValue(Object value){
		if(value!=null){
			if (value instanceof Number || value instanceof Boolean) {
				return value.toString();
			}else if (value instanceof java.util.Date) {
				return "'"+ DateUtils.parse(DateUtils.DATE_TIME_FORMAT, (java.util.Date)value)+"'";
			}else{
				return "'"+value.toString()+"'";
			}
		}
		return "NULL";
	}
	
}
