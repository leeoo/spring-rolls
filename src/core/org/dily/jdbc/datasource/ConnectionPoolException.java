package org.dily.jdbc.datasource;

public class ConnectionPoolException extends RuntimeException{

	private static final long serialVersionUID = 2060615751164567335L;

	public ConnectionPoolException(String message) {
   	 	super(message);
	}
	
}
