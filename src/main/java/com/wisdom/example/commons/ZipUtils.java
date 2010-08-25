package com.wisdom.example.commons;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能描述：<br>
 * zip压缩文件创建工具
 * <br>代码作者：<b>CaoYang</b>
 * <br>创建日期：<b>2009-8-25</b>
 * <br>创建时间：<b>下午09:55:41</b>
 * <br>文件结构：<b>point-system:com.wisdom.psystem.commons/ZipUtils.java</b>
 */
public class ZipUtils {
	
	protected static final Logger logger = LoggerFactory.getLogger(ZipUtils.class);
	
	public static String createZip(Object[] files,String zipRootPath,String fileName,boolean isDelete){
		String[] strs=new String[files.length];
		for(int i=0;i<files.length;i++){
			strs[i]=files[i].toString();
		}
		return createZip(strs, zipRootPath, fileName, isDelete);
	}
	
	/**
	 * 将已存在文件压缩到一个zip文件中
	 * @param files文件名列表
	 * @param zipRootPath压缩文件的存放根路径
	 * @param fileName文件名，不用写扩展名
	 * @param isDelete压缩成功后是否删除源文件
	 * @return zip路径+文件名
	 */
	public static String createZip(String[] files,String zipRootPath,String fileName,boolean isDelete){
		String zipDir=zipRootPath+"//";//压缩文件存放地点
		String zipName=zipDir+fileName+".zip";//压缩文件名
		File fileDir=new File(zipDir);
		fileDir.mkdir();//建立文件夹
		ZipOutputStream out=null;
		try {
			out = new ZipOutputStream(new FileOutputStream(zipName));
		} catch (FileNotFoundException e) {
			logger.error("Zip file not found:"+e.getMessage());
			return null;
		}
		ZipEntry ze=null;
		File file=null;
		for(String filePath:files){
			file=new File(filePath);
			ze=new ZipEntry(file.getName());
			try {
				out.putNextEntry(ze);
				InputStream is=new BufferedInputStream(new FileInputStream(file));   
				byte[] buffer = new byte[1024];   
		        int len = 0;   
		        while ((len = is.read(buffer)) > 0){   
		        	out.write(buffer , 0 , len);   
		        }
	            is.close();
	            if(isDelete){
	            	file.delete();
	            }
			} catch (IOException e) {
				logger.error("Create zip file error:"+e.getMessage());
			}
		}
		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.error("Zip file close error:"+e.getMessage());
		}
		logger.info("Zip file create success:"+zipName);
		return zipName;
	}
}
