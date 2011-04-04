package org.cy.core.jdbc;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import org.cy.core.jdbc.dialect.Dialect;

/**
 * 功能描述(Description):<br><b>
 * jdbc模板接口
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-4-3下午09:33:54</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.cy.core.jdbc.JdbcTemplate.java</b>
 */
public interface JdbcTemplate extends Dialect{
	
	/**
	 * 设置多表查询，MAP结果集将变成[表名/别名].[字段名/别名],默认FALSE
	 * @param isManyTable
	 */
	public void setManyTable(boolean isManyTable);
	
	/**
	 * 执行无参数selectSQL语句,数据以Map装载
	 * @param sql
	 * @return 结果集列表
	 * @throws SQLException
	 */
	public Collection<Map<String, Object>> query(final String sql) throws SQLException ;
	
	/**
	 * 执行无参数selectSQL语句，数据以bean装载
	 * @param sql
	 * @param clazz装载数据bean类型
	 * @return 结果集列表
	 * @throws SQLException
	 */
	public Collection<?> query(final String sql,Class<?> clazz) throws SQLException ;
	
	/**
	 * 带参数selectSQL语句,参数顺序应与?对应,例如：select * from table where id>? and id<?
	 * @param sql
	 * @param arrayParams参数数组
	 * @return
	 * @throws SQLException
	 */
	public Collection<Map<String, Object>> queryByArray(final String sql,Object... arrayParams) throws SQLException ;
	
	/**
	 * 带参数selectSQL语句,数据以bean装载,参数顺序应与?对应,例如：select * from table where id>? and id<?
	 * @param sql
	 * @param clazz装载数据bean类型
	 * @param arrayParams参数数组
	 * @return
	 * @throws SQLException
	 */
	public Collection<?> queryByArray(final String sql,Class<?> clazz,Object... arrayParams) throws SQLException ;
	
	/**
	 * 带参数selectSQL语句，:冒号后变量对应map中的key名称，区分大小写,例如：select * from table where id>:id and name=:name
	 * @param sql
	 * @param mapParams
	 * @return
	 * @throws SQLException
	 */
	public Collection<Map<String, Object>> queryByMap(final String sql,Map<String, Object> mapParams) throws SQLException ;
	
	/**
	 *  带参数selectSQL语句，:冒号后变量对应map中的key名称，区分大小写,例如：select * from table where id>:id and name=:name
	 * @param sql
	 * @param clazz
	 * @param mapParams
	 * @return
	 * @throws SQLException
	 */
	public Collection<?> queryByMap(final String sql,Class<?> clazz,Map<String, Object> mapParams) throws SQLException ;
	
	/**
	 * 带参数selectSQL语句，:冒号后变量对应bean中的属性名称，区分大小写,例如：select * from table where id>:id and name=:name
	 * @param sql
	 * @param clazz
	 * @param beanParams
	 * @return
	 * @throws SQLException
	 */
	public Collection<?> queryByBean(final String sql,Class<?> clazz,Object beanParams) throws SQLException ;
	
	/**
	 * 执行无参数selectSQL语句，返回唯一结果
	 * @param sql
	 * @return 唯一结果集
	 * @throws SQLException
	 */
	public Map<String, Object> queryUnique(final String sql) throws SQLException ;

	/**
	 * 执行无参数selectSQL语句，返回唯一结果
	 * @param sql
	 * @return 唯一结果集
	 * @throws SQLException
	 */
	public Object queryUnique(final String sql,Class<?> clazz) throws SQLException ;
	
	/**
	 * 执行带参数参数selectSQL语句，返回唯一结果,参数顺序应与?对应,例如：select * from table where id>? and id<?
	 * @param sql
	 * @return 唯一结果集
	 * @throws SQLException
	 */
	public Map<String, Object> queryUniqueByArray(final String sql,Object... arrayParams) throws SQLException ;
	
	/**
	 * 执行带参数参数selectSQL语句，返回唯一结果,参数顺序应与?对应,例如：select * from table where id>? and id<?
	 * @param sql
	 * @param clazz
	 * @param arrayParams
	 * @return
	 * @throws SQLException
	 */
	public Object queryUniqueByArray(final String sql,Class<?> clazz,Object... arrayParams) throws SQLException ;
	
	/**
	 * 执行带参数参数selectSQL语句，返回唯一结果,参数顺序应与?对应,例如：select * from table where id>? and id<?
	 * @param sql
	 * @param clazz
	 * @param arrayParams
	 * @return
	 * @throws SQLException
	 */
	public Object queryUniqueByBean(final String sql,Class<?> clazz,Object beanParams) throws SQLException ;
	
	/**
	 * 执行带参数参数selectSQL语句，返回唯一结果.:冒号后变量对应map中的key名称，区分大小写,例如：select * from table where id>:id and name=:name
	 * @param sql
	 * @return 唯一结果集
	 * @throws SQLException
	 */
	public Map<String, Object> queryUniqueByMap(final String sql,Map<String, Object> mapParams) throws SQLException ;
	
