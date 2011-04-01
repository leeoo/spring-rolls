package org.cy.core.orm;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.cy.core.jdbc.GenericJdbcDao;
import org.cy.core.orm.annotation.PrimaryKey;
/**
 * 功 能 描 述:<br>
 * 通用ORM数据操作，功能类似hiberante
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-1下午02:37:54
 * <br>项 目 信 息:paramecium:org.cy.core.orm.GenericOrmDao.java
 */
public final class GenericOrmDao<T>{
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

	public Number save(T bean) throws SQLException {
		String sql = null;
		genericJdbcDao.executeDMLByBean(sql, bean);
		return null;
	}

	private String buildInsertSQL(T bean) {
		String key = bean.getClass().getName().concat("_insert");
		String sql = sqlCache.get(key);
		if(sql!=null){
			return sql;
		}
		StringBuffer stringBuffer = new StringBuffer();
		//===此处实现
		sqlCache.put(key, stringBuffer.toString());
		return stringBuffer.toString();
	}
	
	private Set<PrimaryKey> getPrimaryKey(T bean){
		Set<PrimaryKey> pks = new HashSet<PrimaryKey>();
		Class<?> clazz = bean.getClass();
		for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
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
