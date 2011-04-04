package org.cy.core.test;

import java.util.Date;

import org.cy.core.orm.GenericOrmDao;
import org.cy.core.orm.annotation.Column;
import org.cy.core.orm.annotation.Entity;
import org.cy.core.orm.annotation.PrimaryKey;
import org.cy.core.transaction.TransactionManager;
@Entity(tableName="t_logger_info")
public class Test{
	
	@PrimaryKey
	@Column
	private Integer id;
	
	@Column
	private String logInfo;
	
	@Column
	private Date logDate;
	
	public static void main(String[] args) throws Exception {
		TransactionManager.before();
		try{
			GenericOrmDao<Test, Integer> ormDao = new GenericOrmDao<Test, Integer>();
			Test test = new Test();
			test.setId(1);
			test.setLogInfo("abc");
			ormDao.updateNotNull(test);
		}catch (Exception e) {
			TransactionManager.getCurrentTransaction().setException();
		}
		TransactionManager.end();
	}

	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
