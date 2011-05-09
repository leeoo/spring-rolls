package com.demo.test;

import org.paramecium.commons.DateUtils;
import org.paramecium.ioc.ApplicationContext;
import org.paramecium.jdbc.dialect.Page;

import com.demo.entity.Logger;
import com.demo.service.LoggerService;

public class Test {

	/**
	 * 模拟1000个用户高并发循环操作(增删改查分页)数据库
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		/*for(int i=0;i<1000;i++){
			new Thread(new PoolThread()).start();
		}*/
		/*Connection connection = MultiDataSourceFactory.getDataSource("ds1").getConnection();
		Statement st=  connection.createStatement();
		st.execute("CREATE TABLE t_logger_info(id INT PRIMARY KEY AUTO_INCREMENT, log_info VARCHAR(255) , log_date TIMESTAMP)");*/
	}
	
	static class PoolThread implements Runnable {
		
		public void run() {
			while (true) {
				try{
					LoggerService loggerService = (LoggerService) ApplicationContext.getNotSecurityBean("loggerService");
					Logger logger = new Logger();
					logger.setInfo("测试一2下");
					logger.setDate(DateUtils.getCurrentDateTime());
					int id = loggerService.save(logger);
					logger = loggerService.get(id);
					logger.setInfo("狗篮子");
					loggerService.update(logger);
					loggerService.delete(id);
					Page page = new Page(5);
					page = loggerService.getAll(page);
					ApplicationContext.destroy();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
