package com.cy.app.test;

import org.cy.core.commons.DateUtils;
import org.cy.core.ioc.ApplicationContext;
import org.cy.core.jdbc.dialect.Page;

import com.cy.app.entity.Logger;
import com.cy.app.service.LoggerService;

public class Test {

	public static void main(String[] args) throws Exception {
		LoggerService loggerService = (LoggerService) ApplicationContext.getBean("loggerService");
		for(int i=0;i<50;i++){
			Logger logger = new Logger();
			logger.setInfo("测试一2下");
			logger.setDate(DateUtils.getCurrentDateTime());
			int id = loggerService.save(logger);
			System.out.println(id);
			logger = loggerService.get(id);
			System.out.println(logger.getInfo());
			System.out.println(logger.getDate());
			logger.setInfo("狗篮子");
			loggerService.update(logger);
			loggerService.delete(id);
			Page page = new Page(5);
			page = loggerService.getAll(page);
		}
		Thread.sleep(2*60*1000);
	}
	
}
