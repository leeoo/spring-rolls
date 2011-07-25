package org.paramecium.commons;


public class PathUtils {
	
	public static String webClassRootPath = null;

	public static String getClassRootPath(){
		if(webClassRootPath!=null){
			return webClassRootPath;
		}
		return System.class.getResource("/").getFile();
	}
	
	public static String getClassFile(String fileName){
		return getClassRootPath()+fileName;
	}

}
