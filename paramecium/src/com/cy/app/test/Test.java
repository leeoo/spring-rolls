package com.cy.app.test;

import org.cy.core.commons.DateUtils;
import org.cy.core.ioc.ApplicationContext;

import com.cy.app.entity.Logger;
import com.cy.app.service.LoggerService;

public class Test {

	public static void main(String[] args) throws Exception {
		LoggerService loggerService = (LoggerService) ApplicationContext.getBean("loggerService");
		Logger logger = new Logger();
		logger.setInfo("测试一2下");
		logger.setDate(DateUtils.getCurrentDateTime());
		int id = loggerService.save(logger);
		System.out.println(id);
	}
	
}
