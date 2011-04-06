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
	
	@Column(fieldName="log_info")
	private String info;
	
	@Column
	private Date logDate;
	
	public static void main(String[] args) throws Exception {
		TransactionManager.before();
		try{
			GenericOrmDao<Test, Integer> ormDao1 = new GenericOrmDao<Test, Integer>("ds1",Test.class);
			GenericOrmDao<Test, Integer> ormDao2 = new GenericOrmDao<Test, Integer>("ds2",Test.class);
			Test test = new Test();
			test.setInfo("出错了");
			test.setLogDate(new Date());
			ormDao1.insert(test);
			ormDao2.insert(test);
			int i = 0 / 0;
			System.out.println(i);
		}catch (Exception e) {
			TransactionManager.globalException();
		}
		TransactionManager.end();
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


	public String getInfo() {
		return info;
	}


	public void setInfo(String info) {
		this.info = info;
	}
	
}
