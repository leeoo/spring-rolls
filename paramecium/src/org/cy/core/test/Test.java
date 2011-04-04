package org.cy.core.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.cy.core.jdbc.dialect.Page;
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
			GenericOrmDao<Test, Integer> ormDao = new GenericOrmDao<Test, Integer>(Test.class);
			/*Collection<Test> tests = new ArrayList<Test>();
			for(int i=0;i<100;i++){
				Test test = new Test();
				test.setInfo("aaa"+i);
				test.setLogDate(new Date());
				tests.add(test);
			}
			ormDao.insert(tests);*/
			Test test = new Test();
			test.setInfo("aaa4");
			Page page =new Page(5);
			page.setPageNo(3);
			page = ormDao.select(page,test);
			for(Object test1:page.getResult()){
				System.out.println(((Test)test1).getInfo());
			}
		}catch (Exception e) {
			TransactionManager.getCurrentTransaction().setException();
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
