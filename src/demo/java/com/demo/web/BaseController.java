package com.demo.web;

public abstract class BaseController {

	final static String EXT = ".jhtml";
	
	public String getServletExt(String base){
		return base.concat(EXT);
	}
	
}
