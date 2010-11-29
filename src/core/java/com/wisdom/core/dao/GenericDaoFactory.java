package com.wisdom.core.dao;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wisdom.core.dao.db2.Db2JdbcDao;
import com.wisdom.core.dao.derby.DerbyJdbcDao;
import com.wisdom.core.dao.h2.H2JdbcDao;
import com.wisdom.core.dao.hsql.HSqlJdbcDao;
import com.wisdom.core.dao.informix.InformixJdbcDao;
import com.wisdom.core.dao.mysql.MySqlJdbcDao;
import com.wisdom.core.dao.oracle.OracleJdbcDao;
import com.wisdom.core.dao.postgresql.PostgreSqlJdbcDao;
import com.wisdom.core.dao.sqlite.SqliteJdbcDao;
import com.wisdom.core.dao.sqlserver.SqlServer2005JdbcDao;
import com.wisdom.core.dao.sqlserver.SqlServerJdbcDao;

/**
 * 功能描述：通用dao工厂分配
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-10-29</b>
 * <br>创建时间：<b>下午03:52:35</b>
 * <br>文件结构：<b>spring:com.wisdom.core.dao/GenericDaoFactory.java</b>
 */
public class GenericDaoFactory {
	
	public static String type;
	private static final Logger logger = LoggerFactory.getLogger(GenericDaoFactory.class);
	
	public static JdbcTemplate getJdbcTemplate(DataSource dataSource) {
		if(type.equals("mysql")){
			return new MySqlJdbcDao(dataSource);
		}else if(type.equals("hsql")){
			return new HSqlJdbcDao(dataSource);
		}else if(type.equals("h2")){
			return new H2JdbcDao(dataSource);
		}else if(type.equals("postgresql")){
			return new PostgreSqlJdbcDao(dataSource);
		}else if(type.equals("sqlite")){
			return new SqliteJdbcDao(dataSource);
		}else if(type.equals("derby")){
			return new DerbyJdbcDao(dataSource);
		}else if(type.equals("oracle")){
			return new OracleJdbcDao(dataSource);
		}else if(type.equals("sqlserver")){
			return new SqlServerJdbcDao(dataSource);
		}else if(type.equals("sqlserver2005")||type.equals("sqlserver2008")){
			return new SqlServer2005JdbcDao(dataSource);
		}else if(type.equals("db2")){
			return new Db2JdbcDao(dataSource);
		}else if(type.equals("informix")){
			return new InformixJdbcDao(dataSource);
		}
		return null;
	}
	
	public void setDbType(String dbType) {
		type = dbType.trim().toLowerCase();
		logger.info("Datasource type is {}",type);
	}

}
