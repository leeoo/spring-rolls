package org.paramecium.commons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.paramecium.log.Log;
import org.paramecium.log.LoggerFactory;

import com.sun.xml.internal.ws.util.UtilException;

/**
 * 功 能 描 述:<br>
 * 命令行工具
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-11-18上午09:40:48
 * <br>项 目 信 息:paramecium:org.paramecium.commons.CommandUtils.java
 */
public class CommandUtils {
	
	private final static Log logger = LoggerFactory.getLogger();
	
	/**
	 * 执行命令
	 * @param cmd
	 * @return
	 */
	public static Process run(String cmd) {
		try {
			return Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			logger.error(e);
		}
		return null;
	}


	/**
	 * 执行同步Shell命令并返回执行结果
	 * 
	 * @param cmd
	 *            命令行
	 * @return 返回执行结果
	 * @throws UtilException
	 */
	public static String getSynchRunResult(String cmd) {
		StringBuilder sb = new StringBuilder();
		InputStream in = run(cmd).getInputStream();
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader reader = new BufferedReader(isr);
		try {
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			logger.error(e);
		}
		try {
			if(in!=null){
				in.close();
			}
		} catch (IOException e) {
			logger.error(e);
		}
		try {
			if(isr!=null){
				isr.close();
			}
		} catch (IOException e) {
			logger.error(e);
		}
		try {
			if(reader!=null){
				reader.close();
			}
		} catch (IOException e) {
			logger.error(e);
		}
		return sb.toString();
	}


	/**
	 * 检测程序。
	 * 
	 * @param processName
	 *            线程的名字，请使用准确的名字
	 * @return 找到返回true,没找到返回false
	 */
	public static boolean findProcess(String processName) {
		BufferedReader bufferedReader = null;
		try {
			Process proc = run("tasklist /FI \"IMAGENAME eq "+ processName + "\"");
			bufferedReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.contains(processName)) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			logger.error(e);
		}
		if (bufferedReader != null) {
			try {
				bufferedReader.close();
			} catch (Exception e) {
				logger.error(e);
			}
		}
		return false;
	}
	
}
