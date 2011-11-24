package com.demo.web;

import org.paramecium.security.annotation.Security;


public abstract class BaseController {

	public final static String EXT = ".jhtml";
	public final static String JSP_BASE_DIR = "/WEB-INF/demo/system";
	
	@Security(false)
	public String getRedirect(String base){
		return base.concat(EXT);
	}
	
	@Security(false)
	public String getPage(String jsp){
		return JSP_BASE_DIR.concat(jsp);
	}
	
}
