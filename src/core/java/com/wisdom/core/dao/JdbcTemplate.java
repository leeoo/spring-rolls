package com.wisdom.core.dao;

import java.sql.Connection;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.wisdom.core.utils.Page;

/**
 * 
 * <b>业务说明</b>:<br>
 * jdbc模板接口
 *<br><b>作  者</b>: Cao.Yang<br>
 * <b>建立日期</b>: 2009-3-13<br>
 * <b>建立时间</b>: 上午02:18:43<br>
 * <b>项目名称</b>: easyapp<br>
 * <b>包及类名</b>: com.wisdom.core.dao.jdbc.BaseJdbcTemplate.java<br>
 */
@SuppressWarnings("unchecked")
public interface JdbcTemplate {
	
	/**
	 * 执行存储过程
	 * @param procedureName存储过程名称
	 */
	public void callProcedure(String procedureName);
	
	/**
	 * 执行存储过程
	 * @param procedureName存储过程名称
	 * @param inParameters执行参数集合(key:参数名称,value:参数值)
	 */
	public void callProcedure(String procedureName,Map<String,Object> inParameters);
	
	/**
	 * 执行带out返回值的存储过程,例如:<br>
	 * CREATE PROCEDURE read_actor (<br>
	 * IN in_id INTEGER,<br>
	 *	OUT out_first_name VARCHAR(100),<br>
	 *	OUT out_last_name VARCHAR(100),<br>
	 *	OUT out_birth_date DATE)<br>
	 * BEGIN<br>
	 * 	SELECT first_name, last_name, birth_date<br>
	 * 	INTO out_first_name, out_last_name, out_birth_date<br>
	 * 	FROM t_actor where id = in_id;<br>
	 * END;<br>
	 * @param procedureName存储过程名称
	 * @param inParameters执行参数集合(key:参数名称,value:参数值)
	 * @return out返回值集合(key:out参数名称,value:out参数值)
	 */
	public Map<String,Object> callProcedureQueryOut(String procedureName,Map<String,Object> inParameters);
	
	/**
	 * 执行带out返回值的存储过程,或包括带有查询的select集合返回值.例如:<br>
	 * CREATE PROCEDURE read_all_actors()<br>
	 * BEGIN<br>
	 * SELECT a.id, a.first_name, a.last_name, a.birth_date FROM t_actor a;<br>
	 * END;<br>
	 * @param procedureName存储过程名称
	 * @param inParameters执行参数集合(key:参数名称,value:参数值)
	 * @param outBeansType设置返回beanList集合的bean类型
	 * @return bean集合
	 */
	public List callProcedureQueryListBeans(String procedureName,Map<String,Object> inParameters,Class outBeansType);
	
	/**
	 * 获得当前数据库链接，可以做一些手动操作JDBC工作
	 * @return 链接对象
	 * @throws Exception打开数据库错误
	 */
	public Connection getConnection() throws Exception;
	
	/**
	 * 根据sql语句，返回对象集合
	 * @param sql语句(参数用冒号加参数名，例如select * from tb where id=:id)
	 * @param clazz类型
	 * @param mapParameters参数集合(key为参数名，value为参数值)
	 * @return bean对象集合
	 */
	public List findListBeanByMap(final String sql,Class clazz,Map mapParameters);
	
	/**
	 * 根据sql语句，返回对象集合
	 * @param sql语句(参数用冒号加参数名，例如select * from tb)
	 * @param clazz类型
	 * @return bean对象集合
	 */
	public List findListBean(final String sql,Class clazz);
	
	/**
	 * 根据sql语句，返回对象集合
	 * @param sql语句(参数用冒号加参数名，例如select * from tb where id=:id)
	 * @param clazz类型
	 * @param beanParameters参数集合,对象包含条件
	 * @return bean对象集合
	 */
	public List findListBeanByBean(final String sql,Class clazz,Object beanParameters);
	
	/**
	 * 根据sql语句，返回对象集合
	 * @param sql语句(参数用冒号加参数名，例如select * from tb where id=?)
	 * @param clazz类型
	 * @param arrayParameters参数集合,可以用逗号分隔。
	 * @return bean对象集合
	 */
	public List findListBeanByArray(final String sql,Class clazz,Object... arrayParameters);
	
