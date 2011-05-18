package com.demo.web.system;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;

import org.paramecium.commons.JsonUitls;
import org.paramecium.ioc.annotation.AutoInject;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;

import com.demo.entity.system.Role;
import com.demo.entity.system.User;
import com.demo.service.system.RoleService;
import com.demo.service.system.UserService;

@Controller(namespace="/system/user")
public class UserController {
	
	@AutoInject
	private UserService userService;
	
	@AutoInject
	private RoleService roleService;
	
	@MappingMethod
	public void list(ModelAndView mv){
		mv.forward("/WEB-INF/demo/system/user/list.jsp");
	}
	
	@MappingMethod
	public void data(ModelAndView mv){
		int pageNo = mv.getValue("page", Integer.class);
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(20);
		page = userService.getAll(page);
		String json = JsonUitls.getBeansJson(page.getResult(),false);
		json = ("{\"total\":\""+page.getTotalCount()+"\",\"rows\":["+json+"]}");
		mv.printJSON(json);
	}
	
	@MappingMethod
	public void input(ModelAndView mv){
		Integer id = mv.getValue("id",Integer.class);
		if(id!=null){
			User user = userService.get(id);
			mv.addValue("user", user);
		}
		Collection<Role> allRole = roleService.getAll();
		mv.addValue("roles", allRole);
		mv.forward("/WEB-INF/demo/system/user/input.jsp");
	}
	
	@SuppressWarnings("unchecked")
	@MappingMethod
	public void save(ModelAndView mv){
		User user = mv.getBean("user",User.class);
		Collection<String> rolenames = (Collection<String>) mv.getValues("roles", String.class);
		if(rolenames!=null){
			Collection<Role> roles = new HashSet<Role>();
			for(String rolename : rolenames){
				Role role =new Role();
				role.setRolename(rolename);
				roles.add(role);
			}
			user.setRoles(roles);
		}
		try {
			if(user.getId()==null){
				userService.save(user);
			}else{
				userService.update(user);
			}
		} catch (SQLException e) {
			mv.addValue("user", user);
			Collection<Role> allRole = roleService.getAll();
			mv.addValue("roles", allRole);
			mv.forward("/WEB-INF/demo/system/user/input.jsp");
			return;
		}
		mv.redirect("/system/user/list.jhtml");
	}
	
	@MappingMethod
	public void delete(ModelAndView mv){
		String idstr = mv.getValue("ids",String.class);
		try {
			if(idstr!=null){
				String[] ids = idstr.split(",");
				userService.delete(ids);
			}
		} catch (SQLException e) {
		}
	}
	
	@MappingMethod
	public void disabled(ModelAndView mv){
		String idstr = mv.getValue("ids",String.class);
		try {
			if(idstr!=null){
				String[] ids = idstr.split(",");
				userService.disabled(ids);
			}
		} catch (SQLException e) {
		}
	}

	@MappingMethod
	public void enabled(ModelAndView mv){
		String idstr = mv.getValue("ids",String.class);
		try {
			if(idstr!=null){
				String[] ids = idstr.split(",");
				userService.enabled(ids);
			}
		} catch (SQLException e) {
		}
	}

}
