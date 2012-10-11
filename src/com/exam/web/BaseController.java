package com.exam.web;

import java.text.DateFormat;
import java.util.Collection;
import java.util.Map;

import org.paramecium.commons.JsonUitls;
import org.paramecium.jdbc.dialect.Page;
import org.paramecium.security.annotation.Security;


public abstract class BaseController {

	public final static String EXT = ".jhtml";
	public final static String JSP_BASE_DIR = "/WEB-INF/pages/system";
	public final static String JSP_EXAM_DIR = "/WEB-INF/pages/exam";
	
	@Security(false)
	public String getRedirect(String base){
		return base.concat(EXT);
	}
	
	@Security(false)
	public String getPage(String jsp){
		return JSP_BASE_DIR.concat(jsp);
	}
	
	@Security(false)
	public String getExamPage(String jsp){
		return JSP_EXAM_DIR.concat(jsp);
	}
	
	@Security(false)
	public String getJsonPageData(Page page){
		return getJsonPageData(page, null);
	}
	
	@Security(false)
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
	
	@Security(false)
	public String getJsonPageMapData(Page page){
		@SuppressWarnings("unchecked")
		String json = JsonUitls.getMapsJson((Collection<Map<String, Object>>) page.getResult(),false);
		json = ("{\"total\":\""+page.getTotalCount()+"\",\"rows\":["+json+"]}");
		return json;
	}
	
}
