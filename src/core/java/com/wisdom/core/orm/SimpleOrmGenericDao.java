package com.wisdom.core.orm;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import javax.sql.DataSource;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.wisdom.core.annotation.DynamicWhere;
import com.wisdom.core.annotation.Index;
import com.wisdom.core.annotation.Reference;
import com.wisdom.core.annotation.SimpleEntity;
import com.wisdom.core.dao.CountSqlBuilder;
import com.wisdom.core.dao.GenericDaoFactory;
import com.wisdom.core.dao.IDCreator;
import com.wisdom.core.dao.JdbcTemplate;
import com.wisdom.core.search.SearchIndexCreator;
import com.wisdom.core.utils.BeanUtils;
import com.wisdom.core.utils.Page;

/**
 * 功能描述(Description):<br>简单支持泛型通用DAO,仿造hiberante操作习惯,提供简单的CRUD操作.<br>
 * 使用该通用DAO被操作的实体必须使用@SimpleEntity注解进行声明.<br>
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2010-5-23下午06:29:55</b>
 * <br>项目名称(Project Name): <b>wisdom.3.0RC2</b>
 * <br>包及类名(Package Class): <b>com.wisdom.core.orm.SimpleOrmGenericDao.java</b>
 */
@SuppressWarnings("unchecked")
public class SimpleOrmGenericDao <T, PK extends Serializable>{
	private Logger logger = LoggerFactory.getLogger(getClass());
	public JdbcTemplate jdbcTemplate;
	private Class<T> clazz;
	private String tableName;
	private String pkFieldName;
	private String pkPropertyName;
	private String where;
	private String orderBy;
	private String idName;
	private boolean isUseIDCreator = false;
	private boolean isIndex = false;
	
	/**
	 * 构建简单JDBC通用DAO
	 * @param dataSource数据源实例
	 * @param clazz实体类型
	 */
	public SimpleOrmGenericDao(DataSource dataSource,Class<T> clazz) {
		this.clazz = clazz;
		jdbcTemplate = GenericDaoFactory.getJdbcTemplate(dataSource);
		SimpleEntity simpleEntity = this.clazz.getAnnotation(SimpleEntity.class);
		try{
			Index index = this.clazz.getAnnotation(Index.class);
			if(index!=null){
				this.isIndex = true;
			}
		}catch (Exception e) {
			this.isIndex = false;
		}
		tableName = simpleEntity.tableName();
		pkPropertyName = simpleEntity.pkPropertyName();
		pkFieldName = BeanUtils.getDbFieldName(pkPropertyName);
		where = simpleEntity.where();
		orderBy = simpleEntity.orderBy();
		idName = simpleEntity.IDName();
		isUseIDCreator = simpleEntity.isUseIDCreator();
	}
	/**
	 * 构建简单JDBC通用DAO
	 * @param jdbcTemplate
	 * @param clazz实体类型
	 */
	public SimpleOrmGenericDao(JdbcTemplate jdbcTemplate,Class<T> clazz) {
		this.clazz = clazz;
		this.jdbcTemplate = jdbcTemplate;
		SimpleEntity simpleEntity=this.clazz.getAnnotation(SimpleEntity.class);
		try{
			Index index = this.clazz.getAnnotation(Index.class);
			if(index!=null){
				this.isIndex=true;
			}
		}catch (Exception e) {
			this.isIndex=false;
		}
		tableName = simpleEntity.tableName();
		pkPropertyName = simpleEntity.pkPropertyName();
		pkFieldName = BeanUtils.getDbFieldName(pkPropertyName);
		where = simpleEntity.where();
		orderBy = simpleEntity.orderBy();
		idName = simpleEntity.IDName();
		isUseIDCreator = simpleEntity.isUseIDCreator();
	}
	
	/**
	 * 保存一个实体.
	 * @param bean实体
	 * @throws Exception
	 */
	public void save(T bean) throws Exception{
		validation();
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ").append(tableName).append(BeanUtils.getBuildInsertSql(bean));
		logger.info(sql.toString());
		jdbcTemplate.executeBean(sql.toString(), bean);
		if(isIndex){
			SearchIndexCreator.createIndex(bean);
		}
	}