	/**
	 * 根据sql语句，返回对象
	 * @param sql语句(参数用冒号加参数名，例如select * from tb where id=:id)
	 * @param clazz类型
	 * @param mapParameters参数集合(key为参数名，value为参数值)
	 * @return bean对象
	 */
	public Object findUniqueBeanByMap(final String sql,Class clazz,Map mapParameters);
	
	/**
	 * 根据sql语句，返回对象
	 * @param sql语句(参数用冒号加参数名，例如select * from tb where id=?)
	 * @param clazz类型
	 * @param arrayParameters参数集合,可用逗号分隔。
	 * @return bean对象
	 */
	public Object findUniqueBeanByArray(final String sql,Class clazz,Object... arrayParameters);
	
	/**
	 * 根据sql语句，返回数值型返回结果
	 * @param sql语句(参数用冒号加参数名，例如select count(*) from tb where id=:id)
	 * @param beanParameters参数集合,对象包含条件
	 * @return bean对象
	 */
	public long findLongByBean(final String sql,Object beanParameters);

	/**
	 * 根据sql语句，返回数值型返回结果
	 * @param sql语句(参数用冒号加参数名，例如select count(*) from tb where id=:id)
	 * @param mapParameters参数集合,(key为参数名，value为参数值)
	 * @return bean对象
	 */
	public long findLongByMap(final String sql,Map mapParameters);
	

	/**
	 * 根据sql语句，返回数值型返回结果
	 * @param sql语句(参数用冒号加参数名，例如select count(*) from tb where id=?)
	 * @param arrayParameters参数集合,可用逗号分隔。
	 * @return bean对象
	 */
	public long findLongByArray(final String sql,Object... arrayParameters);
	
	/**
	 * 根据sql语句，返回Map对象,对于某些项目来说，没有准备Bean对象，则可以使用Map代替Key为字段名,value为值
	 * @param sql语句(参数用冒号加参数名，例如select count(*) from tb where id=:id)
	 * @param mapParameters参数集合(key为参数名，value为参数值)
	 * @return bean对象
	 */
	public Map findUniqueMapByMap(final String sql,Map mapParameters);

	/**
	 * 根据sql语句，返回Map对象,对于某些项目来说，没有准备Bean对象，则可以使用Map代替Key为字段名,value为值
	 * @param sql语句(参数用冒号加参数名，例如select count(*) from tb where id=?)
	 * @param arrayParameters参数集合
	 * @return bean对象
	 */
	public Map findUniqueMapByArray(final String sql,Object... arrayParameters);
	
	/**
	 * 根据sql语句，返回Map对象集合
	 * @see findForMap
	 * @param sql语句(参数用冒号加参数名，例如select count(*) from tb where id=:id)
	 * @param mapParameters参数集合(key为参数名，value为参数值)
	 * @return bean对象
	 */
	public List<Map<String,Object>> findListMapByMap(final String sql,Map mapParameters);

	/**
	 * 根据sql语句，返回Map对象集合
	 * @see findForMap
	 * @param sql语句(参数用冒号加参数名，例如select count(*) from tb where id=?)
	 * @param arraryParameters参数集合
	 * @return bean对象
	 */
	public List<Map<String,Object>> findListMapByArray(final String sql,Object... arraryParameters);
	
	/**
	 * 执行insert，update，delete等操作<br>
	 * 例如insert into users (name,login_name,password) values(:name,:loginName,:password)<br>
	 * 参数用冒号,参数为bean的属性名
	 * @param sql
	 * @param beanParameters
	 * @throws Exception 
	 */
	public int executeBean(final String sql,Object beanParameters) throws Exception;

	/**
	 * 执行insert，update，delete等操作<br>
	 * 例如insert into users (name,login_name,password) values(:name,:login_name,:password)<br>
	 * 参数用冒号,参数为Map的key名
	 * @param sql
	 * @param mapParameters
	 * @throws Exception 
	 */
	public int executeMap(final String sql,Map mapParameters) throws Exception;
	
	/**
	 * 执行insert，update，delete等操作<br>
	 * 例如insert into users (name,login_name,password) values(?,?,?)<br>
	 * 参数用问号,参数为数组集合等
	 * @param sql
	 * @param parameters
	 * @throws Exception 
	 */
	public int executeArray(final String sql,Object... arrayParameters) throws Exception;
	
