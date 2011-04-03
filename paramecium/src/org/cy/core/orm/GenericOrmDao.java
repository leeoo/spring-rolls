package org.cy.core.orm;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.cy.core.jdbc.GenericJdbcDao;
import org.cy.core.jdbc.dialect.Page;
import org.cy.core.log.Log;
import org.cy.core.log.LoggerFactory;
import org.cy.core.orm.annotation.PrimaryKey;
/**
 * 功 能 描 述:<br>
 * 通用ORM数据操作，功能类似hiberante
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-1下午02:37:54
 * <br>项 目 信 息:paramecium:org.cy.core.orm.GenericOrmDao.java
 */
public final class GenericOrmDao<T , PK extends Serializable>{
	
	private static Log logger = LoggerFactory.getLogger();
	
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
		return null;
	}

	/**
	 * 批量插入
	 * @param bean
	 * @throws SQLException
	 */
	public void insert(T... bean) throws SQLException {
		
	}

	/**
	 * 修改实体
	 * @param bean
	 * @throws SQLException
	 */
	public void update(T bean) throws SQLException {
		
	}
	
	/**
	 * 只修改值不为空的属性，一般用于小表单对应大表的某几个字段
	 * @param bean
	 * @throws SQLException
	 */
	public void updateNotNull(T bean) throws SQLException {
		
	}
	
	/**
	 * 根据单一主键删除实体
	 * @param primaryKey
	 * @throws SQLException
	 */
	public void delete(PK primaryKey)throws SQLException {
		
	}

	/**
	 * 根据动态条件删除实体，联合主键可以使用该方法进行删除实体
	 * @param whereBean
	 * @throws SQLException
	 */
	public void delete(T whereBean)throws SQLException {
		
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

	
	private Set<PrimaryKey> getPrimaryKey(Class<?> entityClass){
		Set<PrimaryKey> pks = new HashSet<PrimaryKey>();
		for (Class<?> superClass = entityClass; superClass != Object.class; superClass = superClass.getSuperclass()) {
			Field[] fields = superClass.getDeclaredFields();
			for(Field field : fields){
				try {
					PrimaryKey primaryKey = field.getAnnotation(PrimaryKey.class);
					if(primaryKey!=null){
						pks.add(primaryKey);
					}
				} catch (Exception e) {
				}
			}
		}
		return pks;
	}

}
