package com.cy.app.test;

import org.cy.core.ioc.ApplicationContext;
import org.cy.core.jdbc.dialect.Page;

import com.cy.app.entity.Logger;
import com.cy.app.service.LoggerService;

public class Test {

	public static void main(String[] args) throws Exception {
		LoggerService loggerService = (LoggerService) ApplicationContext.getBean("loggerService");
		/*Logger logger = new Logger();
		logger.setInfo("测试一2下");
		logger.setDate(DateUtils.getCurrentDateTime());
		int id = loggerService.save(logger);
		System.out.println(id);
		logger = loggerService.get(4);
		System.out.println(logger.getInfo());
		System.out.println(logger.getDate());
		logger.setInfo("狗篮子");
		loggerService.update(logger);
		loggerService.delete(4);*/
		Page page = new Page(5);
		page = loggerService.getAll(page);
		for(Object logger : page.getResult()){
			System.out.println(((Logger)logger).getInfo());
		}
	}
	
}
