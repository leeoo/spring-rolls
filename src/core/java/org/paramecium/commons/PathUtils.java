package org.paramecium.commons;

import java.io.InputStream;

public class PathUtils {

	public static String getClassRootPath(){
		return PathUtils.class.getResource("/").getFile();
	}
	
	public static String getClassFile(String fileName){
		return PathUtils.class.getResource("/").getFile()+fileName;
	}

	public static InputStream getClassFileStream(String fileName){
		return PathUtils.class.getResourceAsStream(fileName);
	}

}
