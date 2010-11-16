package com.wisdom.core.test.security;

import java.util.Collection;

import com.wisdom.core.security.domain.Resource;
import com.wisdom.core.security.domain.Role;
import com.wisdom.core.security.domain.User;
import com.wisdom.core.test.SpringTestCase;
import com.wisdom.example.service.security.SecurityService;

public class UserServiceTestCase extends SpringTestCase{
	
	@javax.annotation.Resource
	private SecurityService securityService;
	
	public void testSaveResource()throws Exception{
		Resource resource=new Resource();
		resource.setName("RES_EXAMPLE_ALL");
		resource.setCnname("所有资源");
		resource.setPath("/example/**");
		securityService.saveResource(resource);
	}
	
	public void testSaveRole()throws Exception{
		Collection<Resource> resources=securityService.getAllResources();
		Role role=new Role();
		role.setName("ADMIN");
		role.setCnname("管理员");
		role.setResources(resources);
		securityService.saveRole(role);
	}
	
	public void testSaveUser()throws Exception{
		Collection<Role> roles=securityService.getAllRoles();
		User user=new User();
		user.setUsername("admin");
		user.setPassword("admin");
		user.setCnname("系统管理员");
		user.setEmail("admin@ow.com");
		user.setRoles(roles);
		securityService.saveUser(user);
	}
	
	public void testLookData()throws Exception{
		//Thread.sleep(100000);
	}

}