	/**
	 * 批量执行insert，update，delete等操作<br>
	 * 例如insert into users (name,login_name,password) values(:name,:loginName,:password)<br>
	 * 参数用冒号,参数为bean的属性名
	 * @param sql
	 * @param beanParameters bean数据集合
	 * @throws Exception 
	 */
	public int[] executeBatchByCollectionBeans(final String sql,Collection<Object> beanParameters)throws Exception;
	
	/**
	 * 批量执行insert，update，delete等操作<br>
	 * 例如insert into users (name,login_name,password) values(:name,:loginName,:password)<br>
	 * 参数用冒号,参数为bean的属性名
	 * @param sql
	 * @param beanParameters bean数据数组
	 * @throws Exception 
	 */
	public int[] executeBatchByArrayBeans(final String sql,Object... beanParameters)throws Exception;
	
	/**
	 * 批量执行insert，update，delete等操作<br>
	 * 例如insert into users (name,login_name,password) values(:name,:login_name,:password)<br>
	 * 参数用冒号,参数为Map的key名
	 * @param sql
	 * @param mapParameters map数据集合
	 * @throws Exception 
	 */
	public int[] executeBatchByCollectionMaps(final String sql,Collection<Map> mapParameters)throws Exception;
	
	/**
	 * 批量执行insert，update，delete等操作<br>
	 * 例如insert into users (name,login_name,password) values(:name,:login_name,:password)<br>
	 * 参数用冒号,参数为Map的key名
	 * @param sql
	 * @param mapParameters map数据数组
	 * @throws Exception 
	 */
	public int[] executeBatchByArrayMaps(final String sql,Map... mapParameters)throws Exception;
	
	/**
	 * insert时返回主键值
	 * @param tableName表名
	 * @param keyName自增字段名称
	 * @param beanParameters对象bean，例如userName属性对应user_name字段,不建议使用大写单词,如HTMLPath不可以，可以使用htmlPath
	 * @return 自增字段
	 * @throws Exception
	 */
	public long insertBeanGetGeneratedKey(String tableName,String keyName,Object beanParameters) throws Exception;
	
	/**
	 * insert时返回主键值
	 * @param tableName表名
	 * @param keyName自增字段名称
	 * @param mapParameters数据map，key对用字段，建议都小写.
	 * @return 自增字段
	 * @throws Exception
	 */
	public long insertMapGetGeneratedKey(String tableName,String keyName,Map mapParameters) throws Exception;
	
	/**
	 * 通用用分页方法,page对象中返回结果为bean集合
	 * @param sql语句，不用写分页函数、排序，只需要写获取数据的内容，如：select * from tb_table where id=? and user_name like ?
	 * @param clazz返回对象类型
	 * @param arrayParameters参数集合
	 * @param page分页对象
	 * @return
	 */
	public Page findPageListBean(final String sql,Class clazz,Page page,Object... arrayParameters);

	/**
	 * 通用用分页方法,page对象中返回结果为bean集合
	 * @param sql语句，不用写分页函数、排序，只需要写获取数据的内容，如：select * from tb_table where id=:userId and user_name =:userName
	 * @param clazz返回对象类型
	 * @param page分页对象
	 * @param beanParameters参数bean
	 * @return
	 */
	public Page findPageListBeanByBean(final String sql,Class clazz,Page page,Object beanParameters);
	
	/**
	 * 通用用分页方法,page对象中返回结果为bean集合
	 * @param sql语句，不用写分页函数、排序，只需要写获取数据的内容，如：select * from tb_table where id=:user_id and user_name =:user_name
	 * @param clazz返回对象类型
	 * @param page分页对象
	 * @param mapParameters参数map
	 * @return
	 */
	public Page findPageListBeanByMap(final String sql,Class clazz,Page page,Map<String,Object> mapParameters);

	/**
	 * 通用用分页方法,page对象中返回结果为map集合
	 * @param sql语句，不用写分页函数、排序，只需要写获取数据的内容，如：select * from tb_table where id=? and user_name like ?
	 * @param arrayParameters参数集合
	 * @param page分页对象
	 * @return
	 */
	public Page findPageListMap(final String sql,Page page,Object... arrayParameters);
	
	/**
	 * 通用用分页方法,page对象中返回结果为map集合
	 * @param sql语句，不用写分页函数、排序，只需要写获取数据的内容，如：select * from tb_table where id=:user_id and user_name like :user_name
	 * @param page分页对象
	 * @param mapParameters参数map
	 * @return
	 */
	public Page findPageListMapByMap(final String sql,Page page,Map<String,Object> mapParameters);
	
}
