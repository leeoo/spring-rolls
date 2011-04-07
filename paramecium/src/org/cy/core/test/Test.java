package org.cy.core.test;

import java.util.Date;

import org.cy.core.transaction.TransactionProxy;


public class Test{
	
	public static void main(String[] args) throws Exception {
		ILoggerService loggerService =  (ILoggerService) TransactionProxy.getServiceInstance(LoggerService.class);
		Logger logger = new Logger();
		logger.setInfo("第一个");
		logger.setLogDate(new Date());
		loggerService.save(logger);
		logger.setInfo("第四个");
		loggerService.save(logger);
	}

}
