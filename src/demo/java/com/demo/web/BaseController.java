package com.demo.web;

import org.paramecium.commons.PathUtils;

public abstract class BaseController {

	public final static String EXT = ".jhtml";
	public final static String JSP_BASE_DIR = "/WEB-INF/demo/system";
	
	public String getServletExt(String base){
		return PathUtils.getNewPath(base.concat(EXT));
	}
	
	public String getPage(String jsp){
		return JSP_BASE_DIR.concat(jsp);
	}
	
}
