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
		this.clazz=clazz;
		this.jdbcTemplate =jdbcTemplate;
		SimpleEntity simpleEntity=this.clazz.getAnnotation(SimpleEntity.class);
		try{
			Index index = this.clazz.getAnnotation(Index.class);
			if(index!=null){
				this.isIndex=true;
			}
		}catch (Exception e) {
			this.isIndex=false;
		}
		tableName=simpleEntity.tableName();
		pkPropertyName=simpleEntity.pkPropertyName();
		pkFieldName=BeanUtils.getDbFieldName(pkPropertyName);
		where=simpleEntity.where();
		orderBy=simpleEntity.orderBy();
		idName = simpleEntity.IDName();
		isUseIDCreator=simpleEntity.isUseIDCreator();
	}
	
	/**
	 * 保存一个实体.
	 * @param bean实体
	 * @throws Exception
	 */
	public void save(T bean) throws Exception{
		validation();
		String sql="INSERT INTO ".concat(tableName).concat(BeanUtils.getBuildInsertSql(bean));
		logger.info(sql);
		jdbcTemplate.executeBean(sql, bean);
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
		String sql="INSERT INTO ".concat(tableName).concat(BeanUtils.getBuildInsertSql(beans[0]));
		logger.info(sql);	
		jdbcTemplate.executeBatchByArrayBeans(sql, beans);
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
				gpk=IDCreator.getNextId(idName,this.jdbcTemplate);
			}else{
				gpk=IDCreator.getNextId(tableName,this.jdbcTemplate);
			}
			String sql="INSERT INTO ".concat(tableName).concat(BeanUtils.getBuildInsertSql(bean));
			logger.info(sql);
			BeanUtils.setFieldValue(bean, pkPropertyName, gpk);
			jdbcTemplate.executeBean(sql, bean);
		}else{
			gpk=jdbcTemplate.insertBeanGetGeneratedKey(tableName, pkFieldName, bean);
		}
		if(isIndex){
			BeanUtils.setFieldValue(bean, pkPropertyName, gpk);
			SearchIndexCreator.createIndex(bean);
		}
		return (PK)Long.valueOf(gpk);
	}
	
	/**
	 * 修改一个实体.
	 * @param bean实体
	 * @throws Exception
	 */
	public int update(T bean) throws Exception{
		validation();
		String sql="UPDATE ".concat(tableName).concat(" SET ").concat(BeanUtils.getBuildUpdateSql(bean,pkPropertyName));
		logger.info(sql);
		if(isIndex){
			PK pk=(PK)BeanUtils.getFieldValue(bean, pkPropertyName);
			T oldBean = get(pk);
			SearchIndexCreator.removeIndex(oldBean);
			SearchIndexCreator.createIndex(bean);
		}
		return jdbcTemplate.executeBean(sql, bean);
	}

	/**
	 * 修改一系列实体集合.
	 * @param beans实体集合
	 * @throws Exception
	 */
	public int[] updateAll(T... beans) throws Exception{
		validation();
		String sql="UPDATE ".concat(tableName).concat(" SET ").concat(BeanUtils.getBuildUpdateSql(beans[0],pkPropertyName));
		logger.info(sql);
		if(isIndex){
			for(T bean:beans){
				PK pk=(PK)BeanUtils.getFieldValue(bean, pkPropertyName);
				T oldBean = get(pk);
				SearchIndexCreator.removeIndex(oldBean);
				SearchIndexCreator.createIndex(bean);
			}
		}
		return jdbcTemplate.executeBatchByArrayBeans(sql, beans);
	}
	
	/**
	 * 删除一个实体.
	 * @param pk
	 * @throws Exception
	 */
	public int delete(PK pk)throws Exception{
		validation();
		String sql="DELETE FROM ".concat(tableName).concat(" WHERE ".concat(pkFieldName).concat("=?"));
		logger.info(sql);
		if(isIndex){
			T oldBean = get(pk);
			SearchIndexCreator.removeIndex(oldBean);
		}
		return jdbcTemplate.executeArray(sql, pk);
	}

	/**
	 * 根据指定字段删除若干数据.
	 * @param 属性名或字段名
	 * @throws Exception
	 */
	public int deleteByProperty(String name,Object value)throws Exception{
		validation();
		String fieldName = BeanUtils.getDbFieldName(name);
		String sql="DELETE FROM ".concat(tableName).concat(" WHERE ".concat(fieldName).concat("=?"));
		logger.info(sql);
		if(isIndex){
			Collection<T> oldBeans = getAllByProperty(name, value);
			for(T oldBean : oldBeans){
				SearchIndexCreator.removeIndex(oldBean);
			}
		}
		return jdbcTemplate.executeArray(sql, value);
	}
	
	/**
	 * 根据主键数组删除对应的实体集合,采用IN方式进行拼接，建议每次不要超过100个主键.
	 * @param pks
	 * @throws Exception
	 */
	public int deleteAll(PK... pks)throws Exception{
		validation();
		String sql="DELETE FROM ".concat(tableName).concat(" WHERE ".concat(pkFieldName).concat(" IN ("));
		for(int i=0;i<pks.length;i++){
			sql=sql.concat("?,");
		}
		sql=sql.substring(0,sql.length()-1).concat(")");
		logger.info(sql);
		if(isIndex){
			for(PK pk : pks){
				T oldBean = get(pk);
				SearchIndexCreator.removeIndex(oldBean);
			}
		}
		return jdbcTemplate.executeArray(sql, (Object[])pks);
	}
	
	/**
	 * 返回一个实体
	 * @param pk主键
	 * @return 实体
	 */
	public T get(PK pk){
		validation();
		String sql=buildWhereSql("SELECT * "+getReferenceSql()+" FROM ",pk);
		if(where!=null&&!where.isEmpty()){
			sql=sql.concat(" AND ".concat(where));
		}
		logger.info(sql);
		return (T)jdbcTemplate.findUniqueBeanByArray(sql,clazz,pk);
	}
	
	/**
	 * 返回分页实体集合
	 * @param page分页实体
	 * @return 分页实体
	 */
	public Page getAll(Page page){
		validation();
		String sql="SELECT * "+getReferenceSql()+" FROM ".concat(tableName+" AS x");
		if(where!=null&&!where.isEmpty()){
			sql=sql.concat(" WHERE ".concat(where));
		}
		if(orderBy!=null&&!orderBy.isEmpty()){
			sql=sql.concat(" ORDER BY ".concat(orderBy));
		}
		logger.info(sql);
		return jdbcTemplate.findPageListBean(sql, clazz, page);
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
	 * 返回所有实体集合
	 * @return 所有实体
	 */
	public Collection<T> getAll(){
		validation();
		String sql="SELECT * "+getReferenceSql()+" FROM ".concat(tableName+" AS x");
		if(where!=null&&!where.isEmpty()){
			sql=sql.concat(" WHERE ".concat(where));
		}
		if(orderBy!=null&&!orderBy.isEmpty()){
			sql=sql.concat(" ORDER BY ".concat(orderBy));
		}
		logger.info(sql);
		return jdbcTemplate.findListBean(sql, clazz);
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
		String sql="SELECT * "+getReferenceSql()+" FROM ".concat(tableName).concat(" AS x WHERE 1=1");
		if(where!=null&&!where.isEmpty()){
			sql=sql.concat(" AND ".concat(where));
		}
		if(ids!=null&&!ids.isEmpty()){
			sql=sql.concat(" AND ".concat(pkFieldName).concat(" IN("));
			for(int i=0 ; i<ids.size();i++){
				sql=sql.concat("?,");
			}
			sql=sql.substring(0,sql.length()-1).concat(")");
		}
		if(orderBy!=null&&!orderBy.isEmpty()){
			sql=sql.concat(" ORDER BY ".concat(orderBy));
		}
		logger.info(sql);
		return jdbcTemplate.findListBeanByArray(sql, clazz,ids.toArray());
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
		return sql.concat(tableName+" AS x").concat(" WHERE ".concat(pkFieldName).concat("=?"));
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
		String sql="SELECT * "+getReferenceSql()+" FROM ".concat(tableName+" AS x");
		if(where!=null&&!where.isEmpty()){
			sql=sql.concat(" WHERE ".concat(where));
		}
		if(filterBean!=null){
			if(where==null||where.isEmpty()){
				sql=sql.concat(" WHERE 1=1 ");
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
    				sql=sql.concat(" "+logical+" "+comparisonField+comparison+"?");
    				arrayParameters.add(propertyValue);
    			}
    		}
		}
		if(orderBy!=null&&!orderBy.isEmpty()){
			sql=sql.concat(" ORDER BY ".concat(orderBy));
		}
		return sql;
	}
	
	private String getSqlByProperty(String name){
		String sql="SELECT * "+getReferenceSql()+" FROM ".concat(tableName+" AS x");
		if(where!=null&&!where.isEmpty()){
			sql=sql.concat(" WHERE ".concat(where));
		}
		if(name!=null&&!name.trim().equals("")){
			if(where==null||where.isEmpty()){
				sql = sql.concat(" WHERE 1=1 ");
			}
			String fieldName = BeanUtils.getDbFieldName(name);
			sql = sql.concat(" AND "+fieldName+"=? ");
		}
		if(orderBy!=null&&!orderBy.isEmpty()){
			sql=sql.concat(" ORDER BY ".concat(orderBy));
		}
		return sql;
	}
	
	/**
	 * 得到引用SQL
	 * @return
	 */
	private String getReferenceSql(){
		String sql = "";
		PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(clazz);
		for (int i = 0; i < descriptors.length; i++) {
			String propertyName = descriptors[i].getName();
			if(isReference(clazz,propertyName)){
				String asFieldName = BeanUtils.getDbFieldName(propertyName);
				String refTableName = getReferenceTableName(clazz,propertyName);
				String refPKName = BeanUtils.getDbFieldName(getReferencePKName(clazz,propertyName));
				String fkName = BeanUtils.getDbFieldName(getReferenceFKName(clazz,propertyName));
				String fieldName = BeanUtils.getDbFieldName(getReferenceViewName(clazz,propertyName));
				sql = sql.concat(",(SELECT "+fieldName+" FROM "+refTableName+" AS y ");
				sql = sql.concat("WHERE y."+refPKName+" = x."+fkName+" ) AS "+asFieldName);
			}
		}
		return sql;
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
