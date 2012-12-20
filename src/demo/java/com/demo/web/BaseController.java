package com.demo.web;

import java.text.DateFormat;

import org.dily.commons.JsonUitls;
import org.dily.jdbc.dialect.Page;


public abstract class BaseController {

	public final static String EXT = ".jhtml";
	public static String THEME = "gray";
	public final static String JSP_BASE_DIR = "/WEB-INF/demo/system";
	
	public String getRedirect(String base){
		return base.concat(EXT);
	}
	
	public String getPage(String jsp){
		return JSP_BASE_DIR.concat(jsp);
	}
	
	public String getJsonPageData(Page page){
		return getJsonPageData(page, null);
	}
	
	public String getJsonPageData(Page page,DateFormat format){
		String json = null;
		if(format==null){
			json = JsonUitls.getBeansJson(page.getResult(),false);
		}else{
			json = JsonUitls.getBeansJson(page.getResult(),false,format);
		}
		json = ("{\"total\":\""+page.getTotalCount()+"\",\"rows\":["+json+"]}");
		return json;
	}
	
}
