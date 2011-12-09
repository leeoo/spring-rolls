package com.demo.web.system.log;

import org.paramecium.commons.JsonUitls;
import org.paramecium.ioc.annotation.AutoInject;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.annotation.Security;

import com.demo.service.system.LogService;
import com.demo.web.BaseController;

@Security
@ShowLabel("错误日志")
@Controller("/system/log/error")
public class ErrorLogController extends BaseController{
	
	@AutoInject
	private LogService logService;
	
	@ShowLabel("首页界面")
	@MappingMethod
	public void list(ModelAndView mv){
		mv.forward(getPage("/log/error/list.jsp"));
	}
	
	@ShowLabel("获取列表数据")
	@MappingMethod
	public void data(ModelAndView mv){
		int pageNo = mv.getValue("page", int.class);
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(20);
		page = logService.getAll(page,0);
		String json = JsonUitls.getBeansJson(page.getResult(),false);
		json = JsonUitls.Html2Text(json);
		json = ("{\"total\":\""+page.getTotalCount()+"\",\"rows\":["+json+"]}");
		mv.printJSON(json);
	}
	
	@ShowLabel("删除")
	@MappingMethod
	public void delete(ModelAndView mv){
		String idstr = mv.getValue("ids",String.class);
		try {
			if(idstr!=null){
				String[] ids = idstr.split(",");
				logService.delete(ids);
			}
		} catch (Exception e) {
		}
	}

}
