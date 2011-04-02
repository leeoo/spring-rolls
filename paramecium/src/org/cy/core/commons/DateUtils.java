package org.cy.core.commons;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 功 能 描 述:<br>
 * 日期时间工具类
 * <br>代 码 作 者:曹阳(CaoYang)
 * <br>开 发 日 期:2011-4-2下午03:55:50
 * <br>项 目 信 息:paramecium:org.cy.core.commons.DateUtils.java
 */
public abstract class DateUtils {
	
	public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd",java.util.Locale.CHINA);
	public static final DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss",java.util.Locale.CHINA);	
	public static final DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",java.util.Locale.CHINA);
	
	/**
	 * 获得当前日期时间
	 * @return 返回当前时间
	 */
	public static Date getCurrentDateTime() {
		java.util.Calendar calNow = java.util.Calendar.getInstance();
		java.util.Date dtNow = calNow.getTime();
		return dtNow;
	}
	
	/**
	 * 获得当前日期时间字符
	 * @return
	 */
	public static String getCurrentDateTimeStr(){
		return DATE_TIME_FORMAT.format(getCurrentDateTime());
	}
	
}
