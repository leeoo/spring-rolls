package com.wisdom.core.test.security;

import java.util.List;

import com.wisdom.core.security.domain.Resource;
import com.wisdom.core.security.domain.Role;
import com.wisdom.core.security.domain.User;
import com.wisdom.core.security.service.UserService;
import com.wisdom.core.test.SpringTestCase;

public class UserServiceTestCase extends SpringTestCase{
	
	@javax.annotation.Resource
	private UserService userService;
	
	public void testSaveResource()throws Exception{
		Resource resource=new Resource();
		resource.setName("RES_EXAMPLE_USER");
		resource.setCnname("账户管理");
		resource.setPath("/example/user/**");
		userService.saveResource(resource);
		Resource resourc2=new Resource();
		resourc2.setName("RES_EXAMPLE_ROLE");
		resourc2.setCnname("角色管理");
		resourc2.setPath("/example/role/**");
		userService.saveResource(resourc2);
		Resource resourc3=new Resource();
		resourc3.setName("RES_EXAMPLE_RESOURCE");
		resourc3.setCnname("权限管理");
		resourc3.setPath("/example/resource/**");
		userService.saveResource(resourc3);
		Resource resourc4=new Resource();
		resourc4.setName("RES_EXAMPLE_TEMPLATE");
		resourc4.setCnname("模板管理");
		resourc4.setPath("/example/template/**");
		userService.saveResource(resourc4);
		Resource resourc5=new Resource();
		resourc5.setName("RES_EXAMPLE_TASK");
		resourc5.setCnname("任务调度");
		resourc5.setPath("/example/task/**");
		userService.saveResource(resourc5);
		Resource resourc6=new Resource();
		resourc6.setName("RES_EXAMPLE_REPORT");
		resourc6.setCnname("报表统计");
		resourc6.setPath("/example/report/**");
		userService.saveResource(resourc6);
		Resource resourc7=new Resource();
		resourc7.setName("RES_EXAMPLE_SEARCH");
		resourc7.setCnname("搜索引擎");
		resourc7.setPath("/example/search/**");
		userService.saveResource(resourc7);
		Resource resourc8=new Resource();
		resourc8.setName("RES_EXAMPLE_LOGGER");
		resourc8.setCnname("系统日志");
		resourc8.setPath("/example/logger/**");
		userService.saveResource(resourc8);
		Resource resourc9=new Resource();
		resourc9.setName("RES_EXAMPLE_INDEX");
		resourc9.setCnname("演示首页");
		resourc9.setPath("/example/index.htm");
		userService.saveResource(resourc9);
	}
	
	public void testSaveRole()throws Exception{
		List<Resource> resources=userService.getAllResources();
		Role role=new Role();
		role.setName("ADMIN");
		role.setCnname("管理员");
		role.setResources(resources);
		userService.saveRole(role);
	}
	
	public void testSaveUser()throws Exception{
		List<Role> roles=userService.getAllRoles();
		User user=new User();
		user.setUsername("admin");
		user.setPassword("admin");
		user.setCnname("系统管理员");
		user.setEmail("admin@ow.com");
		user.setRoles(roles);
		userService.saveUser(user);
	}
	
	public void testLookData()throws Exception{
		//Thread.sleep(100000);
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
