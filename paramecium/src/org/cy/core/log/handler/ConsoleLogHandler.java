package org.cy.core.log.handler;

import org.cy.core.log.LogHandler;

public class ConsoleLogHandler implements LogHandler{

	public void log(String message) {
		System.out.println(message);
	}

}
