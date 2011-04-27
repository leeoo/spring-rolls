package org.paramecium.log.handler;

import java.io.File;
import java.io.FileInputStream;
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
		if(isError){
			System.err.println(message);
		}else{
			System.out.println(message);
		}
		writFile(message);
	}
	
	public static void main(String[] args) {
		System.out.println("abcde.log".substring("abcde.log".lastIndexOf("."),"abcde.log".length()));
		System.out.println("abcde.log".substring(0,"abcde.log".lastIndexOf(".")));
	}
	
	public void writFile(String message){
		try {
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
				file.createNewFile();
			}else{
				FileInputStream inputStream = new FileInputStream(file);
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
				inputStream.close();
			}
			if(file.canWrite()){
				FileWriter writer = new FileWriter(file,true);
				writer.write(message);
				writer.flush();
				writer.close();
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

}
