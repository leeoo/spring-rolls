package com.exam.web.system;

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
@ShowLabel("主题管理")
@Controller("/system/themes")
public class ThemesController extends BaseController{
	
	@AutoInject
	private ConfigService configService;
	
	
	@ShowLabel("主题切换")
	@MappingMethod
	public ModelAndView change(ModelAndView mv){
		String themeName = mv.getValue("themeName", String.class);
		if(themeName!=null){
			Map<String,String> config = configService.getConfig();
			config.put("themeName", themeName);
			configService.updateConfig(config);
		}
		return mv.redirect(getRedirect("/system/index"));
	}
}
