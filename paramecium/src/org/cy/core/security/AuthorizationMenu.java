package org.cy.core.security;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class AuthorizationMenu{

	private final static ConcurrentMap<Resource, Resource> resources = new ConcurrentHashMap<Resource, Resource>();
	
	
}
