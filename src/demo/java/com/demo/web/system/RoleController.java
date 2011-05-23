package com.demo.web.system;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;

import org.paramecium.commons.JsonUitls;
import org.paramecium.ioc.annotation.AutoInject;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.AuthorizationMenu;
import org.paramecium.security.Resource;

import com.demo.entity.system.Role;
import com.demo.service.system.RoleService;

@Controller(namespace="/system/role")
public class RoleController {

	@AutoInject
	private RoleService roleService;
	
	@MappingMethod
	public void list(ModelAndView mv){
		mv.forward("/WEB-INF/demo/system/role/list.jsp");
	}
	
	@MappingMethod
	public void data(ModelAndView mv){
		int pageNo = mv.getValue("page", Integer.class);
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(20);
		page = roleService.getAll(page);
		String json = JsonUitls.getBeansJson(page.getResult(),false);
		json = ("{\"total\":\""+page.getTotalCount()+"\",\"rows\":["+json+"]}");
		mv.printJSON(json);
	}
	
	@MappingMethod
	public void input(ModelAndView mv){
		Integer id = mv.getValue("id",Integer.class);
		if(id!=null){
			Role role = roleService.get(id);
			mv.addValue("role", role);
		}
		Map<Resource, Collection<Resource>> resources = AuthorizationMenu.getAuthorMenu();
		mv.addValue("resources", resources);
		mv.forward("/WEB-INF/demo/system/role/input.jsp");
	}
	
	@SuppressWarnings("unchecked")
	@MappingMethod
	public void save(ModelAndView mv){
		Role role = mv.getBean("role",Role.class);
		Collection<String> auth = (Collection<String>) mv.getValues("auth", String.class);
		if(role!=null){
			role.setAuth(auth);
		}
		try {
			if(role.getId()==null){
				roleService.save(role);
			}else{
				roleService.update(role);
			}
		} catch (SQLException e) {
			mv.addValue("role", role);
			Map<Resource, Collection<Resource>> resources = AuthorizationMenu.getAuthorMenu();
			mv.addValue("resources", resources);
			mv.forward("/WEB-INF/demo/system/role/input.jsp");
			return;
		}
		mv.redirect("/system/role/list.jhtml");
	}
	
	@MappingMethod
	public void delete(ModelAndView mv){
		String idstr = mv.getValue("ids",String.class);
		try {
			if(idstr!=null){
				String[] ids = idstr.split(",");
				roleService.delete(ids);
			}
		} catch (SQLException e) {
		}
	}
	
}
