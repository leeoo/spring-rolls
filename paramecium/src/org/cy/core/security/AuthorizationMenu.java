package org.cy.core.security;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class AuthorizationMenu{

	private final static ConcurrentMap<Resource, Collection<Resource>> resources = new ConcurrentHashMap<Resource, Collection<Resource>>();
	
	public static void put(Resource resource, Collection<Resource> resources){
		
	}
	
	
	
}
