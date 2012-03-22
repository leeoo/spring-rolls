package com.demo.web.system.log;

import org.paramecium.commons.JsonUitls;
import org.paramecium.ioc.annotation.AutoInject;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.annotation.Security;

import com.demo.entity.system.Log;
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
	public ModelAndView list(ModelAndView mv){
		return mv.forward(getPage("/log/error/list.jsp"));
	}
	
	@ShowLabel("获取列表数据")
	@MappingMethod
	public void data(ModelAndView mv){
		int pageNo = mv.getValue("page", int.class);
		Page page = new Page();
		page.setPageNo(pageNo);
		page.setPageSize(20);
		Log log = mv.getBean(Log.class);
		log.setType(0);
		page = logService.getAll(page,log);
		String json = JsonUitls.getBeansJson(page.getResult(),false);
		json = ("{\"total\":\""+page.getTotalCount()+"\",\"rows\":["+json+"]}");
		mv.printJSON(json);
	}
	
	@ShowLabel("日志详情")
	@MappingMethod
	public ModelAndView detail(ModelAndView mv){
		Integer id = mv.getValue("id", int.class);
		if(id!=null){
			Log log = logService.get(id);
			if(log!=null){
				mv.addValue("log",log.getLog());
			}
			return mv.forward(getPage("/log/detail.jsp"));
		}
		return mv.redirect(getRedirect("/system/log/error"));
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
