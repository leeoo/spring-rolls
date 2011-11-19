package org.paramecium.commons;

import org.paramecium.ioc.ApplicationContextListener;

/**
 * 功 能 描 述:<br>
 * 获得系统路径工具
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-9-30上午09:59:05
 * <br>项 目 信 息:paramecium:org.paramecium.commons.PathUtils.java
 */
public abstract class PathUtils {
	
	public static String webClassRootPath = null;

	private final static String P = "/";
	
	public static String getClassRootPath(){
		if(webClassRootPath!=null){
			return webClassRootPath;
		}
		return System.class.getResource(P).getFile();
	}
	
	public static String getClassFile(String fileName){
		return getClassRootPath()+fileName;
	}
	
	/**
	 * url带项目名的相对地址请求
	 * @param rePath
	 * @return
	 */
	public static String getNewPath(String rePath){
		return ApplicationContextListener.getPath().concat(rePath);
	}

}
