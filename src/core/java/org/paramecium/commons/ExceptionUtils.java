package org.paramecium.commons;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
/**
 * 功能描述(Description):<br><b>
 * 异常工具
 * </b><br>作 者(Author): <i><b>曹阳(Cao.Yang)</b></i>
 * <br>建立日期(Create Date): <b>2011-11-17下午06:39:35</b>
 * <br>项目名称(Project Name): <b>paramecium</b>
 * <br>包及类名(Package Class): <b>org.paramecium.commons.ExceptionUtils.java</b>
 */
public abstract class ExceptionUtils {
	
	public static String getExceptionString(Throwable throwable){
		OutputStream stream = new ByteArrayOutputStream();
		PrintStream print = new PrintStream(stream);
		throwable.printStackTrace(print);
		print.flush();
		print.close();
		try {
			stream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stream.toString();
	}

}