	/**
	 * 保存一系列实体集合.
	 * @param beans实体集合
	 * @throws Exception
	 */
	public void saveAll(T... beans) throws Exception{
		validation();
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ").append(tableName).append(BeanUtils.getBuildInsertSql(beans[0]));
		logger.info(sql.toString());	
		jdbcTemplate.executeBatchByArrayBeans(sql.toString(), beans);
		if(isIndex){
			for(T bean:beans){
				SearchIndexCreator.createIndex(bean);
			}
		}
	}
	
	/**
	 * 保存一个实体，并返回产生的自增值.
	 * @param bean实体
	 * @return PK主键
	 * @throws Exception
	 */
	public PK _save(T bean) throws Exception{
		validation();
		long gpk = -1;
		if(isUseIDCreator){
			if(idName!=null&&!idName.trim().isEmpty()){
				gpk=IDCreator.getNextId(tableName);
			}else{
				gpk=IDCreator.getNextId(tableName);
			}
			BeanUtils.setFieldNumberValue(bean, pkPropertyName, gpk);
			save(bean);
		}else{
			if(GenericDaoFactory.type.equals("oracle")){
				String seqName = "";
				if(idName!=null&&!idName.trim().isEmpty()){
					seqName = idName;
				}else{
					seqName = "SEQ_"+tableName;
				}
				seqName = seqName.toUpperCase();
				long isSeqExist = jdbcTemplate.findLongByArray("SELECT COUNT(1) FROM user_sequences WHERE sequence_name = ?", seqName);
				if(isSeqExist<1){
					jdbcTemplate.executeArray("CREATE SEQUENCE "+seqName);
				}
				gpk = jdbcTemplate.findLongByArray("SELECT "+seqName+".NEXTVAL FROM DUAL");
				BeanUtils.setFieldNumberValue(bean, pkPropertyName, gpk);
				save(bean);
			}else{
				gpk=jdbcTemplate.insertBeanGetGeneratedKey(tableName, pkFieldName, bean);
				BeanUtils.setFieldNumberValue(bean, pkPropertyName, gpk);
				if(isIndex){
					SearchIndexCreator.createIndex(bean);
				}
			}
		}
		return (PK) BeanUtils.getFieldValue(bean, pkPropertyName);
	}
	
	/**
	 * 修改一个实体.
	 * @param bean实体
	 * @throws Exception
	 */
	public int update(T bean) throws Exception{
		validation();
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE ").append(tableName).append(" SET ").append(BeanUtils.getBuildUpdateSql(bean,pkPropertyName));
		logger.info(sql.toString());
		if(isIndex){
			PK pk=(PK)BeanUtils.getFieldValue(bean, pkPropertyName);
			T oldBean = get(pk);
			SearchIndexCreator.removeIndex(oldBean);
			SearchIndexCreator.createIndex(bean);
		}
		return jdbcTemplate.executeBean(sql.toString(), bean);
	}

	/**
	 * 修改一个实体，如果某属性值为空NULL，则不参加更新操作.
	 * @param bean实体
	 * @throws Exception
	 */
	public int updateNotNull(T bean) throws Exception{
		validation();
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE ").append(tableName).append(" SET ").append(BeanUtils.getBuildUpdateSqlNotNull(bean,pkPropertyName));
		logger.info(sql.toString());
		if(isIndex){
			PK pk=(PK)BeanUtils.getFieldValue(bean, pkPropertyName);
			T oldBean = get(pk);
			SearchIndexCreator.removeIndex(oldBean);
			SearchIndexCreator.createIndex(bean);
		}
		return jdbcTemplate.executeBean(sql.toString(), bean);
	}

	/**
	 * 修改一系列实体集合.
	 * @param beans实体集合
	 * @throws Exception
	 */
	public int[] updateAll(T... beans) throws Exception{
		validation();
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE ").append(tableName).append(" SET ").append(BeanUtils.getBuildUpdateSql(beans[0],pkPropertyName));
		logger.info(sql.toString());
		if(isIndex){
			for(T bean:beans){
				PK pk=(PK)BeanUtils.getFieldValue(bean, pkPropertyName);
				T oldBean = get(pk);
				SearchIndexCreator.removeIndex(oldBean);
				SearchIndexCreator.createIndex(bean);
			}
		}
		return jdbcTemplate.executeBatchByArrayBeans(sql.toString(), beans);
	}
	
