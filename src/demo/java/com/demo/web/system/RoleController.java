package com.demo.web.system;

import java.util.Collection;
import java.util.Map;

import org.paramecium.commons.JsonUitls;
import org.paramecium.ioc.annotation.AutoInject;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.mvc.End;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.AuthorizationMenu;
import org.paramecium.security.Resource;

import com.demo.entity.system.Role;
import com.demo.service.system.RoleService;
import com.demo.web.BaseController;

@Controller(namespace="/system/role")
public class RoleController extends BaseController{

	@AutoInject
	private RoleService roleService;
	
	@MappingMethod
	public void list(ModelAndView mv){
		mv.forward(getPage("role/list.jsp"));
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
	public End input(ModelAndView mv){
		Integer id = mv.getValue("id",Integer.class);
		if(id!=null){
			Role role = roleService.get(id);
			mv.addValue("role", role);
		}
		Map<Resource, Collection<Resource>> resources = AuthorizationMenu.getAuthorMenu();
		mv.addValue("resources", resources);
		return mv.forward(getPage("/role/input.jsp"));
	}
	
	@MappingMethod
	public End save(ModelAndView mv){
		Role role = mv.getBean("role",Role.class);
		Collection<String> auth = mv.getValues("auth", String.class);
		if(role!=null){
			role.setAuth(auth);
		}
		try {
			if(role.getId()==null){
				roleService.save(role);
			}else{
				roleService.update(role);
			}
		} catch (Exception e) {
			mv.addValue("role", role);
			Map<Resource, Collection<Resource>> resources = AuthorizationMenu.getAuthorMenu();
			mv.addValue("resources", resources);
			return mv.forward(getPage("/role/input.jsp"));
		}
		return mv.redirect(getServletExt("/system/role/list"));
	}
	
	@MappingMethod
	public void delete(ModelAndView mv){
		String idstr = mv.getValue("ids",String.class);
		try {
			if(idstr!=null){
				String[] ids = idstr.split(",");
				roleService.delete(ids);
			}
		} catch (Exception e) {
		}
	}
	
}
