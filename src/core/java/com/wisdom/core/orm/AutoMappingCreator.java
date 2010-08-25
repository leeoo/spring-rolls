package com.wisdom.core.orm;

import java.beans.PropertyDescriptor;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;

import com.wisdom.core.annotation.SimpleEntity;
import com.wisdom.core.dao.GenericDaoFactory;
import com.wisdom.core.utils.BeanUtils;

/**
 * 功能描述(Description):<br><b>
 * 根据应用侧配置，根据不同数据库以及simpleEntity实体配置情况，生成表结构。
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2010-5-23下午06:24:52</b>
 * <br>项目名称(Project Name): <b>wisdom.3.0RC2</b>
 * <br>包及类名(Package Class): <b>com.wisdom.core.orm.AutoMappingCreator.java</b>
 */

@SuppressWarnings("unchecked")
public class AutoMappingCreator {
	private static Logger logger = LoggerFactory.getLogger(AutoMappingCreator.class);
	private DataSource dataSource;
	private boolean autoCreateTable = false;//判断是否自动创建表
	public List<String> simpleEntitys;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource=dataSource;
	}

	/**
	 * 应用侧配置该方法为入口
	 */
	public void init(){
		if(!autoCreateTable){
			return;
		}
		String dbType=GenericDaoFactory.type.toLowerCase();
		for(String entity:simpleEntitys){
			String sql=null;
			if(dbType.equals("mysql")){
				try {
					sql=buildCreateSqlByMySql(entity);
				} catch (Exception e) {
					logger.error("构建建表语句出现错误{}",e.getMessage());
				}
			}else if(dbType.equals("hsql")){
				try {
					sql=buildCreateSqlByHsql(entity);
				} catch (Exception e) {
					logger.error("构建建表语句出现错误{}",e.getMessage());
				}
			}else if(dbType.equals("oracle")){
				try {
					sql=buildCreateSqlByOracle(entity);
				} catch (Exception e) {
					logger.error("构建建表语句出现错误{}",e.getMessage());
				}
			}else if(dbType.indexOf("sqlserver")>-1){
				try {
					sql=buildCreateSqlBySqlServer(entity);
				} catch (Exception e) {
					logger.error("构建建表语句出现错误{}",e.getMessage());
				}
			}else if(dbType.equals("db2")){
				try {
					sql=buildCreateSqlByDB2(entity);
				} catch (Exception e) {
					logger.error("构建建表语句出现错误{}",e.getMessage());
				}
			}
			if(sql!=null){
				createTable(sql);
			}else{
				logger.error("无法创建表{}",entity);
			}
		}
	}
	
	private void createTable(String sql){
		Connection connection=null;
		Statement statement=null;
		try {
			connection = dataSource.getConnection();
			statement = connection.createStatement();
			statement.execute(sql);
			connection.commit();
			logger.info("创建表成功{}",sql);
		} catch (Exception e){
			logger.error("创建表失败{}",e.getMessage());
		}
		try{statement.close();}catch(Exception e){}
		try{connection.close();}catch(Exception e){}
	}
	
	private String buildCreateSqlByDB2(String entity) throws Exception{
		Class clazz=Class.forName(entity);
		logger.info("类型为{}",clazz.getName());
		SimpleEntity simpleEntity=AnnotationUtils.findAnnotation(clazz, SimpleEntity.class);
		Assert.notNull(simpleEntity,clazz.getName().concat("没有符合SimpleEntity注解"));
		String tableName=simpleEntity.tableName();
		String sql="CREATE TABLE ".concat(tableName).concat("( ");
		String pkPropertyName=simpleEntity.pkPropertyName();
		String pkFieldName=BeanUtils.getDbFieldName(pkPropertyName);
		sql=sql.concat(pkFieldName).concat(" INT(11) NOT NULL IDENTITY(1,1) PRIMARY KEY ,");
		PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(clazz);
		for (int i = 0; i < descriptors.length; i++){
			String propertyName = descriptors[i].getName();
			if(!propertyName.equalsIgnoreCase("class")&&!propertyName.equals(pkPropertyName)&&!BeanUtils.isNotMapping(clazz,propertyName)){
				String fieldName = BeanUtils.getDbFieldName(propertyName);
				sql=sql.concat(" ".concat(fieldName)).concat(getTypeAndLengthByDB2(clazz,descriptors[i]));
				if(BeanUtils.isUnique(clazz,propertyName)){
					sql=sql.concat(" UNIQUE");
				}
				if(BeanUtils.isNotNull(clazz,propertyName)){
					sql=sql.concat(" NOT NULL");
				}
				sql=sql.concat(",");
			}
		}
		sql=sql.substring(0,sql.length()-1).concat(" )");
		return sql;
	}
	
	private String getTypeAndLengthByDB2(Class clazz,PropertyDescriptor descriptor){
		Class propertyType=descriptor.getPropertyType();
		String sql=" ";
		int length=BeanUtils.getMaxLength(clazz,descriptor.getName());
		if (String.class.equals(propertyType)) {
			if(length<=0){
				sql=sql.concat("VARCHAR(255)");
			}else if(length>0&&length<4000){
				sql=sql.concat("VARCHAR(".concat(""+length)).concat(")");
			}else{
				sql=sql.concat("LONGVARCHAR(".concat(""+length)).concat(")");
			}
		}else if (boolean.class.equals(propertyType) || Boolean.class.equals(propertyType)) {
			sql=sql.concat("INT(1)");
		}else if (byte.class.equals(propertyType) || Byte.class.equals(propertyType)) {
			sql=sql.concat("INT(1)");
		}else if (short.class.equals(propertyType) || Short.class.equals(propertyType)) {
			//...
		}else if (int.class.equals(propertyType) || Integer.class.equals(propertyType)) {
			if(length>0){
				sql=sql.concat("INT(".concat(""+length)).concat(")");
			}else{
				sql=sql.concat("INT(8)");
			}
		}else if (long.class.equals(propertyType) || Long.class.equals(propertyType)){
			if(length>0){
				sql=sql.concat("INT(".concat(""+length)).concat(")");
			}else{
				sql=sql.concat("INT(16)");
			}
		}else if (float.class.equals(propertyType) || Float.class.equals(propertyType)) {
			if(length>0){
				sql=sql.concat("NUMERIC(".concat(""+length)).concat(",5)");
			}else{
				sql=sql.concat("NUMERIC(16,5)");
			}
		}else if (double.class.equals(propertyType) || Double.class.equals(propertyType) || Number.class.equals(propertyType)) {
			if(length>0){
				sql=sql.concat("NUMERIC(".concat(""+length)).concat(",8)");
			}else{
				sql=sql.concat("NUMERIC(24,8)");
			}
		}else if (byte[].class.equals(propertyType)) {
			sql=sql.concat("BLOB");
		}else if (java.sql.Date.class.equals(propertyType)) {
			sql=sql.concat("DATE");
		}else if (java.sql.Time.class.equals(propertyType)) {
			sql=sql.concat("TIME");
		}else if (java.sql.Timestamp.class.equals(propertyType) || java.util.Date.class.equals(propertyType)) {
			sql=sql.concat("TIMESTAMP");
		}else if (java.math.BigDecimal.class.equals(propertyType)) {
			if(length>0){
				sql=sql.concat("DECIMAL(".concat(""+length)).concat(",5)");
			}else{
				sql=sql.concat("DECIMAL(11,5)");
			}
		}else if (java.sql.Blob.class.equals(propertyType)) {
			sql=sql.concat("BLOB");
		}else if (java.sql.Clob.class.equals(propertyType)) {
			sql=sql.concat("TEXT");
		}
		return sql;
	}
	
	private String buildCreateSqlBySqlServer(String entity) throws Exception{
		Class clazz=Class.forName(entity);
		logger.info("类型为{}",clazz.getName());
		SimpleEntity simpleEntity=AnnotationUtils.findAnnotation(clazz, SimpleEntity.class);
		Assert.notNull(simpleEntity,clazz.getName().concat("没有符合SimpleEntity注解"));
		String tableName=simpleEntity.tableName();
		String sql="CREATE TABLE ".concat(tableName).concat("( ");
		String pkPropertyName=simpleEntity.pkPropertyName();
		String pkFieldName=BeanUtils.getDbFieldName(pkPropertyName);
		sql=sql.concat(pkFieldName).concat(" INT NOT NULL IDENTITY(1,1) PRIMARY KEY ,");
		PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(clazz);
		for (int i = 0; i < descriptors.length; i++){
			String propertyName = descriptors[i].getName();
			if(!propertyName.equalsIgnoreCase("class")&&!propertyName.equals(pkPropertyName)&&!BeanUtils.isNotMapping(clazz,propertyName)){
				String fieldName = BeanUtils.getDbFieldName(propertyName);
				sql=sql.concat(" ".concat(fieldName)).concat(getTypeAndLengthBySqlServer(clazz,descriptors[i]));
				if(BeanUtils.isUnique(clazz,propertyName)){
					sql=sql.concat(" UNIQUE");
				}
				if(BeanUtils.isNotNull(clazz,propertyName)){
					sql=sql.concat(" NOT NULL");
				}
				sql=sql.concat(",");
			}
		}
		sql=sql.substring(0,sql.length()-1).concat(" )");
		return sql;
	}
	
	private String getTypeAndLengthBySqlServer(Class clazz,PropertyDescriptor descriptor){
		Class propertyType=descriptor.getPropertyType();
		String sql=" ";
		int length=BeanUtils.getMaxLength(clazz,descriptor.getName());
		if (String.class.equals(propertyType)) {
			if(length<=0){
				sql=sql.concat("VARCHAR(255)");
			}else if(length>0&&length<8000){
				sql=sql.concat("VARCHAR(".concat(""+length)).concat(")");
			}else{
				sql=sql.concat("TEXT");
			}
		}else if (boolean.class.equals(propertyType) || Boolean.class.equals(propertyType)) {
			sql=sql.concat("INT");
		}else if (byte.class.equals(propertyType) || Byte.class.equals(propertyType)) {
			sql=sql.concat("INT");
		}else if (short.class.equals(propertyType) || Short.class.equals(propertyType)) {
			//...
		}else if (int.class.equals(propertyType) || Integer.class.equals(propertyType)) {
			if(length>0){
				sql=sql.concat("INT");
			}else{
				sql=sql.concat("INT");
			}
		}else if (long.class.equals(propertyType) || Long.class.equals(propertyType)){
			if(length>0){
				sql=sql.concat("INT");
			}else{
				sql=sql.concat("INT");
			}
		}else if (float.class.equals(propertyType) || Float.class.equals(propertyType)) {
			if(length>0){
				sql=sql.concat("NUMERIC(".concat(""+length)).concat(",5)");
			}else{
				sql=sql.concat("NUMERIC(16,5)");
			}
		}else if (double.class.equals(propertyType) || Double.class.equals(propertyType) || Number.class.equals(propertyType)) {
			if(length>0){
				sql=sql.concat("NUMERIC(".concat(""+length)).concat(",8)");
			}else{
				sql=sql.concat("NUMERIC(24,8)");
			}
		}else if (byte[].class.equals(propertyType)) {
			sql=sql.concat("IMAGE");
		}else if (java.sql.Date.class.equals(propertyType)) {
			sql=sql.concat("DATETIME");
		}else if (java.sql.Time.class.equals(propertyType)) {
			sql=sql.concat("DATETIME");
		}else if (java.sql.Timestamp.class.equals(propertyType) || java.util.Date.class.equals(propertyType)) {
			sql=sql.concat("DATETIME");
		}else if (java.math.BigDecimal.class.equals(propertyType)) {
			if(length>0){
				sql=sql.concat("DECIMAL(".concat(""+length)).concat(",5)");
			}else{
				sql=sql.concat("DECIMAL(11,5)");
			}
		}else if (java.sql.Blob.class.equals(propertyType)) {
			sql=sql.concat("IMAGE");
		}else if (java.sql.Clob.class.equals(propertyType)) {
			sql=sql.concat("TEXT");
		}
		return sql;
	}
	
	private String buildCreateSqlByOracle(String entity) throws Exception{
		Class clazz=Class.forName(entity);
		logger.info("类型为{}",clazz.getName());
		SimpleEntity simpleEntity=AnnotationUtils.findAnnotation(clazz, SimpleEntity.class);
		Assert.notNull(simpleEntity,clazz.getName().concat("没有符合SimpleEntity注解"));
		String tableName=simpleEntity.tableName();
		String sql="CREATE TABLE ".concat(tableName).concat("( ");
		String pkPropertyName=simpleEntity.pkPropertyName();
		String pkFieldName=BeanUtils.getDbFieldName(pkPropertyName);
		sql=sql.concat(pkFieldName).concat(" NUMBER(11) NOT NULL UNIQUE PRIMARY KEY ,");
		PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(clazz);
		for (int i = 0; i < descriptors.length; i++){
			String propertyName = descriptors[i].getName();
			if(!propertyName.equalsIgnoreCase("class")&&!propertyName.equals(pkPropertyName)&&!BeanUtils.isNotMapping(clazz,propertyName)){
				String fieldName = BeanUtils.getDbFieldName(propertyName);
				sql=sql.concat(" ".concat(fieldName)).concat(getTypeAndLengthByOracle(clazz,descriptors[i]));
				if(BeanUtils.isUnique(clazz,propertyName)){
					sql=sql.concat(" UNIQUE");
				}
				if(BeanUtils.isNotNull(clazz,propertyName)){
					sql=sql.concat(" NOT NULL");
				}
				sql=sql.concat(",");
			}
		}
		sql=sql.substring(0,sql.length()-1).concat(" )");
		return sql;
	}
	
	private String getTypeAndLengthByOracle(Class clazz,PropertyDescriptor descriptor){
		Class propertyType=descriptor.getPropertyType();
		String sql=" ";
		int length=BeanUtils.getMaxLength(clazz,descriptor.getName());
		if (String.class.equals(propertyType)) {
			if(length<=0){
				sql=sql.concat("VARCHAR2(255)");
			}else if(length>0&&length<4000){
				sql=sql.concat("VARCHAR2(".concat(""+length)).concat(")");
			}else{
				sql=sql.concat("LONG(".concat(""+length)).concat(")");
			}
		}else if (boolean.class.equals(propertyType) || Boolean.class.equals(propertyType)) {
			sql=sql.concat("NUMBER(1)");
		}else if (byte.class.equals(propertyType) || Byte.class.equals(propertyType)) {
			sql=sql.concat("NUMBER(1)");
		}else if (short.class.equals(propertyType) || Short.class.equals(propertyType)) {
			//...
		}else if (int.class.equals(propertyType) || Integer.class.equals(propertyType)) {
			if(length>0){
				sql=sql.concat("NUMBER(".concat(""+length)).concat(")");
			}else{
				sql=sql.concat("NUMBER(8)");
			}
		}else if (long.class.equals(propertyType) || Long.class.equals(propertyType)){
			if(length>0){
				sql=sql.concat("NUMBER(".concat(""+length)).concat(")");
			}else{
				sql=sql.concat("NUMBER(16)");
			}
		}else if (float.class.equals(propertyType) || Float.class.equals(propertyType)) {
			if(length>0){
				sql=sql.concat("NUMBER(".concat(""+length)).concat(",5)");
			}else{
				sql=sql.concat("NUMBER(16,5)");
			}
		}else if (double.class.equals(propertyType) || Double.class.equals(propertyType) || Number.class.equals(propertyType)) {
			if(length>0){
				sql=sql.concat("NUMBER(".concat(""+length)).concat(",8)");
			}else{
				sql=sql.concat("NUMBER(24,8)");
			}
		}else if (byte[].class.equals(propertyType)) {
			sql=sql.concat("BLOB");
		}else if (java.sql.Date.class.equals(propertyType)) {
			sql=sql.concat("DATE");
		}else if (java.sql.Time.class.equals(propertyType)) {
			sql=sql.concat("DATE");
		}else if (java.sql.Timestamp.class.equals(propertyType) || java.util.Date.class.equals(propertyType)) {
			sql=sql.concat("DATE");
		}else if (java.math.BigDecimal.class.equals(propertyType)) {
			if(length>0){
				sql=sql.concat("DECIMAL(".concat(""+length)).concat(",5)");
			}else{
				sql=sql.concat("DECIMAL(11,5)");
			}
		}else if (java.sql.Blob.class.equals(propertyType)) {
			sql=sql.concat("BLOB");
		}else if (java.sql.Clob.class.equals(propertyType)) {
			sql=sql.concat("CLOB");
		}
		return sql;
	}
	
	private String buildCreateSqlByHsql(String entity) throws Exception{
		Class clazz=Class.forName(entity);
		logger.info("类型为{}",clazz.getName());
		SimpleEntity simpleEntity=AnnotationUtils.findAnnotation(clazz, SimpleEntity.class);
		Assert.notNull(simpleEntity,clazz.getName().concat("没有符合SimpleEntity注解"));
		String tableName=simpleEntity.tableName();
		String sql="CREATE TABLE ".concat(tableName).concat("( ");
		String pkPropertyName=simpleEntity.pkPropertyName();
		String pkFieldName=BeanUtils.getDbFieldName(pkPropertyName);
		sql=sql.concat(pkFieldName).concat(" LONG NOT NULL IDENTITY PRIMARY KEY ,");
		PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(clazz);
		for (int i = 0; i < descriptors.length; i++){
			String propertyName = descriptors[i].getName();
			if(!propertyName.equalsIgnoreCase("class")&&!propertyName.equals(pkPropertyName)&&!BeanUtils.isNotMapping(clazz,propertyName)){
				String fieldName = BeanUtils.getDbFieldName(propertyName);
				sql=sql.concat(" ".concat(fieldName)).concat(getTypeAndLengthByHsql(clazz,descriptors[i]));
				if(BeanUtils.isNotNull(clazz,propertyName)){
					sql=sql.concat(" NOT NULL");
				}
				sql=sql.concat(",");
			}
		}
		sql=sql.substring(0,sql.length()-1).concat(" )");
		return sql;
	}
	
	private String getTypeAndLengthByHsql(Class clazz,PropertyDescriptor descriptor){
		Class propertyType=descriptor.getPropertyType();
		String sql=" ";
		int length=BeanUtils.getMaxLength(clazz,descriptor.getName());
		if (String.class.equals(propertyType)) {
			if(length<=0){
				sql=sql.concat("VARCHAR(255)");
			}else if(length>0){
				sql=sql.concat("VARCHAR(".concat(""+length)).concat(")");
			}
		}else if (boolean.class.equals(propertyType) || Boolean.class.equals(propertyType)) {
			sql=sql.concat("INT");
		}else if (byte.class.equals(propertyType) || Byte.class.equals(propertyType)) {
			sql=sql.concat("INT");
		}else if (short.class.equals(propertyType) || Short.class.equals(propertyType)) {
			//...
		}else if (int.class.equals(propertyType) || Integer.class.equals(propertyType)) {
			sql=sql.concat("INT");
		}else if (long.class.equals(propertyType) || Long.class.equals(propertyType)){
			sql=sql.concat("LONG");
		}else if (float.class.equals(propertyType) || Float.class.equals(propertyType)) {
			sql=sql.concat("FLOAT");
		}else if (double.class.equals(propertyType) || Double.class.equals(propertyType) || Number.class.equals(propertyType)) {
			sql=sql.concat("DOUBLE");
		}else if (byte[].class.equals(propertyType)) {
			sql=sql.concat("BLOB");
		}else if (java.sql.Date.class.equals(propertyType)) {
			sql=sql.concat("DATE");
		}else if (java.sql.Time.class.equals(propertyType)) {
			sql=sql.concat("TIME");
		}else if (java.sql.Timestamp.class.equals(propertyType) || java.util.Date.class.equals(propertyType)) {
			sql=sql.concat("DATETIME");
		}else if (java.math.BigDecimal.class.equals(propertyType)) {
			sql=sql.concat("DECIMAL");
		}else if (java.sql.Blob.class.equals(propertyType)) {
			sql=sql.concat("BLOB");
		}else if (java.sql.Clob.class.equals(propertyType)) {
			sql=sql.concat("CLOB");
		}
		return sql;
	}
	
	private String buildCreateSqlByMySql(String entity) throws Exception{
		Class clazz=Class.forName(entity);
		logger.info("类型为{}",clazz.getName());
		SimpleEntity simpleEntity=AnnotationUtils.findAnnotation(clazz, SimpleEntity.class);
		Assert.notNull(simpleEntity,clazz.getName().concat("没有符合SimpleEntity注解"));
		String tableName=simpleEntity.tableName();
		String sql="CREATE TABLE ".concat(tableName).concat("( ");
		String pkPropertyName=simpleEntity.pkPropertyName();
		String pkFieldName=BeanUtils.getDbFieldName(pkPropertyName);
		sql=sql.concat(pkFieldName).concat(" INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY ,");
		PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(clazz);
		for (int i = 0; i < descriptors.length; i++){
			String propertyName = descriptors[i].getName();
			if(!propertyName.equalsIgnoreCase("class")&&!propertyName.equals(pkPropertyName)&&!BeanUtils.isNotMapping(clazz,propertyName)){
				String fieldName = BeanUtils.getDbFieldName(propertyName);
				sql=sql.concat(" ".concat(fieldName)).concat(getTypeAndLengthByMysql(clazz,descriptors[i]));
				if(BeanUtils.isUnique(clazz,propertyName)){
					sql=sql.concat(" UNIQUE");
				}
				if(BeanUtils.isNotNull(clazz,propertyName)){
					sql=sql.concat(" NOT NULL");
				}
				sql=sql.concat(",");
			}
		}
		sql=sql.substring(0,sql.length()-1).concat(" )");
		return sql;
	}
	
	private String getTypeAndLengthByMysql(Class clazz,PropertyDescriptor descriptor){
		Class propertyType=descriptor.getPropertyType();
		String sql=" ";
		int length=BeanUtils.getMaxLength(clazz,descriptor.getName());
		if (String.class.equals(propertyType)) {
			if(length<=0){
				sql=sql.concat("VARCHAR(255)");
			}else if(length>0&&length<=4000){
				sql=sql.concat("VARCHAR(".concat(""+length)).concat(")");
			}else if(length>4000){
				sql=sql.concat("TEXT(".concat(""+length)).concat(")");
			}
		}else if (boolean.class.equals(propertyType) || Boolean.class.equals(propertyType)) {
			sql=sql.concat("INT(1)");
		}else if (byte.class.equals(propertyType) || Byte.class.equals(propertyType)) {
			sql=sql.concat("INT(1)");
		}else if (short.class.equals(propertyType) || Short.class.equals(propertyType)) {
			//...
		}else if (int.class.equals(propertyType) || Integer.class.equals(propertyType)) {
			if(length>0&&length<11){
				sql=sql.concat("INT(".concat(""+length)).concat(")");
			}else{
				sql=sql.concat("INT(8)");
			}
		}else if (long.class.equals(propertyType) || Long.class.equals(propertyType)){
			if(length>0){
				sql=sql.concat("BIGINT(".concat(""+length)).concat(")");
			}else{
				sql=sql.concat("BIGINT");
			}
		}else if (float.class.equals(propertyType) || Float.class.equals(propertyType)) {
			if(length>0){
				sql=sql.concat("FLOAT(".concat(""+length)).concat(")");
			}else{
				sql=sql.concat("FLOAT");
			}
		}else if (double.class.equals(propertyType) || Double.class.equals(propertyType) || Number.class.equals(propertyType)) {
			if(length>0){
				sql=sql.concat("DOUBLE(".concat(""+length)).concat(")");
			}else{
				sql=sql.concat("DOUBLE");
			}
		}else if (byte[].class.equals(propertyType)) {
			sql=sql.concat("BLOB");
		}else if (java.sql.Date.class.equals(propertyType)) {
			sql=sql.concat("DATE");
		}else if (java.sql.Time.class.equals(propertyType)) {
			sql=sql.concat("TIME");
		}else if (java.sql.Timestamp.class.equals(propertyType) || java.util.Date.class.equals(propertyType)) {
			sql=sql.concat("DATETIME");
		}else if (java.math.BigDecimal.class.equals(propertyType)) {
			sql=sql.concat("DECIMAL");
		}else if (java.sql.Blob.class.equals(propertyType)) {
			sql=sql.concat("LONGBLOB");
		}else if (java.sql.Clob.class.equals(propertyType)) {
			if(length>0){
				sql=sql.concat("LONGTEXT(".concat(""+length)).concat(")");
			}else{
				sql=sql.concat("LONGTEXT");
			}
		}
		return sql;
	}

	public boolean isAutoCreateTable() {
		return autoCreateTable;
	}

	public void setAutoCreateTable(boolean autoCreateTable) {
		this.autoCreateTable = autoCreateTable;
	}

	public void setSimpleEntitys(List<String> simpleEntitys) {
		this.simpleEntitys = simpleEntitys;
	}

	
}
