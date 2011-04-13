package org.cy.core.ioc;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.cy.core.commons.PropertiesUitls;

public class ClassScanner {
	
	public static String iocScanBasePackage;
	private static Collection<String> classes = new ArrayList<String>();
	
	static{
		Map<String,String> properties = PropertiesUitls.get("/context.properties");
		iocScanBasePackage = properties.get("iocScanBasePackage");
	}
	
	public static void main(String[] args) throws Exception{
		for(String baesPackage:iocScanBasePackage.split(",")){
			if(baesPackage!=null&&!baesPackage.trim().isEmpty()){
				String baesPath = baesPackage.replaceAll("\\.", "//").trim();
				String filePath = System.class.getResource("/").getFile()+baesPath;
				readClassFile(filePath,baesPackage.trim());
			}
		}
		for(String cp:classes){
			//Class<?> clazz = Class.forName(cp);
			System.out.println(cp);
		}
	}
	
	private static void readClassFile(String currentPath,String classPackage){
		File file = new File(currentPath);
		for(String str : file.list()){
			String currentFile = currentPath.concat("//"+str);
			String currentPackage = classPackage+"."+str;
			if(new File(currentFile).isDirectory()){
				readClassFile(currentFile,currentPackage);
			}else if(str.length()>6&&str.substring(str.length()-6, str.length()).equalsIgnoreCase(".class")){
				classes.add(currentPackage.substring(0, currentPackage.length()-6));
			}
		}
	}
	
}
