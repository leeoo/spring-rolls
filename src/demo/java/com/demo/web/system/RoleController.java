package com.demo.web.system;

import java.util.Collection;
import java.util.Map;

import org.paramecium.commons.JsonUitls;
import org.paramecium.ioc.annotation.AutoInject;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.AuthorizationMenu;
import org.paramecium.security.Resource;
import org.paramecium.security.annotation.Security;

import com.demo.entity.system.Role;
import com.demo.service.system.RoleService;
import com.demo.web.BaseController;
@Security
@ShowLabel("角色管理")
@Controller("/system/role")
public class RoleController extends BaseController{

	@AutoInject
	private RoleService roleService;
	
	@ShowLabel("首页界面")
	@MappingMethod
	public void list(ModelAndView mv){
		mv.forward(getPage("/role/list.jsp"));
	}
	
	@ShowLabel("获取列表数据")
	@MappingMethod
	public void data(ModelAndView mv){
		int pageNo = mv.getValue("page", int.class);
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(20);
		Role role = mv.getBean(Role.class);
		page = roleService.getAll(page,role);
		String json = JsonUitls.getBeansJson(page.getResult(),false);
		json = ("{\"total\":\""+page.getTotalCount()+"\",\"rows\":["+json+"]}");
		mv.printJSON(json);
	}
	
	@ShowLabel("新增及维护界面")
	@MappingMethod
	public ModelAndView input(ModelAndView mv){
		Integer id = mv.getValue("id",Integer.class);
		if(id!=null){
			Role role = roleService.get(id);
			mv.addValue("role", role);
		}
		Map<Resource, Collection<Resource>> resources = AuthorizationMenu.getAuthorMenu();
		mv.addValue("resources", resources);
		return mv.forward(getPage("/role/input.jsp"));
	}
	
	@ShowLabel("保存")
	@MappingMethod
	public ModelAndView save(ModelAndView mv){
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
			mv.setSuccessMessage("操作成功!");
		} catch (Exception e) {
			mv.addValue("role", role);
			Map<Resource, Collection<Resource>> resources = AuthorizationMenu.getAuthorMenu();
			mv.addValue("resources", resources);
			mv.setErrorMessage(e.getMessage());
			return mv.forward(getPage("/role/input.jsp"));
		}
		return mv.redirect(getRedirect("/system/role/list"));
	}
	
	@ShowLabel("删除")
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