	/**
	 * 获得唯一值，如select count(1) from table;
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public Object queryUniqueColumnValue(final String sql) throws SQLException ;

	/**
	 * 获得唯一值，如select count(1) from table where date>? and date<?;
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public Object queryUniqueColumnValueByArray(final String sql,Object... arrayParams) throws SQLException ;

	/**
	 * 获得唯一值，如select count(1) from table where date>:startDate and date<:entDate;
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public Object queryUniqueColumnValueByMap(final String sql,Map<String, Object> mapParams) throws SQLException ;

	/**
	 * 获得唯一值，如select count(1) from table where date>:startDate and date<:entDate;
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public Object queryUniqueColumnValueByBean(final String sql,Object beanParams) throws SQLException ;
	
	
	/**
	 * 执行无参数DDL的SQL语句，如：CREATE,DROP,ALTER,GRANT等语句
	 * @param sql
	 * @throws SQLException
	 */
	public void executeDDL(final String sql) throws SQLException ;
	
	/**
	 * 执行除select以外的DML语句,如：INSERT,UPDATE,DELETE语句
	 * @param sql
	 * @return 受影响的条目数
	 * @throws SQLException
	 */
	public int executeDML(final String sql) throws SQLException ;
	
	/**
	 * 对带有自增列的表插入INSERT操作，并获得自增值
	 * @param sql
	 * @return 受影响的条目数
	 * @throws SQLException
	 */
	public Number insertGetGeneratedKey(final String sql) throws SQLException ;
	
	/**
	 * 带参数执行除select以外的DML语句,如：INSERT,UPDATE,DELETE语句,例如:INSERT INTO table(id,name) VALUES(?,?)
	 * @param sql
	 * @return 受影响的条目数
	 * @throws SQLException
	 */
	public int executeDMLByArray(final String sql,Object... arrayParams) throws SQLException ;
	
	/**
	 * 对带有自增列的表插入INSERT操作，并获得自增值
	 * @param sql
	 * @return 受影响的条目数
	 * @throws SQLException
	 */
	public Number insertGetGeneratedKeyByArray(final String sql,Object... arrayParams) throws SQLException;
	
	/**
	 * 带参数执行除select以外的DML语句,如：INSERT,UPDATE,DELETE语句,参数形式为:冒号,例如:INSERT INTO table(id,name) VALUES(:id,:name)
	 * @param sql
	 * @return 受影响的条目数
	 * @throws SQLException
	 */
	public int executeDMLByMap(final String sql,Map<String, Object> mapParams) throws SQLException ;
	
	/**
	 * 对带有自增列的表插入INSERT操作，并获得自增值
	 * @param sql
	 * @return 受影响的条目数
	 * @throws SQLException
	 */
	public Number insertGetGeneratedKeyByMap(final String sql,Map<String, Object> mapParams) throws SQLException ;
	
	/**
	 * 带参数执行除select以外的DML语句,如：INSERT,UPDATE,DELETE语句,参数形式为:冒号,例如:INSERT INTO table(id,name) VALUES(:id,:name)
	 * @param sql
	 * @return 受影响的条目数
	 * @throws SQLException
	 */
	public int executeDMLByBean(final String sql,Object bean) throws SQLException ;
	
	/**
	 * 对带有自增列的表插入INSERT操作，并获得自增值
	 * @param sql
	 * @return 受影响的条目数
	 * @throws SQLException
	 */
	public Number insertGetGeneratedKeyByBean(final String sql,Object bean) throws SQLException ;
	
	/**
	 * 批量执行除select以外的DML语句,如：INSERT,UPDATE,DELETE语句
	 * @param sqls数组
	 * @return 受影响的条目数组
	 * @throws SQLException
	 */
	public int[] executeBatchDML(final String... sqls) throws SQLException ;
	
	/**
	 * 批量执行除select以外的DML语句,如：INSERT,UPDATE,DELETE语句
	 * @param sqls集合
	 * @return 受影响的条目数组
	 * @throws SQLException
	 */
	public int[] executeBatchDML(final Collection<String> sqls) throws SQLException ;
	
	/**
	 * 批量执行带参数除select以外的DML语句,如：INSERT,UPDATE,DELETE语句
	 * @param sql
	 * @param mapParamsList
	 * @return
	 * @throws SQLException
	 */
	public int[] executeBatchDMLByMaps(final String sql,Collection<Map<String, Object>> mapParamsList) throws SQLException ;
	
	/**
	 * 批量执行带参数除select以外的DML语句,如：INSERT,UPDATE,DELETE语句
	 * @param sql
	 * @param beanParamsList
	 * @return
	 * @throws SQLException
	 */
	public int[] executeBatchDMLByBeans(final String sql,Collection<?> beanParamsList) throws SQLException ;
	
	/**
	 * 调用存储过程;例如：" {call 过程名}"
	 * @param sql
	 * @throws SQLException
	 */
	public void call(final String sql) throws SQLException ;

	
	/**
	 * 调用存储过程;例如：" {call 过程名(?,?)}"
	 * @param sql
	 * @param inParams IN参数集合=?问号的顺序
	 * @throws SQLException
	 */
	public void call(final String sql,Object... inParams) throws SQLException ;
	
	/**
	 * 调用存储过程;例如：" {? = call 过程名(?,?)}"
	 * @param sql
	 * @param outParams OUT参数集合=?问号的顺序,详见 java.sql.Types;
	 * @throws SQLException
	 */
	public Collection<?> call(final String sql,int... outParams) throws SQLException ;

	/**
	 * 调用存储过程;例如：" {? = call 过程名(?,?)}"
	 * @param sql
	 * @param outParams OUT参数集合=?问号的顺序,详见 java.sql.Types;
	 * @param inParams IN参数集合=?问号的顺序
	 * @throws SQLException
	 */
	public Map<String,Object> call(final String sql,Map<String,Object> inParams,Map<String,Integer> outParams) throws SQLException ;

}
