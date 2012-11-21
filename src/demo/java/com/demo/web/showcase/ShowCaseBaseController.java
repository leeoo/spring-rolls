package com.demo.web.showcase;

import org.paramecium.security.annotation.Security;

import com.demo.web.BaseController;

public class ShowCaseBaseController extends BaseController{
	
	private final static String JSP_BASE_DIR = "/WEB-INF/demo/showcase";
	
	@Security(false)
	public String getPage(String jsp){
		return JSP_BASE_DIR.concat(jsp);
	}
	
}
