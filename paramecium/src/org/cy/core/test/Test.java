package org.cy.core.test;

import java.util.Date;

import org.cy.core.transaction.TransactionAutoProxy;


public class Test{
	
	public static void main(String[] args) throws Exception {
		ILoggerService loggerService =  (ILoggerService) TransactionAutoProxy.getServiceInstance(LoggerService.class);
		Logger logger = new Logger();
		logger.setInfo("第一个");
		logger.setLogDate(new Date());
		System.out.println(loggerService.save(logger));
		logger.setInfo("第四个");
		System.out.println(loggerService.save(logger));
	}

}
