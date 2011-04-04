package org.cy.core.orm;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.cy.core.commons.EntityUtils;
import org.cy.core.jdbc.GenericJdbcDao;
import org.cy.core.jdbc.dialect.Page;
/**
 * 功 能 描 述:<br>
 * 通用ORM数据操作，功能类似hiberante
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-1下午02:37:54
 * <br>项 目 信 息:paramecium:org.cy.core.orm.GenericOrmDao.java
 */
public final class GenericOrmDao<T , PK extends Serializable>{
	
	private static ConcurrentMap<String, String> sqlCache = new ConcurrentHashMap<String, String>();
	
	private GenericJdbcDao genericJdbcDao;
	
	/**
	 * 默认构造方法会自动加载事务线程
	 */
	public GenericOrmDao(){
		genericJdbcDao = new GenericJdbcDao();
	}

	/**
	 * 该构造方法需要手动控制事务
	 * @param connection
	 */
	public GenericOrmDao(Connection connection) {
		genericJdbcDao = new GenericJdbcDao(connection);
	}

	/**
	 * 插入新实体，如果有自动生成序号，则返回。
	 * @param bean
	 * @return
	 * @throws SQLException
	 */
	public Number insert(T bean) throws SQLException {
		String key = bean.getClass().getName()+":insert";
		String sql = sqlCache.get(key);
		if(sql!=null){
			return genericJdbcDao.insertGetGeneratedKeyByBean(sql, bean);
		}
		sql = EntityUtils.getInsertSql(bean);
		sqlCache.put(key, sql);
		return genericJdbcDao.insertGetGeneratedKeyByBean(sql, bean);
	}

	/**
	 * 批量插入
	 * @param bean
	 * @throws SQLException
	 */
	public void insert(Collection<T> beans) throws SQLException {
		String key = beans.iterator().next().getClass().getName()+":insert";
		String sql = sqlCache.get(key);
		if(sql!=null){
			genericJdbcDao.executeBatchDMLByBeans(sql, beans);
			return;
		}
		sql = EntityUtils.getInsertSql(beans.iterator().next());
		sqlCache.put(key, sql);
		genericJdbcDao.executeBatchDMLByBeans(sql, beans);
	}

	/**
	 * 修改实体
	 * @param bean
	 * @throws SQLException
	 */
	public void update(T bean) throws SQLException {
		String key = bean.getClass().getName()+":update";
		String sql = sqlCache.get(key);
		if(sql!=null){
			genericJdbcDao.executeDMLByBean(sql, bean);
			return;
		}
		sql = EntityUtils.getUpdateSql(bean, true);
		sqlCache.put(key, sql);
		genericJdbcDao.executeDMLByBean(sql, bean);
	}
	
	/**
	 * 只修改值不为空的属性，一般用于小表单对应大表的某几个字段
	 * @param bean
	 * @throws SQLException
	 */
	public void updateNotNull(T bean) throws SQLException {
		String key = bean.getClass().getName()+":update2";
		String sql = sqlCache.get(key);
		if(sql!=null){
			genericJdbcDao.executeDMLByBean(sql, bean);
			return;
		}
		sql = EntityUtils.getUpdateSql(bean, false);
		sqlCache.put(key, sql);
		genericJdbcDao.executeDMLByBean(sql, bean);
	}
	
	/**
	 * 根据单一主键删除实体
	 * @param primaryKey
	 * @throws SQLException
	 */
	public void delete(Class<T> clazz,PK primaryKey)throws SQLException {
		String key = clazz.getName()+":delete";
		String sql = sqlCache.get(key);
		if(sql!=null){
			genericJdbcDao.executeDMLByArray(sql, primaryKey);
			return;
		}
		sql = EntityUtils.getDeleteSql(clazz);
		sqlCache.put(key, sql);
		genericJdbcDao.executeDMLByArray(sql, primaryKey);
	}

	/**
	 * 根据动态条件删除实体，联合主键可以使用该方法进行删除实体
	 * @param whereBean
	 * @throws SQLException
	 */
	public void delete(T whereBean)throws SQLException {
		String sql = EntityUtils.getDeleteSql(whereBean);
		genericJdbcDao.executeDMLByBean(sql, whereBean);
	}
	
	/**
	 * 根据单一主键获得实体信息
	 * @param primaryKey
	 * @return
	 */
	public T select(PK primaryKey){
		return null;
	}

	/**
	 * 获得分页信息
	 * @param page
	 * @return
	 */
	public Page select(Page page){
		return null;
	}

	/**
	 * 根据动态查询条件获得分页信息
	 * @param page
	 * @param whereBean
	 * @return
	 */
	public Page select(Page page,T whereBean){
		return null;
	}
	
	/**
	 * 根据动态条件获得所有相符的实体集合
	 * @param whereBean
	 * @return
	 */
	public Collection<T> select(T whereBean){
		return null;
	}

}
