package org.cy.core.log;

public abstract class LoggerFactory {
	
	public Log getLogger(String name){
		return new Logger();
	}
	
	private class Logger implements Log{

		public void debug(String message) {
			
		}

		
		public void debug(Throwable throwable) {
			
		}

		
		public void error(String message) {
			
		}

		
		public void error(Throwable throwable) {
			
		}

		
		public void fatal(String message) {
			
		}

		public void fatal(Throwable throwable) {
			
		}

		public void info(String message) {
			
		}

		public void info(Throwable throwable) {
			
		}

		public void trace(String message) {
			
		}

		public void trace(Throwable throwable) {
			
		}
		
		public void warn(String message) {
			
		}

		public void warn(Throwable throwable) {
			
		}
		
	}
}
