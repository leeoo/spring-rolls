package com.exam.web.exam;

import java.util.Map;

import org.paramecium.ioc.annotation.AutoInject;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.annotation.Security;

import com.exam.service.exam.ConfigService;
import com.exam.web.BaseController;

@Security
@ShowLabel("系统配置管理")
@Controller("/exam/config")
public class ConfigController extends BaseController{
	
	@AutoInject
	private ConfigService configService;
	
	@ShowLabel("首页界面")
	@MappingMethod
	public void index(ModelAndView mv){
		mv.forward(getExamPage("/config/index.jsp"));
	}
	
	@ShowLabel("保存配置")
	@MappingMethod
	public ModelAndView save(ModelAndView mv){
		int examineeDays = mv.getValue("examineeDays", int.class);
		String themeName = mv.getValue("themeName", String.class);
		String title = mv.getValue("title", String.class);
		Map<String,String> config = configService.getConfig();
		config.put("examineeDays", String.valueOf(examineeDays));
		config.put("themeName", themeName);
		config.put("title", title);
		configService.updateConfig(config);
		return mv.redirect(getRedirect("/exam/config/index"));
	}
	
}
