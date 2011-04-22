package org.cy.core.security;


public class Security {
	
	private final static ThreadLocal<String> transactionThreadLocal = new ThreadLocal<String>();
	
	public static void put(String securityUser){
		transactionThreadLocal.set(securityUser);
	}
	
	public static String get(){
		return transactionThreadLocal.get();
	}
	
	public static void remove(){
		transactionThreadLocal.remove();
	}
	
}
