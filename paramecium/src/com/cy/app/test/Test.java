package com.cy.app.test;

import org.cy.core.commons.DateUtils;
import org.cy.core.ioc.ApplicationContext;
import org.cy.core.jdbc.dialect.Page;

import com.cy.app.entity.Logger;
import com.cy.app.service.LoggerService;

public class Test {

	/**
	 * 模拟1000个用户高并发循环操作(增删改查分页)数据库
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		for(int i=0;i<1000;i++){
			new Thread(new PoolThread()).start();
		}
	}
	
	private static class PoolThread implements Runnable {
		
		public void run() {
			while (true) {
				try{
					LoggerService loggerService = (LoggerService) ApplicationContext.getBean("loggerService");
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
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
