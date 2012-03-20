package com.demo.web.system;

import java.util.Map;

import org.paramecium.ioc.annotation.AutoInject;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.annotation.Security;

import com.demo.service.system.DataSourceService;
import com.demo.web.BaseController;

@Security
@ShowLabel("数据源配置")
@Controller("/system/ds")
public class DataSourceController extends BaseController{
	
	private final static Log logger = LoggerFactory.getLogger();
	
	@AutoInject
	private DataSourceService dataSourceService;
	
	@ShowLabel("首页界面")
	@MappingMethod
	public ModelAndView list(ModelAndView mv){
		mv.addValue("dataSources", dataSourceService.getDataSources());
		return mv.forward(getPage("/ds/list.jsp"));
	}
	
	@ShowLabel("保存配置")
	@MappingMethod
	public ModelAndView save(ModelAndView mv){
		try{
			String dsName = mv.getValue("dsName",String.class);
			Map<String, Object> map = mv.getMap(dsName);
			dataSourceService.putDefaultDataSource(dsName, map);
			mv.setSuccessMessage("保存成功!");
		}catch (Exception e) {
			logger.error(e);
			mv.setErrorMessage("保存失败!");
		}
		return mv.redirect(getRedirect("/system/ds/list"));
	}

}
