package com.demo.web;

public abstract class BaseController {

	public final static String EXT = ".jhtml";
	
	public String getServletExt(String base){
		return base.concat(EXT);
	}
	
}
