package com.demo.web.system;

import java.util.Collection;
import java.util.HashSet;

import org.paramecium.commons.JsonUitls;
import org.paramecium.ioc.annotation.AutoInject;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.mvc.End;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;

import com.demo.entity.system.Role;
import com.demo.entity.system.User;
import com.demo.service.system.RoleService;
import com.demo.service.system.UserService;
import com.demo.web.BaseController;

@Controller(namespace="/system/user")
public class UserController extends BaseController{
	
	@AutoInject
	private UserService userService;
	
	@AutoInject
	private RoleService roleService;
	
	@MappingMethod
	public void list(ModelAndView mv){
		mv.forward(getPage("/user/list.jsp"));
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
	public End input(ModelAndView mv){
		Integer id = mv.getValue("id",Integer.class);
		if(id!=null){
			User user = userService.get(id);
			mv.addValue("user", user);
		}
		Collection<Role> allRole = roleService.getAll();
		mv.addValue("roles", allRole);
		return mv.forward(getPage("user/input.jsp"));
	}
	
	@MappingMethod
	public End save(ModelAndView mv){
		User user = mv.getBean("user",User.class);
		Collection<String> rolenames = mv.getValues("roles", String.class);
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
		} catch (Exception e) {
			mv.addValue("user", user);
			Collection<Role> allRole = roleService.getAll();
			mv.addValue("roles", allRole);
			return mv.forward(getPage("user/input.jsp"));
		}
		return mv.redirect(getServletExt("/system/user/list"));
	}
	
	@MappingMethod
	public void delete(ModelAndView mv){
		String idstr = mv.getValue("ids",String.class);
		try {
			if(idstr!=null){
				String[] ids = idstr.split(",");
				userService.delete(ids);
			}
		} catch (Exception e) {
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
		} catch (Exception e) {
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
		} catch (Exception e) {
		}
	}

}
