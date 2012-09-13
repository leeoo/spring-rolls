package com.exam.web.system;

import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.annotation.Security;

import com.exam.web.BaseController;

@Security
@ShowLabel("主题管理")
@Controller("/system/themes")
public class ThemesController extends BaseController{
	
	@ShowLabel("主题切换")
	@MappingMethod
	public ModelAndView change(ModelAndView mv){
		String themeName = mv.getValue("themeName", String.class);
		if(themeName!=null){
			THEME_NAME = themeName;
		}
		return mv.redirect(getRedirect("/system/index"));
	}
}