	/**
	 * 删除一个实体.
	 * @param pk
	 * @throws Exception
	 */
	public int delete(PK pk)throws Exception{
		validation();
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM ").append(tableName).append(" WHERE ").append(pkFieldName).append("=?");
		logger.info(sql.toString());
		if(isIndex){
			T oldBean = get(pk);
			SearchIndexCreator.removeIndex(oldBean);
		}
		return jdbcTemplate.executeArray(sql.toString(), pk);
	}

	/**
	 * 根据指定字段删除若干数据.
	 * @param 属性名或字段名
	 * @throws Exception
	 */
	public int deleteByProperty(String name,Object value)throws Exception{
		validation();
		String fieldName = BeanUtils.getDbFieldName(name);
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM ").append(tableName).append(" WHERE ").append(fieldName).append("=?");
		logger.info(sql.toString());
		if(isIndex){
			Collection<T> oldBeans = getAllByProperty(name, value);
			for(T oldBean : oldBeans){
				SearchIndexCreator.removeIndex(oldBean);
			}
		}
		return jdbcTemplate.executeArray(sql.toString(), value);
	}
	
	/**
	 * 根据主键数组删除对应的实体集合,采用IN方式进行拼接，建议每次不要超过100个主键.
	 * @param pks
	 * @throws Exception
	 */
	public int deleteAll(PK... pks)throws Exception{
		validation();
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM ").append(tableName).append(" WHERE ").append(pkFieldName).append(" IN (");
		for(int i=0;i<pks.length;i++){
			sql.append("?,");
		}
		sql.delete(sql.length()-1, sql.length()).append(")");
		logger.info(sql.toString());
		if(isIndex){
			for(PK pk : pks){
				T oldBean = get(pk);
				SearchIndexCreator.removeIndex(oldBean);
			}
		}
		return jdbcTemplate.executeArray(sql.toString(), (Object[])pks);
	}
	
	/**
	 * 返回一个实体
	 * @param pk主键
	 * @return 实体
	 */
	public T get(PK pk){
		validation();
		StringBuffer sql = new StringBuffer();
		sql.append(buildWhereSql("SELECT x.* "+getReferenceSql()+" FROM ",pk));
		if(where!=null&&!where.isEmpty()){
			sql.append(" AND ").append(where);
		}
		logger.info(sql.toString());
		return (T)jdbcTemplate.findUniqueBeanByArray(sql.toString(),clazz,pk);
	}
	
	/**
	 * 返回分页实体集合
	 * @param page分页实体
	 * @return 分页实体
	 */
	public Page getAll(Page page){
		validation();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT x.* ").append(getReferenceSql()).append(" FROM ").append(tableName).append(" x");
		if(where!=null&&!where.isEmpty()){
			sql.append(" WHERE ").append(where);
		}
		if(orderBy!=null&&!orderBy.isEmpty()){
			sql.append(" ORDER BY ").append(orderBy);
		}
		logger.info(sql.toString());
		return jdbcTemplate.findPageListBean(sql.toString(), clazz, page);
	}
	
	/**
	 * 返回分页实体集合
	 * @param page分页实体
	 * @param filterBean查询条件实体
	 * @return 分页实体
	 */
	public Page getAllByFilter(Page page,T filterBean){
		validation();
		Collection<Object> arrayParameters = new LinkedList<Object>();
		String sql=getFilterSql(filterBean,arrayParameters);
		logger.info(sql);
		if(filterBean!=null&&arrayParameters!=null&&!arrayParameters.isEmpty()){
			return jdbcTemplate.findPageListBean(sql, clazz, page,arrayParameters.toArray());
		}
		return jdbcTemplate.findPageListBean(sql, clazz, page);
	}

	/**
	 * 返回实体集合
	 * @param filterBean查询条件实体
	 * @return 实体集合
	 */
	public Collection<T> getAllByFilter(T filterBean){
		validation();
		Collection<Object> arrayParameters = new LinkedList<Object>();
		String sql=getFilterSql(filterBean,arrayParameters);
		logger.info(sql);
		if(filterBean!=null&&arrayParameters!=null&&!arrayParameters.isEmpty()){
			return jdbcTemplate.findListBeanByArray(sql, clazz, arrayParameters.toArray());
		}
		return jdbcTemplate.findListBean(sql, clazz);
	}

	/**
	 * 根据属性名或字段名获得分页数据集合
	 * @param name属性名或字段名
	 * @param value所载值
	 * @return 分页实体
	 */
	public Page getAllByProperty(Page page,String name,Object value){
		validation();
		Assert.notNull(name,"属性名或字段名不能为空！");
		Assert.notNull(value,"属性值或字段值不能为空！");
		String sql=getSqlByProperty(name);
		logger.info(sql);
		return jdbcTemplate.findPageListBean(sql, clazz, page, value);
	}

	/**
	 * 根据属性名或字段名获得数据
	 * @param name属性名或字段名
	 * @param value所载值
	 * @return 实体集合
	 */
	public Collection<T> getAllByProperty(String name,Object value){
		validation();
		Assert.notNull(name,"属性名或字段名不能为空！");
		Assert.notNull(value,"属性值或字段值不能为空！");
		String sql=getSqlByProperty(name);
		logger.info(sql);
		return jdbcTemplate.findListBeanByArray(sql, clazz, value);
	}
	
	/**
	 * 根据某个实体的属性值判断是否存在
	 * @param name属性名或字段名
	 * @param value所载值
	 * @return 是否存在(true为存在)
	 */
	public boolean isExist(String name,Object value){
		validation();
		Assert.notNull(name,"属性名或字段名不能为空！");
		Assert.notNull(value,"属性值或字段值不能为空！");
		String sql=getSqlByProperty(name);
		long count=jdbcTemplate.findLongByArray(CountSqlBuilder.getCountSql(sql), value);
		if(count>0){
			return true;
		}
		return false;
	}
	
	
	/**
	 * 返回所有实体集合
	 * @return 所有实体
	 */
	public Collection<T> getAll(){
		validation();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT x.* ").append(getReferenceSql()).append(" FROM ").append(tableName).append(" x");
		if(where!=null&&!where.isEmpty()){
			sql.append(" WHERE ").append(where);
		}
		if(orderBy!=null&&!orderBy.isEmpty()){
			sql.append(" ORDER BY ").append(orderBy);
		}
		logger.info(sql.toString());
		return jdbcTemplate.findListBean(sql.toString(), clazz);
	}
	
	/**
	 * 通过select语句返回所有实体集合,稍复杂的查询自己定义语句.
	 * @param sql
	 * @param parameters
	 * @return 所有实体
	 */
	public Collection<T> getAll(String sql,Object... parameters){
		validation();
		logger.info(sql);
		return jdbcTemplate.findListBeanByArray(sql, clazz, parameters);
	}
	
	/**
	 * 通过搜索引擎查询数据
	 * @param queryText查询关键字
	 * @return
	 */
	public Collection<T> getAllByText(String queryText){
		validation();
		Collection<PK> ids=(Collection<PK>) SearchIndexCreator.searchKeyword(clazz, queryText);
		if(ids==null||ids.isEmpty()){
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT x.* ").append(getReferenceSql()).append(" FROM ").append(tableName).append(" x WHERE 1=1");
		if(where!=null&&!where.isEmpty()){
			sql.append(" AND ").append(where);
		}
		if(ids!=null&&!ids.isEmpty()){
			sql.append(" AND ").append(pkFieldName).append(" IN(");
			for(int i=0 ; i<ids.size();i++){
				sql.append("?,");
			}
			sql.delete(sql.length()-1, sql.length()).append(")");
		}
		if(orderBy!=null&&!orderBy.isEmpty()){
			sql.append(" ORDER BY ").append(orderBy);
		}
		logger.info(sql.toString());
		return jdbcTemplate.findListBeanByArray(sql.toString(), clazz,ids.toArray());
	}
	
	/**
	 * 验证该dao是否可以使用.
	 */
	private void validation(){
		Assert.notNull(jdbcTemplate,"jdbcTemplate实例为空,请合理配置Spring容器!");
		Assert.notNull(clazz,"clazz实例为空,请合理配置Spring容器!");
		Assert.notNull(tableName,"tableName表名不能为空!请使用@SimpleEntity注解加入实体");
		Assert.notNull(pkFieldName,"tableName表名不能为空!请使用@SimpleEntity注解加入实体");
		Assert.notNull(pkPropertyName,"tableName表名不能为空!请使用@SimpleEntity注解加入实体");
	}
	
	/**
	 * 构建where语句
	 * @param sql语句
	 * @param pk主键
	 * @return 构建好的sql
	 */
	private String buildWhereSql(String sql,PK pk){
		StringBuffer _sql = new StringBuffer();
		_sql.append(sql).append(tableName).append(" x").append(" WHERE ").append(pkFieldName).append("=?");
		return _sql.toString();
	}
	
	/**
	 * 判断是否是动态where注解
	 * @param clazz
	 * @param propertyName
	 * @return
	 */
	private static boolean isDynamicWhere(Class clazz,String propertyName){
		DynamicWhere dynamicWhere=null;
		try {
			dynamicWhere = clazz.getDeclaredField(propertyName).getAnnotation(DynamicWhere.class);
		} catch (Exception e) {}
		if(dynamicWhere!=null){
			return true;
		}
		return false;
	}
	
	/**
	 * 得到过滤条件SQL
	 * @param filterBean
	 * @param arrayParameters
	 * @return
	 */
	private String getFilterSql(T filterBean,Collection<Object> arrayParameters){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT x.* ").append(getReferenceSql()).append(" FROM ").append(tableName).append(" x");
		if(where!=null&&!where.isEmpty()){
			sql.append(" WHERE ").append(where);
		}
		if(filterBean!=null){
			if(where==null||where.isEmpty()){
				sql.append(" WHERE 1=1 ");
			}
			PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(filterBean);
    		for (int i = 0; i < descriptors.length; i++) {
    			String propertyName = descriptors[i].getName();
    			if(isDynamicWhere(filterBean.getClass(),propertyName)){
    				Object propertyValue = null;
					propertyValue = BeanUtils.getFieldValue(filterBean, propertyName);
					if(propertyValue==null||propertyValue.toString().trim().equals("")){
						continue;
					}
    				String logical = getDynamicWhereLogical(filterBean.getClass(),propertyName);
    				String comparison = getDynamicWhereComparison(filterBean.getClass(),propertyName);
    				String comparisonField = getDynamicWhereComparisonField(filterBean.getClass(),propertyName);
    				if(comparisonField.trim().isEmpty()){
    					comparisonField = BeanUtils.getDbFieldName(propertyName);
    				}else{
    					comparisonField = BeanUtils.getDbFieldName(comparisonField);
    				}
    				sql.append(" ").append(logical).append(" ").append(comparisonField).append(comparison).append("?");
    				arrayParameters.add(propertyValue);
    			}
    		}
		}
		if(orderBy!=null&&!orderBy.isEmpty()){
			sql.append(" ORDER BY ").append(orderBy);
		}
		return sql.toString();
	}
	
	private String getSqlByProperty(String name){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT x.* ").append(getReferenceSql()).append(" FROM ").append(tableName).append(" x");
		if(where!=null&&!where.isEmpty()){
			sql.append(" WHERE ").append(where);
		}
		if(name!=null&&!name.trim().equals("")){
			if(where==null||where.isEmpty()){
				sql.append(" WHERE 1=1 ");
			}
			String fieldName = BeanUtils.getDbFieldName(name);
			sql.append(" AND ").append(fieldName).append("=? ");
		}
		if(orderBy!=null&&!orderBy.isEmpty()){
			sql.append(" ORDER BY ").append(orderBy);
		}
		return sql.toString();
	}
	
	/**
	 * 得到引用SQL
	 * @return
	 */
	private String getReferenceSql(){
		StringBuffer sql = new StringBuffer();
		PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(clazz);
		for (int i = 0; i < descriptors.length; i++) {
			String propertyName = descriptors[i].getName();
			if(isReference(clazz,propertyName)){
				String asFieldName = BeanUtils.getDbFieldName(propertyName);
				String refTableName = getReferenceTableName(clazz,propertyName);
				String refPKName = BeanUtils.getDbFieldName(getReferencePKName(clazz,propertyName));
				String fkName = BeanUtils.getDbFieldName(getReferenceFKName(clazz,propertyName));
				String fieldName = BeanUtils.getDbFieldName(getReferenceViewName(clazz,propertyName));
				sql.append(",(SELECT ").append(fieldName).append(" FROM ").append(refTableName).append(" y ");
				sql.append("WHERE y.").append(refPKName).append(" = x.").append(fkName).append(" ) ").append(asFieldName);
			}
		}
		return sql.toString();
	}
	
	/**
	 * 判断是否为引用注解
	 * @param clazz
	 * @param propertyName
	 * @return
	 */
	private static boolean isReference(Class clazz,String propertyName){
		Reference reference=null;
		try {
			reference = clazz.getDeclaredField(propertyName).getAnnotation(Reference.class);
		} catch (Exception e) {}
		if(reference!=null){
			return true;
		}
		return false;
	}
	
	/**
	 * 获得表名
	 * @param clazz
	 * @param propertyName
	 * @return
	 */
	private static String getReferenceTableName(Class clazz,String propertyName){
		Reference reference=null;
		try {
			reference = clazz.getDeclaredField(propertyName).getAnnotation(Reference.class);
		} catch (Exception e) {}
		if(reference!=null){
			return reference.refTableName();
		}
		return null;
	}
	
	/**
	 * 得到引用注解的引用主键名
	 * @param clazz
	 * @param propertyName
	 * @return
	 */
	private static String getReferencePKName(Class clazz,String propertyName){
		Reference reference=null;
		try {
			reference = clazz.getDeclaredField(propertyName).getAnnotation(Reference.class);
		} catch (Exception e) {}
		if(reference!=null){
			return reference.refPKFieldName();
		}
		return null;
	}
	
	/**
	 * 得到引用注解的外键名
	 * @param clazz
	 * @param propertyName
	 * @return
	 */
	private static String getReferenceFKName(Class clazz,String propertyName){
		Reference reference=null;
		try {
			reference = clazz.getDeclaredField(propertyName).getAnnotation(Reference.class);
		} catch (Exception e) {}
		if(reference!=null){
			return reference.FKFieldName();
		}
		return null;
	}
	
	/**
	 * 得到引用注解的显示的字段名
	 * @param clazz
	 * @param propertyName
	 * @return
	 */
	private static String getReferenceViewName(Class clazz,String propertyName){
		Reference reference=null;
		try {
			reference = clazz.getDeclaredField(propertyName).getAnnotation(Reference.class);
		} catch (Exception e) {}
		if(reference!=null){
			return reference.refViewFieldName();
		}
		return null;
	}
	
	/**
	 * 获得动态条件查询的逻辑运算符
	 * @param clazz
	 * @param propertyName
	 * @return
	 */
	private static String getDynamicWhereLogical(Class clazz,String propertyName){
		DynamicWhere dynamicWhere=null;
		try {
			dynamicWhere = clazz.getDeclaredField(propertyName).getAnnotation(DynamicWhere.class);
		} catch (Exception e) {}
		if(dynamicWhere!=null){
			return dynamicWhere.logical();
		}
		return null;
	}
	
	/**
	 * 获得动态条件查询的比较运算符
	 * @param clazz
	 * @param propertyName
	 * @return
	 */
	private static String getDynamicWhereComparison(Class clazz,String propertyName){
		DynamicWhere dynamicWhere=null;
		try {
			dynamicWhere = clazz.getDeclaredField(propertyName).getAnnotation(DynamicWhere.class);
		} catch (Exception e) {}
		if(dynamicWhere!=null){
			return dynamicWhere.comparison();
		}
		return null;
	}

	/**
	 * 获得动态条件查询的比较运算符
	 * @param clazz
	 * @param propertyName
	 * @return
	 */
	private static String getDynamicWhereComparisonField(Class clazz,String propertyName){
		DynamicWhere dynamicWhere=null;
		try {
			dynamicWhere = clazz.getDeclaredField(propertyName).getAnnotation(DynamicWhere.class);
		} catch (Exception e) {}
		if(dynamicWhere!=null){
			return dynamicWhere.comparisonField();
		}
		return null;
	}
	
}
