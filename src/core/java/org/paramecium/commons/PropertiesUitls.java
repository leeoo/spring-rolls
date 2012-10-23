package org.paramecium.commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
/**
 * 功 能 描 述:<br>
 * 对Properties文件进行信息获取
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-6上午11:45:52
 * <br>项 目 信 息:paramecium:org.paramecium.commons.PropertiesUitls.java
 */
public abstract class PropertiesUitls {
	
	/**
	 * 获取以.为分割的区域类别划分信息，如ds1.username,ds1.url;ds2.username,ds2.url;
	 * @param propertiesName
	 * @return
	 */
	public static Map<String,Map<String,String>> getByType(String propertiesName){
		Map<String,Map<String,String>> map = new HashMap<String, Map<String,String>>();
		Properties properties = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File(PathUtils.getClassFile(propertiesName)));
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String,String> values = null;
		for(Object key : properties.keySet()){
			String property = (String)key;
			int dotIndex = property.indexOf('.');
			String newType = property.substring(0,dotIndex);//类型名
			String value = property.substring(dotIndex+1,property.length());//同类型下的属性名
			String proValue = properties.getProperty(property);//属性值
			values = map.get(newType);
			if(values==null){
				values =new HashMap<String,String>();
			}
			values.put(value, proValue);
			map.put(newType, values);
		}
		if(inputStream!=null){
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		properties.clear();
		return map;
	}
	
	/**
	 * 给普通properties文件使用
	 * @param propertiesName
	 * @return
	 */
	public static Map<String,String> get(String propertiesName){
		Map<String,String> map = new HashMap<String, String>();
		Properties properties = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File(PathUtils.getClassFile(propertiesName)));
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(Object key : properties.keySet()){
			String property = (String)key;
			String proValue = properties.getProperty(property);
			map.put(property, proValue);
		}
		if(inputStream!=null){
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		properties.clear();
		return map;
	}
	
	/**
	 * 为properties设置值
	 * @param propertiesName
	 * @param key
	 * @param value
	 */
	public static void set(String propertiesName,String key,String value){
		Properties properties = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File(PathUtils.getClassFile(propertiesName)));
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		properties.setProperty(key, value);
		if(inputStream!=null){
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(PathUtils.getClassFile(propertiesName));
			properties.store(outputStream,"PARAMECIUM_PROPERTIES");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(outputStream!=null){
			try {
				outputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		properties.clear();
	}
	
	/**
	 * 为properties设置值
	 * @param propertiesName
	 * @param values
	 */
	public static void set(String propertiesName,Map<String,String> values){
		Properties properties = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File(PathUtils.getClassFile(propertiesName)));
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(String key : values.keySet()){
			String value = values.get(key);
			properties.setProperty(key, value);
		}
		if(inputStream!=null){
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(PathUtils.getClassFile(propertiesName));
			properties.store(outputStream,"PARAMECIUM_PROPERTIES");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(outputStream!=null){
			try {
				outputStream.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		properties.clear();
	}
	
}
