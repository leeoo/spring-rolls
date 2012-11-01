package com.demo.web.system;

import org.paramecium.cache.CacheManager;
import org.paramecium.ioc.annotation.ShowLabel;
import org.paramecium.mvc.ModelAndView;
import org.paramecium.mvc.annotation.Controller;
import org.paramecium.mvc.annotation.MappingMethod;
import org.paramecium.security.annotation.Security;

import com.demo.web.BaseController;

@Security
@ShowLabel("缓存监控")
@Controller("/system/cache")
public class CacheController extends BaseController{
	
	@ShowLabel("首页界面")
	@MappingMethod
	public ModelAndView list(ModelAndView mv){
		mv.addValue("caches", CacheManager.getCacheReport());
		return mv.forward(getPage("/cache/list.jsp"));
	}

}
