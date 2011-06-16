package org.paramecium.commons;


public class PathUtils {
	
	public static String webClassRootPath = null;

	public static String getClassRootPath(){
		if(webClassRootPath!=null){
			return webClassRootPath;
		}
		return PathUtils.class.getResource("//").getFile();
	}
	
	public static String getClassFile(String fileName){
		if(webClassRootPath!=null){
			return webClassRootPath+fileName;
		}
		return PathUtils.class.getResource("//").getFile()+fileName;
	}

}
