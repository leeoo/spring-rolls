package org.paramecium.log.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.paramecium.log.LogHandler;
import org.paramecium.log.LoggerFactory;
/**
 * 功 能 描 述:<br>
 * 日志写入文件
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-2下午04:08:36
 * <br>项 目 信 息:paramecium:org.paramecium.log.handler.FileLogHandler.java
 */
public class FileLogHandler implements LogHandler {

	public void log(String message, boolean isError) {
		writFile(message);
	}
	
	private static void writFile(String message){
		File file = new File(LoggerFactory.loggerFileName);
		if(!file.exists()){
			int lastLine = 0;
			if(LoggerFactory.loggerFileName.lastIndexOf("/")>lastLine){
				lastLine = LoggerFactory.loggerFileName.lastIndexOf("/");
			}
			if(LoggerFactory.loggerFileName.lastIndexOf("\\")>lastLine){
				lastLine = LoggerFactory.loggerFileName.lastIndexOf("\\");
			}
			File dfile = new File(LoggerFactory.loggerFileName.substring(0,lastLine));
			dfile.mkdir();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}else{
			FileInputStream inputStream = null;
			try {
				inputStream = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return;
			}
			try {
				if(inputStream.available()>LoggerFactory.loggerFileMax*1024*1024){//查看文件容量是否超出额定
					int dotIndex = LoggerFactory.loggerFileName.lastIndexOf(".");
					if(dotIndex<0){
						dotIndex = LoggerFactory.loggerFileName.length();
					}
					String newFileName = new SimpleDateFormat("yyMMdd-HHmmss",java.util.Locale.CHINA).format(new Date());
					String f1 = LoggerFactory.loggerFileName.substring(0,dotIndex);
					String f2 = LoggerFactory.loggerFileName.substring(dotIndex,LoggerFactory.loggerFileName.length());
					LoggerFactory.loggerFileName = f1+newFileName+f2;
					writFile(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(inputStream!=null){
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(file.canWrite()){
			FileWriter writer = null;
			try {
				writer = new FileWriter(file,true);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(writer!=null){
					writer.write(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(writer!=null){
					writer.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(writer!=null){
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
