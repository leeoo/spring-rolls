package com.wisdom.core.utils;

import javax.sql.DataSource;

import org.hsqldb.util.DatabaseManagerSwing;

/**
 * 
 * <b>业务说明</b>:<br>
 * HSQL库图形化
 *<br><b>作  者</b>: Cao.Yang<br>
 * <b>建立日期</b>: Mar 24, 2008<br>
 * <b>建立时间</b>: 12:44:43 PM<br>
 */
public class HsqlUi {
	private DataSource dataSource;

	public void init() throws Exception {
		com.jolbox.bonecp.BoneCPDataSource basicDataSource = (com.jolbox.bonecp.BoneCPDataSource) getDataSource();
		String[] args = new String[] { "--url", basicDataSource.getJdbcUrl(), "--noexit" };
		DatabaseManagerSwing.main(args);
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	
}
