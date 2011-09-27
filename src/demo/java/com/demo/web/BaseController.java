package com.demo.web;

public abstract class BaseController {

	public final static String EXT = ".jhtml";
	public final static String JSP_BASE_DIR = "/WEB-INF/demo/system";
	
	public String getServletExt(String base){
		return base.concat(EXT);
	}
	
	public String getPage(String jsp){
		return JSP_BASE_DIR.concat(jsp);
	}
	
}
