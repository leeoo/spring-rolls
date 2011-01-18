package com.wisdom.core.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.springframework.util.Assert;


/**
 * 
 * <b>业务说明</b>:<br>
 * 日期工具类
 *<br><b>作  者</b>: Cao.Yang<br>
 * <b>建立日期</b>: May 14, 2008<br>
 * <b>建立时间</b>: 2:49:51 PM<br>
 */
public abstract class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    
	/**
	 * 返回当前时间
	 * 
	 * @return 返回当前时间
	 */
	public static Date getCurrentDateTime() {
		java.util.Calendar calNow = java.util.Calendar.getInstance();
		java.util.Date dtNow = calNow.getTime();
		return dtNow;
	}

	
    /**
     * @return 返回今天日期，时间部分为0。例如：2006-4-8 00:00:00
     */
    public static Date getToday() {
        return truncate(new Date(), Calendar.DATE);
    }
    
    /**
     * @return 返回今天日期，时间部分为23:59:59.999。例如：2006-4-19 23:59:59.999
     */
    public static Date getTodayEnd() {
        return getDayEnd(new Date());
    }
    
    /**
     * 将字符串转换为日期（不包括时间）
     * @param dateString "yyyy-MM-dd"格式的日期字符串
     * @return 日期
     */
    public static Date convertToDate(String dateString) {
		try{
			return FormatConstants.DATE_FORMAT.parse(dateString);
		}catch (ParseException e) {
		    return null;
		}
    }
    
    /**
     * 检查字符串是否为日期格式yyyy-MM-dd
     * @param dateString
     * @return true=是；false=否
     */
    public static boolean checkDateString(String dateString) {
        return (convertToDate(dateString)!=null);
    }
    
    /**
     * 将字符串转换为日期（包括时间）
     * @param dateString "yyyy-MM-dd HH:mm:ss"格式的日期字符串
     * @return 日期时间
     */
    public static Date convertToDateTime(String dateTimeString) {
		try{
			return FormatConstants.DATE_TIME_FORMAT.parse(dateTimeString);
		}catch (ParseException e) {
		    return null;
		}
    }
    
    /**
     * 检查字符串是否为日期时间格式yyyy-MM-dd HH:mm:ss
     * @param dateString
     * @return true=是；false=否
     */
    public static boolean checkDateTimeString(String dateTimeString) {
        return (convertToDateTime(dateTimeString)!=null);
    }
    
    /**
     * 获取月底
     * @param year 年 4位年度
     * @param month 月 1~12
     * @return 月底的Date对象。例如：2006-3-31 23:59:59.999
     */
    public static Date getMonthEnd(int year, int month) {
        StringBuffer sb = new StringBuffer(10);
        Date date;
        if (month<12) {
            sb.append(Integer.toString(year));
            sb.append("-");
            sb.append(month+1);
            sb.append("-1");
            date = convertToDate(sb.toString());
        }else{
            sb.append(Integer.toString(year+1));
            sb.append("-1-1");
            date = convertToDate(sb.toString());
        }
        date.setTime(date.getTime() - 1);
        return date;
    }
    
    /**
     * 获取月底
     * @param when 要计算月底的日期
     * @return 月底的Date对象。例如：2006-3-31 23:59:59.999
     */
    public static Date getMonthEnd(Date when) {
        Assert.notNull(when,"date must not be null !");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(when);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        return getMonthEnd(year,month);
    }
    
    /**
     * 获取给定日的最后一刻。
     * @param when 给定日
     * @return 最后一刻。例如：2006-4-19 23:59:59.999
     */
    public static Date getDayEnd(Date when) {
        Date date = truncate(when,Calendar.DATE);
        date = addDays(date,1);
        date.setTime(date.getTime() - 1);
        return date;
    }
    
    /**
     * 获取给定日的第一刻。
     * @param when 给定日
     * @return 第一刻。例如：2006-4-19 00:00:01
     */
    public static Date getDay(Date when) {
        Date date = truncate(when,Calendar.DATE);
        date = addDays(date,-1);
        date.setTime(date.getTime() + 1);
        return date;
    }

    /**
     * 日期加法
     * @param when 被计算的日期
     * @param field the time field. 在Calendar中定义的常数，例如Calendar.DATE
     * @param amount 加数
     * @return 计算后的日期
     */
    public static Date add(Date when, int field, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(when);
        calendar.add(field,amount);
        return calendar.getTime();
    }
    
    /**
     * 计算给定的日期加上给定的天数。
     * @param when 给定的日期
     * @param amount 给定的天数
     * @return 计算后的日期
     */
    public static Date addDays(Date when, int amount) {
        return add(when,Calendar.DAY_OF_YEAR,amount);
    }
    
    /**
     * 计算给定的日期加上给定的月数。
     * @param when 给定的日期
     * @param amount 给定的月数
     * @return 计算后的日期
     */
    public static Date addMonths(Date when, int amount) {
        return add(when,Calendar.MONTH,amount);
    }

}
